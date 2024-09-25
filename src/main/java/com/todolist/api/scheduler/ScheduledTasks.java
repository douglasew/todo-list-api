package com.todolist.api.scheduler;

import com.todolist.api.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduledTasks {

    private final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private TaskService taskService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkTaskStatus(){
        var today = LocalDate.now();
        taskService.checkStatus(today);
        log.info("updated tasks");
    }
}
