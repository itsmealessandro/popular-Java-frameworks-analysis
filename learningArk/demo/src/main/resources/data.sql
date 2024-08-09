create table if not exists Ingredient (
    id varchar(4) not null primary key,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists Taco (
    id identity not null primary key,
    name varchar(50) not null,
    date timestamp not null
);

create table if not exists Taco_Ingredients (
    taco bigint not null,
    ingredient varchar(4) not null
);

alter table
    Taco_Ingredients
add
    foreign key (taco) references Taco(id);

alter table
    Taco_Ingredients
add
    foreign key (ingredient) references Ingredient(id);

create table if not exists Taco_Order (
    id identity,
    deliveryName varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity varchar(50) not null,
    deliveryState varchar(2) not null,
    deliveryZip varchar(10) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCVV varchar(3) not null,
    placedAt timestamp not null
);

create table if not exists Taco_Order_Tacos (
    tacoOrder bigint not null,
    taco bigint not null
);

alter table
    Taco_Order_Tacos
add
    foreign key (tacoOrder) references Taco_Order(id);

alter table
    Taco_Order_Tacos
add
    foreign key (taco) references Taco(id);

delete from
    Taco_Order_Tacos;

delete from
    Taco_Ingredients;

delete from
    Taco;

delete from
    Taco_Order;

delete from
    Ingredient;

insert into
    Ingredient (id, name, type)
values
    ('FLTO', 'Flour Tortilla', 'WRAP');

insert into
    Ingredient (id, name, type)
values
    ('COTO', 'Corn Tortilla', 'WRAP');

insert into
    Ingredient (id, name, type)
values
    ('GRBF', 'Ground Beef', 'PROTEIN');

insert into
    Ingredient (id, name, type)
values
    ('CARN', 'Carnitas', 'PROTEIN');

insert into
    Ingredient (id, name, type)
values
    ('TMTO', 'Diced Tomatoes', 'VEGGIES');

insert into
    Ingredient (id, name, type)
values
    ('LETC', 'Lettuce', 'VEGGIES');

insert into
    Ingredient (id, name, type)
values
    ('CHED', 'Cheddar', 'CHEESE');

insert into
    Ingredient (id, name, type)
values
    ('JACK', 'Monterrey Jack', 'CHEESE');

insert into
    Ingredient (id, name, type)
values
    ('SLSA', 'Salsa', 'SAUCE');

insert into
    Ingredient (id, name, type)
values
    ('SRCR', 'Sour Cream', 'SAUCE');