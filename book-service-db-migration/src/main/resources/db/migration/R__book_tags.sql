CREATE TABLE IF NOT EXISTS `book_tags` (
    `book_id` BIGINT NOT NULL,
    `tag_id` BIGINT NOT NULL,
    INDEX `book_index`(`book_id` ASC),
    INDEX `tag_index`(`tag_id` ASC)
);