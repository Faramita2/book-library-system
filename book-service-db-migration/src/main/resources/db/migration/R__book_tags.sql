CREATE TABLE IF NOT EXISTS `book_tags` (
    `book_id` BIGINT NOT NULL,
    `tag_id` BIGINT NOT NULL,
    INDEX `book_index`(`book_id` ASC),
    INDEX `tag_index`(`tag_id` ASC)
);

-- add primary key columns `id`

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'book_tags' AND COLUMN_NAME = 'id'
);

set @sqlStmt := if(
    @columnExisting = 0,
    'ALTER TABLE book_tags ADD COLUMN id BIGINT PRIMARY KEY AUTO_INCREMENT;',
    "SELECT 'INFO: Column exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;