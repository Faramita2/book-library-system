CREATE TABLE IF NOT EXISTS `book_categories` (
    `book_id` BIGINT NOT NULL,
    `category_id` BIGINT NOT NULL,
    INDEX `book_index`(`book_id` ASC),
    INDEX `category_index`(`category_id` ASC)
);