create table accomp_passp_waste (
                                    accomp_passp_waste_id int not null auto_increment,
                                    accomp_passp_id int,
                                    waste_type_id  int null,
                                    goal_id int null,
                                    waste_weight  double,
                                    creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    CONSTRAINT PRIMARY KEY (accomp_passp_waste_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE accomp_passp_waste
    ADD CONSTRAINT accomp_passp_waste_accomp_passp_fk FOREIGN KEY (accomp_passp_id)
        REFERENCES accomp_passp (accomp_passp_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE accomp_passp_waste
    ADD CONSTRAINT accomp_passp_waste_waste_type_fk FOREIGN KEY (waste_type_id)
        REFERENCES waste_types (waste_type_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE accomp_passp_waste
    ADD CONSTRAINT accomp_passp_waste_goal_fk FOREIGN KEY (goal_id)
        REFERENCES goals (goal_id) ON DELETE RESTRICT ON UPDATE CASCADE;
