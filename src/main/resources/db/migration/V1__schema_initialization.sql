--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

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
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comment
(
    id         bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    comment    character varying(255),
    rating_id  bigint,
    user_id    bigint
);


ALTER TABLE public.comment
    OWNER TO postgres;

--
-- Name: rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rating
(
    id         bigint  NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    review     character varying(255),
    value      integer NOT NULL,
    book_id    bigint,
    user_id    bigint,
    CONSTRAINT rating_value_check CHECK (((value <= 5) AND (value >= 1)))
);


ALTER TABLE public.rating
    OWNER TO postgres;

--
-- Name: share; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.share
(
    id          bigint NOT NULL,
    created_at  timestamp(6) without time zone,
    updated_at  timestamp(6) without time zone,
    share_quote text,
    rating_id   bigint,
    user_id     bigint
);


ALTER TABLE public.share
    OWNER TO postgres;

--
-- Name: activity; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.activity AS
SELECT 'RATING'::text AS type,
       r.id,
       r.updated_at,
       r.created_at
FROM public.rating r
UNION ALL
SELECT 'COMMENT'::text AS type,
       c.id,
       c.updated_at,
       c.created_at
FROM public.comment c
UNION ALL
SELECT 'SHARE'::text AS type,
       s.id,
       s.updated_at,
       s.created_at
FROM public.share s;


ALTER VIEW public.activity OWNER TO postgres;

--
-- Name: author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.author
(
    id                   bigint NOT NULL,
    created_at           timestamp(6) without time zone,
    updated_at           timestamp(6) without time zone,
    good_reads_author_id bigint,
    name                 character varying(255)
);


ALTER TABLE public.author
    OWNER TO postgres;

--
-- Name: author_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.author_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.author_id_seq OWNER TO postgres;

--
-- Name: author_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.author_id_seq OWNED BY public.author.id;


--
-- Name: book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book
(
    id               bigint  NOT NULL,
    created_at       timestamp(6) without time zone,
    updated_at       timestamp(6) without time zone,
    isbn             character varying(255),
    isbn13           character varying(255),
    cover_image      text,
    description      text,
    goodreads_id     bigint,
    goodreads_link   text,
    language_code    character varying(255),
    page_count       integer NOT NULL,
    publication_year integer NOT NULL,
    publisher        character varying(255),
    title            text
);


ALTER TABLE public.book
    OWNER TO postgres;

--
-- Name: book_author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_author
(
    sort_order integer NOT NULL,
    book_id    bigint  NOT NULL,
    author_id  bigint  NOT NULL
);


ALTER TABLE public.book_author
    OWNER TO postgres;

--
-- Name: book_genre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_genre
(
    sort_order integer NOT NULL,
    genre_id   bigint  NOT NULL,
    book_id    bigint  NOT NULL
);


ALTER TABLE public.book_genre
    OWNER TO postgres;

--
-- Name: book_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.book_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.book_id_seq OWNER TO postgres;

--
-- Name: book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_id_seq OWNED BY public.book.id;


--
-- Name: book_shelf; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_shelf
(
    id          bigint NOT NULL,
    created_at  timestamp(6) without time zone,
    updated_at  timestamp(6) without time zone,
    access      character varying(255),
    description character varying(255),
    title       character varying(255),
    CONSTRAINT book_shelf_access_check CHECK (((access)::text = ANY
                                               ((ARRAY ['PRIVATE'::character varying, 'FRIENDS'::character varying, 'PUBLIC'::character varying])::text[])))
);


ALTER TABLE public.book_shelf
    OWNER TO postgres;

--
-- Name: book_shelf_book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_shelf_book
(
    sort_order    integer NOT NULL,
    book_shelf_id bigint  NOT NULL,
    book_id       bigint  NOT NULL
);


ALTER TABLE public.book_shelf_book
    OWNER TO postgres;

--
-- Name: book_shelf_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.book_shelf_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.book_shelf_id_seq OWNER TO postgres;

--
-- Name: book_shelf_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_shelf_id_seq OWNED BY public.book_shelf.id;


--
-- Name: comment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.comment_id_seq OWNER TO postgres;

--
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comment_id_seq OWNED BY public.comment.id;


--
-- Name: follower; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.follower
(
    created_at  timestamp(6) without time zone,
    updated_at  timestamp(6) without time zone,
    follower_id bigint NOT NULL,
    followed_id bigint NOT NULL
);


ALTER TABLE public.follower
    OWNER TO postgres;

--
-- Name: genre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genre
(
    id         bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    name       character varying(255)
);


ALTER TABLE public.genre
    OWNER TO postgres;

--
-- Name: genre_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.genre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.genre_id_seq OWNER TO postgres;

--
-- Name: genre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.genre_id_seq OWNED BY public.genre.id;


--
-- Name: goal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.goal
(
    id          bigint NOT NULL,
    created_at  timestamp(6) without time zone,
    updated_at  timestamp(6) without time zone,
    description character varying(255),
    title       character varying(255),
    year        integer
);


ALTER TABLE public.goal
    OWNER TO postgres;

--
-- Name: goal_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.goal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.goal_id_seq OWNER TO postgres;

--
-- Name: goal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.goal_id_seq OWNED BY public.goal.id;


--
-- Name: publisher; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.publisher
(
    id         bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    name       character varying(255)
);


ALTER TABLE public.publisher
    OWNER TO postgres;

--
-- Name: publisher_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.publisher_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.publisher_id_seq OWNER TO postgres;

--
-- Name: publisher_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.publisher_id_seq OWNED BY public.publisher.id;


--
-- Name: rating_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.rating_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.rating_id_seq OWNER TO postgres;

--
-- Name: rating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.rating_id_seq OWNED BY public.rating.id;


--
-- Name: share_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.share_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.share_id_seq OWNER TO postgres;

--
-- Name: share_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.share_id_seq OWNED BY public.share.id;


--
-- Name: user_book_activity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_book_activity
(
    id         bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    status     character varying(255),
    book_id    bigint,
    user_id    bigint,
    CONSTRAINT user_book_activity_status_check CHECK (((status)::text = ANY
                                                       ((ARRAY ['NONE'::character varying, 'READ'::character varying, 'READING'::character varying, 'WANT_TO_READ'::character varying])::text[])))
);


ALTER TABLE public.user_book_activity
    OWNER TO postgres;

--
-- Name: user_book_activity_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_book_activity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_book_activity_id_seq OWNER TO postgres;

--
-- Name: user_book_activity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_book_activity_id_seq OWNED BY public.user_book_activity.id;


--
-- Name: user_book_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_book_status
(
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    status     character varying(255),
    user_id    bigint NOT NULL,
    book_id    bigint NOT NULL,
    CONSTRAINT user_book_status_status_check CHECK (((status)::text = ANY
                                                     ((ARRAY ['NONE'::character varying, 'READ'::character varying, 'READING'::character varying, 'WANT_TO_READ'::character varying])::text[])))
);


ALTER TABLE public.user_book_status
    OWNER TO postgres;

--
-- Name: user_goal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_goal
(
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    target     integer NOT NULL,
    user_id    bigint  NOT NULL,
    goal_id    bigint  NOT NULL,
    CONSTRAINT user_goal_target_check CHECK ((target >= 1))
);


ALTER TABLE public.user_goal
    OWNER TO postgres;

--
-- Name: user_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_profile
(
    id                    bigint NOT NULL,
    created_at            timestamp(6) without time zone,
    updated_at            timestamp(6) without time zone,
    bio                   character varying(255),
    cover_picture         character varying(255),
    date_of_birth         date,
    display_name          character varying(255),
    facebook_profile      character varying(255),
    first_name            character varying(255),
    gender                character varying(255),
    goodreads_profile     character varying(255),
    instagram_profile     character varying(255),
    last_name             character varying(255),
    linkedin_profile      character varying(255),
    profile_picture       character varying(255),
    twitter_profile       character varying(255),
    youtube_profile       character varying(255),
    user_id               bigint,
    want_to_read_shelf_id bigint,
    CONSTRAINT user_profile_gender_check CHECK (((gender)::text = ANY
                                                 ((ARRAY ['MALE'::character varying, 'FEMALE'::character varying])::text[])))
);


ALTER TABLE public.user_profile
    OWNER TO postgres;

--
-- Name: user_profile_genre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_profile_genre
(
    sort_order integer NOT NULL,
    profile_id bigint  NOT NULL,
    genre_id   bigint  NOT NULL
);


ALTER TABLE public.user_profile_genre
    OWNER TO postgres;

--
-- Name: user_profile_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_profile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_profile_id_seq OWNER TO postgres;

--
-- Name: user_profile_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_profile_id_seq OWNED BY public.user_profile.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users
(
    id                     bigint                 NOT NULL,
    created_at             timestamp(6) without time zone,
    updated_at             timestamp(6) without time zone,
    authorities            character varying(255)[],
    email                  character varying(255) NOT NULL,
    is_account_non_expired boolean                NOT NULL,
    is_account_non_locked  boolean                NOT NULL,
    is_enabled             boolean                NOT NULL,
    password               character varying(255),
    role                   character varying(255),
    username               character varying(255) NOT NULL,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY
                                        ((ARRAY ['GUEST'::character varying, 'CUSTOM'::character varying, 'USER'::character varying, 'MANAGER'::character varying, 'ADMIN'::character varying])::text[])))
);


ALTER TABLE public.users
    OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: author id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ALTER COLUMN id SET DEFAULT nextval('public.author_id_seq'::regclass);


--
-- Name: book id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ALTER COLUMN id SET DEFAULT nextval('public.book_id_seq'::regclass);


--
-- Name: book_shelf id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_shelf
    ALTER COLUMN id SET DEFAULT nextval('public.book_shelf_id_seq'::regclass);


--
-- Name: comment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ALTER COLUMN id SET DEFAULT nextval('public.comment_id_seq'::regclass);


--
-- Name: genre id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre
    ALTER COLUMN id SET DEFAULT nextval('public.genre_id_seq'::regclass);


--
-- Name: goal id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goal
    ALTER COLUMN id SET DEFAULT nextval('public.goal_id_seq'::regclass);


--
-- Name: publisher id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publisher
    ALTER COLUMN id SET DEFAULT nextval('public.publisher_id_seq'::regclass);


--
-- Name: rating id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating
    ALTER COLUMN id SET DEFAULT nextval('public.rating_id_seq'::regclass);


--
-- Name: share id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.share
    ALTER COLUMN id SET DEFAULT nextval('public.share_id_seq'::regclass);


--
-- Name: user_book_activity id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_book_activity
    ALTER COLUMN id SET DEFAULT nextval('public.user_book_activity_id_seq'::regclass);


--
-- Name: user_profile id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile
    ALTER COLUMN id SET DEFAULT nextval('public.user_profile_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: author author_goodreads_author_id_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_goodreads_author_id_idx UNIQUE (good_reads_author_id);


--
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);


--
-- Name: book_author book_author_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_author
    ADD CONSTRAINT book_author_pkey PRIMARY KEY (author_id, book_id);


--
-- Name: book_genre book_genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_genre
    ADD CONSTRAINT book_genre_pkey PRIMARY KEY (book_id, genre_id);


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: book_shelf_book book_shelf_book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_shelf_book
    ADD CONSTRAINT book_shelf_book_pkey PRIMARY KEY (book_id, book_shelf_id);


--
-- Name: book_shelf book_shelf_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_shelf
    ADD CONSTRAINT book_shelf_pkey PRIMARY KEY (id);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- Name: follower follower_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.follower
    ADD CONSTRAINT follower_pkey PRIMARY KEY (followed_id, follower_id);


--
-- Name: genre genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- Name: goal goal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goal
    ADD CONSTRAINT goal_pkey PRIMARY KEY (id);


--
-- Name: publisher publisher_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publisher
    ADD CONSTRAINT publisher_pkey PRIMARY KEY (id);


--
-- Name: rating rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT rating_pkey PRIMARY KEY (id);


--
-- Name: share share_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.share
    ADD CONSTRAINT share_pkey PRIMARY KEY (id);


--
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: user_profile uk_ebc21hy5j7scdvcjt0jy6xxrv; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile
    ADD CONSTRAINT uk_ebc21hy5j7scdvcjt0jy6xxrv UNIQUE (user_id);


--
-- Name: user_profile uk_h1048s5lcxjnsjfdck8cibv2k; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile
    ADD CONSTRAINT uk_h1048s5lcxjnsjfdck8cibv2k UNIQUE (want_to_read_shelf_id);


--
-- Name: goal uk_qp1srm8n1roo6h84o471mhd47; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goal
    ADD CONSTRAINT uk_qp1srm8n1roo6h84o471mhd47 UNIQUE (year);


--
-- Name: users uk_r43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: user_book_activity user_book_activity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_book_activity
    ADD CONSTRAINT user_book_activity_pkey PRIMARY KEY (id);


--
-- Name: user_book_status user_book_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_book_status
    ADD CONSTRAINT user_book_status_pkey PRIMARY KEY (book_id, user_id);


--
-- Name: user_goal user_goal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_goal
    ADD CONSTRAINT user_goal_pkey PRIMARY KEY (goal_id, user_id);


--
-- Name: user_profile_genre user_profile_genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile_genre
    ADD CONSTRAINT user_profile_genre_pkey PRIMARY KEY (genre_id, profile_id);


--
-- Name: user_profile user_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile
    ADD CONSTRAINT user_profile_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: comment fk1ycdvbhcpqly0e1hje19gp6um; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fk1ycdvbhcpqly0e1hje19gp6um FOREIGN KEY (rating_id) REFERENCES public.rating (id);


--
-- Name: user_book_status fk3wrjv72lf4u67elc8vhjtgs84; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_book_status
    ADD CONSTRAINT fk3wrjv72lf4u67elc8vhjtgs84 FOREIGN KEY (book_id) REFERENCES public.book (id);


--
-- Name: book_shelf_book fk51ocbvgx36oggmqxm70pbtfgm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_shelf_book
    ADD CONSTRAINT fk51ocbvgx36oggmqxm70pbtfgm FOREIGN KEY (book_id) REFERENCES public.book (id);


--
-- Name: book_genre fk52evq6pdc5ypanf41bij5u218; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_genre
    ADD CONSTRAINT fk52evq6pdc5ypanf41bij5u218 FOREIGN KEY (book_id) REFERENCES public.book (id);


--
-- Name: user_book_status fk6ggvp4rcd68xc0t3krh9ec341; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_book_status
    ADD CONSTRAINT fk6ggvp4rcd68xc0t3krh9ec341 FOREIGN KEY (user_id) REFERENCES public.users (id);


--
-- Name: rating fk7y1acs6la7vkgb5ulm44729sc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT fk7y1acs6la7vkgb5ulm44729sc FOREIGN KEY (book_id) REFERENCES public.book (id);


--
-- Name: user_profile fk8bbdocfaltvsgg7pluom0eq29; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile
    ADD CONSTRAINT fk8bbdocfaltvsgg7pluom0eq29 FOREIGN KEY (want_to_read_shelf_id) REFERENCES public.book_shelf (id);


--
-- Name: user_goal fk8h8plouhv0i01uem7wsxn0v6v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_goal
    ADD CONSTRAINT fk8h8plouhv0i01uem7wsxn0v6v FOREIGN KEY (goal_id) REFERENCES public.goal (id);


--
-- Name: book_genre fk8l6ops8exmjrlr89hmfow4mmo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_genre
    ADD CONSTRAINT fk8l6ops8exmjrlr89hmfow4mmo FOREIGN KEY (genre_id) REFERENCES public.genre (id);


--
-- Name: share fk8w9gobrtcmuyifxiyh1gvlnka; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.share
    ADD CONSTRAINT fk8w9gobrtcmuyifxiyh1gvlnka FOREIGN KEY (rating_id) REFERENCES public.rating (id);


--
-- Name: book_author fkbjqhp85wjv8vpr0beygh6jsgo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_author
    ADD CONSTRAINT fkbjqhp85wjv8vpr0beygh6jsgo FOREIGN KEY (author_id) REFERENCES public.author (id);


--
-- Name: user_goal fkd09eq8bpxgy8p5wi7mjglujck; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_goal
    ADD CONSTRAINT fkd09eq8bpxgy8p5wi7mjglujck FOREIGN KEY (user_id) REFERENCES public.users (id);


--
-- Name: follower fkd3eogweewsvq7fvghxqwaupx0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.follower
    ADD CONSTRAINT fkd3eogweewsvq7fvghxqwaupx0 FOREIGN KEY (follower_id) REFERENCES public.users (id);


--
-- Name: book_shelf_book fkdk9sx9x6fc8vbbbesmbc1jk3x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_shelf_book
    ADD CONSTRAINT fkdk9sx9x6fc8vbbbesmbc1jk3x FOREIGN KEY (book_shelf_id) REFERENCES public.book_shelf (id);


--
-- Name: user_book_activity fke7c2ckerv1mxm2kgwvxjhpk4y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_book_activity
    ADD CONSTRAINT fke7c2ckerv1mxm2kgwvxjhpk4y FOREIGN KEY (user_id) REFERENCES public.users (id);


--
-- Name: rating fkf68lgbsbxl310n0jifwpfqgfh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT fkf68lgbsbxl310n0jifwpfqgfh FOREIGN KEY (user_id) REFERENCES public.users (id);


--
-- Name: follower fkg0qvdavbkdr7cf6hroqnf3r9p; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.follower
    ADD CONSTRAINT fkg0qvdavbkdr7cf6hroqnf3r9p FOREIGN KEY (followed_id) REFERENCES public.users (id);


--
-- Name: book_author fkhwgu59n9o80xv75plf9ggj7xn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_author
    ADD CONSTRAINT fkhwgu59n9o80xv75plf9ggj7xn FOREIGN KEY (book_id) REFERENCES public.book (id);


--
-- Name: user_profile_genre fkjji5y02d6lek6leuyunykeoia; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile_genre
    ADD CONSTRAINT fkjji5y02d6lek6leuyunykeoia FOREIGN KEY (profile_id) REFERENCES public.user_profile (id);


--
-- Name: user_book_activity fko2qyv9yseq9hrftfhbji5wksm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_book_activity
    ADD CONSTRAINT fko2qyv9yseq9hrftfhbji5wksm FOREIGN KEY (book_id) REFERENCES public.book (id);


--
-- Name: user_profile_genre fkpnd4a9jl6p2lk9xya6jgslbkd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile_genre
    ADD CONSTRAINT fkpnd4a9jl6p2lk9xya6jgslbkd FOREIGN KEY (genre_id) REFERENCES public.genre (id);


--
-- Name: comment fkqm52p1v3o13hy268he0wcngr5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkqm52p1v3o13hy268he0wcngr5 FOREIGN KEY (user_id) REFERENCES public.users (id);


--
-- Name: share fkqpl1v3hvb4io91a90ltbfhkwf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.share
    ADD CONSTRAINT fkqpl1v3hvb4io91a90ltbfhkwf FOREIGN KEY (user_id) REFERENCES public.users (id);


--
-- Name: user_profile fkuganfwvnbll4kn2a3jeyxtyi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_profile
    ADD CONSTRAINT fkuganfwvnbll4kn2a3jeyxtyi FOREIGN KEY (user_id) REFERENCES public.users (id);


--
-- PostgreSQL database dump complete
--

