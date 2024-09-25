CREATE TABLE "tasks" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "title" VARCHAR(50) NOT NULL,
    "description" VARCHAR(180) NOT NULL,
    "priority_id" SERIAL,
    FOREIGN KEY (priority_id) REFERENCES priorities(id),
    "status_id" SERIAL,
    FOREIGN KEY (status_id) REFERENCES status(id),
    "end_at" DATE NOT NULL,
    "user_id" TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    "created_at" TIMESTAMP,
    "updated_at" TIMESTAMP
);
