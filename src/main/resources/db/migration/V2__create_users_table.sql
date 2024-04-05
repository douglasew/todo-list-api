CREATE TABLE "users" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL,
    "username" VARCHAR(50) NOT NULL UNIQUE ,
    "email" VARCHAR(100) NOT NULL UNIQUE,
    "photo" TEXT,
    "password" VARCHAR(70) NOT NULL,
    "role_id" SERIAL,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    "created_at" TIMESTAMP,
    "updated_at" TIMESTAMP
);