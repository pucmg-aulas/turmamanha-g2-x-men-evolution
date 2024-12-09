--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2024-12-09 07:58:04

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- TOC entry 217 (class 1259 OID 24754)
-- Name: clients; Type: TABLE; Schema: public; Owner: LPM
--

CREATE TABLE public.clients (
    id uuid NOT NULL,
    name character varying(255),
    cpf character varying(14)
);


ALTER TABLE public.clients OWNER TO "LPM";

--
-- TOC entry 221 (class 1259 OID 24826)
-- Name: historical; Type: TABLE; Schema: public; Owner: LPM
--

CREATE TABLE public.historical (
    client_cpf character varying(14) NOT NULL,
    client_name character varying(255) NOT NULL,
    vehicle_plate character varying(255) NOT NULL,
    spot_id character varying(255) NOT NULL,
    parking_lot_name character varying(255) NOT NULL,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone,
    amount_paid double precision
);


ALTER TABLE public.historical OWNER TO "LPM";

--
-- TOC entry 219 (class 1259 OID 24766)
-- Name: parking_lots; Type: TABLE; Schema: public; Owner: LPM
--

CREATE TABLE public.parking_lots (
    name character varying(255) NOT NULL,
    number_of_spots integer
);


ALTER TABLE public.parking_lots OWNER TO "LPM";

--
-- TOC entry 220 (class 1259 OID 24771)
-- Name: parking_spots; Type: TABLE; Schema: public; Owner: LPM
--

CREATE TABLE public.parking_spots (
    id character varying(50) NOT NULL,
    type character varying(50),
    vehicle_placa character varying(10),
    parking_lot_name character varying(255),
    "position" character varying(255),
    start_time timestamp without time zone
);


ALTER TABLE public.parking_spots OWNER TO "LPM";

--
-- TOC entry 218 (class 1259 OID 24759)
-- Name: vehicles; Type: TABLE; Schema: public; Owner: LPM
--

CREATE TABLE public.vehicles (
    placa character varying(10) NOT NULL,
    model character varying(255),
    color character varying(50),
    owner character varying(255),
    cpf character varying(14)
);


ALTER TABLE public.vehicles OWNER TO "LPM";

--
-- TOC entry 3799 (class 2606 OID 24758)
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: LPM
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- TOC entry 3803 (class 2606 OID 24770)
-- Name: parking_lots parking_lots_pkey; Type: CONSTRAINT; Schema: public; Owner: LPM
--

ALTER TABLE ONLY public.parking_lots
    ADD CONSTRAINT parking_lots_pkey PRIMARY KEY (name);


--
-- TOC entry 3805 (class 2606 OID 24787)
-- Name: parking_spots parking_spots_pkey; Type: CONSTRAINT; Schema: public; Owner: LPM
--

ALTER TABLE ONLY public.parking_spots
    ADD CONSTRAINT parking_spots_pkey PRIMARY KEY (id);


--
-- TOC entry 3807 (class 2606 OID 24789)
-- Name: parking_spots unique_id_parking_lot; Type: CONSTRAINT; Schema: public; Owner: LPM
--

ALTER TABLE ONLY public.parking_spots
    ADD CONSTRAINT unique_id_parking_lot UNIQUE (id, parking_lot_name);


--
-- TOC entry 3801 (class 2606 OID 24765)
-- Name: vehicles vehicles_pkey; Type: CONSTRAINT; Schema: public; Owner: LPM
--

ALTER TABLE ONLY public.vehicles
    ADD CONSTRAINT vehicles_pkey PRIMARY KEY (placa);


--
-- TOC entry 3808 (class 2606 OID 24781)
-- Name: parking_spots parking_spots_parking_lot_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: LPM
--

ALTER TABLE ONLY public.parking_spots
    ADD CONSTRAINT parking_spots_parking_lot_name_fkey FOREIGN KEY (parking_lot_name) REFERENCES public.parking_lots(name);


--
-- TOC entry 3809 (class 2606 OID 24776)
-- Name: parking_spots parking_spots_vehicle_placa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: LPM
--

ALTER TABLE ONLY public.parking_spots
    ADD CONSTRAINT parking_spots_vehicle_placa_fkey FOREIGN KEY (vehicle_placa) REFERENCES public.vehicles(placa);


-- Completed on 2024-12-09 07:58:08

--
-- PostgreSQL database dump complete
--

