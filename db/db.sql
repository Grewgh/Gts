CREATE DATABASE rkpp ENCODING 'UTF-8';


CREATE TABLE IF NOT EXISTS gts (
  id   serial PRIMARY KEY ,
  name VARCHAR(25) UNIQUE NOT NULL

);

--INSERT INTO gts (name) VALUES ('Gts_1');


CREATE TABLE IF NOT EXISTS ats (
  id       serial primary key ,
  name     VARCHAR(25) UNIQUE NOT NULL,
  id_gts INTEGER NOT NULL ,
  FOREIGN KEY (id_gts) REFERENCES gts (id)
 );

--INSERT INTO  ats (name, id_gts) VALUES ('ats_1', 1);



CREATE TABLE IF NOT EXISTS city_ats (
      id       serial primary key ,
      id_ats INTEGER NOT NULL,
      name varchar(25) UNIQUE NOT NULL ,
      FOREIGN KEY (id_ats) REFERENCES ats (id)
      --id_region int NOT NULL

);

--INSERT INTO  city_ats (type_ats) VALUES (1);


CREATE TABLE IF NOT EXISTS institutional_ats (
    id serial primary key ,
    id_ats INTEGER NOT NULL,
    name varchar(25) UNIQUE NOT NULL ,
    FOREIGN KEY (id_ats) REFERENCES ats (id)

);


CREATE TABLE IF NOT EXISTS departmental_ats (
    id serial primary key ,
    id_ats INTEGER NOT NULL,
    name varchar(25) UNIQUE NOT NULL ,
    FOREIGN KEY (id_ats) REFERENCES ats (id)

);


CREATE TABLE IF NOT EXISTS region(
                                       id serial PRIMARY KEY,
                                       name varchar(25) NOT NULL,
                                       id_city_ats INTEGER NOT NULL ,
                                       FOREIGN KEY (id_city_ats) REFERENCES city_ats (id)

  );

CREATE TABLE IF NOT EXISTS street(
                                     id serial PRIMARY KEY,
                                     name varchar(25) NOT NULL,
                                     id_region INTEGER NOT NULL ,
                                     FOREIGN KEY (id_region) REFERENCES region (id)

);

CREATE TABLE IF NOT EXISTS house(
                                     id serial PRIMARY KEY,
                                     name varchar(25) NOT NULL,
                                     id_street INTEGER NOT NULL ,
                                     FOREIGN KEY (id_street) REFERENCES street (id)

);

CREATE TABLE IF NOT EXISTS apartment(
                                          id serial PRIMARY KEY,
                                          name varchar(25) NOT NULL,
                                          id_house INTEGER NOT NULL ,
                                          FOREIGN KEY (id_house) REFERENCES house (id)

  );

CREATE TABLE IF NOT EXISTS telephone_number(
                                        id serial PRIMARY KEY,
                                        number INTEGER NOT NULL,
                                        status BOOLEAN NOT NULL ,
                                        type_ats INTEGER NOT NULL,
                                        FOREIGN KEY (type_ats) REFERENCES city_ats (id)

);

CREATE TABLE IF NOT EXISTS connection(
                                        id serial PRIMARY KEY,
                                        disconnection_date date ,
                                        connection_date date NOT NULL ,
                                        id_apartment integer not null,
                                        type_telephone varchar(25) not null,
                                        status boolean not null,
                                        id_number integer not null,
                                        id_client integer not null,
                                        FOREIGN KEY (id_apartment) REFERENCES apartment (id),
                                        FOREIGN KEY (id_number) REFERENCES telephone_number (id),
                                        FOREIGN KEY (id_client) REFERENCES client (id)

);

CREATE TABLE IF NOT EXISTS exemption(
                                       id serial PRIMARY KEY,
                                       name varchar(25) NOT NULL,
                                       percent INTEGER NOT NULL

  );

CREATE TABLE IF NOT EXISTS client(
                                     id serial PRIMARY KEY,
                                     surname varchar(25) NOT NULL,
                                     name varchar(25) NOT NULL,
                                     middlename varchar(25) NOT NULL,
                                     gender varchar(25) NOT NULL,
                                     age INTEGER NOT NULL ,
                                     id_exemption INTEGER,
                                     FOREIGN KEY (id_exemption) REFERENCES exemption (id)

);

CREATE TABLE IF NOT EXISTS receipt(
                                     id serial PRIMARY KEY,
                                     id_con INTEGER NOT NULL,
                                     accrual_date date NOT NULL,
                                     sum FLOAT NOT NULL,
                                     status boolean NOT NULL,
                                     payment_date date,
                                     FOREIGN KEY (id_con) REFERENCES connection (id)

);

CREATE TABLE IF NOT EXISTS tariff(
                                     id serial PRIMARY KEY,
                                     day_night varchar (25) NOT NULL,
                                     type VARCHAR (25) NOT NULL,
                                     price_minute FLOAT NOT NULL

);

CREATE TABLE IF NOT EXISTS call(
                                     id serial PRIMARY KEY,
                                     id_con INTEGER NOT NULL,
                                     call_start timestamp NOT NULL,
                                     call_stop timestamp NOT NULL,
                                     id_tariff INTEGER NOT NULL,
                                     call_duration varchar (25) NOT NULL ,
                                     sum FLOAT NOT NULL,
                                     FOREIGN KEY (id_con) REFERENCES connection (id),
                                     FOREIGN KEY (id_tariff) REFERENCES tariff (id)

);

CREATE TABLE IF NOT EXISTS receipt_call(
                                     id serial PRIMARY KEY,
                                     id_receipt INTEGER NOT NULL,
                                     id_call INTEGER NOT NULL,
                                     FOREIGN KEY (id_receipt) REFERENCES connection (id),
                                     FOREIGN KEY (id_call) REFERENCES connection (id)

);

CREATE TABLE IF NOT EXISTS queue(
                                     id serial PRIMARY KEY,
                                     id_client INTEGER NOT NULL,
                                     queue_date DATE NOT NULL,
                                     exemption boolean NOT NULL,
                                     FOREIGN KEY (id_client) REFERENCES client (id)

);

CREATE TABLE IF NOT EXISTS payphone(
                                     id serial PRIMARY KEY,
                                     id_house INTEGER NOT NULL,
                                     id_telephone_number INTEGER NOT NULL,
                                     FOREIGN KEY (id_telephone_number) REFERENCES telephone_number (id),
                                     FOREIGN KEY (id_house) REFERENCES house (id)

);









CREATE OR REPLACE FUNCTION street (IN integer)
      RETURNS TABLE (id integer , name text, id_region integer ) AS
   $BODY$
      select * from street where id_region = $1
   $BODY$
      LANGUAGE 'sql' VOLATILE
      COST 100
      ROWS 1000;
   ALTER FUNCTION street (integer) OWNER TO postgres;

select * from street(71);

CREATE OR REPLACE FUNCTION streetid(integer)
           RETURNS integer AS
   $BODY$
           SELECT id+id
           FROM street
           WHERE id = $1;
   $BODY$
        LANGUAGE 'sql' VOLATILE

        select * from streetid (62);


