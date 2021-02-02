ALTER TABLE accomp_passp ADD COLUMN count_stored double null after boxing;

update accomp_passp set accomp_passp.count_stored = 0;
update accomp_passp set accomp_passp.count_stored = 0.882 where accomp_passp_number = 889;
update accomp_passp set accomp_passp.count_stored = 0.45 where accomp_passp_number = 890;
update accomp_passp set accomp_passp.count_stored = 0.229 where accomp_passp_number = 891;
update accomp_passp set accomp_passp.count_stored = 0.148 where accomp_passp_number = 892;
update accomp_passp set accomp_passp.count_stored = 0 where accomp_passp_number = 893;
update accomp_passp set accomp_passp.count_stored = 0 where accomp_passp_number = 894;
update accomp_passp set accomp_passp.count_stored = 0.089 where accomp_passp_number = 895;
