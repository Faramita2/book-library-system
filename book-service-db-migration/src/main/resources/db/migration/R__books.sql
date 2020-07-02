CREATE TABLE IF NOT EXISTS `books` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(1024) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `borrower_id` BIGINT,
    `borrowed_at` TIMESTAMP,
    `return_at` TIMESTAMP,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,

    INDEX `name_index`(`name` ASC),
    INDEX `description_index`(`description`(255) ASC),
    INDEX `borrower_id_index`(`borrower_id` ASC)
);

---- remove unnecessary columns `tag_id`, `author_id`, `category_id`

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'books' AND COLUMN_NAME = 'tag_id'
);

set @sqlStmt := if(
    @columnExisting > 0,
	"ALTER TABLE `books` DROP COLUMN `tag_id`",
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'books' AND COLUMN_NAME = 'author_id'
);

set @sqlStmt := if(
    @columnExisting > 0,
	"ALTER TABLE `books` DROP COLUMN `author_id`",
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;


set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'books' AND COLUMN_NAME = 'category_id'
);

set @sqlStmt := if(
    @columnExisting > 0,
	"ALTER TABLE `books` DROP COLUMN `category_id`",
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @indexExist := (
    select count(*) from information_schema.STATISTICS where table_name = 'books' and index_name = 'tag_id_index' and table_schema = database()
);
set @sqlStmt := if(
    @indexExist > 0,
    "DROP index `tag_id_index` on `books`",
    "select 'INFO: Index NOT exists.'"
);
PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @indexExist := (
    select count(*) from information_schema.STATISTICS where table_name = 'books' and index_name = 'author_id_index' and table_schema = database()
);
set @sqlStmt := if(
    @indexExist > 0,
    "DROP index `author_id_index` on `books`",
    "select 'INFO: Index NOT exists.'"
);
PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @indexExist := (
    select count(*) from information_schema.STATISTICS where table_name = 'books' and index_name = 'category_id_index' and table_schema = database()
);
set @sqlStmt := if(
    @indexExist > 0,
    "DROP index `category_id_index` on `books`",
    "select 'INFO: Index NOT exists.'"
);
PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;