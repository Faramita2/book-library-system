CREATE TABLE IF NOT EXISTS `book_categories` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `book_id` BIGINT NOT NULL,
    `category_id` BIGINT NOT NULL,
    INDEX `book_index`(`book_id` ASC),
    INDEX `ix_book_categories_category_id`(`category_id` ASC)
);