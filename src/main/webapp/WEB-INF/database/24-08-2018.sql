-- Database: cwpc
-- DROP DATABASE cwpc;
CREATE DATABASE cwpc
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;



-- SCHEMA: cwpc
-- DROP SCHEMA cwpc ;
CREATE SCHEMA cwpc
    AUTHORIZATION postgres;



-- SEQUENCE: cwpc.contact_id_seq
-- DROP SEQUENCE cwpc.contact_id_seq;
CREATE SEQUENCE cwpc.contact_id_seq;
ALTER SEQUENCE cwpc.contact_id_seq
    OWNER TO postgres;



-- SEQUENCE: cwpc.keyword_id_seq
-- DROP SEQUENCE cwpc.keyword_id_seq;
CREATE SEQUENCE cwpc.keyword_id_seq;
ALTER SEQUENCE cwpc.keyword_id_seq
    OWNER TO postgres;



-- SEQUENCE: cwpc.profile_id_seq
-- DROP SEQUENCE cwpc.profile_id_seq;
CREATE SEQUENCE cwpc.profile_id_seq;
ALTER SEQUENCE cwpc.profile_id_seq
    OWNER TO postgres;



-- SEQUENCE: cwpc.searchresult_id_seq
-- DROP SEQUENCE cwpc.searchresult_id_seq;
CREATE SEQUENCE cwpc.searchresult_id_seq;
ALTER SEQUENCE cwpc.searchresult_id_seq
    OWNER TO postgres;



-- SEQUENCE: cwpc.url_id_seq
-- DROP SEQUENCE cwpc.url_id_seq;
CREATE SEQUENCE cwpc.url_id_seq;
ALTER SEQUENCE cwpc.url_id_seq
    OWNER TO postgres;



-- Table: cwpc.profile
-- DROP TABLE cwpc.profile;
CREATE TABLE cwpc.profile
(
    id bigint NOT NULL DEFAULT nextval('cwpc.profile_id_seq'::regclass),
    created timestamp without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    updated timestamp without time zone,
    CONSTRAINT profile_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.profile
    OWNER to postgres;



-- Table: cwpc.searchresult
-- DROP TABLE cwpc.searchresult;
CREATE TABLE cwpc.searchresult
(
    id bigint NOT NULL DEFAULT nextval('cwpc.searchresult_id_seq'::regclass),
    countrycode character varying(255) COLLATE pg_catalog."default",
    created timestamp without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    updated timestamp without time zone,
    CONSTRAINT searchresult_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.searchresult
    OWNER to postgres;



-- Table: cwpc.contact
-- DROP TABLE cwpc.contact;
CREATE TABLE cwpc.contact
(
    id bigint NOT NULL DEFAULT nextval('cwpc.contact_id_seq'::regclass),
    created timestamp without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    updated timestamp without time zone,
    search_result_id bigint,
    CONSTRAINT contact_pkey PRIMARY KEY (id),
    CONSTRAINT fkoovvqijv39ixc76w9ger7xiv3 FOREIGN KEY (search_result_id)
        REFERENCES cwpc.searchresult (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.contact
    OWNER to postgres;



-- Table: cwpc.keyword
-- DROP TABLE cwpc.keyword;
CREATE TABLE cwpc.keyword
(
    id bigint NOT NULL DEFAULT nextval('cwpc.keyword_id_seq'::regclass),
    created timestamp without time zone,
    keyword character varying(255) COLLATE pg_catalog."default",
    updated timestamp without time zone,
    profile_id bigint,
    search_result_id bigint,
    CONSTRAINT keyword_pkey PRIMARY KEY (id),
    CONSTRAINT fkcrreyf2cagr7v9r9c5l3q4wio FOREIGN KEY (profile_id)
        REFERENCES cwpc.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fks9m0gkqyrlpfre4d1utmciyfi FOREIGN KEY (search_result_id)
        REFERENCES cwpc.searchresult (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.keyword
    OWNER to postgres;



-- Table: cwpc.url
-- DROP TABLE cwpc.url;
CREATE TABLE cwpc.url
(
    id bigint NOT NULL DEFAULT nextval('cwpc.url_id_seq'::regclass),
    created timestamp without time zone,
    similarity integer NOT NULL,
    updated timestamp without time zone,
    url character varying(255) COLLATE pg_catalog."default",
    profile_id bigint,
    search_result_id bigint,
    CONSTRAINT url_pkey PRIMARY KEY (id),
    CONSTRAINT fkajw92ewnxjjy1ok4g85mcqh0n FOREIGN KEY (profile_id)
        REFERENCES cwpc.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkanwh93ffdcsijwgqtk0uapiw8 FOREIGN KEY (search_result_id)
        REFERENCES cwpc.searchresult (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.url
    OWNER to postgres;