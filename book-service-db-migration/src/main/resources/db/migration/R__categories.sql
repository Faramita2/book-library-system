CREATE TABLE IF NOT EXISTS `categories` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,
    UNIQUE INDEX `name_index`(`name` ASC)
);

---- change index `name_index` to unique

set @indexExisting := (
    SELECT COUNT(*) FROM information_schema.STATISTICS where table_name = 'categories' and index_name = 'name_index' and table_schema = database()
);

set @sqlStmt := if(
    @indexExisting > 0,
    'DROP INDEX name_index ON categories;',
    "SELECT 'INFO: INDEX NOT exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;

set @indexExisting := (
    SELECT COUNT(*) FROM information_schema.STATISTICS where table_name = 'categories' and index_name = 'name_index' and table_schema = database()
);

set @sqlStmt := if(
    @indexExisting = 0,
    'CREATE UNIQUE INDEX `name_index` ON categories (`name`);',
    "SELECT 'INFO: INDEX exists.'"
);

PREPARE stmt FROM @sqlStmt;
EXECUTE stmt;