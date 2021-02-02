ALTER TABLE accomp_passp ADD COLUMN address varchar(100) null after accomp_passp_id;
update accomp_passp set address = "не задано";
