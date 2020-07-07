CREATE TABLE IF NOT EXISTS `admins` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `account` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `salt` VARCHAR(50) NOT NULL,
    `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_by` VARCHAR(50) NOT NULL,
    UNIQUE INDEX `account_index`(`account` ASC)
);

INSERT IGNORE INTO `admins`
    (`account`, `password`, `salt`, `created_time`, `updated_time`, `created_by`, `updated_by`)
VALUES
    ('admin','rxHURjEixmKPk5EP971Hqw==','2Cc2SC9d+Jx7o38FrCU4lQ==', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'default','default');