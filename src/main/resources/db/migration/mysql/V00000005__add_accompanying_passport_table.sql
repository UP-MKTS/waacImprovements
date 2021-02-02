create table accomp_passp (
                            accomp_passp_id int not null auto_increment,
                            accomp_passp_number int not null,
                            accomp_passp_date date not null,
                            recipient_organization_id int not null,
                            carrier_organization_id int not null,
                            transportation_date date not null,
                            car_model varchar(50) not null,
                            car_number varchar(9) not null,
                            driver_fio varchar(100) not null,
                            boxing varchar(100) null,
                            creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
CONSTRAINT PRIMARY KEY (accomp_passp_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


ALTER TABLE accomp_passp
    ADD CONSTRAINT accomp_passp_recipient_organization_fk FOREIGN KEY (recipient_organization_id)
        REFERENCES organizations (organization_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE accomp_passp
    ADD CONSTRAINT accomp_passp_carrier_organization_fk FOREIGN KEY (carrier_organization_id)
        REFERENCES organizations (organization_id) ON DELETE RESTRICT ON UPDATE CASCADE;

