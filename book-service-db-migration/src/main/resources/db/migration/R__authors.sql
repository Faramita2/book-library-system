CREATE TABLE IF NOT EXISTS `authors` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,
    INDEX `name_index`(`name` ASC)
);

---- rename columns `created_at`, `updated_at`

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'authors' AND COLUMN_NAME = 'created_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE authors RENAME COLUMN created_at TO created_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'authors' AND COLUMN_NAME = 'updated_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE authors RENAME COLUMN updated_at TO updated_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;