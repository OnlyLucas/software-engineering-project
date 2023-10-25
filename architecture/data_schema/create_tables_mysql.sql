CREATE TABLE `users` (
  `id` varchar(36) PRIMARY KEY,
  `email` varchar(255),
  `username` varchar(255),
  `password` varchar(255),
  `first_name` varchar(255),
  `last_name` varchar(255),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `groups` (
  `id` varchar(36) PRIMARY KEY,
  `name` varchar(255),
  `description` varchar(255),
  `created_by` varchar(36),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `group_memberships` (
  `group_id` varchar(36),
  `user_id` varchar(36),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP()),
  PRIMARY KEY (`group_id`, `user_id`)
);

CREATE TABLE `cleaning_template` (
  `id` varchar(36) PRIMARY KEY,
  `name` varchar(255),
  `description` varchar(255),
  `start_date` date,
  `end_date` date,
  `interval` integer COMMENT 'Interval in days',
  `created_by` varchar(36),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `cleanings` (
  `id` varchar(36) PRIMARY KEY,
  `user_id` varchar(36),
  `cleaning_template` varchar(36),
  `date` date,
  `is_completed` boolean,
  `completed_at` timestamp
);

CREATE TABLE `group_groceries` (
  `id` varchar(36) PRIMARY KEY,
  `group_id` varchar(36),
  `name` varchar(255) COMMENT 'We agreed to include amounts here',
  `created_by` varchar(36),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP()),
  `is_completed` boolean COMMENT 'If true shown in history',
  `completed_by` varchar(36),
  `completed_at` timestamp
);

CREATE TABLE `payments` (
  `id` varchar(36) PRIMARY KEY,
  `amount` decimal(11, 2),
  `currency_code` char(3),
  `paid_by` varchar(36),
  `created_by` varchar(36),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `payments_changes` (
  `id` varchar(36) PRIMARY KEY,
  `payment_id` varchar(36),
  `amount` decimal(11, 2),
  `currency_code` char(3),
  `paid_by` varchar(36),
  `changed_by` varchar(36),
  `changed_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `payment_participations` (
  `payment_id` varchar(36),
  `user_id` varchar(36),
  `participation_amount` decimal(11, 2),
  `currency_code` char(3),
  `is_paid` boolean,
  `paid_at` timestamp,
  PRIMARY KEY (`payment_id`, `user_id`)
);

ALTER TABLE `groups` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);

ALTER TABLE `group_memberships` ADD FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`);

ALTER TABLE `group_memberships` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `cleaning_template` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);

ALTER TABLE `cleanings` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `cleanings` ADD FOREIGN KEY (`cleaning_template`) REFERENCES `cleaning_template` (`id`);

ALTER TABLE `group_groceries` ADD FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`);

ALTER TABLE `group_groceries` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);

ALTER TABLE `group_groceries` ADD FOREIGN KEY (`completed_by`) REFERENCES `users` (`id`);

ALTER TABLE `payments` ADD FOREIGN KEY (`paid_by`) REFERENCES `users` (`id`);

ALTER TABLE `payments` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);

ALTER TABLE `payments_changes` ADD FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`);

ALTER TABLE `payments_changes` ADD FOREIGN KEY (`paid_by`) REFERENCES `users` (`id`);

ALTER TABLE `payments_changes` ADD FOREIGN KEY (`changed_by`) REFERENCES `users` (`id`);

ALTER TABLE `payment_participations` ADD FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`);

ALTER TABLE `payment_participations` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
