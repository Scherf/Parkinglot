create table CarPark (
                         id  bigserial not null,
                         name varchar(255),
                         owner bytea,
                         primary key (id)
)