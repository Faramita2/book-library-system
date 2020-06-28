CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `salt` VARCHAR(50) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,
    UNIQUE INDEX `username_index`(`username` ASC),
    INDEX `email_index`(`email` ASC)
);

-- add column `email` if not exists.
set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'users' AND COLUMN_NAME = 'email'
);

set @sqlStmt := if(
    @columnExisting = 0,
	"ALTER TABLE `users` ADD COLUMN `email` VARCHAR(50) NOT NULL",
    "SELECT 'INFO: Column exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

-- add index `email` if not exists.
set @indexExist := (
    select count(*) from information_schema.STATISTICS where table_name = 'users' and index_name = 'email_index' and table_schema = database()
);
set @sqlStmt := if(
    @indexExist = 0,
    "ALTER TABLE `users` ADD INDEX `email_index` (`email` ASC);",
    "select 'INFO: Index exists.'"
);
PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;