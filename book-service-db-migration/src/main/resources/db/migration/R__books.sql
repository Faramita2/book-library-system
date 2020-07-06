CREATE TABLE IF NOT EXISTS `books` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(1024) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `borrow_user_id` BIGINT,
    `borrowed_time` TIMESTAMP,
    `return_date` TIMESTAMP,
    `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,

    INDEX `status_index`(`status` ASC),
    INDEX `name_index`(`name` ASC),
    INDEX `description_index`(`description` ASC),
    INDEX `borrow_user_id_index`(`borrow_user_id` ASC)
);

-- rename columns `borrower_id`

set @columnExisting := (
    select count(COLUMN_NAME) from information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'books' AND COLUMN_NAME = 'borrower_id'
);

set @sqlStmt := if(
    @columnExisting > 0,
    'ALTER TABLE books RENAME COLUMN borrower_id TO borrow_user_id;',
    "SELECT 'INFO: Column NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

-- drop index `borrower_id_index`

set @indexExist := (
    select count(*) from information_schema.STATISTICS where table_name = 'books' and index_name = 'borrower_id_index' and table_schema = database()
);
set @sqlStmt := if(
    @indexExist > 0,
    "DROP INDEX borrower_id_index ON books;",
    "select 'INFO: Index NOT exists.'"
);
PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

-- add index for `borrow_user_id`

set @indexExist := (
    select count(*) from information_schema.STATISTICS where table_name = 'books' and index_name = 'borrow_user_id_index' and table_schema = database()
);
set @sqlStmt := if(
    @indexExist = 0,
    "ALTER TABLE books ADD INDEX borrow_user_id_index(borrow_user_id ASC);",
    "select 'INFO: Index exists.'"
);
PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;