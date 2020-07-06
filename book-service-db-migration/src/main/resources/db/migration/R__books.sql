CREATE TABLE IF NOT EXISTS `books` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(1024) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `borrower_id` BIGINT,
    `borrowed_time` TIMESTAMP,
    `return_date` TIMESTAMP,
    `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,

    INDEX `status_index`(`status` ASC),
    INDEX `name_index`(`name` ASC),
    INDEX `description_index`(`description`(255) ASC),
    INDEX `borrower_id_index`(`borrower_id` ASC)
);

---- rename columns `borrowed_at`, `return_at`

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'books' AND COLUMN_NAME = 'borrowed_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE books RENAME COLUMN borrowed_at TO borrowed_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'books' AND COLUMN_NAME = 'return_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE books RENAME COLUMN return_at TO return_date;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;