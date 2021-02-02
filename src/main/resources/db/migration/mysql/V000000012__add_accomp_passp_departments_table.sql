create table accomp_passp_department(
                                    accomp_passp_department_id int not null auto_increment,
                                    accomp_passp_id int,
                                    department_id  int not null,
                                    creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    CONSTRAINT PRIMARY KEY (accomp_passp_department_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE accomp_passp_department
    ADD CONSTRAINT accomp_passp_department_accomp_passp_fk FOREIGN KEY (accomp_passp_id)
        REFERENCES accomp_passp (accomp_passp_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE accomp_passp_department
    ADD CONSTRAINT accomp_passp_department_department_fk FOREIGN KEY (department_id)
        REFERENCES departments (department_id) ON DELETE RESTRICT ON UPDATE CASCADE;
