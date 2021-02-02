create table contracts (
                            contract_id int not null auto_increment,
                            contract_number varchar(50) not null,
                            contract_date date not null,
                            organization_id int not null,
                            creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            last_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            CONSTRAINT PRIMARY KEY (contract_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE contracts
    ADD CONSTRAINT contracts_organization_fk FOREIGN KEY (organization_id)
        REFERENCES organizations (organization_id) ON DELETE RESTRICT ON UPDATE CASCADE;

insert into contracts (contract_number, contract_date, organization_id) VALUE ('623/И', '2020-03-20', 2)
