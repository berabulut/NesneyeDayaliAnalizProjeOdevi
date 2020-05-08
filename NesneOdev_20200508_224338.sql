--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12rc1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: NesneOdev; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "NesneOdev" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE "NesneOdev" OWNER TO postgres;

\connect "NesneOdev"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Kullanicilar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Kullanicilar" (
    "kullaniciAdi" character varying,
    sifre character varying,
    "kullaniciNo" character varying NOT NULL,
    "SogutucuDurumu" character varying
);


ALTER TABLE public."Kullanicilar" OWNER TO postgres;

--
-- Name: Uyarilar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Uyarilar" (
    uyari character varying(2044) NOT NULL
);


ALTER TABLE public."Uyarilar" OWNER TO postgres;

--
-- Data for Name: Kullanicilar; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."Kullanicilar" VALUES ('Deneme', 'Deneme', '1', 'true');
INSERT INTO public."Kullanicilar" VALUES ('bera', 'bulut', '2', 'false');


--
-- Data for Name: Uyarilar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: Kullanicilar kullaniciPK; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Kullanicilar"
    ADD CONSTRAINT "kullaniciPK" PRIMARY KEY ("kullaniciNo");


--
-- PostgreSQL database dump complete
--

