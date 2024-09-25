package com.todolist.api.config;

import com.todolist.api.domain.priority.Priority;

import com.todolist.api.domain.status.Status;
import com.todolist.api.domain.user.User;
import com.todolist.api.repositories.PriorityRepository;
import com.todolist.api.repositories.RoleRepository;
import com.todolist.api.repositories.StatusRepository;
import com.todolist.api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.todolist.api.domain.role.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Configuration
public class DataLoaderConfig implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(DataLoaderConfig.class);

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0) {
            Arrays.stream(Role.Values.values())
                    .map(Role.Values::toRole)
                    .forEach(roleRepository::save);

            log.info("roles were created");
        }

        if (priorityRepository.count() == 0) {
            Arrays.stream(Priority.Values.values())
                    .map(Priority.Values::toPriority)
                    .forEach(priorityRepository::save);

            log.info("priorities were created");
        }

        if (statusRepository.count() == 0) {
            Arrays.stream(Status.Values.values())
                    .map(Status.Values::toStatus)
                    .forEach(statusRepository::save);

            log.info("status were created");
        }

        createAdmin();
    }

    private void createAdmin(){
        if(userRepository.findByUsername("ADM") == null){
            String encryptedPassword = new BCryptPasswordEncoder().encode(adminPassword);

            User admin = new User();

            admin.setUsername("ADM");
            admin.setName("ADM");
            admin.setEmail(adminEmail);
            admin.setRole(Role.Values.ADMIN.toRole());
            admin.setPassword(encryptedPassword);

            userRepository.save(admin);

            log.info("Admin created");
        }
    }
}
