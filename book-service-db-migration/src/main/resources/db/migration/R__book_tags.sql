CREATE TABLE IF NOT EXISTS `book_tags` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `book_id` BIGINT NOT NULL,
    `tag_id` BIGINT NOT NULL,
    INDEX `book_index`(`book_id` ASC),
    INDEX `ix_book_tags_tag_id`(`tag_id` ASC)
);