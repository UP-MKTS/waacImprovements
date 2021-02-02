insert into roles values (null, 'admin'), (null, 'editor'), (null, 'supervisor'), (null, 'user');

insert into departments (department_full_name, department_short_name) values ('Район тепловых сетей №1', 'РТС-1');
insert into departments (department_full_name, department_short_name) values ('Район тепловых сетей №2', 'РТС-2');
insert into departments (department_full_name, department_short_name) values ('Район тепловых сетей №3', 'РТС-3');
insert into departments (department_full_name, department_short_name) values ('Район тепловых сетей №4', 'РТС-4');
insert into departments (department_full_name, department_short_name) values ('Район тепловых сетей №5', 'РТС-5');
insert into departments (department_full_name, department_short_name) values ('Район тепловых сетей №6', 'РТС-6');
insert into departments (department_full_name, department_short_name) values ('Район тепловых сетей №7', 'РТС-7');
insert into departments (department_full_name, department_short_name) values ('Экологическая лаборатория', 'ЭЛ');
insert into departments (department_full_name, department_short_name) values ('Служба АСУиС', 'САСУ и С');
insert into departments (department_full_name, department_short_name) values ('Транспортный участок', 'ТУ');
insert into departments (department_full_name, department_short_name) values ('Производственный цех', 'ПЦ');
insert into departments (department_full_name, department_short_name) values ('Топливно-заготовительный учаток', 'ТЗУ');
insert into departments (department_full_name, department_short_name) values ('Аварийно-ремонтная служба', 'АРС');
insert into departments (department_full_name, department_short_name) values ('Служба главного метролога', 'СГМ');
insert into departments (department_full_name, department_short_name) values ('Служба электрохозяйства', 'СЭХ');
insert into departments (department_full_name, department_short_name) values ('Центральный склад', 'ЦС');
insert into departments (department_full_name, department_short_name) values ('Административно-хозяйственный отдел', 'АХО');

insert into users (user_fio, user_name, password, email, department_id, activated, role_id, login_status)
    values ('Мацкевич Сергей Михайлович', 'admin', '$2a$10$.fuPdNstUAfcHp7ONNVwfeJpKhluTrSKZklNFloeVmA7nGzSCAY.e', 'matskevich@mkts.by', 9, 1, 1, 0);

insert into users (user_fio, user_name, password, email, department_id, activated, role_id, login_status)
    values ('Фиало Елена Сергеевна', 'ecolog', '$2a$10$ctFvF9KpiWSAP9dSd6lB7uT4KMEYnY9ClmUg/weHIu8f9scWZrDZ2', 'ecolog@mkts.by', 8, 1, 2, 0);

insert into users (user_fio, user_name, password, email, department_id, activated, role_id, login_status)
    values ('Тестовый', 'test', '$2a$10$/bbMN4CJqPdkB5y8dtQizeAdI3GlovXc2lSrD1bwJmG5F0CEv2QJ2', 'test@mkts.by', 9, 0, 4, 0);

insert into persons (person_fio, person_position, department_id, order_number, order_date) values ('В.А. Иванов', 'Зам. начальника РТС-1', '1', 'Приказ 755', '05.10.2017');

insert into organizations (organization_name, organization_address) values ('УП "Минсккоммунтеплосеть"', '220049, г. Минск, ул. Волгоградская, 12, телефон 389-28-26');
insert into organizations (organization_name, organization_address, waste_destination) values ('КУП "Экорес"', '220075, г. Минск, ул. Селицкого, 35', 'Полигон "Тростенецкий" - ул. Павловского, 7');

insert into goals (goal_name) values ('Использование');
insert into goals (goal_name) values ('Обезвреживание');
insert into goals (goal_name) values ('Хранение');
insert into goals (goal_name) values ('Захоронение');

insert into dates (month, year) value (9, 2020);

insert into dangerous_pow (pow_name) values ('Чрезвычайно опасные');
insert into dangerous_pow (pow_name) values ('Умеренно опасные');

insert into dangerous_class (class_name) values ('1');
insert into dangerous_class (class_name) values ('2');
insert into dangerous_class (class_name) values ('3');
insert into dangerous_class (class_name) values ('4');
insert into dangerous_class (class_name) values ('н/о');

insert into activity_kinds (activity_kind_name) values ('Замена по техническому регламенту');
insert into activity_kinds (activity_kind_name) values ('Использование местных видов топлива');
insert into activity_kinds (activity_kind_name) values ('Ремонт электрооборудования');

insert into waste_types (waste_code, waste_name, dangerous_pow_id, dangerous_class_id, activity_kind_id, waste_norm)
    values (3532201, 'Свинцовые аккумуляторы отработанные неповрежденные с неслитым электролитом', 1, 1, 1, 'Не задано');
