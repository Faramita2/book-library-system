CREATE TABLE IF NOT EXISTS `notifications` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `content` VARCHAR(255) NOT NULL,
    `created_time` TIMESTAMP NOT NULL,
    `updated_time` TIMESTAMP NOT NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,

    INDEX `index_user_id`(`user_id`),
    INDEX `index_content`(`content`)
);

---- rename columns `created_at`, `updated_at`

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'notifications' AND COLUMN_NAME = 'created_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE notifications RENAME COLUMN created_at TO created_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'notifications' AND COLUMN_NAME = 'updated_at'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE notifications RENAME COLUMN updated_at TO updated_time;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;