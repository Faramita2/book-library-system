CREATE TABLE IF NOT EXISTS `admins` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `account` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `salt` VARCHAR(50) NOT NULL,
    `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,
    UNIQUE INDEX `account_index`(`account` ASC)
);

---- rename columns `created_at`, `updated_at`

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'admins' AND COLUMN_NAME = 'created_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE admins RENAME COLUMN created_at TO created_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'admins' AND COLUMN_NAME = 'updated_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE admins RENAME COLUMN updated_at TO updated_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;