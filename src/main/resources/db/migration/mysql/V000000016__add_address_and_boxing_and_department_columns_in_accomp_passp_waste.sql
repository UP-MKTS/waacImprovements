ALTER TABLE accomp_passp_waste ADD COLUMN accomp_passp_waste_address varchar(100) null after accomp_passp_id;
update accomp_passp_waste set accomp_passp_waste_address = "не задано";

ALTER TABLE accomp_passp_waste ADD COLUMN accomp_passp_waste_boxing varchar(100) null after accomp_passp_id;
update accomp_passp_waste set accomp_passp_waste_boxing = "не задано";

alter table accomp_passp_waste add column department_id int null;

ALTER TABLE accomp_passp_waste
    ADD CONSTRAINT accomp_passp_waste_department_fk FOREIGN KEY (department_id)
        REFERENCES departments (department_id) ON DELETE RESTRICT ON UPDATE CASCADE;