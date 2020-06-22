CREATE TABLE IF NOT EXISTS `book_authors` (
    `book_id` BIGINT NOT NULL,
    `author_id` BIGINT NOT NULL,
    INDEX `book_index`(`book_id` ASC),
    INDEX `author_index`(`author_id` ASC)
);