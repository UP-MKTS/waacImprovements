create table dangerous_pow(
                            dangerous_pow_id int not null auto_increment,
                            pow_name varchar(100) not null,
                            creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            CONSTRAINT PRIMARY KEY (dangerous_pow_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table dangerous_class(
                              dangerous_class_id int not null auto_increment,
                              class_name varchar(100) not null,
                              creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              CONSTRAINT PRIMARY KEY (dangerous_class_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table goals(
                      goal_id int not null auto_increment,
                      goal_name varchar(200) not null,
                      creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      CONSTRAINT PRIMARY KEY (goal_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table organizations(
                            organization_id int not null auto_increment,
                            organization_name varchar(100) not null,
                            organization_address varchar(200) not null,
                            waste_destination varchar(200) null,
                            creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            CONSTRAINT PRIMARY KEY (organization_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table departments(
                          department_id int not null auto_increment,
                          department_full_name varchar(100) not null,
                          department_short_name varchar(100) not null,
                          creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT PRIMARY KEY (department_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table roles(
                      role_id int not null auto_increment,
                      role_name varchar(100) not null,
                      CONSTRAINT PRIMARY KEY (role_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table users(
                    user_id int not null auto_increment,
                    user_fio varchar(200) not null,
                    user_name varchar(50) not null,
                    password varchar(100) not null,
                    email varchar(50) not null,
                    department_id int not null,
                    activated tinyint not null,
                    role_id int not null,
                    login_status tinyint not null,
                    date_login datetime null,
                    date_destroy datetime null,
                    CONSTRAINT PRIMARY KEY (user_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE users
    ADD CONSTRAINT users_department_fk FOREIGN KEY (department_id)
        REFERENCES departments (department_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE users
    ADD CONSTRAINT users_role_fk FOREIGN KEY (role_id)
        REFERENCES roles (role_id) ON DELETE RESTRICT ON UPDATE CASCADE;

create table persons(
                        person_id int not null auto_increment,
                        person_fio varchar(100) not null,
                        person_position varchar(200) not null,
                        department_id int not null,
                        order_number varchar(50) not null,
                        order_date date not null,
                        creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        CONSTRAINT PRIMARY KEY (person_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE persons
    ADD CONSTRAINT persons_department_fk FOREIGN KEY (department_id)
        REFERENCES departments (department_id) ON DELETE RESTRICT ON UPDATE CASCADE;

create table activity_kinds(
                               activity_kind_id int not null auto_increment,
                               activity_kind_name varchar(200) not null,
                               creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               CONSTRAINT PRIMARY KEY (activity_kind_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table dates(
                    date_id int not null auto_increment,
                    month tinyint not null,
                    year year not null,
                    creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    CONSTRAINT PRIMARY KEY (date_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table waste_types(
                            waste_type_id int not null auto_increment,
                            waste_code int not null,
                            waste_name varchar(200) not null,
                            dangerous_pow_id int not null,
                            dangerous_class_id int not null,
                            activity_kind_id int not null,
                            waste_norm varchar(200) not null,
                            creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            CONSTRAINT PRIMARY KEY (waste_type_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE waste_types
    ADD CONSTRAINT waste_types_dangerous_pow_fk FOREIGN KEY (dangerous_pow_id)
        REFERENCES dangerous_pow (dangerous_pow_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE waste_types
    ADD CONSTRAINT waste_types_dangerous_class_fk FOREIGN KEY (dangerous_class_id)
        REFERENCES dangerous_class (dangerous_class_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE waste_types
    ADD CONSTRAINT waste_types_activity_kind_fk FOREIGN KEY (activity_kind_id)
        REFERENCES activity_kinds (activity_kind_id) ON DELETE RESTRICT ON UPDATE CASCADE;

create table wastes(
                    waste_id int not null auto_increment,
                    waste_type_id int not null,
                    waste_date date not null,
                    create_count float not null,
                    from_another float not null,
                    organization_id int not null,
                    from_people float null,
                    used float null,
                    neutralized float null,
                    count_transferred float not null,
                    transfer_organization_id int not null,
                    goal_id int not null,
                    keeping float null,
                    responsible_id int not null,
                    checking_id int not null,
                    date_id int not null,
                    creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    CONSTRAINT PRIMARY KEY (waste_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE wastes
    ADD CONSTRAINT wastes_waste_type_fk FOREIGN KEY (waste_type_id)
        REFERENCES waste_types (waste_type_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE wastes
    ADD CONSTRAINT wastes_organization_fk FOREIGN KEY (organization_id)
        REFERENCES organizations (organization_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE wastes
    ADD CONSTRAINT wastes_rebase_organization_fk FOREIGN KEY (transfer_organization_id)
        REFERENCES organizations (organization_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE wastes
    ADD CONSTRAINT wastes_goal_fk FOREIGN KEY (goal_id)
        REFERENCES goals (goal_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE wastes
    ADD CONSTRAINT wastes_responsible_fk FOREIGN KEY (responsible_id)
        REFERENCES persons (person_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE wastes
    ADD CONSTRAINT wastes_checking_fk FOREIGN KEY (checking_id)
        REFERENCES persons (person_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE wastes
    ADD CONSTRAINT wastes_date_fk FOREIGN KEY (date_id)
        REFERENCES dates (date_id) ON DELETE RESTRICT ON UPDATE CASCADE;
