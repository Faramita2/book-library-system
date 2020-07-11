CREATE TABLE IF NOT EXISTS `book_authors` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `book_id` BIGINT NOT NULL,
    `author_id` BIGINT NOT NULL,
    INDEX `book_index`(`book_id` ASC),
    INDEX `ix_book_authors_author_id`(`author_id` ASC)
);