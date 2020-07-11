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

    INDEX `ix_books_status`(`status` ASC),
    INDEX `ix_books_name`(`name` ASC),
    INDEX `ix_books_description`(`description`(255) ASC),
    INDEX `ix_books_borrow_user_id`(`borrow_user_id` ASC)
);