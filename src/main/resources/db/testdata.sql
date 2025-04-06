-- Testdata to be initialized for convenience if needed for integration testing

insert into lab_employees(id, name, birth_year)
values (1, 'Simon Lundgren', 1994);
insert into lab_employees(id, name, birth_year)
values (2, 'Kent Beck', 1968);
insert into lab_employees(id, name, birth_year)
values (3, 'Martin Fowler', 1956);
insert into lab_employees(id, name, birth_year)
values (4, 'Nicholas Cage', 1962);

insert into lab_sites(id, name, address, postal_code, postal_area, property_designation)
values (1, 'Sjömakarhuset', 'Storgatan 5', 80310, 'Sandviken', 'SANDVIKEN 24:10');
insert into lab_sites(id, name, address, postal_code, postal_area, property_designation)
values (2, 'Tullhuset', 'Gurkgatan 12', 80209, 'Sandviken', 'SANDVIKEN 1:15');

insert into lab_rooms(id, size_in_sqm, description, site_id)
values (1, 15.0, 'Huvudköket', 1);
insert into lab_rooms(id, size_in_sqm, description, site_id)
values (2, 5.0, 'Toaletten', 1);
insert into lab_rooms(id, size_in_sqm, description, site_id)
values (3, 45.0, 'Konferensrummet', 2);

insert into lab_cleaning_orders(id, employee_id, room_id, time_scheduled, time_finished, order_status)
values (1, 1, 1, '2025-05-18T13:00:00', '2025-05-18T15:00:00', 'FINISHED');
insert into lab_cleaning_orders(id, employee_id, room_id, time_scheduled, time_finished, order_status)
values (2, 2, 2, '2025-05-18T14:00:00', '2025-05-18T16:00:00', 'FINISHED');
insert into lab_cleaning_orders(id, employee_id, room_id, time_scheduled, time_finished, order_status)
values (3, 2, 3, '2025-05-18T14:00:00', null, 'SCHEDULED');




