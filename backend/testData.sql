insert into postgres.public.hospital
values (1, 'улица1', 'поликлиника №1');
insert into postgres.public.hospital
values (2, 'улица2', 'поликлиника №2');
insert into postgres.public.hospital
values (3, 'улица3', 'поликлиника №3');
insert into postgres.public.hospital
values (4, 'улица4', 'поликлиника №4');

insert into postgres.public.doctor
values (1, 'Тарасов', 'Олег', 'SURGEON', 'Вениаминович');
insert into postgres.public.doctor
values (2, 'Пупкин', 'Василий', 'GYNECOLOGIST', 'Александрович');
insert into postgres.public.doctor
values (3, 'Пупкина', 'Акакия', 'UROLOGIST', 'Акакьевна');
insert into postgres.public.doctor
values (4, 'Васильев', 'Николай', 'PEDIATRICIAN', 'Евгениевич');

insert into postgres.public.doctor_hospitals
values (1, 2);
insert into postgres.public.doctor_hospitals
values (1, 4);
insert into postgres.public.doctor_hospitals
values (2, 3);
insert into postgres.public.doctor_hospitals
values (3, 1);
insert into postgres.public.doctor_hospitals
values (4, 1);

insert into postgres.public.patient
values (1, 20, 'Вотинцев', 'Евгений', 'Вячеславович');
insert into postgres.public.patient
values (2, 20, 'Просекин', 'Владислав', 'Николаевич');
insert into postgres.public.patient
values (3, 20, 'Воробьев', 'Дмитрий', 'Олегович');

insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (1, '18:00', 'MONDAY', '9:00', '1 hour', 3, 1, 'permanent');
insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (2, '15:00', 'TUESDAY', '8:00', '1 hour', 3, 1, 'permanent');
insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (3, '15:00', 'WEDNESDAY', '10:00', '1 hour', 3, 1, 'permanent');
insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (4, '13:00', 'THURSDAY', '8:00', '1 hour', 3, 1, 'permanent');
insert into postgres.public.schedule (id, day_end, date, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (5, '13:00', '2021-6-15', '10:00', '1 hour', 3, 1, 'extra');

insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (6, '12:00', 'MONDAY', '8:00', '2 hour', 1, 2, 'permanent');
insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (7, '17:00', 'MONDAY', '14:00', '1 hour', 1, 4, 'permanent');



