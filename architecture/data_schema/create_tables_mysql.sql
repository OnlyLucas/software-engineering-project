CREATE TABLE `users` (
  `id` varchar(36) PRIMARY KEY,
  `email` varchar(255) NOT NULL,
  `username` varchar(255),
  `password` varchar(255) NOT NULL,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `living_groups` (
  `id` varchar(36) PRIMARY KEY,
  `group_name` varchar(255) NOT NULL,
  `group_description` varchar(255),
  `created_by` varchar(36) NOT NULL,
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `group_memberships` (
  `id` varchar(36) PRIMARY KEY,
  `group_id` varchar(36),
  `user_id` varchar(36),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `cleaning_template` (
  `id` varchar(36) PRIMARY KEY NOT NULL,
  `group_id` varchar(36) NOT NULL,
  `task_name` varchar(255) NOT NULL,
  `template_description` varchar(255),
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `interval` integer COMMENT 'Interval in days',
  `created_by` varchar(36),
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `cleanings` (
  `id` varchar(36) PRIMARY KEY,
  `group_id` varchar(36) NOT NULL,
  `user_id` varchar(36),
  `cleaning_template` varchar(36),
  `date` date NOT NULL,
  `is_completed` boolean,
  `completed_at` timestamp
);

CREATE TABLE `group_groceries` (
  `id` varchar(36) PRIMARY KEY,
  `group_id` varchar(36) NOT NULL,
  `grocery_name` varchar(255) NOT NULL COMMENT 'We agreed to include amounts here',
  `created_by` varchar(36) NOT NULL,
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP()),
  `is_completed` boolean COMMENT 'If true shown in history',
  `completed_by` varchar(36),
  `completed_at` timestamp
);

CREATE TABLE `payments` (
  `id` varchar(36) PRIMARY KEY,
  `group_id` varchar(36) NOT NULL,
  `payment_name` varchar(255) NOT NULL,
  `amount` decimal(11, 2) NOT NULL,
  `currency_code` char(3),
  `paid_by` varchar(36) NOT NULL,
  `created_by` varchar(36) NOT NULL,
  `created_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `payments_changes` (
  `id` varchar(36) PRIMARY KEY,
  `payment_id` varchar(36),
  `group_id` varchar(36) NOT NULL,
  `payment_name` varchar(255) NOT NULL,
  `amount` decimal(11, 2) NOT NULL,
  `currency_code` char(3),
  `paid_by` varchar(36) NOT NULL,
  `changed_by` varchar(36) NOT NULL,
  `changed_at` timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE `payment_participations` (
  `id` varchar(36) PRIMARY KEY,
  `payment_id` varchar(36),
  `group_id` varchar(36) NOT NULL,
  `user_id` varchar(36),
  `participation_amount` decimal(11, 2) NOT NULL,
  `currency_code` char(3),
  `is_paid` boolean NOT NULL,
  `paid_at` timestamp
);

ALTER TABLE `living_groups` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);

ALTER TABLE `group_memberships` ADD FOREIGN KEY (`group_id`) REFERENCES `living_groups` (`id`);

ALTER TABLE `group_memberships` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `cleaning_template` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);

ALTER TABLE `cleaning_template` ADD FOREIGN KEY (`group_id`) REFERENCES `living_groups` (`id`);

ALTER TABLE `cleanings` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `cleanings` ADD FOREIGN KEY (`group_id`) REFERENCES `living_groups` (`id`);

ALTER TABLE `cleanings` ADD FOREIGN KEY (`cleaning_template`) REFERENCES `cleaning_template` (`id`);

ALTER TABLE `group_groceries` ADD FOREIGN KEY (`group_id`) REFERENCES `living_groups` (`id`);

ALTER TABLE `group_groceries` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);

ALTER TABLE `group_groceries` ADD FOREIGN KEY (`completed_by`) REFERENCES `users` (`id`);

ALTER TABLE `payments` ADD FOREIGN KEY (`paid_by`) REFERENCES `users` (`id`);

ALTER TABLE `payments` ADD FOREIGN KEY (`group_id`) REFERENCES `living_groups` (`id`);

ALTER TABLE `payments` ADD FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);

ALTER TABLE `payments_changes` ADD FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`);

ALTER TABLE `payments_changes` ADD FOREIGN KEY (`group_id`) REFERENCES `living_groups` (`id`);

ALTER TABLE `payments_changes` ADD FOREIGN KEY (`paid_by`) REFERENCES `users` (`id`);

ALTER TABLE `payments_changes` ADD FOREIGN KEY (`changed_by`) REFERENCES `users` (`id`);

ALTER TABLE `payment_participations` ADD FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`);

ALTER TABLE `payment_participations` ADD FOREIGN KEY (`group_id`) REFERENCES `living_groups` (`id`);

ALTER TABLE `payment_participations` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
