CREATE TABLE IF NOT EXISTS `admins` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `account` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `salt` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,
    UNIQUE INDEX `account_index`(`account` ASC)
);
// init admin