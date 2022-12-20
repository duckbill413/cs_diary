-- call next value for hibernate_sequence;
insert into member(`id`, `name`, `email`, `created_at`, `updated_at`)
values (1, 'martin', 'martin@gmail.com', now(), now());

-- call next value for hibernate_sequence;
insert into member(`id`, `name`, `email`, `created_at`, `updated_at`)
values (2, 'dennis', 'dennis@gmail.com', now(), now());

-- call next value for hibernate_sequence;
insert into member(`id`, `name`, `email`, `created_at`, `updated_at`)
values (3, 'john', 'john@gmail.com', now(), now());

-- call next value for hibernate_sequence;
insert into member(`id`, `name`, `email`, `created_at`, `updated_at`)
values (4, 'sophia', 'sophia@gmail.com', now(), now());

-- call next value for hibernate_sequence;
insert into member(`id`, `name`, `email`, `created_at`, `updated_at`)
values (5, 'martin', 'martin@yahoo.ac.kr', now(), now());

insert into publisher(`id`, `name`) values (1, 'Lets Start');

insert into book (`id`, `name`, `publisher_id`, `deleted`) values (1, 'JPA Master', 1, false);

insert into book (`id`, `name`, `publisher_id`, `deleted`) values (2, 'Spring Security Master', 1, false);

insert into book (`id`, `name`, `publisher_id`, `deleted`) values (3, 'Spring JPA All Master', 1, true);