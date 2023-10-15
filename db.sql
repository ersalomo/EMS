CREATE TABLE "users" (
                         "id" bigint PRIMARY KEY NOT NULL,
                         "email_addr" varchar(255) NOT NULL,
                         "password" varchar(64) NOT NULL,
                         "created_at" timestamp DEFAULT (now()),
                         "updated_at" timestamp DEFAULT (now())
);

CREATE TABLE "merchants" (
                             "id" bigint PRIMARY KEY NOT NULL,
                             "marchant_name" varchar(255) NOT NULL,
                             "marchant_location" varchar(255) NOT NULL,
                             "created_at" timestamp DEFAULT (now()),
                             "updated_at" timestamp DEFAULT (now())
);

CREATE TABLE "orders" (
                          "id" bigint PRIMARY KEY NOT NULL,
                          "order_time" time NOT NULL,
                          "destination_addr" varchar(255) NOT NULL,
                          "user_id" bigint NOT NULL,
                          "completed" int DEFAULT 0,
                          "created_at" timestamp DEFAULT (now()),
                          "updated_at" timestamp DEFAULT (now())
);

CREATE TABLE "products" (
                            "id" bigint PRIMARY KEY NOT NULL,
                            "product_name" varchar(255) NOT NULL,
                            "price" double precision NOT NULL,
                            "merchant_id" bigint,
                            "created_at" timestamp DEFAULT (now()),
                            "updated_at" timestamp DEFAULT (now())
);

CREATE TABLE "order_detail" (
                                "id" bigint PRIMARY KEY NOT NULL,
                                "order_id" bigint NOT NULL,
                                "product_id" bigint NOT NULL,
                                "qty" int NOT NULL,
                                "total_price" double precision NOT NULL,
                                "created_at" timestamp DEFAULT (now()),
                                "updated_at" timestamp DEFAULT (now())
);

CREATE INDEX ON "users" ("email_addr");

CREATE INDEX ON "merchants" ("marchant_name");

CREATE INDEX ON "orders" ("user_id");

CREATE INDEX ON "products" ("product_name");

ALTER TABLE "orders" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "products" ADD FOREIGN KEY ("merchant_id") REFERENCES "merchants" ("id");

ALTER TABLE "order_detail" ADD FOREIGN KEY ("order_id") REFERENCES "orders" ("id");

ALTER TABLE "order_detail" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");