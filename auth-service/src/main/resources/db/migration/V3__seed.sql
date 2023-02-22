insert into employees(tab_num, password, first_name, last_name, patronymic, email,
position_name, account_locked,account_enabled)
values (300047, '$2a$10$sIFR/ye7w95cRxMQhaMdGutZkjxkbTmRaTY5Gho9V6ihPPlhfV6Ne', 'Игорь', 'Чернышов', null, 'igorchernyshov@yandex.ru', 'Developer', false, true);

insert into employees(tab_num, password, first_name, last_name, patronymic, email,
                      position_name, account_locked,account_enabled)
values (100000, '$2a$10$sIFR/ye7w95cRxMQhaMdGutZkjxkbTmRaTY5Gho9V6ihPPlhfV6Ne', 'Тест', 'Тестов', 'Тестович', 'igorchernyshov@yandex.ru', 'Test', false, true);

insert into employee_authorities(employee_id, authority_id)
select id, 'AUTH:ADMIN' from employees where tab_num = 300047;



