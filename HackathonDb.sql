--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-06-27 15:37:15

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
-- TOC entry 225 (class 1259 OID 24654)
-- Name: aggiornamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.aggiornamento (
    id integer NOT NULL,
    nome text NOT NULL,
    documento text NOT NULL,
    commento text NOT NULL,
    giudice_id text NOT NULL,
    team_id integer NOT NULL
);


ALTER TABLE public.aggiornamento OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 24653)
-- Name: aggiornamento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.aggiornamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.aggiornamento_id_seq OWNER TO postgres;

--
-- TOC entry 4919 (class 0 OID 0)
-- Dependencies: 224
-- Name: aggiornamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.aggiornamento_id_seq OWNED BY public.aggiornamento.id;


--
-- TOC entry 218 (class 1259 OID 24601)
-- Name: giudice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.giudice (
    nome text NOT NULL,
    cognome text NOT NULL,
    email text NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    hackathon_id integer
);


ALTER TABLE public.giudice OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 24673)
-- Name: hackathon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hackathon (
    id integer NOT NULL,
    titolo text NOT NULL,
    sede text,
    problema text,
    data_inizio date,
    data_fine date,
    fine_periodo_prenotazioni date,
    max_iscritti integer,
    max_dim_team integer,
    organizzatore_id text
);


ALTER TABLE public.hackathon OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 24672)
-- Name: hackathon_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hackathon_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.hackathon_id_seq OWNER TO postgres;

--
-- TOC entry 4920 (class 0 OID 0)
-- Dependencies: 226
-- Name: hackathon_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.hackathon_id_seq OWNED BY public.hackathon.id;


--
-- TOC entry 219 (class 1259 OID 24610)
-- Name: organizzatore; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organizzatore (
    nome text NOT NULL,
    cognome text NOT NULL,
    email text NOT NULL,
    username text NOT NULL,
    password text NOT NULL
);


ALTER TABLE public.organizzatore OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24620)
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    id integer NOT NULL,
    nome text NOT NULL,
    mediavoti real,
    hackathon_id integer
);


ALTER TABLE public.team OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 24619)
-- Name: team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.team_id_seq OWNER TO postgres;

--
-- TOC entry 4921 (class 0 OID 0)
-- Dependencies: 220
-- Name: team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_id_seq OWNED BY public.team.id;


--
-- TOC entry 217 (class 1259 OID 24592)
-- Name: utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente (
    nome text NOT NULL,
    cognome text NOT NULL,
    email text NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    team_id integer DEFAULT 0,
    hackathon_id integer DEFAULT 0
);


ALTER TABLE public.utente OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24634)
-- Name: voti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.voti (
    id integer NOT NULL,
    team_id integer NOT NULL,
    voto integer NOT NULL,
    giudice_id text,
    CONSTRAINT voti_voto_check CHECK (((voto >= 0) AND (voto <= 10)))
);


ALTER TABLE public.voti OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 24633)
-- Name: voti_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.voti_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.voti_id_seq OWNER TO postgres;

--
-- TOC entry 4922 (class 0 OID 0)
-- Dependencies: 222
-- Name: voti_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.voti_id_seq OWNED BY public.voti.id;


--
-- TOC entry 4726 (class 2604 OID 24657)
-- Name: aggiornamento id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aggiornamento ALTER COLUMN id SET DEFAULT nextval('public.aggiornamento_id_seq'::regclass);


--
-- TOC entry 4727 (class 2604 OID 24676)
-- Name: hackathon id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hackathon ALTER COLUMN id SET DEFAULT nextval('public.hackathon_id_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 24623)
-- Name: team id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team ALTER COLUMN id SET DEFAULT nextval('public.team_id_seq'::regclass);


--
-- TOC entry 4725 (class 2604 OID 24637)
-- Name: voti id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti ALTER COLUMN id SET DEFAULT nextval('public.voti_id_seq'::regclass);


--
-- TOC entry 4911 (class 0 OID 24654)
-- Dependencies: 225
-- Data for Name: aggiornamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.aggiornamento (id, nome, documento, commento, giudice_id, team_id) FROM stdin;
\.


--
-- TOC entry 4904 (class 0 OID 24601)
-- Dependencies: 218
-- Data for Name: giudice; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.giudice (nome, cognome, email, username, password, hackathon_id) FROM stdin;
\.


--
-- TOC entry 4913 (class 0 OID 24673)
-- Dependencies: 227
-- Data for Name: hackathon; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hackathon (id, titolo, sede, problema, data_inizio, data_fine, fine_periodo_prenotazioni, max_iscritti, max_dim_team, organizzatore_id) FROM stdin;
\.


--
-- TOC entry 4905 (class 0 OID 24610)
-- Dependencies: 219
-- Data for Name: organizzatore; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organizzatore (nome, cognome, email, username, password) FROM stdin;
\.


--
-- TOC entry 4907 (class 0 OID 24620)
-- Dependencies: 221
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team (id, nome, mediavoti, hackathon_id) FROM stdin;
2	ciao	21	\N
\.


--
-- TOC entry 4903 (class 0 OID 24592)
-- Dependencies: 217
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utente (nome, cognome, email, username, password, team_id, hackathon_id) FROM stdin;
luca	morrone	luke@gmail.com	luca123	ciao	2	\N
\.


--
-- TOC entry 4909 (class 0 OID 24634)
-- Dependencies: 223
-- Data for Name: voti; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.voti (id, team_id, voto, giudice_id) FROM stdin;
\.


--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 224
-- Name: aggiornamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.aggiornamento_id_seq', 1, false);


--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 226
-- Name: hackathon_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hackathon_id_seq', 1, false);


--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 220
-- Name: team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_id_seq', 1, false);


--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 222
-- Name: voti_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.voti_id_seq', 1, false);


--
-- TOC entry 4746 (class 2606 OID 24661)
-- Name: aggiornamento aggiornamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aggiornamento
    ADD CONSTRAINT aggiornamento_pkey PRIMARY KEY (id);


--
-- TOC entry 4734 (class 2606 OID 24609)
-- Name: giudice giudice_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.giudice
    ADD CONSTRAINT giudice_email_key UNIQUE (email);


--
-- TOC entry 4736 (class 2606 OID 24607)
-- Name: giudice giudice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.giudice
    ADD CONSTRAINT giudice_pkey PRIMARY KEY (username);


--
-- TOC entry 4748 (class 2606 OID 24680)
-- Name: hackathon hackathon_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hackathon
    ADD CONSTRAINT hackathon_pkey PRIMARY KEY (id);


--
-- TOC entry 4738 (class 2606 OID 24618)
-- Name: organizzatore organizzatore_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizzatore
    ADD CONSTRAINT organizzatore_email_key UNIQUE (email);


--
-- TOC entry 4740 (class 2606 OID 24616)
-- Name: organizzatore organizzatore_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizzatore
    ADD CONSTRAINT organizzatore_pkey PRIMARY KEY (username);


--
-- TOC entry 4742 (class 2606 OID 24627)
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);


--
-- TOC entry 4730 (class 2606 OID 24600)
-- Name: utente utente_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_email_key UNIQUE (email);


--
-- TOC entry 4732 (class 2606 OID 24598)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (username);


--
-- TOC entry 4744 (class 2606 OID 24640)
-- Name: voti voti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti
    ADD CONSTRAINT voti_pkey PRIMARY KEY (id);


--
-- TOC entry 4755 (class 2606 OID 24662)
-- Name: aggiornamento aggiornamento_giudice_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aggiornamento
    ADD CONSTRAINT aggiornamento_giudice_id_fkey FOREIGN KEY (giudice_id) REFERENCES public.giudice(username) ON DELETE CASCADE;


--
-- TOC entry 4756 (class 2606 OID 24667)
-- Name: aggiornamento aggiornamento_team_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aggiornamento
    ADD CONSTRAINT aggiornamento_team_id_fkey FOREIGN KEY (team_id) REFERENCES public.team(id) ON DELETE CASCADE;


--
-- TOC entry 4751 (class 2606 OID 24691)
-- Name: giudice giudice_hackathon_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.giudice
    ADD CONSTRAINT giudice_hackathon_id_fkey FOREIGN KEY (hackathon_id) REFERENCES public.hackathon(id) ON DELETE CASCADE;


--
-- TOC entry 4757 (class 2606 OID 24696)
-- Name: hackathon hackathon_organizzatore_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hackathon
    ADD CONSTRAINT hackathon_organizzatore_id_fkey FOREIGN KEY (organizzatore_id) REFERENCES public.organizzatore(username) ON DELETE SET NULL;


--
-- TOC entry 4752 (class 2606 OID 24681)
-- Name: team team_hackathon_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_hackathon_id_fkey FOREIGN KEY (hackathon_id) REFERENCES public.hackathon(id) ON DELETE CASCADE;


--
-- TOC entry 4749 (class 2606 OID 24686)
-- Name: utente utente_hackathon_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_hackathon_id_fkey FOREIGN KEY (hackathon_id) REFERENCES public.hackathon(id) ON DELETE CASCADE;


--
-- TOC entry 4750 (class 2606 OID 24628)
-- Name: utente utente_team_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_team_id_fkey FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- TOC entry 4753 (class 2606 OID 24646)
-- Name: voti voti_giudice_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti
    ADD CONSTRAINT voti_giudice_id_fkey FOREIGN KEY (giudice_id) REFERENCES public.giudice(username) ON DELETE SET NULL;


--
-- TOC entry 4754 (class 2606 OID 24641)
-- Name: voti voti_team_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voti
    ADD CONSTRAINT voti_team_id_fkey FOREIGN KEY (team_id) REFERENCES public.team(id) ON DELETE CASCADE;


-- Completed on 2025-06-27 15:37:16

--
-- PostgreSQL database dump complete
--

