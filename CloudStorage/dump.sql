--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.5

-- Started on 2020-07-24 23:03:45

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
-- TOC entry 2 (class 3079 OID 24623)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2854 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 16410)
-- Name: tariff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tariff (
    tariff_id integer NOT NULL,
    tariff_name character(50) NOT NULL,
    tariff_limit_mb integer NOT NULL
);


ALTER TABLE public.tariff OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16415)
-- Name: Tariffes_tarriff_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tariff ALTER COLUMN tariff_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Tariffes_tarriff_id_seq"
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 197 (class 1259 OID 16403)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_email character(50) NOT NULL,
    user_name character(50) NOT NULL,
    user_password character(255) NOT NULL,
    user_role character(50),
    user_tariff integer
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16408)
-- Name: Users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Users_user_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 201 (class 1259 OID 24602)
-- Name: file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.file (
    file_id integer NOT NULL,
    path character(255) NOT NULL,
    file_name character(255) NOT NULL,
    size integer,
    user_id integer NOT NULL,
    file_uuid uuid DEFAULT public.uuid_generate_v1() NOT NULL
);


ALTER TABLE public.file OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 24610)
-- Name: file_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.file ALTER COLUMN file_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.file_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 2847 (class 0 OID 24602)
-- Dependencies: 201
-- Data for Name: file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.file (file_id, path, file_name, size, user_id, file_uuid) FROM stdin;
\.


--
-- TOC entry 2845 (class 0 OID 16410)
-- Dependencies: 199
-- Data for Name: tariff; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tariff (tariff_id, tariff_name, tariff_limit_mb) FROM stdin;
102	Супер 300                                         	300
100	Базовый 10Мб                                      	10
101	Домашний 20Мб                                     	20
\.


--
-- TOC entry 2843 (class 0 OID 16403)
-- Dependencies: 197
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (user_id, user_email, user_name, user_password, user_role, user_tariff) FROM stdin;
1	admin@admin.com                                   	Admin                                             	$2a$10$4C6zs79rJFSQAXxHRbIFkOhSr.julP0Y3Ylvy5sb5vJqKY2JReqH6                                                                                                                                                                                                   	admin                                             	102
\.


--
-- TOC entry 2858 (class 0 OID 0)
-- Dependencies: 200
-- Name: Tariffes_tarriff_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Tariffes_tarriff_id_seq"', 102, true);


--
-- TOC entry 2859 (class 0 OID 0)
-- Dependencies: 198
-- Name: Users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Users_user_id_seq"', 14, true);


--
-- TOC entry 2860 (class 0 OID 0)
-- Dependencies: 202
-- Name: file_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.file_file_id_seq', 38, true);


--
-- TOC entry 2714 (class 2606 OID 16414)
-- Name: tariff Tariffes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tariff
    ADD CONSTRAINT "Tariffes_pkey" PRIMARY KEY (tariff_id);


--
-- TOC entry 2711 (class 2606 OID 16407)
-- Name: user Users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT "Users_pkey" PRIMARY KEY (user_id);


--
-- TOC entry 2716 (class 2606 OID 24609)
-- Name: file file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_pkey PRIMARY KEY (file_id);


--
-- TOC entry 2718 (class 2606 OID 24644)
-- Name: file file_uuid_constr; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_uuid_constr UNIQUE (file_uuid);


--
-- TOC entry 2719 (class 1259 OID 24617)
-- Name: fki_FK_user_file; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_FK_user_file" ON public.file USING btree (user_id);


--
-- TOC entry 2712 (class 1259 OID 24601)
-- Name: fki_user_tariff; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_user_tariff ON public."user" USING btree (user_tariff);


--
-- TOC entry 2721 (class 2606 OID 24645)
-- Name: file FK_user_file; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT "FK_user_file" FOREIGN KEY (user_id) REFERENCES public."user"(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2720 (class 2606 OID 24596)
-- Name: user user_tariff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_tariff FOREIGN KEY (user_tariff) REFERENCES public.tariff(tariff_id);


--
-- TOC entry 2855 (class 0 OID 0)
-- Dependencies: 199
-- Name: TABLE tariff; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT ON TABLE public.tariff TO nordic;


--
-- TOC entry 2856 (class 0 OID 0)
-- Dependencies: 197
-- Name: TABLE "user"; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public."user" TO nordic;


--
-- TOC entry 2857 (class 0 OID 0)
-- Dependencies: 201
-- Name: TABLE file; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.file TO nordic;


-- Completed on 2020-07-24 23:03:48

--
-- PostgreSQL database dump complete
--

