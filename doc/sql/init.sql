--- ---------------------
--- users Table
--- ---------------------
CREATE TABLE "users" (
    "id" bigserial,
    "username" varchar(255),
    "password" varchar(255),
    "creator" int8,
    "updater" int8,
    "create_time" timestamp(6),
    "update_time" timestamp(6),
    "is_deleted" int2,
    PRIMARY KEY ("id")
);
--- ---------------------
--- Insert Admin User
--- ---------------------
INSERT INTO "users" ("creator", "updater", "create_time", "update_time", "is_deleted", "username", "password") VALUES (NULL, NULL, '2024-01-02 19:54:45.106799', '2024-01-02 19:54:45.106799', 0, 'mingle', '$2a$10$oqINx4KUq0B9.ExpQzl5EOi1NHSElGtdGJe5UITEIc/4vTcixG8U6');

--- ---------------------
--- file Table
--- ---------------------
CREATE TABLE "file" (
    "id" bigserial,
    "name" varchar(255),
    "content_type" varchar(255),
    "size" int8,
    "path" varchar(255),
    "creator" int8,
    "updater" int8,
    "create_time" timestamp(6),
    "update_time" timestamp(6),
    "is_deleted" int2,
    PRIMARY KEY ("id")
);
