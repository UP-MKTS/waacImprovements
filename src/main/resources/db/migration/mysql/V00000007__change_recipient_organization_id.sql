update accomp_passp set recipient_organization_id = 1;
ALTER TABLE accomp_passp change recipient_organization_id contract_id int not null ;

