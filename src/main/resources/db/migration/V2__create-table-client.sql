create table client (

    id SERIAL PRIMARY KEY,
    cpf varchar(100),
    phone varchar(100),
    rg varchar(100),
    person_id bigint,

    CONSTRAINT fk_person
        FOREIGN KEY(person_id)
            REFERENCES person(id)
);