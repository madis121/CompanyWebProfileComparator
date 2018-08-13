-- SEQUENCE: cwpc.profile_id_seq
-- DROP SEQUENCE cwpc.profile_id_seq;
CREATE SEQUENCE cwpc.profile_id_seq;
ALTER SEQUENCE cwpc.profile_id_seq OWNER TO postgres;



-- SEQUENCE: cwpc.keyword_id_seq
-- DROP SEQUENCE cwpc.keyword_id_seq;
CREATE SEQUENCE cwpc.keyword_id_seq;
ALTER SEQUENCE cwpc.keyword_id_seq OWNER TO postgres;



-- SEQUENCE: cwpc.url_id_seq
-- DROP SEQUENCE cwpc.url_id_seq;
CREATE SEQUENCE cwpc.url_id_seq;
ALTER SEQUENCE cwpc.url_id_seq OWNER TO postgres;



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
ALTER TABLE cwpc.profile OWNER to postgres;



-- Table: cwpc.keyword
-- DROP TABLE cwpc.keyword;
CREATE TABLE cwpc.keyword
(
    id bigint NOT NULL DEFAULT nextval('cwpc.keyword_id_seq'::regclass),
    added timestamp without time zone,
    word character varying(255) COLLATE pg_catalog."default",
    profile_id bigint NOT NULL,
    CONSTRAINT keyword_pkey PRIMARY KEY (id),
    CONSTRAINT fkcrreyf2cagr7v9r9c5l3q4wio FOREIGN KEY (profile_id)
        REFERENCES cwpc.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
ALTER TABLE cwpc.keyword OWNER to postgres;



-- Table: cwpc.url
-- DROP TABLE cwpc.url;
CREATE TABLE cwpc.url
(
    id bigint NOT NULL DEFAULT nextval('cwpc.url_id_seq'::regclass),
    url character varying(255) COLLATE pg_catalog."default",
    visited timestamp without time zone,
    profile_id bigint NOT NULL,
    CONSTRAINT url_pkey PRIMARY KEY (id),
    CONSTRAINT fkajw92ewnxjjy1ok4g85mcqh0n FOREIGN KEY (profile_id)
        REFERENCES cwpc.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
ALTER TABLE cwpc.url OWNER to postgres;