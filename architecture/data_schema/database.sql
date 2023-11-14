-- Users
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('1', 'john.doe@example.com', 'johndoe', 'password1', 'John', 'Doe');
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('2', 'jane.doe@example.com', 'janedoe', 'password2', 'Jane', 'Doe');
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('3', 'mark.doe@example.com', 'markdoe', 'password3', 'Mark', 'Doe');
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('4', 'mike.doe@example.com', 'mikedoe', 'password4', 'Mike', 'Doe');
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('5', 'emma.doe@example.com', 'emmadoe', 'password5', 'Emma', 'Doe');

-- Groups
INSERT INTO `groups` (`id`, `group_name`, `description`, `created_by`) VALUES ('1', 'Group 1', 'Group description 1', '1');
INSERT INTO `groups` (`id`, `group_name`, `description`, `created_by`) VALUES ('2', 'Group 2', 'Group description 2', '5');

-- Group Memberships
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('1', '2');
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('1', '3');
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('1', '4');
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('2', '5');

-- Cleaning Templates
INSERT INTO `cleaning_template` (`id`, `task_name`, `description`, `start_date`, `end_date`, `interval`, `created_by`) VALUES ('1', 'Clean the floor', 'Ensure that the floor is cleaned and mopped', '2023-11-24', '2024-02-24', '7', '30');

-- Cleanings
INSERT INTO `cleanings` (`id`, `user_id`, `cleaning_template`, `date`, `is_completed`, `completed_at`) VALUES ('1', '1', '1', '2023-11-24', true, null);
INSERT INTO `cleanings` (`id`, `user_id`, `cleaning_template`, `date`, `is_completed`, `completed_at`) VALUES ('1', '1', '1', '2023-12-24', false, null);

-- Group Groceries
INSERT INTO `group_groceries` (`id`, `group_id`, `grocery_name`, `created_by`, `is_completed`, `completed_by`, `completed_at`) VALUES ('1', '1', '10 Eggs', '1', false, null, null);
INSERT INTO `group_groceries` (`id`, `group_id`, `grocery_name`, `created_by`, `is_completed`, `completed_by`, `completed_at`) VALUES ('2', '1', '2 Breads', '2', false, null, null);
INSERT INTO `group_groceries` (`id`, `group_id`, `grocery_name`, `created_by`, `is_completed`, `completed_by`, `completed_at`) VALUES ('3', '1', '3 Milk', '1', false, null, null);
-- Payments
INSERT INTO `payments` (`id`, `payment_name`, `amount`, `currency_code`, `paid_by`, `created_by`) VALUES ('1', 'Rent', '1000.00', 'EUR', '1', '1');
INSERT INTO `payments` (`id`, `payment_name`, `amount`, `currency_code`, `paid_by`, `created_by`) VALUES ('2', 'Restaurant', '60.00', 'EUR', '2', '2');

-- Payments Changes
INSERT INTO `payments_changes` (`id`, `payment_id`, `payment_name`, `amount`, `currency_code`, `paid_by`, `changed_by`, `changed_at`) VALUES ('1', '1', 'Rent', '999.00', 'EUR', '1', '1', '2023-11-14 15:00:00');
INSERT INTO `payments_changes` (`id`, `payment_id`, `payment_name`, `amount`, `currency_code`, `paid_by`, `changed_by`) VALUES ('1', '1', 'Rent', '1000.00', 'EUR', '1', '1');

-- Payment Participations
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('1', '1', '250.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('1', '2', '250.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('1', '3', '250.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('1', '4', '250.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('2', '1', '30.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('2', '2', '30.00', 'EUR', true, CURRENT_TIMESTAMP);