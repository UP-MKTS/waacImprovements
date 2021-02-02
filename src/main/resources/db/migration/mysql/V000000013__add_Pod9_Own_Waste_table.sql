create table pod9_own_waste (
                                pod9_own_waste_id int not null auto_increment,
                                accomp_passp_waste_id int null,
                                date date null,
                                waste_generate double null,
                                count_from_other double null,
                                name_other varchar(100) null,
                                count_from_people double null,
                                count_used double null,
                                count_neutralized double null,
                                count_keeping double null,
                                creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                CONSTRAINT PRIMARY KEY (pod9_own_waste_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE pod9_own_waste
    ADD CONSTRAINT pod9_own_waste_accomp_passp_waste_fk FOREIGN KEY (accomp_passp_waste_id)
        REFERENCES accomp_passp_waste (accomp_passp_waste_id) ON DELETE RESTRICT ON UPDATE CASCADE;

