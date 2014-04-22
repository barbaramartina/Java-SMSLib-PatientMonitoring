--
-- PostgreSQL database dump
--

-- Started on 2010-08-02 15:05:54

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;


--
-- TOC entry 356 (class 2612 OID 204861)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;


--
-- PostgreSQL database dump
--

-- Started on 2010-08-01 20:04:48

-- Role: "smsadmin"

-- DROP ROLE smsadmin;

CREATE ROLE smsadmin LOGIN
  ENCRYPTED PASSWORD 'md5dfbbd95ceb95e91a61f2aadab243a9ef'
  SUPERUSER NOINHERIT CREATEDB CREATEROLE;
UPDATE pg_authid SET rolcatupdate=false WHERE rolname='smsadmin';

-- Role: "usuario_procesamiento"

-- DROP ROLE usuario_procesamiento;

CREATE ROLE usuario_procesamiento LOGIN
  ENCRYPTED PASSWORD 'md5e362ed45174bfd21120fb9f29e341ece'
  NOSUPERUSER NOINHERIT NOCREATEDB NOCREATEROLE;
COMMENT ON ROLE usuario_procesamiento IS 'Usuario de conexión para la aplicación de procesamiento';

-- Role: "usuario_web"

-- DROP ROLE usuario_web;

CREATE ROLE usuario_web LOGIN
  ENCRYPTED PASSWORD 'md56db57fcb901311f9cfd69df84a39f647'
  NOSUPERUSER NOINHERIT NOCREATEDB NOCREATEROLE;
COMMENT ON ROLE usuario_web IS 'Rol de permisos para la aplicación web externa';




--
-- TOC entry 1528 (class 1259 OID 204862)
-- Dependencies: 1828 6
-- Name: campania; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE campania (
    descripcion text DEFAULT 'No disponible'::text,
    fecha_hasta date,
    fecha_desde date NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.campania OWNER TO smsadmin;

--
-- TOC entry 1948 (class 0 OID 0)
-- Dependencies: 1528
-- Name: TABLE campania; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE campania IS 'Tabla que registra las campañas de salud promovidas desde el hospital.
Un paciente puede asociarse a una campaña de salud, a fin de recibir novedades de dicha campaña, o recomendaciones.
Los médicos también pueden asociase a las campañas a fin de estar al tanto de novedades en salud.
Un administrador será encargado de dar de altas campañas.
Los administradores o médicos habilitados pueden enviar mensajes a todos los pacientes / médicos asociados a una campaña en particular.';


--
-- TOC entry 1949 (class 0 OID 0)
-- Dependencies: 1528
-- Name: COLUMN campania.descripcion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania.descripcion IS 'Descripción de la campaña, obejtivos, creación fecha de vigencia, etc. Cualquier dato considerado de interés por el creador de la misma.';


--
-- TOC entry 1950 (class 0 OID 0)
-- Dependencies: 1528
-- Name: COLUMN campania.fecha_hasta; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania.fecha_hasta IS 'Fecha en que finaliza la campaña.';


--
-- TOC entry 1951 (class 0 OID 0)
-- Dependencies: 1528
-- Name: COLUMN campania.fecha_desde; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania.fecha_desde IS 'Fecha inicial de la campania';


--
-- TOC entry 1529 (class 1259 OID 204869)
-- Dependencies: 6
-- Name: campania_editor; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE campania_editor (
    id_campania integer NOT NULL,
    id_medico integer NOT NULL
);


ALTER TABLE public.campania_editor OWNER TO smsadmin;

--
-- TOC entry 1953 (class 0 OID 0)
-- Dependencies: 1529
-- Name: TABLE campania_editor; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE campania_editor IS 'Identifica a todos aquellos usuarios medicos habilitados para poder enviar mensajes para una campaña.';


--
-- TOC entry 1954 (class 0 OID 0)
-- Dependencies: 1529
-- Name: COLUMN campania_editor.id_campania; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_editor.id_campania IS 'Identificador de campaña.';


--
-- TOC entry 1955 (class 0 OID 0)
-- Dependencies: 1529
-- Name: COLUMN campania_editor.id_medico; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_editor.id_medico IS 'Identificador de médico.';


--
-- TOC entry 1530 (class 1259 OID 204872)
-- Dependencies: 1528 6
-- Name: campania_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE campania_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.campania_id_seq OWNER TO smsadmin;

--
-- TOC entry 1957 (class 0 OID 0)
-- Dependencies: 1530
-- Name: campania_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE campania_id_seq OWNED BY campania.id;


--
-- TOC entry 1958 (class 0 OID 0)
-- Dependencies: 1530
-- Name: campania_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('campania_id_seq', 2, true);


--
-- TOC entry 1531 (class 1259 OID 204874)
-- Dependencies: 6
-- Name: campania_novedad; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE campania_novedad (
    id_campania integer NOT NULL,
    contenido text,
    fecha_publicacion date NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.campania_novedad OWNER TO smsadmin;

--
-- TOC entry 1960 (class 0 OID 0)
-- Dependencies: 1531
-- Name: TABLE campania_novedad; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE campania_novedad IS 'Registra las novedades publicadas para una campaña.';


--
-- TOC entry 1961 (class 0 OID 0)
-- Dependencies: 1531
-- Name: COLUMN campania_novedad.id_campania; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_novedad.id_campania IS 'Identificador de campaña.';


--
-- TOC entry 1962 (class 0 OID 0)
-- Dependencies: 1531
-- Name: COLUMN campania_novedad.contenido; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_novedad.contenido IS 'Contenido de la novedad.';


--
-- TOC entry 1963 (class 0 OID 0)
-- Dependencies: 1531
-- Name: COLUMN campania_novedad.fecha_publicacion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_novedad.fecha_publicacion IS 'Fecha de publicación de la novedad.';


--
-- TOC entry 1532 (class 1259 OID 204880)
-- Dependencies: 6
-- Name: campania_novedad_backup; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE campania_novedad_backup (
    id integer NOT NULL,
    contenido text,
    fecha_publicacion date,
    id_campania integer NOT NULL
)
WITH (fillfactor=60);


ALTER TABLE public.campania_novedad_backup OWNER TO smsadmin;

--
-- TOC entry 1965 (class 0 OID 0)
-- Dependencies: 1532
-- Name: TABLE campania_novedad_backup; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE campania_novedad_backup IS 'Almacena las novedades nuevas insertadas en base de datos que aún no se han difundido a los asociados.';


--
-- TOC entry 1966 (class 0 OID 0)
-- Dependencies: 1532
-- Name: COLUMN campania_novedad_backup.id; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_novedad_backup.id IS 'Identificador primario.
Se corresponde con el identificador de la tabla de campania_novedad original.
';


--
-- TOC entry 1967 (class 0 OID 0)
-- Dependencies: 1532
-- Name: COLUMN campania_novedad_backup.contenido; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_novedad_backup.contenido IS 'Contenido publicitario o informativo.';


--
-- TOC entry 1968 (class 0 OID 0)
-- Dependencies: 1532
-- Name: COLUMN campania_novedad_backup.fecha_publicacion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_novedad_backup.fecha_publicacion IS 'Fecha en que se registró el contenido en el sistema';


--
-- TOC entry 1969 (class 0 OID 0)
-- Dependencies: 1532
-- Name: COLUMN campania_novedad_backup.id_campania; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_novedad_backup.id_campania IS 'Identificador de campaña a la que pertenece la novedad';


--
-- TOC entry 1533 (class 1259 OID 204886)
-- Dependencies: 1531 6
-- Name: campania_novedad_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE campania_novedad_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.campania_novedad_id_seq OWNER TO smsadmin;

--
-- TOC entry 1971 (class 0 OID 0)
-- Dependencies: 1533
-- Name: campania_novedad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE campania_novedad_id_seq OWNED BY campania_novedad.id;


--
-- TOC entry 1972 (class 0 OID 0)
-- Dependencies: 1533
-- Name: campania_novedad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('campania_novedad_id_seq', 90, true);


--
-- TOC entry 1534 (class 1259 OID 204888)
-- Dependencies: 6
-- Name: campania_usuario; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE campania_usuario (
    id_usuario integer NOT NULL,
    id_campania integer NOT NULL
);


ALTER TABLE public.campania_usuario OWNER TO smsadmin;

--
-- TOC entry 1974 (class 0 OID 0)
-- Dependencies: 1534
-- Name: TABLE campania_usuario; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE campania_usuario IS 'Tabla donde se registran todos los usuarios, ya sean médicos o pacientes que deseen recibir novedades respecto a una campaña.';


--
-- TOC entry 1975 (class 0 OID 0)
-- Dependencies: 1534
-- Name: COLUMN campania_usuario.id_usuario; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_usuario.id_usuario IS 'Usuario asociado.';


--
-- TOC entry 1976 (class 0 OID 0)
-- Dependencies: 1534
-- Name: COLUMN campania_usuario.id_campania; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN campania_usuario.id_campania IS 'Identificador de campaña.';


--
-- TOC entry 1535 (class 1259 OID 204891)
-- Dependencies: 6
-- Name: categoria_medicion; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE categoria_medicion (
    id integer NOT NULL,
    nombre text NOT NULL
)
WITH (fillfactor=30);


ALTER TABLE public.categoria_medicion OWNER TO smsadmin;

--
-- TOC entry 1978 (class 0 OID 0)
-- Dependencies: 1535
-- Name: TABLE categoria_medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE categoria_medicion IS 'Categorías de tipos de mediciones que se pueden registrar.';


--
-- TOC entry 1536 (class 1259 OID 204897)
-- Dependencies: 1535 6
-- Name: categoria_medicion_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE categoria_medicion_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.categoria_medicion_id_seq OWNER TO smsadmin;

--
-- TOC entry 1980 (class 0 OID 0)
-- Dependencies: 1536
-- Name: categoria_medicion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE categoria_medicion_id_seq OWNED BY categoria_medicion.id;


--
-- TOC entry 1981 (class 0 OID 0)
-- Dependencies: 1536
-- Name: categoria_medicion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('categoria_medicion_id_seq', 3, true);


--
-- TOC entry 1537 (class 1259 OID 204899)
-- Dependencies: 6
-- Name: especialidad; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE especialidad (
    id integer NOT NULL,
    nombre text NOT NULL,
    descripcion text
);


ALTER TABLE public.especialidad OWNER TO smsadmin;

--
-- TOC entry 1982 (class 0 OID 0)
-- Dependencies: 1537
-- Name: TABLE especialidad; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE especialidad IS 'Especialidad médica';


--
-- TOC entry 1538 (class 1259 OID 204905)
-- Dependencies: 6 1537
-- Name: especialidad_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE especialidad_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.especialidad_id_seq OWNER TO smsadmin;

--
-- TOC entry 1984 (class 0 OID 0)
-- Dependencies: 1538
-- Name: especialidad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE especialidad_id_seq OWNED BY especialidad.id;


--
-- TOC entry 1985 (class 0 OID 0)
-- Dependencies: 1538
-- Name: especialidad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('especialidad_id_seq', 18, true);


--
-- TOC entry 1539 (class 1259 OID 204907)
-- Dependencies: 6
-- Name: institucion; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE institucion (
    nombre text,
    direccion text,
    id integer NOT NULL,
    telefono text,
    subseccion integer
);


ALTER TABLE public.institucion OWNER TO smsadmin;

--
-- TOC entry 1986 (class 0 OID 0)
-- Dependencies: 1539
-- Name: TABLE institucion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE institucion IS 'Representa los datos de instituciones de salud, como hospitales, consultorios, etc.En una institución prestan servicios uno o varios médicos. A una institución concurren uno o varios pacientes.
';


--
-- TOC entry 1987 (class 0 OID 0)
-- Dependencies: 1539
-- Name: COLUMN institucion.telefono; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN institucion.telefono IS 'Teléfono de la institución
';


--
-- TOC entry 1540 (class 1259 OID 204913)
-- Dependencies: 1539 6
-- Name: institucion_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE institucion_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.institucion_id_seq OWNER TO smsadmin;

--
-- TOC entry 1989 (class 0 OID 0)
-- Dependencies: 1540
-- Name: institucion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE institucion_id_seq OWNED BY institucion.id;


--
-- TOC entry 1990 (class 0 OID 0)
-- Dependencies: 1540
-- Name: institucion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('institucion_id_seq', 7, true);


--
-- TOC entry 1541 (class 1259 OID 204915)
-- Dependencies: 6
-- Name: medicion; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE medicion (
    contenido text,
    id_paciente integer NOT NULL,
    tipo_medicion integer NOT NULL,
    fecha_hora timestamp with time zone NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.medicion OWNER TO smsadmin;

--
-- TOC entry 1991 (class 0 OID 0)
-- Dependencies: 1541
-- Name: TABLE medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE medicion IS 'Representa los datos de una medición enviada por mensaje de texto.
Un mensaje es enviado por un paciente, y posee una categoría de medición a la que pertenece. Se guarda el remitente, destinatario y cuerpo del mensaje original. Se registrará la hora de envío del mismo. Un mensaje puede tener asociadas, cero , una o varias respuestas, las cuales se generan en base al análisis que realicen los médicos.';


--
-- TOC entry 1992 (class 0 OID 0)
-- Dependencies: 1541
-- Name: COLUMN medicion.fecha_hora; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion.fecha_hora IS 'Registra la fecha y hora en que se envío la medición';


--
-- TOC entry 1542 (class 1259 OID 204921)
-- Dependencies: 6 1541
-- Name: medicion_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE medicion_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.medicion_id_seq OWNER TO smsadmin;

--
-- TOC entry 1994 (class 0 OID 0)
-- Dependencies: 1542
-- Name: medicion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE medicion_id_seq OWNED BY medicion.id;


--
-- TOC entry 1995 (class 0 OID 0)
-- Dependencies: 1542
-- Name: medicion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('medicion_id_seq', 2133, true);


--
-- TOC entry 1543 (class 1259 OID 204923)
-- Dependencies: 1835 1836 6
-- Name: medicion_respuesta; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE medicion_respuesta (
    fecha_hora_respuesta timestamp without time zone NOT NULL,
    cantidad_avisos integer DEFAULT 0,
    enviado boolean DEFAULT false,
    id_medico integer,
    id_paciente integer NOT NULL,
    respuesta text NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.medicion_respuesta OWNER TO smsadmin;

--
-- TOC entry 1997 (class 0 OID 0)
-- Dependencies: 1543
-- Name: TABLE medicion_respuesta; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE medicion_respuesta IS 'Un usuario puede tener asociadas respuestas a las mediciones ingresadas que se originan en el análisis realizado por el médico o bien por alertas autométicas generadas por el sistema.
';


--
-- TOC entry 1998 (class 0 OID 0)
-- Dependencies: 1543
-- Name: COLUMN medicion_respuesta.enviado; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta.enviado IS 'Indica si ha llegado confirmación de la recepción del mensaje de respuesta.';


--
-- TOC entry 1999 (class 0 OID 0)
-- Dependencies: 1543
-- Name: COLUMN medicion_respuesta.id_medico; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta.id_medico IS 'Medico que generó la respuesta. Puede llegar a ser vacío en caso de la generación de alertas automáticas.';


--
-- TOC entry 2000 (class 0 OID 0)
-- Dependencies: 1543
-- Name: COLUMN medicion_respuesta.id_paciente; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta.id_paciente IS 'Paciente al que debe enviarse la respuesta. 
Este campo no puede ser vacío porque se requiere para obtener el número celular de destino.';


--
-- TOC entry 2001 (class 0 OID 0)
-- Dependencies: 1543
-- Name: COLUMN medicion_respuesta.respuesta; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta.respuesta IS 'Respuesta del médico al paciente. ';


--
-- TOC entry 1544 (class 1259 OID 204931)
-- Dependencies: 6
-- Name: medicion_respuesta_backup; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE medicion_respuesta_backup (
    id integer NOT NULL,
    id_paciente integer NOT NULL,
    id_medico integer NOT NULL,
    fecha_hora_respuesta timestamp without time zone,
    cantidad_avisos integer,
    enviado boolean,
    respuesta text NOT NULL
);


ALTER TABLE public.medicion_respuesta_backup OWNER TO smsadmin;

--
-- TOC entry 2003 (class 0 OID 0)
-- Dependencies: 1544
-- Name: TABLE medicion_respuesta_backup; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE medicion_respuesta_backup IS 'Tabla donde se registrarán las respuestas que vayan generando los médicos para ser enviadas a los pacientes.';


--
-- TOC entry 2004 (class 0 OID 0)
-- Dependencies: 1544
-- Name: COLUMN medicion_respuesta_backup.id; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta_backup.id IS 'Identificador primario que se corresponde con el identificador de la tabla original de respuestas';


--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 1544
-- Name: COLUMN medicion_respuesta_backup.id_paciente; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta_backup.id_paciente IS 'Identificador de paciente';


--
-- TOC entry 2006 (class 0 OID 0)
-- Dependencies: 1544
-- Name: COLUMN medicion_respuesta_backup.id_medico; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta_backup.id_medico IS 'Identificador de médico';


--
-- TOC entry 2007 (class 0 OID 0)
-- Dependencies: 1544
-- Name: COLUMN medicion_respuesta_backup.fecha_hora_respuesta; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta_backup.fecha_hora_respuesta IS 'Fecha en la que se registró la respuesta del médico en el sistema.
Es distinta a la hora en que pueda llegar a enviarse la respuesta al paciente.';


--
-- TOC entry 2008 (class 0 OID 0)
-- Dependencies: 1544
-- Name: COLUMN medicion_respuesta_backup.cantidad_avisos; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta_backup.cantidad_avisos IS 'Cantidad de veces que se le han enviado al paciente las respuestas del médico.';


--
-- TOC entry 2009 (class 0 OID 0)
-- Dependencies: 1544
-- Name: COLUMN medicion_respuesta_backup.enviado; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta_backup.enviado IS 'Indicador de si el mensaje de respuesta ha sido enviado por el sistema al médico.
';


--
-- TOC entry 2010 (class 0 OID 0)
-- Dependencies: 1544
-- Name: COLUMN medicion_respuesta_backup.respuesta; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN medicion_respuesta_backup.respuesta IS 'Texto de respuesta ingresado por el médico.';


--
-- TOC entry 1545 (class 1259 OID 204937)
-- Dependencies: 1543 6
-- Name: medicion_respuesta_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE medicion_respuesta_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.medicion_respuesta_id_seq OWNER TO smsadmin;

--
-- TOC entry 2012 (class 0 OID 0)
-- Dependencies: 1545
-- Name: medicion_respuesta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE medicion_respuesta_id_seq OWNED BY medicion_respuesta.id;


--
-- TOC entry 2013 (class 0 OID 0)
-- Dependencies: 1545
-- Name: medicion_respuesta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('medicion_respuesta_id_seq', 36, true);


--
-- TOC entry 1546 (class 1259 OID 204939)
-- Dependencies: 6
-- Name: usuario; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE usuario (
    nombre text,
    apellido text,
    id integer NOT NULL,
    celular text,
    telefono text,
    mail text,
    fecha_nacimiento date
);


ALTER TABLE public.usuario OWNER TO smsadmin;

--
-- TOC entry 2015 (class 0 OID 0)
-- Dependencies: 1546
-- Name: TABLE usuario; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE usuario IS 'Tabla que representa los usuario en general, tanto administradores como médicos y pacientes. A nivel de base de datos representa información básica como personas.

';


--
-- TOC entry 2016 (class 0 OID 0)
-- Dependencies: 1546
-- Name: COLUMN usuario.celular; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN usuario.celular IS 'Teléfono celular del usuario';


--
-- TOC entry 2017 (class 0 OID 0)
-- Dependencies: 1546
-- Name: COLUMN usuario.mail; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN usuario.mail IS 'Correo del usuario
';


--
-- TOC entry 2018 (class 0 OID 0)
-- Dependencies: 1546
-- Name: COLUMN usuario.fecha_nacimiento; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN usuario.fecha_nacimiento IS 'Fecha de nacimiento';


--
-- TOC entry 1547 (class 1259 OID 204945)
-- Dependencies: 1546 6
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE usuario_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO smsadmin;

--
-- TOC entry 2020 (class 0 OID 0)
-- Dependencies: 1547
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 2021 (class 0 OID 0)
-- Dependencies: 1547
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('usuario_id_seq', 64, true);


--
-- TOC entry 1548 (class 1259 OID 204947)
-- Dependencies: 1839 6 1546
-- Name: medico; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE medico (
    matricula text,
    institucion integer,
    especialidad integer
)
INHERITS (usuario);


ALTER TABLE public.medico OWNER TO smsadmin;

--
-- TOC entry 2023 (class 0 OID 0)
-- Dependencies: 1548
-- Name: TABLE medico; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE medico IS 'Representa los usuarios médicos, los cuales pueden pertenecer a una institución en particular y poseen matrícula.Los médicos atienden a un conjunto de pacientes y posee,aunque no necesariamente, a una especialidad.';


--
-- TOC entry 1549 (class 1259 OID 204959)
-- Dependencies: 1840 6
-- Name: paciente_medico_categoria_medicion; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE paciente_medico_categoria_medicion (
    id_medico integer NOT NULL,
    id_paciente integer NOT NULL,
    id_categoria integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.paciente_medico_categoria_medicion OWNER TO smsadmin;

--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 1549
-- Name: TABLE paciente_medico_categoria_medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE paciente_medico_categoria_medicion IS 'Indica que medico tiene asignado el usuario en el sistema para cada tipo de medicion que envie. Ello permite llevar el control de que Juan tiene asignado al Dr. X para mediciones de GLUCOSA, a la vez que el Dr. Y analiza sus mediciones de presion; y quizás el Dr. Z también este analizando por inter-consulta sus mediciones de presión.';


--
-- TOC entry 2026 (class 0 OID 0)
-- Dependencies: 1549
-- Name: COLUMN paciente_medico_categoria_medicion.id_medico; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN paciente_medico_categoria_medicion.id_medico IS 'Medico asignado al paciente para analizar ciertos tipos de medicion';


--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 1549
-- Name: COLUMN paciente_medico_categoria_medicion.id_paciente; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN paciente_medico_categoria_medicion.id_paciente IS 'Paciente que estará registrando cierto tipo de mediciones
';


--
-- TOC entry 1557 (class 1259 OID 237623)
-- Dependencies: 6
-- Name: pais; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE pais (
    id integer NOT NULL,
    nombre text NOT NULL
);


ALTER TABLE public.pais OWNER TO smsadmin;

--
-- TOC entry 2029 (class 0 OID 0)
-- Dependencies: 1557
-- Name: TABLE pais; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE pais IS 'Tabla de paises';


--
-- TOC entry 2030 (class 0 OID 0)
-- Dependencies: 1557
-- Name: COLUMN pais.id; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN pais.id IS 'Identificador
';


--
-- TOC entry 2031 (class 0 OID 0)
-- Dependencies: 1557
-- Name: COLUMN pais.nombre; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN pais.nombre IS 'Nombre del pais';


--
-- TOC entry 1556 (class 1259 OID 237621)
-- Dependencies: 6 1557
-- Name: pais_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE pais_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.pais_id_seq OWNER TO smsadmin;

--
-- TOC entry 2033 (class 0 OID 0)
-- Dependencies: 1556
-- Name: pais_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE pais_id_seq OWNED BY pais.id;


--
-- TOC entry 2034 (class 0 OID 0)
-- Dependencies: 1556
-- Name: pais_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('pais_id_seq', 13, true);


--
-- TOC entry 1550 (class 1259 OID 204962)
-- Dependencies: 1841 1842 1843 6
-- Name: parametro_categoria_medicion; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE parametro_categoria_medicion (
    descripcion text,
    validacion text NOT NULL,
    id_categoria integer NOT NULL,
    id integer NOT NULL,
    posicion integer DEFAULT 0 NOT NULL,
    valor_minimo real DEFAULT 0 NOT NULL,
    valor_maximo real DEFAULT 0 NOT NULL
);


ALTER TABLE public.parametro_categoria_medicion OWNER TO smsadmin;

--
-- TOC entry 2035 (class 0 OID 0)
-- Dependencies: 1550
-- Name: TABLE parametro_categoria_medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE parametro_categoria_medicion IS 'Especifica un parametro requerido por un tipo de medicion. Por ejemplo un parametro de la medición de tipo GLUCOSA, sería el parámetro ENCABEZADO, que podría adquirir los valores en el conjunto {GAD, GDD, GAM,.., etc} según el momento del día en que se haya registrado la medida.';


--
-- TOC entry 2036 (class 0 OID 0)
-- Dependencies: 1550
-- Name: COLUMN parametro_categoria_medicion.posicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN parametro_categoria_medicion.posicion IS 'Indica la posición del parámetros en una cierta cadena de caracteres.';


--
-- TOC entry 1551 (class 1259 OID 204971)
-- Dependencies: 6 1550
-- Name: parametro_categoria_medicion_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE parametro_categoria_medicion_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.parametro_categoria_medicion_id_seq OWNER TO smsadmin;

--
-- TOC entry 2038 (class 0 OID 0)
-- Dependencies: 1551
-- Name: parametro_categoria_medicion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE parametro_categoria_medicion_id_seq OWNED BY parametro_categoria_medicion.id;


--
-- TOC entry 2039 (class 0 OID 0)
-- Dependencies: 1551
-- Name: parametro_categoria_medicion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('parametro_categoria_medicion_id_seq', 5, true);


--
-- TOC entry 1555 (class 1259 OID 237612)
-- Dependencies: 6
-- Name: seccion; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE seccion (
    id integer NOT NULL,
    nombre text NOT NULL,
    id_pais integer NOT NULL
);


ALTER TABLE public.seccion OWNER TO smsadmin;

--
-- TOC entry 2040 (class 0 OID 0)
-- Dependencies: 1555
-- Name: TABLE seccion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE seccion IS 'Tabla de secciones de un pais, pueden ser provincias o distritos, etc.';


--
-- TOC entry 2041 (class 0 OID 0)
-- Dependencies: 1555
-- Name: COLUMN seccion.id; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN seccion.id IS 'Identificador';


--
-- TOC entry 2042 (class 0 OID 0)
-- Dependencies: 1555
-- Name: COLUMN seccion.nombre; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN seccion.nombre IS 'Nombre de la seccion';


--
-- TOC entry 2043 (class 0 OID 0)
-- Dependencies: 1555
-- Name: COLUMN seccion.id_pais; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN seccion.id_pais IS 'Clave externa a pais al que corresponde la sección';


--
-- TOC entry 1554 (class 1259 OID 237610)
-- Dependencies: 6 1555
-- Name: seccion_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE seccion_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seccion_id_seq OWNER TO smsadmin;

--
-- TOC entry 2045 (class 0 OID 0)
-- Dependencies: 1554
-- Name: seccion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE seccion_id_seq OWNED BY seccion.id;


--
-- TOC entry 2046 (class 0 OID 0)
-- Dependencies: 1554
-- Name: seccion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('seccion_id_seq', 22, true);


--
-- TOC entry 1558 (class 1259 OID 237637)
-- Dependencies: 6
-- Name: subseccion; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE subseccion (
    nombre text NOT NULL,
    id_seccion integer,
    id integer NOT NULL
);


ALTER TABLE public.subseccion OWNER TO smsadmin;

--
-- TOC entry 2047 (class 0 OID 0)
-- Dependencies: 1558
-- Name: TABLE subseccion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE subseccion IS 'Subsección perteneciente a una sección, puede representar un municipio por ejemplo.';


--
-- TOC entry 2048 (class 0 OID 0)
-- Dependencies: 1558
-- Name: COLUMN subseccion.nombre; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN subseccion.nombre IS 'Nombre de la subsección';


--
-- TOC entry 2049 (class 0 OID 0)
-- Dependencies: 1558
-- Name: COLUMN subseccion.id_seccion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN subseccion.id_seccion IS 'Clave extranjera a la sección padre';


--
-- TOC entry 1559 (class 1259 OID 237650)
-- Dependencies: 1558 6
-- Name: subseccion_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE subseccion_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.subseccion_id_seq OWNER TO smsadmin;

--
-- TOC entry 2051 (class 0 OID 0)
-- Dependencies: 1559
-- Name: subseccion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE subseccion_id_seq OWNED BY subseccion.id;


--
-- TOC entry 2052 (class 0 OID 0)
-- Dependencies: 1559
-- Name: subseccion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('subseccion_id_seq', 15, true);


--
-- TOC entry 1552 (class 1259 OID 204992)
-- Dependencies: 6
-- Name: tipo_medicion; Type: TABLE; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE TABLE tipo_medicion (
    valor text NOT NULL,
    descripcion text NOT NULL,
    id integer NOT NULL,
    id_categoria integer NOT NULL
);


ALTER TABLE public.tipo_medicion OWNER TO smsadmin;

--
-- TOC entry 2053 (class 0 OID 0)
-- Dependencies: 1552
-- Name: TABLE tipo_medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON TABLE tipo_medicion IS 'Representa los tipos de mediciones que pueden llegar a ser registradas, junto con las expresiones regulares que las definen.
Cada tipo de medicion posee un conjunto de parametros de medicion asociados. 
Por ejemplo el tipo de medicion GLUCOSA, posee asociado el parámetro abreviatura de medición, con valores en el conjunto {GAD, GDD, GAM, GDM,..,etc}, ';


--
-- TOC entry 2054 (class 0 OID 0)
-- Dependencies: 1552
-- Name: COLUMN tipo_medicion.valor; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN tipo_medicion.valor IS 'Valor asociado al tipo de medición. Ejemplo la medición con valor GLUCOSA, identifica a la glucosa. El valor es único para cada tipo de medición y es una clave primaria alternativa.';


--
-- TOC entry 2055 (class 0 OID 0)
-- Dependencies: 1552
-- Name: COLUMN tipo_medicion.descripcion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON COLUMN tipo_medicion.descripcion IS 'Descripción del tipo de medición. Puede ser una expresion regular.';


--
-- TOC entry 1553 (class 1259 OID 204998)
-- Dependencies: 6 1552
-- Name: tipo_medicion_id_seq; Type: SEQUENCE; Schema: public; Owner: smsadmin
--

CREATE SEQUENCE tipo_medicion_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tipo_medicion_id_seq OWNER TO smsadmin;

--
-- TOC entry 2057 (class 0 OID 0)
-- Dependencies: 1553
-- Name: tipo_medicion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smsadmin
--

ALTER SEQUENCE tipo_medicion_id_seq OWNED BY tipo_medicion.id;


--
-- TOC entry 2058 (class 0 OID 0)
-- Dependencies: 1553
-- Name: tipo_medicion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smsadmin
--

SELECT pg_catalog.setval('tipo_medicion_id_seq', 13, true);


--
-- TOC entry 1829 (class 2604 OID 205008)
-- Dependencies: 1530 1528
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE campania ALTER COLUMN id SET DEFAULT nextval('campania_id_seq'::regclass);


--
-- TOC entry 1830 (class 2604 OID 205009)
-- Dependencies: 1533 1531
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE campania_novedad ALTER COLUMN id SET DEFAULT nextval('campania_novedad_id_seq'::regclass);


--
-- TOC entry 1831 (class 2604 OID 205010)
-- Dependencies: 1536 1535
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE categoria_medicion ALTER COLUMN id SET DEFAULT nextval('categoria_medicion_id_seq'::regclass);


--
-- TOC entry 1832 (class 2604 OID 205011)
-- Dependencies: 1538 1537
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE especialidad ALTER COLUMN id SET DEFAULT nextval('especialidad_id_seq'::regclass);


--
-- TOC entry 1833 (class 2604 OID 205012)
-- Dependencies: 1540 1539
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE institucion ALTER COLUMN id SET DEFAULT nextval('institucion_id_seq'::regclass);


--
-- TOC entry 1834 (class 2604 OID 205013)
-- Dependencies: 1542 1541
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE medicion ALTER COLUMN id SET DEFAULT nextval('medicion_id_seq'::regclass);


--
-- TOC entry 1837 (class 2604 OID 205014)
-- Dependencies: 1545 1543
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE medicion_respuesta ALTER COLUMN id SET DEFAULT nextval('medicion_respuesta_id_seq'::regclass);


--
-- TOC entry 1847 (class 2604 OID 237626)
-- Dependencies: 1557 1556 1557
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE pais ALTER COLUMN id SET DEFAULT nextval('pais_id_seq'::regclass);


--
-- TOC entry 1844 (class 2604 OID 205015)
-- Dependencies: 1551 1550
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE parametro_categoria_medicion ALTER COLUMN id SET DEFAULT nextval('parametro_categoria_medicion_id_seq'::regclass);


--
-- TOC entry 1846 (class 2604 OID 237615)
-- Dependencies: 1554 1555 1555
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE seccion ALTER COLUMN id SET DEFAULT nextval('seccion_id_seq'::regclass);


--
-- TOC entry 1848 (class 2604 OID 237652)
-- Dependencies: 1559 1558
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE subseccion ALTER COLUMN id SET DEFAULT nextval('subseccion_id_seq'::regclass);


--
-- TOC entry 1845 (class 2604 OID 205018)
-- Dependencies: 1553 1552
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE tipo_medicion ALTER COLUMN id SET DEFAULT nextval('tipo_medicion_id_seq'::regclass);


--
-- TOC entry 1838 (class 2604 OID 205019)
-- Dependencies: 1547 1546
-- Name: id; Type: DEFAULT; Schema: public; Owner: smsadmin
--

ALTER TABLE usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 1924 (class 0 OID 204862)
-- Dependencies: 1528
-- Data for Name: campania; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO campania (descripcion, fecha_hasta, fecha_desde, id) VALUES ('Campaña de prevención de la gripe A', '2010-08-21', '2010-09-22', 1);


--
-- TOC entry 1925 (class 0 OID 204869)
-- Dependencies: 1529
-- Data for Name: campania_editor; Type: TABLE DATA; Schema: public; Owner: smsadmin
--



--
-- TOC entry 1926 (class 0 OID 204874)
-- Dependencies: 1531
-- Data for Name: campania_novedad; Type: TABLE DATA; Schema: public; Owner: smsadmin
--



--
-- TOC entry 1927 (class 0 OID 204880)
-- Dependencies: 1532
-- Data for Name: campania_novedad_backup; Type: TABLE DATA; Schema: public; Owner: smsadmin
--



--
-- TOC entry 1928 (class 0 OID 204888)
-- Dependencies: 1534
-- Data for Name: campania_usuario; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO campania_usuario (id_usuario, id_campania) VALUES (64, 1);


--
-- TOC entry 1929 (class 0 OID 204891)
-- Dependencies: 1535
-- Data for Name: categoria_medicion; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO categoria_medicion (id, nombre) VALUES (1, 'GLUCOSA');
INSERT INTO categoria_medicion (id, nombre) VALUES (2, 'PRESION');


--
-- TOC entry 1930 (class 0 OID 204899)
-- Dependencies: 1537
-- Data for Name: especialidad; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO especialidad (id, nombre, descripcion) VALUES (1, 'Anestesiología', 'Anestesiología gral.');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (2, 'Bioética', 'Bioética');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (4, 'Cardiología', 'Cardiología');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (5, 'Cirugía', 'Cirugía');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (6, 'Dermatología', 'Dermatología');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (7, 'Clínica', 'Clínica');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (8, 'Medicina deportiva', 'Medicina deportiva');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (9, 'Endocrinología', 'Endocrinología');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (10, 'Neurología', 'Neurología');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (11, 'Odontología', 'Odontología');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (12, 'Neumología', 'Neumología');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (13, 'Medicina preventiva', 'Medicina preventiva');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (14, 'Nutrición', 'Nutrición');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (15, 'Pediratría', 'Pediatría');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (16, 'Traumatología', 'Traumatología');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (17, 'Urgencias médicas', 'Urgencias médicas');
INSERT INTO especialidad (id, nombre, descripcion) VALUES (18, 'Hematología', 'Hematología');


--
-- TOC entry 1931 (class 0 OID 204907)
-- Dependencies: 1539
-- Data for Name: institucion; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO institucion (nombre, direccion, id, telefono, subseccion) VALUES ('Hospital Municipal Ramón Santamarina', 'Gral. Paz N° 1406. Tandil', 1, '02293-430168', 1);
INSERT INTO institucion (nombre, direccion, id, telefono, subseccion) VALUES ('Hospital Municipal Dr. Hector M. Cura', 'Rivadavia N° 4057. Olavarría', 3, '02284-440800/806', 2);
INSERT INTO institucion (nombre, direccion, id, telefono, subseccion) VALUES ('Hospital Municipal de Agudos "Dr. Leónidas"', 'Estomba N° 968. Bahia Blanca.', 4, '0291-4598484/456183', 5);
INSERT INTO institucion (nombre, direccion, id, telefono, subseccion) VALUES ('Hospital Municipal de General Villegas', 'R. Isturiz S/N', 5, '03388-423629', 13);
INSERT INTO institucion (nombre, direccion, id, telefono, subseccion) VALUES ('Hospital Zonal Dr. Juan Carlos Aramburu. Pehuajó.', 'Dean Funes N° 56', 6, '02396-472203', 14);
INSERT INTO institucion (nombre, direccion, id, telefono, subseccion) VALUES ('Hospital Dr. Guillermo del Soldato.', 'Ugarte N° 317', 7, '02392-498660', 15);


--
-- TOC entry 1932 (class 0 OID 204915)
-- Dependencies: 1541
-- Data for Name: medicion; Type: TABLE DATA; Schema: public; Owner: smsadmin
--



--
-- TOC entry 1933 (class 0 OID 204923)
-- Dependencies: 1543
-- Data for Name: medicion_respuesta; Type: TABLE DATA; Schema: public; Owner: smsadmin
--



--
-- TOC entry 1934 (class 0 OID 204931)
-- Dependencies: 1544
-- Data for Name: medicion_respuesta_backup; Type: TABLE DATA; Schema: public; Owner: smsadmin
--



--
-- TOC entry 1936 (class 0 OID 204947)
-- Dependencies: 1548
-- Data for Name: medico; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO medico (nombre, apellido, id, celular, matricula, institucion, especialidad, telefono, mail, fecha_nacimiento) VALUES ('med', 'doctor', 63, '226815565959', '444555mp', 1, 1, '555', '555@c.com', '2000-05-06');


--
-- TOC entry 1937 (class 0 OID 204959)
-- Dependencies: 1549
-- Data for Name: paciente_medico_categoria_medicion; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO paciente_medico_categoria_medicion (id_medico, id_paciente, id_categoria) VALUES (63, 64, 1);
INSERT INTO paciente_medico_categoria_medicion (id_medico, id_paciente, id_categoria) VALUES (63, 64, 2);


--
-- TOC entry 1941 (class 0 OID 237623)
-- Dependencies: 1557
-- Data for Name: pais; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO pais (id, nombre) VALUES (1, 'Argentina');
INSERT INTO pais (id, nombre) VALUES (2, 'Uruguay');
INSERT INTO pais (id, nombre) VALUES (3, 'Chile');
INSERT INTO pais (id, nombre) VALUES (4, 'Brasil');
INSERT INTO pais (id, nombre) VALUES (5, 'Perú');
INSERT INTO pais (id, nombre) VALUES (6, 'Colombia');
INSERT INTO pais (id, nombre) VALUES (7, 'Venezuela');
INSERT INTO pais (id, nombre) VALUES (8, 'Bolivia');
INSERT INTO pais (id, nombre) VALUES (9, 'México');
INSERT INTO pais (id, nombre) VALUES (10, 'Ecuador');
INSERT INTO pais (id, nombre) VALUES (11, 'EEUU');
INSERT INTO pais (id, nombre) VALUES (12, 'España');
INSERT INTO pais (id, nombre) VALUES (13, 'Portugal');


--
-- TOC entry 1938 (class 0 OID 204962)
-- Dependencies: 1550
-- Data for Name: parametro_categoria_medicion; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO parametro_categoria_medicion (descripcion, validacion, id_categoria, id, posicion, valor_minimo, valor_maximo) VALUES ('valor', '[0-9]*', 1, 2, 1, 0, 500);
INSERT INTO parametro_categoria_medicion (descripcion, validacion, id_categoria, id, posicion, valor_minimo, valor_maximo) VALUES ('valorMin', '[0-9]*', 2, 4, 1, 0, 100);
INSERT INTO parametro_categoria_medicion (descripcion, validacion, id_categoria, id, posicion, valor_minimo, valor_maximo) VALUES ('valorMax', '[0-9]*', 2, 5, 2, 0, 100);
INSERT INTO parametro_categoria_medicion (descripcion, validacion, id_categoria, id, posicion, valor_minimo, valor_maximo) VALUES ('tipo', '[a-zA-Z]*', 2, 3, 0, 0, 0);
INSERT INTO parametro_categoria_medicion (descripcion, validacion, id_categoria, id, posicion, valor_minimo, valor_maximo) VALUES ('tipo', '[a-zA-Z]*', 1, 1, 0, 0, 0);


--
-- TOC entry 1940 (class 0 OID 237612)
-- Dependencies: 1555
-- Data for Name: seccion; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO seccion (id, nombre, id_pais) VALUES (1, 'Buenos Aires', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (2, 'La Pampa', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (3, 'Córdoba', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (4, 'Santa Fe', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (5, 'Entre Ríos', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (6, 'Corrientes', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (7, 'Misiones', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (8, 'Chaco', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (9, 'Formosa', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (10, 'Tucumán', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (11, 'Salta', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (12, 'Jujuy', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (13, 'La Rioja', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (14, 'Neuquén', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (15, 'Santa Cruz', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (16, 'Chubut', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (17, 'Río Negro', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (18, 'Mendoza', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (19, 'Catamarca', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (20, 'San Juan', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (21, 'Santiago del Estero', 1);
INSERT INTO seccion (id, nombre, id_pais) VALUES (22, 'Tierra del Fuego', 1);


--
-- TOC entry 1942 (class 0 OID 237637)
-- Dependencies: 1558
-- Data for Name: subseccion; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Tandil', 1, 1);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Olavarría', 1, 2);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Azul', 1, 3);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Trenque Lauquen', 1, 4);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Bahía Blanca', 1, 5);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Capital Federal', 1, 6);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Gral. Juan Madariaga', 1, 7);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('La Plata', 1, 9);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Mar del Plata', 1, 8);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('América', 1, 10);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Rawson', 1, 11);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Ayacucho', 1, 12);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('General Villegas', 1, 13);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Pehuajó', 1, 14);
INSERT INTO subseccion (nombre, id_seccion, id) VALUES ('Pellegrini', 1, 15);


--
-- TOC entry 1939 (class 0 OID 204992)
-- Dependencies: 1552
-- Data for Name: tipo_medicion; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('GAD', 'GLUCOSA antes del desayuno', 1, 1);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('PM', 'PRESION tomada por la mañana', 2, 2);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('PT', 'PRESION tomada por la tarde', 3, 2);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('PN', 'PRESION tomada por la noche', 4, 2);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('GDD', 'GLUCOSA después del desyuno', 5, 1);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('GAA', 'GLUCOSA antes del almuerzo', 6, 1);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('GAM', 'GLUCOSA antes de la merienda', 8, 1);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('GDM', 'GLUCOSA después de la merienda', 9, 1);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('GAC', 'GLUCOSA antes de la cena', 10, 1);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('GDC', 'GLUCOSA después de la cena', 11, 1);
INSERT INTO tipo_medicion (valor, descripcion, id, id_categoria) VALUES ('GDA', 'GLUCOSA después del almuerzo', 7, 1);


--
-- TOC entry 1935 (class 0 OID 204939)
-- Dependencies: 1546
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: smsadmin
--

INSERT INTO usuario (nombre, apellido, id, celular, telefono, mail, fecha_nacimiento) VALUES ('med', 'doctor', 63, '226815565959', '555', '555@c.com', '2000-05-06');
INSERT INTO usuario (nombre, apellido, id, celular, telefono, mail, fecha_nacimiento) VALUES ('barbara', 'rodeker', 163, '2147483647', '0', 'barby@barb.com', '1981-12-25');
INSERT INTO usuario (nombre, apellido, id, celular, telefono, mail, fecha_nacimiento) VALUES ('Paciente', 'Paciente', 64, '2293658110', '123456', 'minuevomail@hotmail.com', '0200-06-05');


--
-- TOC entry 1892 (class 2606 OID 205023)
-- Dependencies: 1552 1552
-- Name: categoria_medicion_valor_key; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY tipo_medicion
    ADD CONSTRAINT categoria_medicion_valor_key UNIQUE (valor);


--
-- TOC entry 1852 (class 2606 OID 205025)
-- Dependencies: 1529 1529 1529
-- Name: pk_camapaniaeditor; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY campania_editor
    ADD CONSTRAINT pk_camapaniaeditor PRIMARY KEY (id_campania, id_medico);


--
-- TOC entry 1850 (class 2606 OID 205027)
-- Dependencies: 1528 1528
-- Name: pk_campania; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY campania
    ADD CONSTRAINT pk_campania PRIMARY KEY (id);


--
-- TOC entry 1857 (class 2606 OID 205029)
-- Dependencies: 1532 1532
-- Name: pk_campania_novedad_backup; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY campania_novedad_backup
    ADD CONSTRAINT pk_campania_novedad_backup PRIMARY KEY (id);


--
-- TOC entry 1855 (class 2606 OID 205031)
-- Dependencies: 1531 1531
-- Name: pk_campanianovedad; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY campania_novedad
    ADD CONSTRAINT pk_campanianovedad PRIMARY KEY (id);


--
-- TOC entry 1860 (class 2606 OID 205033)
-- Dependencies: 1534 1534 1534
-- Name: pk_campaniausuario; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY campania_usuario
    ADD CONSTRAINT pk_campaniausuario PRIMARY KEY (id_usuario, id_campania);


--
-- TOC entry 1862 (class 2606 OID 205035)
-- Dependencies: 1535 1535
-- Name: pk_categoriamedicion; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY categoria_medicion
    ADD CONSTRAINT pk_categoriamedicion PRIMARY KEY (id);


--
-- TOC entry 1864 (class 2606 OID 205037)
-- Dependencies: 1537 1537
-- Name: pk_especialidad; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY especialidad
    ADD CONSTRAINT pk_especialidad PRIMARY KEY (id);


--
-- TOC entry 1866 (class 2606 OID 205039)
-- Dependencies: 1539 1539
-- Name: pk_institucion; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY institucion
    ADD CONSTRAINT pk_institucion PRIMARY KEY (id);


--
-- TOC entry 1870 (class 2606 OID 205041)
-- Dependencies: 1541 1541
-- Name: pk_medicion; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY medicion
    ADD CONSTRAINT pk_medicion PRIMARY KEY (id);


--
-- TOC entry 1876 (class 2606 OID 205043)
-- Dependencies: 1544 1544
-- Name: pk_medicion_respuesta_backup; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY medicion_respuesta_backup
    ADD CONSTRAINT pk_medicion_respuesta_backup PRIMARY KEY (id);


--
-- TOC entry 1874 (class 2606 OID 205045)
-- Dependencies: 1543 1543
-- Name: pk_medicionrespuesta; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY medicion_respuesta
    ADD CONSTRAINT pk_medicionrespuesta PRIMARY KEY (id);


--
-- TOC entry 1882 (class 2606 OID 205047)
-- Dependencies: 1548 1548
-- Name: pk_medico; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY medico
    ADD CONSTRAINT pk_medico PRIMARY KEY (id);


--
-- TOC entry 1884 (class 2606 OID 229392)
-- Dependencies: 1549 1549 1549 1549
-- Name: pk_paciente_medico_categoria; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY paciente_medico_categoria_medicion
    ADD CONSTRAINT pk_paciente_medico_categoria PRIMARY KEY (id_medico, id_paciente, id_categoria);


--
-- TOC entry 1901 (class 2606 OID 237631)
-- Dependencies: 1557 1557
-- Name: pk_pais; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT pk_pais PRIMARY KEY (id);


--
-- TOC entry 1890 (class 2606 OID 205053)
-- Dependencies: 1550 1550
-- Name: pk_param_tm; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY parametro_categoria_medicion
    ADD CONSTRAINT pk_param_tm PRIMARY KEY (id);


--
-- TOC entry 1899 (class 2606 OID 237620)
-- Dependencies: 1555 1555
-- Name: pk_seccion; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY seccion
    ADD CONSTRAINT pk_seccion PRIMARY KEY (id);


--
-- TOC entry 1903 (class 2606 OID 237661)
-- Dependencies: 1558 1558
-- Name: pk_subseccion; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY subseccion
    ADD CONSTRAINT pk_subseccion PRIMARY KEY (id);


--
-- TOC entry 1895 (class 2606 OID 205061)
-- Dependencies: 1552 1552
-- Name: pk_tipomedicion; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY tipo_medicion
    ADD CONSTRAINT pk_tipomedicion PRIMARY KEY (id);


--
-- TOC entry 1878 (class 2606 OID 205063)
-- Dependencies: 1546 1546
-- Name: pk_usuario; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT pk_usuario PRIMARY KEY (id);


--
-- TOC entry 1897 (class 2606 OID 205065)
-- Dependencies: 1552 1552
-- Name: unique_valor; Type: CONSTRAINT; Schema: public; Owner: smsadmin; Tablespace: 
--

ALTER TABLE ONLY tipo_medicion
    ADD CONSTRAINT unique_valor UNIQUE (valor);


--
-- TOC entry 1893 (class 1259 OID 237609)
-- Dependencies: 1552
-- Name: fk_categoria; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX fk_categoria ON tipo_medicion USING btree (id_categoria);


--
-- TOC entry 1853 (class 1259 OID 213010)
-- Dependencies: 1531
-- Name: fki_campania; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX fki_campania ON campania_novedad USING btree (id_campania);


--
-- TOC entry 1888 (class 1259 OID 237598)
-- Dependencies: 1550
-- Name: fki_categoria; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX fki_categoria ON parametro_categoria_medicion USING btree (id_categoria);


--
-- TOC entry 1879 (class 1259 OID 237586)
-- Dependencies: 1548
-- Name: fki_especialidad; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX fki_especialidad ON medico USING btree (especialidad);


--
-- TOC entry 1880 (class 1259 OID 237592)
-- Dependencies: 1548
-- Name: fki_institucion; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX fki_institucion ON medico USING btree (institucion);


--
-- TOC entry 1858 (class 1259 OID 213021)
-- Dependencies: 1534
-- Name: fkii_campania; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX fkii_campania ON campania_usuario USING btree (id_campania);


--
-- TOC entry 1867 (class 1259 OID 205069)
-- Dependencies: 1541
-- Name: medicion_paciente_indice; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX medicion_paciente_indice ON medicion USING btree (id_paciente) WITH (fillfactor=20);


--
-- TOC entry 1871 (class 1259 OID 205070)
-- Dependencies: 1543
-- Name: medicion_respuesta_medico_indice; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX medicion_respuesta_medico_indice ON medicion_respuesta USING btree (id_medico) WITH (fillfactor=20);


--
-- TOC entry 1872 (class 1259 OID 205071)
-- Dependencies: 1543
-- Name: medicion_respuesta_paciente_indice; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX medicion_respuesta_paciente_indice ON medicion_respuesta USING btree (id_paciente) WITH (fillfactor=20);


--
-- TOC entry 1868 (class 1259 OID 205072)
-- Dependencies: 1541
-- Name: medicion_tipo_medicion_indice; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX medicion_tipo_medicion_indice ON medicion USING btree (tipo_medicion) WITH (fillfactor=20);


--
-- TOC entry 1885 (class 1259 OID 229398)
-- Dependencies: 1549
-- Name: pmtm_categoria_indice; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX pmtm_categoria_indice ON paciente_medico_categoria_medicion USING btree (id_categoria);


--
-- TOC entry 2059 (class 0 OID 0)
-- Dependencies: 1885
-- Name: INDEX pmtm_categoria_indice; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON INDEX pmtm_categoria_indice IS 'Indice sobre valores relativos a categorías de mediciones';


--
-- TOC entry 1886 (class 1259 OID 205073)
-- Dependencies: 1549
-- Name: pmtm_medico_indice; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX pmtm_medico_indice ON paciente_medico_categoria_medicion USING btree (id_medico) WITH (fillfactor=60);


--
-- TOC entry 1887 (class 1259 OID 205074)
-- Dependencies: 1549
-- Name: pmtm_paciente; Type: INDEX; Schema: public; Owner: smsadmin; Tablespace: 
--

CREATE INDEX pmtm_paciente ON paciente_medico_categoria_medicion USING btree (id_paciente) WITH (fillfactor=60);


--
-- TOC entry 1635 (class 2618 OID 205076)
-- Dependencies: 1531 1531 1531 1531 1531 1531 1532
-- Name: comunicar_novedad_regla; Type: RULE; Schema: public; Owner: smsadmin
--

CREATE RULE comunicar_novedad_regla AS ON INSERT TO campania_novedad DO (INSERT INTO campania_novedad_backup (id, contenido, fecha_publicacion, id_campania) VALUES (new.id, new.contenido, new.fecha_publicacion, new.id_campania); NOTIFY campania_novedad; );


--
-- TOC entry 2060 (class 0 OID 0)
-- Dependencies: 1635
-- Name: RULE comunicar_novedad_regla ON campania_novedad; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON RULE comunicar_novedad_regla ON campania_novedad IS 'Regla para notificaciones asincrónicas a la aplicación ante llegada de una respuesta del médico.';


--
-- TOC entry 1636 (class 2618 OID 205077)
-- Dependencies: 1543 1543 1544 1543 1543 1543 1543 1543 1543 1543
-- Name: comunicar_respuesta_regla; Type: RULE; Schema: public; Owner: smsadmin
--

CREATE RULE comunicar_respuesta_regla AS ON INSERT TO medicion_respuesta DO (INSERT INTO medicion_respuesta_backup (id, id_paciente, id_medico, fecha_hora_respuesta, cantidad_avisos, enviado, respuesta) VALUES (new.id, new.id_paciente, new.id_medico, new.fecha_hora_respuesta, new.cantidad_avisos, new.enviado, new.respuesta); NOTIFY medicion_respuesta; );


--
-- TOC entry 1905 (class 2606 OID 213000)
-- Dependencies: 1528 1529 1849
-- Name: fk_campania; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY campania_editor
    ADD CONSTRAINT fk_campania FOREIGN KEY (id_campania) REFERENCES campania(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2061 (class 0 OID 0)
-- Dependencies: 1905
-- Name: CONSTRAINT fk_campania ON campania_editor; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_campania ON campania_editor IS 'Clave extranjera a la campaña del editor';


--
-- TOC entry 1906 (class 2606 OID 213005)
-- Dependencies: 1528 1531 1849
-- Name: fk_campania; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY campania_novedad
    ADD CONSTRAINT fk_campania FOREIGN KEY (id_campania) REFERENCES campania(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2062 (class 0 OID 0)
-- Dependencies: 1906
-- Name: CONSTRAINT fk_campania ON campania_novedad; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_campania ON campania_novedad IS 'Clave extranjera a la campaña padre';


--
-- TOC entry 1907 (class 2606 OID 213016)
-- Dependencies: 1528 1534 1849
-- Name: fk_campania; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY campania_usuario
    ADD CONSTRAINT fk_campania FOREIGN KEY (id_campania) REFERENCES campania(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2063 (class 0 OID 0)
-- Dependencies: 1907
-- Name: CONSTRAINT fk_campania ON campania_usuario; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_campania ON campania_usuario IS 'Clave extranjera a la campaña de la cual el usuario esta asociado.';


--
-- TOC entry 1919 (class 2606 OID 229393)
-- Dependencies: 1535 1549 1861
-- Name: fk_categoria; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY paciente_medico_categoria_medicion
    ADD CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categoria_medicion(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1920 (class 2606 OID 237593)
-- Dependencies: 1550 1861 1535
-- Name: fk_categoria; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY parametro_categoria_medicion
    ADD CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categoria_medicion(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2064 (class 0 OID 0)
-- Dependencies: 1920
-- Name: CONSTRAINT fk_categoria ON parametro_categoria_medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_categoria ON parametro_categoria_medicion IS 'Clave extranjera a categoría';


--
-- TOC entry 1921 (class 2606 OID 237604)
-- Dependencies: 1861 1535 1552
-- Name: fk_categoria; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY tipo_medicion
    ADD CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categoria_medicion(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2065 (class 0 OID 0)
-- Dependencies: 1921
-- Name: CONSTRAINT fk_categoria ON tipo_medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_categoria ON tipo_medicion IS 'Clave extranjera a categoría';


--
-- TOC entry 1915 (class 2606 OID 237581)
-- Dependencies: 1863 1548 1537
-- Name: fk_especialidad; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY medico
    ADD CONSTRAINT fk_especialidad FOREIGN KEY (especialidad) REFERENCES especialidad(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2066 (class 0 OID 0)
-- Dependencies: 1915
-- Name: CONSTRAINT fk_especialidad ON medico; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_especialidad ON medico IS 'Clave extranjera a especialidad';


--
-- TOC entry 1916 (class 2606 OID 237587)
-- Dependencies: 1865 1539 1548
-- Name: fk_institucion; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY medico
    ADD CONSTRAINT fk_institucion FOREIGN KEY (institucion) REFERENCES institucion(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2067 (class 0 OID 0)
-- Dependencies: 1916
-- Name: CONSTRAINT fk_institucion ON medico; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_institucion ON medico IS 'Clave extranjera a institucion	';


--
-- TOC entry 1904 (class 2606 OID 205128)
-- Dependencies: 1548 1529 1881
-- Name: fk_medico; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY campania_editor
    ADD CONSTRAINT fk_medico FOREIGN KEY (id_medico) REFERENCES medico(id);


--
-- TOC entry 1912 (class 2606 OID 213027)
-- Dependencies: 1881 1543 1548
-- Name: fk_medico; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY medicion_respuesta
    ADD CONSTRAINT fk_medico FOREIGN KEY (id_medico) REFERENCES medico(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2068 (class 0 OID 0)
-- Dependencies: 1912
-- Name: CONSTRAINT fk_medico ON medicion_respuesta; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_medico ON medicion_respuesta IS 'Clave extranjera al médico que atendió la medición';


--
-- TOC entry 1917 (class 2606 OID 213042)
-- Dependencies: 1881 1548 1549
-- Name: fk_medico; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY paciente_medico_categoria_medicion
    ADD CONSTRAINT fk_medico FOREIGN KEY (id_medico) REFERENCES medico(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2069 (class 0 OID 0)
-- Dependencies: 1917
-- Name: CONSTRAINT fk_medico ON paciente_medico_categoria_medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_medico ON paciente_medico_categoria_medicion IS 'Clave extranjera a tabla médico';


--
-- TOC entry 1911 (class 2606 OID 213047)
-- Dependencies: 1877 1546 1541
-- Name: fk_paciente; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY medicion
    ADD CONSTRAINT fk_paciente FOREIGN KEY (id_paciente) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2070 (class 0 OID 0)
-- Dependencies: 1911
-- Name: CONSTRAINT fk_paciente ON medicion; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_paciente ON medicion IS 'Usuario paciente que registra la medición';


--
-- TOC entry 1913 (class 2606 OID 213052)
-- Dependencies: 1877 1546 1543
-- Name: fk_paciente; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY medicion_respuesta
    ADD CONSTRAINT fk_paciente FOREIGN KEY (id_paciente) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2071 (class 0 OID 0)
-- Dependencies: 1913
-- Name: CONSTRAINT fk_paciente ON medicion_respuesta; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_paciente ON medicion_respuesta IS 'Clave extranjera al usuario que registró la medición';


--
-- TOC entry 1918 (class 2606 OID 213057)
-- Dependencies: 1877 1549 1546
-- Name: fk_paciente; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY paciente_medico_categoria_medicion
    ADD CONSTRAINT fk_paciente FOREIGN KEY (id_paciente) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1922 (class 2606 OID 237632)
-- Dependencies: 1555 1900 1557
-- Name: fk_pais; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY seccion
    ADD CONSTRAINT fk_pais FOREIGN KEY (id_pais) REFERENCES pais(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1923 (class 2606 OID 237645)
-- Dependencies: 1555 1558 1898
-- Name: fk_seccion; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY subseccion
    ADD CONSTRAINT fk_seccion FOREIGN KEY (id_seccion) REFERENCES seccion(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1909 (class 2606 OID 237667)
-- Dependencies: 1902 1558 1539
-- Name: fk_subseccion; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY institucion
    ADD CONSTRAINT fk_subseccion FOREIGN KEY (subseccion) REFERENCES subseccion(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 1914 (class 2606 OID 221192)
-- Dependencies: 1877 1546 1548
-- Name: fk_usuario; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY medico
    ADD CONSTRAINT fk_usuario FOREIGN KEY (id) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1908 (class 2606 OID 237576)
-- Dependencies: 1534 1546 1877
-- Name: fk_usuario; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY campania_usuario
    ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2072 (class 0 OID 0)
-- Dependencies: 1908
-- Name: CONSTRAINT fk_usuario ON campania_usuario; Type: COMMENT; Schema: public; Owner: smsadmin
--

COMMENT ON CONSTRAINT fk_usuario ON campania_usuario IS 'Clave extranjera a usuario';


--
-- TOC entry 1910 (class 2606 OID 205173)
-- Dependencies: 1541 1894 1552
-- Name: tipo_medicion; Type: FK CONSTRAINT; Schema: public; Owner: smsadmin
--

ALTER TABLE ONLY medicion
    ADD CONSTRAINT tipo_medicion FOREIGN KEY (tipo_medicion) REFERENCES tipo_medicion(id);


--
-- TOC entry 1947 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: smsadmin
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM smsadmin;
GRANT ALL ON SCHEMA public TO smsadmin;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 1952 (class 0 OID 0)
-- Dependencies: 1528
-- Name: campania; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE campania FROM PUBLIC;
REVOKE ALL ON TABLE campania FROM smsadmin;
GRANT ALL ON TABLE campania TO smsadmin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE campania TO usuario_web;
GRANT SELECT ON TABLE campania TO usuario_procesamiento;


--
-- TOC entry 1956 (class 0 OID 0)
-- Dependencies: 1529
-- Name: campania_editor; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE campania_editor FROM PUBLIC;
REVOKE ALL ON TABLE campania_editor FROM smsadmin;
GRANT ALL ON TABLE campania_editor TO smsadmin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE campania_editor TO usuario_web;
GRANT SELECT ON TABLE campania_editor TO usuario_procesamiento;


--
-- TOC entry 1959 (class 0 OID 0)
-- Dependencies: 1530
-- Name: campania_id_seq; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON SEQUENCE campania_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE campania_id_seq FROM smsadmin;
GRANT ALL ON SEQUENCE campania_id_seq TO smsadmin;
GRANT SELECT,UPDATE ON SEQUENCE campania_id_seq TO usuario_web;
GRANT SELECT,UPDATE ON SEQUENCE campania_id_seq TO usuario_procesamiento;


--
-- TOC entry 1964 (class 0 OID 0)
-- Dependencies: 1531
-- Name: campania_novedad; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE campania_novedad FROM PUBLIC;
REVOKE ALL ON TABLE campania_novedad FROM smsadmin;
GRANT ALL ON TABLE campania_novedad TO smsadmin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE campania_novedad TO usuario_web;
GRANT SELECT ON TABLE campania_novedad TO usuario_procesamiento;


--
-- TOC entry 1970 (class 0 OID 0)
-- Dependencies: 1532
-- Name: campania_novedad_backup; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE campania_novedad_backup FROM PUBLIC;
REVOKE ALL ON TABLE campania_novedad_backup FROM smsadmin;
GRANT ALL ON TABLE campania_novedad_backup TO smsadmin;
GRANT SELECT,DELETE ON TABLE campania_novedad_backup TO usuario_procesamiento;


--
-- TOC entry 1973 (class 0 OID 0)
-- Dependencies: 1533
-- Name: campania_novedad_id_seq; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON SEQUENCE campania_novedad_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE campania_novedad_id_seq FROM smsadmin;
GRANT ALL ON SEQUENCE campania_novedad_id_seq TO smsadmin;
GRANT SELECT,UPDATE ON SEQUENCE campania_novedad_id_seq TO usuario_web;
GRANT SELECT,UPDATE ON SEQUENCE campania_novedad_id_seq TO usuario_procesamiento;


--
-- TOC entry 1977 (class 0 OID 0)
-- Dependencies: 1534
-- Name: campania_usuario; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE campania_usuario FROM PUBLIC;
REVOKE ALL ON TABLE campania_usuario FROM smsadmin;
GRANT ALL ON TABLE campania_usuario TO smsadmin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE campania_usuario TO usuario_web;
GRANT SELECT ON TABLE campania_usuario TO usuario_procesamiento;


--
-- TOC entry 1979 (class 0 OID 0)
-- Dependencies: 1535
-- Name: categoria_medicion; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE categoria_medicion FROM PUBLIC;
REVOKE ALL ON TABLE categoria_medicion FROM smsadmin;
GRANT ALL ON TABLE categoria_medicion TO smsadmin;
GRANT SELECT ON TABLE categoria_medicion TO usuario_procesamiento;
GRANT SELECT ON TABLE categoria_medicion TO usuario_web;


--
-- TOC entry 1983 (class 0 OID 0)
-- Dependencies: 1537
-- Name: especialidad; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE especialidad FROM PUBLIC;
REVOKE ALL ON TABLE especialidad FROM smsadmin;
GRANT ALL ON TABLE especialidad TO smsadmin;
GRANT SELECT ON TABLE especialidad TO usuario_procesamiento;
GRANT SELECT ON TABLE especialidad TO usuario_web;


--
-- TOC entry 1988 (class 0 OID 0)
-- Dependencies: 1539
-- Name: institucion; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE institucion FROM PUBLIC;
REVOKE ALL ON TABLE institucion FROM smsadmin;
GRANT ALL ON TABLE institucion TO smsadmin;
GRANT SELECT ON TABLE institucion TO usuario_procesamiento;
GRANT SELECT ON TABLE institucion TO usuario_web;


--
-- TOC entry 1993 (class 0 OID 0)
-- Dependencies: 1541
-- Name: medicion; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE medicion FROM PUBLIC;
REVOKE ALL ON TABLE medicion FROM smsadmin;
GRANT ALL ON TABLE medicion TO smsadmin;
GRANT SELECT,INSERT,UPDATE ON TABLE medicion TO usuario_web;
GRANT SELECT ON TABLE medicion TO usuario_procesamiento;


--
-- TOC entry 1996 (class 0 OID 0)
-- Dependencies: 1542
-- Name: medicion_id_seq; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON SEQUENCE medicion_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE medicion_id_seq FROM smsadmin;
GRANT ALL ON SEQUENCE medicion_id_seq TO smsadmin;
GRANT SELECT,UPDATE ON SEQUENCE medicion_id_seq TO usuario_web;
GRANT SELECT,UPDATE ON SEQUENCE medicion_id_seq TO usuario_procesamiento;


--
-- TOC entry 2002 (class 0 OID 0)
-- Dependencies: 1543
-- Name: medicion_respuesta; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE medicion_respuesta FROM PUBLIC;
REVOKE ALL ON TABLE medicion_respuesta FROM smsadmin;
GRANT SELECT,INSERT,UPDATE ON TABLE medicion_respuesta TO usuario_web;
GRANT SELECT ON TABLE medicion_respuesta TO usuario_procesamiento;


--
-- TOC entry 2011 (class 0 OID 0)
-- Dependencies: 1544
-- Name: medicion_respuesta_backup; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE medicion_respuesta_backup FROM PUBLIC;
REVOKE ALL ON TABLE medicion_respuesta_backup FROM smsadmin;
GRANT ALL ON TABLE medicion_respuesta_backup TO smsadmin;
GRANT SELECT,DELETE ON TABLE medicion_respuesta_backup TO usuario_procesamiento;


--
-- TOC entry 2014 (class 0 OID 0)
-- Dependencies: 1545
-- Name: medicion_respuesta_id_seq; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON SEQUENCE medicion_respuesta_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE medicion_respuesta_id_seq FROM smsadmin;
GRANT ALL ON SEQUENCE medicion_respuesta_id_seq TO smsadmin;
GRANT SELECT,UPDATE ON SEQUENCE medicion_respuesta_id_seq TO usuario_web;
GRANT SELECT,UPDATE ON SEQUENCE medicion_respuesta_id_seq TO usuario_procesamiento;


--
-- TOC entry 2019 (class 0 OID 0)
-- Dependencies: 1546
-- Name: usuario; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE usuario FROM PUBLIC;
REVOKE ALL ON TABLE usuario FROM smsadmin;
GRANT ALL ON TABLE usuario TO smsadmin;
GRANT SELECT,INSERT,UPDATE ON TABLE usuario TO usuario_web;
GRANT SELECT ON TABLE usuario TO usuario_procesamiento;


--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 1547
-- Name: usuario_id_seq; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON SEQUENCE usuario_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE usuario_id_seq FROM smsadmin;
GRANT ALL ON SEQUENCE usuario_id_seq TO smsadmin;
GRANT SELECT,UPDATE ON SEQUENCE usuario_id_seq TO usuario_web;
GRANT SELECT,UPDATE ON SEQUENCE usuario_id_seq TO usuario_procesamiento;


--
-- TOC entry 2024 (class 0 OID 0)
-- Dependencies: 1548
-- Name: medico; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE medico FROM PUBLIC;
REVOKE ALL ON TABLE medico FROM smsadmin;
GRANT SELECT,INSERT,UPDATE ON TABLE medico TO usuario_web;
GRANT SELECT ON TABLE medico TO usuario_procesamiento;


--
-- TOC entry 2028 (class 0 OID 0)
-- Dependencies: 1549
-- Name: paciente_medico_categoria_medicion; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE paciente_medico_categoria_medicion FROM PUBLIC;
REVOKE ALL ON TABLE paciente_medico_categoria_medicion FROM smsadmin;
GRANT ALL ON TABLE paciente_medico_categoria_medicion TO smsadmin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE paciente_medico_categoria_medicion TO usuario_web;
GRANT SELECT ON TABLE paciente_medico_categoria_medicion TO usuario_procesamiento;


--
-- TOC entry 2032 (class 0 OID 0)
-- Dependencies: 1557
-- Name: pais; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE pais FROM PUBLIC;
REVOKE ALL ON TABLE pais FROM smsadmin;
GRANT ALL ON TABLE pais TO smsadmin;
GRANT SELECT ON TABLE pais TO usuario_procesamiento;


--
-- TOC entry 2037 (class 0 OID 0)
-- Dependencies: 1550
-- Name: parametro_categoria_medicion; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE parametro_categoria_medicion FROM PUBLIC;
REVOKE ALL ON TABLE parametro_categoria_medicion FROM smsadmin;
GRANT ALL ON TABLE parametro_categoria_medicion TO smsadmin;
GRANT SELECT ON TABLE parametro_categoria_medicion TO usuario_procesamiento;


--
-- TOC entry 2044 (class 0 OID 0)
-- Dependencies: 1555
-- Name: seccion; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE seccion FROM PUBLIC;
REVOKE ALL ON TABLE seccion FROM smsadmin;
GRANT ALL ON TABLE seccion TO smsadmin;
GRANT SELECT ON TABLE seccion TO usuario_procesamiento;


--
-- TOC entry 2050 (class 0 OID 0)
-- Dependencies: 1558
-- Name: subseccion; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE subseccion FROM PUBLIC;
REVOKE ALL ON TABLE subseccion FROM smsadmin;
GRANT ALL ON TABLE subseccion TO smsadmin;
GRANT SELECT ON TABLE subseccion TO usuario_procesamiento;


--
-- TOC entry 2056 (class 0 OID 0)
-- Dependencies: 1552
-- Name: tipo_medicion; Type: ACL; Schema: public; Owner: smsadmin
--

REVOKE ALL ON TABLE tipo_medicion FROM PUBLIC;
REVOKE ALL ON TABLE tipo_medicion FROM smsadmin;
GRANT ALL ON TABLE tipo_medicion TO smsadmin;
GRANT SELECT ON TABLE tipo_medicion TO usuario_procesamiento;
GRANT SELECT ON TABLE tipo_medicion TO usuario_web;


-- Completed on 2010-08-09 22:54:20

--
-- PostgreSQL database dump complete
--



