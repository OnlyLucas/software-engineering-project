-- Users
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('00000000-0000-0000-0000-000000000000', 'john.doe@example.com', 'johndoe', 'password1', 'John', 'Doe');
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('00000000-0000-0000-0000-000000000001', 'jane.doe@example.com', 'janedoe', 'password2', 'Jane', 'Doe');
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('00000000-0000-0000-0000-000000000002', 'mark.doe@example.com', 'markdoe', 'password3', 'Mark', 'Doe');
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('00000000-0000-0000-0000-000000000003', 'mike.doe@example.com', 'mikedoe', 'password4', 'Mike', 'Doe');
INSERT INTO `users` (`id`, `email`, `username`, `password`, `first_name`, `last_name`) VALUES ('00000000-0000-0000-0000-000000000004', 'emma.doe@example.com', 'emmadoe', 'password5', 'Emma', 'Doe');

-- Groups
INSERT INTO `living_groups` (`id`, `group_name`, `group_description`, `created_by`) VALUES ('00000000-0000-0000-0000-000000000000', 'Group 1', 'Group description 1', '00000000-0000-0000-0000-000000000000');
INSERT INTO `living_groups` (`id`, `group_name`, `group_description`, `created_by`) VALUES ('00000000-0000-0000-0000-000000000001', 'Group 2', 'Group description 2', '00000000-0000-0000-0000-000000000004');

-- Group Memberships
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000');
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('00000000-0000-0000-0000-000000000000', ‘00000000-0000-0000-0000-000000000001’);
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('00000000-0000-0000-0000-000000000000', ‘00000000-0000-0000-0000-000000000002’);
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES ('00000000-0000-0000-0000-000000000000', ‘00000000-0000-0000-0000-000000000003’);
INSERT INTO `group_memberships` (`group_id`, `user_id`) VALUES (‘00000000-0000-0000-0000-000000000001’, ‘00000000-0000-0000-0000-000000000004’);

-- Cleaning Templates
INSERT INTO `cleaning_template` (`id`, `task_name`, `description`, `start_date`, `end_date`, `interval`, `created_by`) VALUES (‘00000000-0000-0000-0000-000000000000’, 'Clean the floor', 'Ensure that the floor is cleaned and mopped', '2023-11-24', '2024-02-24', '7', '00000000-0000-0000-0000-000000000000');

-- Cleanings
INSERT INTO `cleanings` (`id`, `user_id`, `cleaning_template`, `date`, `is_completed`, `completed_at`) VALUES (‘00000000-0000-0000-0000-000000000000’, ‘00000000-0000-0000-0000-000000000000’, ‘00000000-0000-0000-0000-000000000000’, '2023-11-24', true, null);
INSERT INTO `cleanings` (`id`, `user_id`, `cleaning_template`, `date`, `is_completed`, `completed_at`) VALUES (‘00000000-0000-0000-0000-000000000001’, ‘00000000-0000-0000-0000-000000000000’, ‘00000000-0000-0000-0000-000000000000’, '2023-12-24', false, null);

-- Group Groceries
INSERT INTO `group_groceries` (`id`, `group_id`, `grocery_name`, `created_by`, `is_completed`, `completed_by`, `completed_at`) VALUES (‘00000000-0000-0000-0000-000000000000’, '00000000-0000-0000-0000-000000000000', '10 Eggs', '00000000-0000-0000-0000-000000000000', false, null, null);
INSERT INTO `group_groceries` (`id`, `group_id`, `grocery_name`, `created_by`, `is_completed`, `completed_by`, `completed_at`) VALUES (‘00000000-0000-0000-0000-000000000001’, '00000000-0000-0000-0000-000000000000', '2 Breads', ‘00000000-0000-0000-0000-000000000001’, false, null, null);
INSERT INTO `group_groceries` (`id`, `group_id`, `grocery_name`, `created_by`, `is_completed`, `completed_by`, `completed_at`) VALUES (‘00000000-0000-0000-0000-000000000002’, ‘00000000-0000-0000-0000-000000000000’, '3 Milk', '00000000-0000-0000-0000-000000000000', false, null, null);
-- Payments
INSERT INTO `payments` (`id`, `payment_name`, `amount`, `currency_code`, `paid_by`, `created_by`) VALUES (‘00000000-0000-0000-0000-000000000000’, 'Rent', '1000.00', 'EUR', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000');
INSERT INTO `payments` (`id`, `payment_name`, `amount`, `currency_code`, `paid_by`, `created_by`) VALUES (‘00000000-0000-0000-0000-000000000001’, 'Restaurant', '60.00', 'EUR', ‘00000000-0000-0000-0000-000000000001’, ‘00000000-0000-0000-0000-000000000001’);

-- Payments Changes
INSERT INTO `payments_changes` (`id`, `payment_id`, `payment_name`, `amount`, `currency_code`, `paid_by`, `changed_by`, `changed_at`) VALUES (‘00000000-0000-0000-0000-000000000000’, ‘00000000-0000-0000-0000-000000000000’, 'Rent', '999.00', 'EUR', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000', '2023-11-14 15:00:00');
INSERT INTO `payments_changes` (`id`, `payment_id`, `payment_name`, `amount`, `currency_code`, `paid_by`, `changed_by`) VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', 'Rent', '1000.00', 'EUR', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000');

-- Payment Participations
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000', '250.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000001', '250.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000002', '250.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000003', '250.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '30.00', 'EUR', true, CURRENT_TIMESTAMP);
INSERT INTO `payment_participations` (`payment_id`, `user_id`, `participation_amount`, `currency_code`, `is_paid`, `paid_at`) VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', '30.00', 'EUR', true, CURRENT_TIMESTAMP);
