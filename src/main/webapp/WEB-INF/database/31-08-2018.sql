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



-- SEQUENCE: cwpc.company_profile_id_seq
-- DROP SEQUENCE cwpc.company_profile_id_seq;
CREATE SEQUENCE cwpc.company_profile_id_seq;
ALTER SEQUENCE cwpc.company_profile_id_seq
    OWNER TO postgres;



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



-- SEQUENCE: cwpc.search_result_id_seq
-- DROP SEQUENCE cwpc.search_result_id_seq;
CREATE SEQUENCE cwpc.search_result_id_seq;
ALTER SEQUENCE cwpc.search_result_id_seq
    OWNER TO postgres;



-- SEQUENCE: cwpc.subject_id_seq
-- DROP SEQUENCE cwpc.subject_id_seq;
CREATE SEQUENCE cwpc.subject_id_seq;
ALTER SEQUENCE cwpc.subject_id_seq
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



-- Table: cwpc.search_result
-- DROP TABLE cwpc.search_result;
CREATE TABLE cwpc.search_result
(
    id bigint NOT NULL DEFAULT nextval('cwpc.search_result_id_seq'::regclass),
    country_code character varying(255) COLLATE pg_catalog."default",
    created timestamp without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    updated timestamp without time zone,
    CONSTRAINT search_result_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.search_result
    OWNER to postgres;



-- Table: cwpc.company_profile
-- DROP TABLE cwpc.company_profile;
CREATE TABLE cwpc.company_profile
(
    id bigint NOT NULL DEFAULT nextval('cwpc.company_profile_id_seq'::regclass),
    created timestamp without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default",
    similarity integer NOT NULL,
    updated timestamp without time zone,
    website character varying(255) COLLATE pg_catalog."default",
    search_result_id bigint,
    CONSTRAINT company_profile_pkey PRIMARY KEY (id),
    CONSTRAINT fk7emft9gam7ea75l4p4i5t7jkt FOREIGN KEY (search_result_id)
        REFERENCES cwpc.search_result (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.company_profile
    OWNER to postgres;



-- Table: cwpc.subject
-- DROP TABLE cwpc.subject;
CREATE TABLE cwpc.subject
(
    id bigint NOT NULL DEFAULT nextval('cwpc.subject_id_seq'::regclass),
    created timestamp without time zone,
    field character varying(255) COLLATE pg_catalog."default",
    firstname character varying(255) COLLATE pg_catalog."default",
    lastname character varying(255) COLLATE pg_catalog."default",
    linkedin character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default",
    updated timestamp without time zone,
    company_profile_id bigint,
    CONSTRAINT subject_pkey PRIMARY KEY (id),
    CONSTRAINT fkgkq07h0t4q2m4aik7iu71t50 FOREIGN KEY (company_profile_id)
        REFERENCES cwpc.company_profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.subject
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
    CONSTRAINT fkrdmaxf1oim7cqhfwfqukve8l4 FOREIGN KEY (search_result_id)
        REFERENCES cwpc.search_result (id) MATCH SIMPLE
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
    CONSTRAINT fk5fwik6tkmc54d0h1rq6rq1neo FOREIGN KEY (search_result_id)
        REFERENCES cwpc.search_result (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkcrreyf2cagr7v9r9c5l3q4wio FOREIGN KEY (profile_id)
        REFERENCES cwpc.profile (id) MATCH SIMPLE
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
    updated timestamp without time zone,
    url character varying(255) COLLATE pg_catalog."default",
    profile_id bigint,
    search_result_id bigint,
    CONSTRAINT url_pkey PRIMARY KEY (id),
    CONSTRAINT fkajw92ewnxjjy1ok4g85mcqh0n FOREIGN KEY (profile_id)
        REFERENCES cwpc.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkci7jvam8xc6tpvwa4s6t5dy04 FOREIGN KEY (search_result_id)
        REFERENCES cwpc.search_result (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE cwpc.url
    OWNER to postgres;
