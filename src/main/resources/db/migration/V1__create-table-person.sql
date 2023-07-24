create table person (

    id SERIAL PRIMARY KEY,
    name varchar(100),
    email varchar(100),
    role varchar(100),
    createdAt date,
    updatedAt date,
    deletedAt date
);