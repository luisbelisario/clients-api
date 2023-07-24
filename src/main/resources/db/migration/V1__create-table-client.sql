create table client (

    id SERIAL PRIMARY KEY,
    name varchar(100),
    email varchar(100),
    role varchar(100),
    cpf varchar(100),
    phone varchar(100),
    rg varchar(100),
    created_at date,
    updated_at date,
    deleted_at date
);