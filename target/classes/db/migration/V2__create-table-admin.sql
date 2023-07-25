create table admin (

    id SERIAL PRIMARY KEY,
    name varchar(100),
    email varchar(100),
    role varchar(100),
    cpf_cnpj varchar(100),
    created_at date,
    updated_at date,
    deleted_at date
);