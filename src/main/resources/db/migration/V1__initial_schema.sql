create table botanical_species
(
    estimated_days_to_germination integer,
    id                            bigserial   not null,
    name                          varchar(50) not null unique,
    description                   varchar(255),
    primary key (id)
);

create table growing_location
(
    id   bigserial    not null,
    name varchar(255) not null unique,
    primary key (id)
);

create table plant
(
    germination_date    date,
    planting_date       date,
    growing_location_id bigint,
    id                  bigserial not null,
    seed_package_id     bigint,
    primary key (id)
);

create table plant_comment
(
    created_date timestamp(6),
    id           bigserial not null,
    plant_id     bigint,
    text         varchar(255),
    primary key (id)
);

create table seed_package
(
    number_of_seeds      integer,
    botanical_species_id bigint       not null,
    id                   bigserial    not null,
    name                 varchar(255) not null,
    primary key (id)
);

alter table if exists plant
    add constraint FKckllhsnd3v6w87ia436npvrc5
        foreign key (growing_location_id)
            references growing_location;

alter table if exists plant
    add constraint FKqjpe0eb3nhb1k2jj0n15giibf
        foreign key (seed_package_id)
            references seed_package;

alter table if exists plant_comment
    add constraint FKpp3xlrlhpl7ih4j6pbr1j1ouw
        foreign key (plant_id)
            references plant;

alter table if exists seed_package
    add constraint FKbijntwofslapms1kfju6e4ios
        foreign key (botanical_species_id)
            references botanical_species;
