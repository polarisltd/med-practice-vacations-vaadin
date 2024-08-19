

# postgreSql create table

create table public.vacations
(
id            bigserial primary key,
date_end      date not null,
date_start    date not null,
description   varchar(255),
person_id     integer not null,
vacation_type char not null,
constraint fk_person foreign key (person_id) references public.vacations_contact (id)
);


create table public.vacations_contact
(
id         bigserial
primary key,
balance    numeric(38, 2) not null,
email      varchar(255),
first_name varchar(255),
last_name  varchar(255)
);


-- Add foreign key constraint as a separate DDL statement
alter table public.vacations
add constraint fk_person foreign key (person_id) references public.vacations_contact (id);

-- Set the owner of the tables
alter table public.vacations owner to kolposkopija;
alter table public.vacations_contact owner to kolposkopija;


