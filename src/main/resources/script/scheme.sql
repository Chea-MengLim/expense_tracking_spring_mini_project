CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create database expense_tracking_mini_project;

create table users (
                       user_id uuid default uuid_generate_v4() primary key,
                       email varchar(50),
                       password varchar(255),
                       profile_image varchar(50)
);

create table otps (
                      otp_id uuid default uuid_generate_v4() primary key,
                      otp_code varchar(10),
                      issued_at timestamp default now(),      -- time that release otp
                      expiration timestamp,     -- time that otp is expired
                      verify boolean,
                      user_id uuid,
                      constraint user_id_otps_fk foreign key (user_id) references users(user_id)
                          on delete cascade
                          on update cascade
);

create table categories (
                            category_id uuid default uuid_generate_v4() primary key,
                            name varchar(50),
                            description varchar(255),
                            user_id uuid,
                            constraint user_id_categories_fk foreign key (user_id) references users(user_id)
                                on delete cascade
                                on update cascade
);

create table expenses (
                          expense_id uuid default uuid_generate_v4() primary key,
                          amount decimal(8, 2),
                          description varchar(255),
                          date timestamp,
                          user_id uuid,
                          constraint user_id_expense_fk foreign key (user_id) references users (user_id)
                              on delete cascade
                              on update cascade,
                          category_id uuid,
                          constraint category_id_expense_fk foreign key (category_id) references categories (category_id)
                              on delete cascade
                              on update cascade
);

