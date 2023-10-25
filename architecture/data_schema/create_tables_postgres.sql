CREATE TABLE "users" (
  "id" UUID PRIMARY KEY,
  "email" varchar,
  "username" varchar,
  "password" varchar,
  "first_name" varchar,
  "last_name" varchar,
  "created_at" timestamptz DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "groups" (
  "id" UUID PRIMARY KEY,
  "name" varchar,
  "description" varchar,
  "created_by" UUID,
  "created_at" timestamptz DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "group_memberships" (
  "group_id" UUID,
  "user_id" UUID,
  "created_at" timestamptz DEFAULT (CURRENT_TIMESTAMP),
  PRIMARY KEY ("group_id", "user_id")
);

CREATE TABLE "cleaning_template" (
  "id" UUID PRIMARY KEY,
  "name" varchar,
  "description" varchar,
  "start_date" date,
  "end_date" date,
  "interval" integer,
  "created_by" UUID,
  "created_at" timestamptz DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "cleanings" (
  "id" UUID PRIMARY KEY,
  "user_id" UUID,
  "cleaning_template" UUID,
  "date" date,
  "is_completed" boolean,
  "completed_at" timestamptz
);

CREATE TABLE "group_groceries" (
  "id" UUID PRIMARY KEY,
  "group_id" UUID,
  "name" varchar,
  "created_by" UUID,
  "created_at" timestamptz DEFAULT (CURRENT_TIMESTAMP),
  "is_completed" boolean,
  "completed_by" UUID,
  "completed_at" timestamptz
);

CREATE TABLE "payments" (
  "id" UUID PRIMARY KEY,
  "amount" "decimal(11, 2)",
  "currency_code" char(3),
  "paid_by" UUID,
  "created_by" UUID,
  "created_at" timestamptz DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "payments_changes" (
  "id" UUID PRIMARY KEY,
  "payment_id" UUID,
  "amount" "decimal(11, 2)",
  "currency_code" char(3),
  "paid_by" UUID,
  "changed_by" UUID,
  "changed_at" timestamptz DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "payment_participations" (
  "payment_id" UUID,
  "user_id" UUID,
  "participation_amount" "decimal(11, 2)",
  "currency_code" char(3),
  "is_paid" boolean,
  "paid_at" timestamptz,
  PRIMARY KEY ("payment_id", "user_id")
);

COMMENT ON COLUMN "cleaning_template"."interval" IS 'Interval in days';

COMMENT ON COLUMN "group_groceries"."name" IS 'We agreed to include amounts here';

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
