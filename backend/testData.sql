insert into postgres.public.hospital
values (1, 'улица1', 'поликлиника №1') on conflict do nothing;
insert into postgres.public.hospital
values (2, 'улица2', 'поликлиника №2') on conflict do nothing;
insert into postgres.public.hospital
values (3, 'улица3', 'поликлиника №3') on conflict do nothing;
insert into postgres.public.hospital
values (4, 'улица4', 'поликлиника №4') on conflict do nothing;

insert into postgres.public.doctor
values (1, 'Тарасов', 'Олег', 'SURGEON', 'Вениаминович') on conflict do nothing;
insert into postgres.public.doctor
values (2, 'Пупкин', 'Василий', 'GYNECOLOGIST', 'Александрович') on conflict do nothing;
insert into postgres.public.doctor
values (3, 'Пупкина', 'Акакия', 'UROLOGIST', 'Акакьевна') on conflict do nothing;
insert into postgres.public.doctor
values (4, 'Васильев', 'Николай', 'PEDIATRICIAN', 'Евгениевич') on conflict do nothing;
insert into postgres.public.doctor
values (5, 'Нефедов', 'Флексей', 'PEDIATRICIAN', 'Иванович') on conflict do nothing;

insert into postgres.public.doctor_hospitals
values (1, 2) on conflict do nothing;
insert into postgres.public.doctor_hospitals
values (1, 4) on conflict do nothing;
insert into postgres.public.doctor_hospitals
values (2, 3) on conflict do nothing;
insert into postgres.public.doctor_hospitals
values (3, 1) on conflict do nothing;
insert into postgres.public.doctor_hospitals
values (4, 1) on conflict do nothing;
insert into postgres.public.doctor_hospitals
values (5, 1) on conflict do nothing;

insert into postgres.public.patient
values (1, 20, 'Вотинцев', 'Евгений', 'Вячеславович') on conflict do nothing;
insert into postgres.public.patient
values (2, 20, 'Просекин', 'Владислав', 'Николаевич') on conflict do nothing;
insert into postgres.public.patient
values (3, 20, 'Воробьев', 'Дмитрий', 'Олегович') on conflict do nothing;

insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (1, '18:00', 'MONDAY', '9:00', '1 hour', 3, 1, 'permanent') on conflict do nothing;
insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (2, '15:00', 'TUESDAY', '8:00', '1 hour', 3, 1, 'permanent') on conflict do nothing;
insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (3, '15:00', 'WEDNESDAY', '10:00', '1 hour', 3, 1, 'permanent') on conflict do nothing;
insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (4, '13:00', 'THURSDAY', '8:00', '1 hour', 3, 1, 'permanent') on conflict do nothing;
insert into postgres.public.schedule (id, day_end, date, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (5, '13:00', '2021-6-15', '10:00', '1 hour', 3, 1, 'extra') on conflict do nothing;

insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (6, '12:00', 'MONDAY', '8:00', '2 hour', 1, 2, 'permanent') on conflict do nothing;
insert into postgres.public.schedule (id, day_end, day_of_week, day_start, appointment_duration, doctor_id, hospital_id,
                                      type)
values (7, '17:00', 'MONDAY', '14:00', '1 hour', 1, 4, 'permanent') on conflict do nothing;



