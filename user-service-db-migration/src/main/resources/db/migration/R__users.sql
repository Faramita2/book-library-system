CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `salt` VARCHAR(50) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,
    UNIQUE INDEX `username_index`(`username` ASC),
    INDEX `email_index`(`email` ASC),
    INDEX `status_index`(`status` ASC)
);

---- rename columns `created_at`, `updated_at`

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'users' AND COLUMN_NAME = 'created_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE users RENAME COLUMN created_at TO created_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'users' AND COLUMN_NAME = 'updated_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE users RENAME COLUMN updated_at TO updated_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;