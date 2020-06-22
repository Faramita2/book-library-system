CREATE TABLE IF NOT EXISTS `books` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(1024) NOT NULL,
    `author_id` BIGINT NOT NULL,
    `tag_id` BIGINT NOT NULL,
    `category_id` BIGINT NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `borrower_id` BIGINT NOT NULL DEFAULT 0,
    `borrowed_at` TIMESTAMP,
    `return_at` TIMESTAMP,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,

    INDEX `name_index`(`name` ASC),
    INDEX `description_index`(`description`(50) ASC),
    INDEX `author_id_index`(`author_id` ASC),
    INDEX `tag_id_index`(`tag_id` ASC),
    INDEX `category_id_index`(`category_id` ASC),
    INDEX `borrower_id_index`(`borrower_id` ASC)
);