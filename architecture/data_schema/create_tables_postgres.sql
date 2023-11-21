CREATE TABLE "users" (
  "id" varchar(36) PRIMARY KEY,
  "email" varchar NOT NULL,
  "username" varchar,
  "password" varchar NOT NULL,
  "first_name" varchar,
  "last_name" varchar,
  "created_at" timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE "groups" (
  "id" varchar(36) PRIMARY KEY,
  "group_name" varchar NOT NULL,
  "description" varchar,
  "created_by" varchar(36) NOT NULL,
  "created_at" timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE "group_memberships" (
  "id" varchar(36) PRIMARY KEY,
  "group_id" varchar(36),
  "user_id" varchar(36),
  "created_at" timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE "cleaning_template" (
  "id" varchar(36) PRIMARY KEY NOT NULL,
  "task_name" varchar NOT NULL,
  "description" varchar,
  "start_date" date NOT NULL,
  "end_date" date NOT NULL,
  "interval" integer,
  "created_by" varchar(36),
  "created_at" timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE "cleanings" (
  "id" varchar(36) PRIMARY KEY,
  "user_id" varchar(36),
  "cleaning_template" varchar(36),
  "date" date NOT NULL,
  "is_completed" boolean,
  "completed_at" timestamp
);

CREATE TABLE "group_groceries" (
  "id" varchar(36) PRIMARY KEY,
  "group_id" varchar(36) NOT NULL,
  "grocery_name" varchar NOT NULL,
  "created_by" varchar(36) NOT NULL,
  "created_at" timestamp DEFAULT (CURRENT_TIMESTAMP()),
  "is_completed" boolean,
  "completed_by" varchar(36),
  "completed_at" timestamp
);

CREATE TABLE "payments" (
  "id" varchar(36) PRIMARY KEY,
  "payment_name" varchar NOT NULL,
  "amount" "decimal(11, 2)" NOT NULL,
  "currency_code" char(3),
  "paid_by" varchar(36) NOT NULL,
  "created_by" varchar(36) NOT NULL,
  "created_at" timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE "payments_changes" (
  "id" varchar(36) PRIMARY KEY,
  "payment_id" varchar(36),
  "payment_name" varchar NOT NULL,
  "amount" "decimal(11, 2)" NOT NULL,
  "currency_code" char(3),
  "paid_by" varchar(36) NOT NULL,
  "changed_by" varchar(36) NOT NULL,
  "changed_at" timestamp DEFAULT (CURRENT_TIMESTAMP())
);

CREATE TABLE "payment_participations" (
  "id" varchar(36) PRIMARY KEY,
  "payment_id" varchar(36),
  "user_id" varchar(36),
  "participation_amount" "decimal(11, 2)" NOT NULL,
  "currency_code" char(3),
  "is_paid" boolean NOT NULL,
  "paid_at" timestamp
);

COMMENT ON COLUMN "cleaning_template"."interval" IS 'Interval in days';

COMMENT ON COLUMN "group_groceries"."grocery_name" IS 'We agreed to include amounts here';

COMMENT ON COLUMN "group_groceries"."is_completed" IS 'If true shown in history';

ALTER TABLE "groups" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "group_memberships" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id");

ALTER TABLE "group_memberships" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "cleaning_template" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "cleanings" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "cleanings" ADD FOREIGN KEY ("cleaning_template") REFERENCES "cleaning_template" ("id");

ALTER TABLE "group_groceries" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id");

ALTER TABLE "group_groceries" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "group_groceries" ADD FOREIGN KEY ("completed_by") REFERENCES "users" ("id");

ALTER TABLE "payments" ADD FOREIGN KEY ("paid_by") REFERENCES "users" ("id");

ALTER TABLE "payments" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "payments_changes" ADD FOREIGN KEY ("payment_id") REFERENCES "payments" ("id");

ALTER TABLE "payments_changes" ADD FOREIGN KEY ("paid_by") REFERENCES "users" ("id");

ALTER TABLE "payments_changes" ADD FOREIGN KEY ("changed_by") REFERENCES "users" ("id");

ALTER TABLE "payment_participations" ADD FOREIGN KEY ("payment_id") REFERENCES "payments" ("id");

ALTER TABLE "payment_participations" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");
