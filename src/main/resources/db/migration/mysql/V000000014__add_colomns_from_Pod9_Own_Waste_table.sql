

alter table pod9_own_waste add column department_id int null;
alter table pod9_own_waste add column waste_type_id int null;

ALTER TABLE pod9_own_waste
    ADD CONSTRAINT pod9_own_waste_department_fk FOREIGN KEY (department_id)
        REFERENCES departments (department_id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE pod9_own_waste
    ADD CONSTRAINT pod9_own_waste_waste_type_fk FOREIGN KEY (waste_type_id)
        REFERENCES waste_types (waste_type_id) ON DELETE RESTRICT ON UPDATE CASCADE;

