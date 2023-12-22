--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.1 (Debian 16.1-1.pgdg120+1)

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

ALTER TABLE ONLY public.card_stock DROP CONSTRAINT card_stock_storage_id_fkey;
ALTER TABLE ONLY public.card DROP CONSTRAINT card_card_stock_id_fkey;
DROP INDEX public.flyway_schema_history_s_idx;
ALTER TABLE ONLY public.card_stock DROP CONSTRAINT unique_storage_id_card_stock_name;
ALTER TABLE ONLY public.card DROP CONSTRAINT unique_card_stock_key;
ALTER TABLE ONLY public.storage DROP CONSTRAINT storage_pkey;
ALTER TABLE ONLY public.flyway_schema_history DROP CONSTRAINT flyway_schema_history_pk;
ALTER TABLE ONLY public.card_stock DROP CONSTRAINT card_stock_pkey;
ALTER TABLE ONLY public.card DROP CONSTRAINT card_pkey;
ALTER TABLE public.storage ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.card_stock ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.card ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE public.storage_id_seq;
DROP TABLE public.storage;
DROP TABLE public.flyway_schema_history;
DROP SEQUENCE public.card_stock_id_seq;
DROP TABLE public.card_stock;
DROP SEQUENCE public.card_id_seq;
DROP TABLE public.card;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: card; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.card (
    id integer NOT NULL,
    card_stock_id integer NOT NULL,
    card_key character varying(255) NOT NULL,
    card_value text,
    point_from_key integer NOT NULL,
    point_to_key integer NOT NULL,
    status_from_key character varying(255),
    status_to_key character varying(255)
);


ALTER TABLE public.card OWNER TO root;

--
-- Name: card_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.card_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.card_id_seq OWNER TO root;

--
-- Name: card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.card_id_seq OWNED BY public.card.id;


--
-- Name: card_stock; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.card_stock (
    id integer NOT NULL,
    storage_id integer NOT NULL,
    card_stock_name character varying(255),
    description text,
    key_type character varying(255),
    value_type character varying(255),
    max_point integer NOT NULL,
    test_mode_is_available boolean NOT NULL,
    only_from_key boolean NOT NULL
);


ALTER TABLE public.card_stock OWNER TO root;

--
-- Name: card_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.card_stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.card_stock_id_seq OWNER TO root;

--
-- Name: card_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.card_stock_id_seq OWNED BY public.card_stock.id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO root;

--
-- Name: storage; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.storage (
    id integer NOT NULL,
    user_id bigint NOT NULL,
    storage_name character varying(255)
);


ALTER TABLE public.storage OWNER TO root;

--
-- Name: storage_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.storage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.storage_id_seq OWNER TO root;

--
-- Name: storage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.storage_id_seq OWNED BY public.storage.id;


--
-- Name: card id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.card ALTER COLUMN id SET DEFAULT nextval('public.card_id_seq'::regclass);


--
-- Name: card_stock id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.card_stock ALTER COLUMN id SET DEFAULT nextval('public.card_stock_id_seq'::regclass);


--
-- Name: storage id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.storage ALTER COLUMN id SET DEFAULT nextval('public.storage_id_seq'::regclass);


--
-- Data for Name: card; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.card (id, card_stock_id, card_key, card_value, point_from_key, point_to_key, status_from_key, status_to_key) FROM stdin;
74	1	matter	иметь значение	2	-1	NORMAL	HARD
62	1	declare	объявить, заявить, декларировать	1	2	NORMAL	NORMAL
76	1	implications	последствия	-2	-3	HARD	HARD
79	1	extension	расширение	2	1	NORMAL	NORMAL
83	1	comprehensive	комплексный, всеобъемлющий	-1	1	HARD	NORMAL
73	1	embrace	охватить, принять	-2	2	HARD	NORMAL
60	1	semicolon	;	5	5	NORMAL	NORMAL
35	1	patience	терпение	1	-3	NORMAL	HARD
39	1	muddle	запутаться	-1	-2	HARD	HARD
71	1	substitutable	заменяемый	2	2	NORMAL	NORMAL
28	1	just	просто	-1	2	HARD	NORMAL
72	1	agenda	повестка дня	2	3	NORMAL	NORMAL
69	1	confirm	подтвердить	1	-2	NORMAL	HARD
34	1	higher-ups	вышестоящие руководство	2	-2	NORMAL	HARD
30	1	wrapper	обёртка	-2	2	HARD	NORMAL
70	1	fancy	увлекаться	-2	-2	HARD	HARD
82	1	bottleneck	узкое места	-1	2	HARD	NORMAL
84	1	explore	изучить	-1	1	HARD	NORMAL
78	1	regardless	независимо от	-2	3	HARD	NORMAL
67	1	destination	назначение, путь	2	-2	NORMAL	HARD
27	1	equivalent	эквивалент, аналогичный	2	2	NORMAL	NORMAL
36	1	self-improvement	самосовершенствование	-1	3	HARD	NORMAL
58	1	bit	немного	-1	3	HARD	NORMAL
32	1	concise	кратко, лаконично	2	1	NORMAL	NORMAL
66	1	inside	внутри	2	-3	NORMAL	HARD
38	1	multiply	умножить	-1	-2	HARD	HARD
80	1	rapid	быстрый	1	3	NORMAL	NORMAL
65	1	colon	:	4	4	NORMAL	NORMAL
57	1	interoperable	совместимый	-2	-2	HARD	HARD
61	1	variable	переменная	1	2	NORMAL	NORMAL
26	1	expand	расширить	2	2	NORMAL	NORMAL
169	1	less	меньше	2	3	NORMAL	NORMAL
37	1	hint	подсказка	1	2	NORMAL	NORMAL
59	1	even	даже	-1	1	HARD	NORMAL
75	1	enhance	увеличить, повысить	1	-2	NORMAL	HARD
81	1	term	термин, понятие, срок, период	1	2	NORMAL	NORMAL
77	1	engagement	вовлеченность	2	3	NORMAL	NORMAL
68	1	dismiss	уволить	-1	-2	HARD	HARD
63	1	likely	вероятность, вероятно	1	1	NORMAL	NORMAL
31	1	pursuit	стремление	-2	-2	HARD	HARD
88	1	misconception	заблуждение	2	1	NORMAL	NORMAL
156	1	coupling	сокрытие	2	2	NORMAL	NORMAL
87	1	disrupt	нарушать	1	-2	NORMAL	HARD
93	1	pros and cons	плюсы и минусы	1	-1	NORMAL	HARD
162	1	whereas	в то время как	2	2	NORMAL	NORMAL
163	1	access	доступ	1	3	NORMAL	NORMAL
112	1	comply	соблюдать	-1	-2	HARD	HARD
139	1	considered	считается	1	2	NORMAL	NORMAL
123	1	adore	обожать	1	2	NORMAL	NORMAL
91	1	troubleshoot	устранять неисправности	1	3	NORMAL	NORMAL
154	1	arise	возникнуть, возникать	2	3	NORMAL	NORMAL
140	1	truly	действительно	2	1	NORMAL	NORMAL
95	1	whilst	в то время как	1	2	NORMAL	NORMAL
131	1	endure	терпеть, выдержать,	-1	-1	HARD	HARD
148	1	reusability	возможность переиспользовать	2	2	NORMAL	NORMAL
100	1	mates	товарищи	1	2	NORMAL	NORMAL
121	1	activity	деятельность	-1	2	HARD	NORMAL
120	1	choice	выбор	1	3	NORMAL	NORMAL
114	1	precise	точный	-1	-3	HARD	HARD
167	1	according	согласно	2	1	NORMAL	NORMAL
92	1	notion	понятие, представление	2	2	NORMAL	NORMAL
90	1	wide	широкий	-1	-2	HARD	HARD
126	1	supplier	поставщик	1	3	NORMAL	NORMAL
103	1	forbid	запрещать, запретить	-1	-1	HARD	HARD
128	1	sufficient	достаточный	-1	3	HARD	NORMAL
104	1	honest	честный	2	2	NORMAL	NORMAL
149	1	perform	выполнять	1	3	NORMAL	NORMAL
155	1	weaker	слабее	1	1	NORMAL	NORMAL
142	1	simplify	упрощать	1	2	NORMAL	NORMAL
102	1	summary	сводка	1	3	NORMAL	NORMAL
145	1	each	каждый	1	3	NORMAL	NORMAL
108	1	surrounds	окружение	1	1	NORMAL	NORMAL
106	1	conscientious	сознательный, осознанный	1	-3	NORMAL	HARD
143	1	maintenance 	обслуживание	-1	2	HARD	NORMAL
115	1	elaborate	проработанный	-1	-2	HARD	HARD
152	1	binding	связывание	1	-1	NORMAL	HARD
164	1	except	за исключением	1	3	NORMAL	NORMAL
135	1	consequently	следовательно	2	3	NORMAL	NORMAL
147	1	acquire	приобретать	-1	-1	HARD	HARD
99	1	curious	любопытный	1	3	NORMAL	NORMAL
113	1	persist	сохранять	-1	3	HARD	NORMAL
119	1	bold	смелый	1	3	NORMAL	NORMAL
137	1	while	в то время как	2	-2	NORMAL	HARD
116	1	capability	возможность	1	3	NORMAL	NORMAL
111	1	handle	управлять, справляться	-1	2	HARD	NORMAL
98	1	self-reliant 	самодостаточный	-1	2	HARD	NORMAL
146	1	necessary	необходимо, необходимый	1	3	NORMAL	NORMAL
159	1	relationship	отношение	1	2	NORMAL	NORMAL
144	1	behavior	поведение	2	2	NORMAL	NORMAL
134	1	lastly	наконец	-2	-3	HARD	HARD
86	1	workshop	семинар, мастер-класс	1	3	NORMAL	NORMAL
151	1	achieve	добиться, добиваться	2	3	NORMAL	NORMAL
150	1	convince	убедить, убеждать	1	3	NORMAL	NORMAL
161	1	grow	расти	6	7	COMPLETED	COMPLETED
141	1	mean	означать	1	2	NORMAL	NORMAL
132	1	furthermore	кроме того	-1	3	HARD	NORMAL
166	1	suggest	предлагать	2	-2	NORMAL	HARD
97	1	promise	обещать	2	2	NORMAL	NORMAL
127	1	consumer	потребитель	2	2	NORMAL	NORMAL
105	1	persistent	постоянный, устойчивый	2	-2	NORMAL	HARD
160	1	advantage	преимущество	1	-3	NORMAL	HARD
168	1	erroneous	ошибочный	2	3	NORMAL	NORMAL
96	1	approve	утвердить, одобрить	-1	-1	HARD	HARD
117	1	insist	настаивать	-1	-2	HARD	HARD
157	1	cohesion	сцепленность, связанность	1	-2	NORMAL	HARD
124	1	hurry	торопиться	2	1	NORMAL	NORMAL
125	1	stuff	штука	5	5	NORMAL	NORMAL
101	1	range	диапазон	-1	2	HARD	NORMAL
122	1	enjoy	наслаждаться	-1	3	HARD	NORMAL
89	1	constantly	постоянно	2	-2	NORMAL	HARD
109	1	evaluation	оценка	2	2	NORMAL	NORMAL
118	1	brackets	скобки	1	3	NORMAL	NORMAL
136	1	nevertheless	тем не менее	1	3	NORMAL	NORMAL
165	1	dispatch	отправка	1	3	NORMAL	NORMAL
133	1	besides	кроме того	-1	2	HARD	NORMAL
129	1	steadily	стабильно	2	-3	NORMAL	HARD
170	1	appropriate	соответствующий	1	-2	NORMAL	HARD
209	1	deduce	сделать выводы	1	3	NORMAL	NORMAL
250	1	biased	необъективный	1	-2	NORMAL	HARD
188	1	distinct	отдельный	2	1	NORMAL	NORMAL
207	1	hence	следовательно	-1	-1	HARD	HARD
174	1	digits	цифры	2	2	NORMAL	NORMAL
172	1	multiple	множество	2	-1	NORMAL	HARD
197	1	distinguish	различать	-1	1	HARD	NORMAL
244	1	exactly	именно	1	3	NORMAL	NORMAL
212	1	suppose	предполагать	2	-1	NORMAL	HARD
175	1	tangible and intangible	материальный и не материальный	2	3	NORMAL	NORMAL
227	1	contribute	вносить вклад	1	1	NORMAL	NORMAL
230	1	passionate	страстный, страстно, страсть	2	-1	NORMAL	HARD
235	1	ensure	обеспечить	2	2	NORMAL	NORMAL
215	1	equipped	оборудован	1	-2	NORMAL	HARD
216	1	stare	уставиться, пялиться	1	2	NORMAL	NORMAL
224	1	prospect	перспектива	1	2	NORMAL	NORMAL
251	1	considerations	мнения, соображения	2	3	NORMAL	NORMAL
204	1	bound 	связанный, связанно	-1	2	HARD	NORMAL
190	1	assigning	назначить, назначение, присвоение	-2	3	HARD	NORMAL
240	1	opinion	мнение	1	2	NORMAL	NORMAL
177	1	common	общий	5	7	NORMAL	COMPLETED
173	1	avoid	избегать	1	2	NORMAL	NORMAL
195	1	restriction	ограничение	1	3	NORMAL	NORMAL
203	1	occur	происходить	2	1	NORMAL	NORMAL
238	1	opportunity	возможность	2	3	NORMAL	NORMAL
239	1	preparing	подготовка	1	2	NORMAL	NORMAL
220	1	article	статья	1	1	NORMAL	NORMAL
226	1	proprietary	собственная	1	-1	NORMAL	HARD
243	1	provide	предоставлять, обеспечивать	1	-1	NORMAL	HARD
180	1	expose	раскрыть, выставить	2	3	NORMAL	NORMAL
223	1	supplement	дополнение	1	-3	NORMAL	HARD
192	1	mention	упоминать	1	2	NORMAL	NORMAL
247	1	fulfill	выполнять, выполнить	1	3	NORMAL	NORMAL
221	1	encounter	встреча	1	2	NORMAL	NORMAL
213	1	comprises	включает в себя	1	-2	NORMAL	HARD
210	1	exacerbate	усугублять	1	1	NORMAL	NORMAL
198	1	statement	заявление	2	3	NORMAL	NORMAL
194	1	rather	скорее всего, довольно	-2	3	HARD	NORMAL
236	1	oversight	надзор	1	1	NORMAL	NORMAL
214	1	diverse	разнообразный	1	2	NORMAL	NORMAL
199	1	moreover	более того	1	2	NORMAL	NORMAL
184	1	invoke	вызывать	2	2	NORMAL	NORMAL
232	1	engaged	задействован, занят, занято	1	2	NORMAL	NORMAL
229	1	proficient	опытный	2	2	NORMAL	NORMAL
208	1	actually	на самом деле	2	2	NORMAL	NORMAL
187	1	explicitly and implicitly	явный и не явный	2	2	NORMAL	NORMAL
179	1	allocate	выделять	1	1	NORMAL	NORMAL
183	1	through	через	-1	3	HARD	NORMAL
225	1	complicated	сложный	1	-1	NORMAL	HARD
193	1	belong	принадлежать	2	1	NORMAL	NORMAL
185	1	similar	аналогичный	1	3	NORMAL	NORMAL
217	1	intently	внимательно	2	-1	NORMAL	HARD
237	1	reason	причина	1	3	NORMAL	NORMAL
202	1	ambiguity	двусмысленность	1	-1	NORMAL	HARD
205	1	explanation	пояснение, объяснение	1	2	NORMAL	NORMAL
206	1	thus	таким образом	-1	1	HARD	NORMAL
211	1	allow	позволять	1	1	NORMAL	NORMAL
246	1	vast	обширный	-1	-2	HARD	HARD
191	1	members	члены	1	2	NORMAL	NORMAL
200	1	otherwise	иначе	1	1	NORMAL	NORMAL
228	1	immediately	немедленно	2	3	NORMAL	NORMAL
201	1	meaningful	значимый	2	1	NORMAL	NORMAL
234	1	define	определить	1	-3	NORMAL	HARD
186	1	available	доступный	2	2	NORMAL	NORMAL
178	1	nested	вложенный	1	2	NORMAL	NORMAL
252	1	impacting	влияющие, воздействие	2	-2	NORMAL	HARD
222	1	commitment	обязательство	1	2	NORMAL	NORMAL
245	1	additional	дополнительный, дополнительно	2	1	NORMAL	NORMAL
248	1	hardware	оборудование	-1	-2	HARD	HARD
233	1	properly	правильно	-1	3	HARD	NORMAL
218	1	essential	существенный	1	2	NORMAL	NORMAL
189	1	arrange	организовать	-1	2	HARD	NORMAL
285	1	observation	наблюдение, обзор	1	-2	NORMAL	HARD
301	1	harmful	вредный	1	-1	NORMAL	HARD
265	1	emphasize	подчеркнуть	2	1	NORMAL	NORMAL
264	1	particular	конкретный	1	-2	NORMAL	HARD
334	1	attain	достичь	-2	-2	HARD	HARD
269	1	performance	производительность, исполнение	2	2	NORMAL	NORMAL
254	1	broad	широкий	-1	-2	HARD	HARD
278	1	significant	значительный	1	1	NORMAL	NORMAL
315	1	compose	сочинять	1	2	NORMAL	NORMAL
329	1	least	наименее	-1	-1	HARD	HARD
308	1	fragile	хрупкий	1	3	NORMAL	NORMAL
335	1	amplifies	усиливается	-1	3	HARD	NORMAL
293	1	curve	кривая	1	1	NORMAL	NORMAL
256	1	reveal	раскрыть, раскрывать, показать	1	2	NORMAL	NORMAL
307	1	huge	огромный	-1	2	HARD	NORMAL
271	1	quantities	количества, объемы, величины	1	3	NORMAL	NORMAL
331	1	advice	совет	5	5	NORMAL	NORMAL
326	1	shrink	сокращаться	-1	3	HARD	NORMAL
272	1	effort	усилия	1	1	NORMAL	NORMAL
299	1	occurred	произошло	1	-2	NORMAL	HARD
297	1	evaluate	оценить	1	2	NORMAL	NORMAL
276	1	flow	поток, течение	4	5	NORMAL	NORMAL
312	1	divide	разделять, делить	1	2	NORMAL	NORMAL
287	1	conclusion	вывод, заключение	2	-2	NORMAL	HARD
284	1	operate	управлять	2	3	NORMAL	NORMAL
322	1	solution	решение	1	-1	NORMAL	HARD
330	1	formed	сформированный	1	2	NORMAL	NORMAL
289	1	computational	вычислительные (мощности)	1	-3	NORMAL	HARD
273	1	measure	измерять	2	1	NORMAL	NORMAL
314	1	gather	собирать	1	2	NORMAL	NORMAL
324	1	accept	принять, принимать	1	-1	NORMAL	HARD
267	1	care	забота, обслуживание	1	3	NORMAL	NORMAL
317	1	retain	удерживать	2	2	NORMAL	NORMAL
298	1	duty	обязанность	1	3	NORMAL	NORMAL
268	1	bandwidth	пропускная способность	1	2	NORMAL	NORMAL
292	1	appearance 	внешний вид	1	3	NORMAL	NORMAL
300	1	drudgery	рутинная работа	1	1	NORMAL	NORMAL
263	1	sense	 смысл, чувство	1	-2	NORMAL	HARD
282	1	guess	угадать	1	2	NORMAL	NORMAL
253	1	consequences	последствия	1	1	NORMAL	NORMAL
303	1	adopted	принят, одобрен	1	2	NORMAL	NORMAL
313	1	further	далее, дальше	1	1	NORMAL	NORMAL
302	1	impose	накладывать, вводить	2	-2	NORMAL	HARD
319	1	separated	отдельный	1	-1	NORMAL	HARD
290	1	disappointment	разочарование	1	1	NORMAL	NORMAL
310	1	seem	казаться, кажется	2	2	NORMAL	NORMAL
306	1	align	выровнять	1	3	NORMAL	NORMAL
260	1	belief	убеждение, вера	1	2	NORMAL	NORMAL
281	1	eliminate	исключить, устранить	1	-3	NORMAL	HARD
323	1	altered	изменено	1	2	NORMAL	NORMAL
295	1	recognize	узнать	1	-1	NORMAL	HARD
316	1	eventually	в конце концов	-1	1	HARD	NORMAL
266	1	appreciate	ценить, оценить, оценивать	-2	-1	HARD	HARD
328	1	entirely	полностью	1	-2	NORMAL	HARD
304	1	assignment	задание, поручение	1	3	NORMAL	NORMAL
327	1	oppose	выступать против, быть против	2	1	NORMAL	NORMAL
288	1	consider	учитывать	-1	3	HARD	NORMAL
259	1	either	либо	2	2	NORMAL	NORMAL
332	1	explain	объяснять	1	1	NORMAL	NORMAL
294	1	messy	грязный	1	3	NORMAL	NORMAL
280	1	rigidity	жесткость, прочность	1	1	NORMAL	NORMAL
318	1	therefore	следовательно	-1	-2	HARD	HARD
258	1	defies	бросает вызов	-2	-1	HARD	HARD
261	1	improbable	невероятный	1	-2	NORMAL	HARD
262	1	mundane	обыденный	-1	2	HARD	NORMAL
283	1	preserve	сохранить, сохранять	2	-1	NORMAL	HARD
320	1	exceed	превысить	1	2	NORMAL	NORMAL
311	1	investigation 	расследование	1	2	NORMAL	NORMAL
255	1	obvious	очевидно	1	2	NORMAL	NORMAL
309	1	unreliable	ненадёжный	1	1	NORMAL	NORMAL
296	1	urgent	срочно, срочный	1	3	NORMAL	NORMAL
270	1	forces	силы	1	3	NORMAL	NORMAL
274	1	determine	определять	1	1	NORMAL	NORMAL
257	1	appeal	обращение	-1	-2	HARD	HARD
305	1	intent	намерение, замысел	1	1	NORMAL	NORMAL
277	1	lead	приводит, приводить	1	1	NORMAL	NORMAL
321	1	bounds	границы	2	3	NORMAL	NORMAL
398	4	let’s go over	давайте перейдём	0	0	NORMAL	NORMAL
399	4	at the same time	в тоже время	0	0	NORMAL	NORMAL
400	4	to have a way with smth or smb	иметь дело с	0	0	NORMAL	NORMAL
401	4	to be still on my way	быть в процессе	0	0	NORMAL	NORMAL
402	4	no way	ни в коем случае	0	0	NORMAL	NORMAL
403	4	in a way	в каком-то смысле, своего рода	0	0	NORMAL	NORMAL
404	4	in a good way	в хорошем смысле	0	0	NORMAL	NORMAL
405	4	in a different way	по другому	0	0	NORMAL	NORMAL
406	4	in my own way	по-моему	0	0	NORMAL	NORMAL
407	4	this way	таким образом, в этом случае	0	0	NORMAL	NORMAL
408	4	by the way	между прочим, кстати	0	0	NORMAL	NORMAL
409	4	to be proud of	годиться	0	0	NORMAL	NORMAL
410	4	to feel guilty for	испытывать чувство вины за	0	0	NORMAL	NORMAL
411	4	tried and true	проверенный временем	0	0	NORMAL	NORMAL
412	4	go ahead	продолжать	0	0	NORMAL	NORMAL
413	4	i pointed out	я указал	0	0	NORMAL	NORMAL
414	4	come over	зашёл, пришёл, приехал	0	0	NORMAL	NORMAL
374	1	succeed	достигать цели	-1	-1	HARD	HARD
347	1	assume	предполагать	1	1	NORMAL	NORMAL
415	4	march on	идти дальше	0	-1	NORMAL	HARD
340	1	involve	задействовать, привлечь, вовлечь	-1	-3	HARD	HARD
342	1	collocate	располагать	1	2	NORMAL	NORMAL
379	1	implement	внедрять, реализовывать	-1	2	HARD	NORMAL
377	1	assist	помогать	1	2	NORMAL	NORMAL
336	1	seldom	редко	1	3	NORMAL	NORMAL
380	1	deserve	заслуживать	-2	-2	HARD	HARD
357	1	perhaps	возможно	2	2	NORMAL	NORMAL
383	1	trouble	беда	1	3	NORMAL	NORMAL
346	1	indeed	действительно	-1	1	HARD	NORMAL
360	1	influence	оказывать влияние	1	-1	NORMAL	HARD
361	1	clarify	уточнять, уточнить	1	1	NORMAL	NORMAL
391	1	distribution	распределение, распространение	1	2	NORMAL	NORMAL
381	1	participant	участник	1	-1	NORMAL	HARD
366	1	prevent	предотвращать	2	3	NORMAL	NORMAL
356	1	division	разделение	2	2	NORMAL	NORMAL
341	1	 assemble	собирать	1	1	NORMAL	NORMAL
363	1	establish	установить, создать, основать, учредить	1	-1	NORMAL	HARD
392	1	salary	зарплата	4	4	NORMAL	NORMAL
362	1	claim	претензия, утверждать	-1	2	HARD	NORMAL
382	1	competition	конкуренция	1	2	NORMAL	NORMAL
368	1	order	приказывать, заказ	1	5	NORMAL	NORMAL
348	1	derive	получать, происходить, извлекать	-1	-1	HARD	HARD
397	1	plenty	множество, достаточно	1	2	NORMAL	NORMAL
337	1	within	в пределах	2	3	NORMAL	NORMAL
358	1	impede	препятствовать	-1	-2	HARD	HARD
372	1	attempt	попытка, пытаться	1	3	NORMAL	NORMAL
371	1	seek	искать	-1	-3	HARD	HARD
395	1	deputy	заместитель	1	-1	NORMAL	HARD
352	1	conformance	соответствие	1	-3	NORMAL	HARD
339	1	variety	разнообразный	1	2	NORMAL	NORMAL
350	1	useless	бесполезный	1	2	NORMAL	NORMAL
376	1	supply	поставлять, снабжать, обеспечивать	-1	2	HARD	NORMAL
390	1	disorder	беспорядок	1	1	NORMAL	NORMAL
385	1	complaint	жалоба	-1	-1	HARD	HARD
344	1	such	такие	1	1	NORMAL	NORMAL
386	1	lack	нехватка	-1	2	HARD	NORMAL
369	1	engage	привлекать, заниматься	-1	1	HARD	NORMAL
353	1	merely	просто	2	2	NORMAL	NORMAL
364	1	apply	применять, подавать заявление	-1	1	HARD	NORMAL
359	1	efficient	эффективный	1	2	NORMAL	NORMAL
387	1	outcome	итог, результат, исход	1	1	NORMAL	NORMAL
338	1	implied	подразумеваемый	1	-2	NORMAL	HARD
384	1	participation	участие	1	2	NORMAL	NORMAL
370	1	solve	решать, решить	1	2	NORMAL	NORMAL
393	1	wage	зарплата	2	2	NORMAL	NORMAL
365	1	improve	улучшать	2	1	NORMAL	NORMAL
367	1	conduct	проводить	-2	-2	HARD	HARD
389	1	entry	запись в журнале	1	1	NORMAL	NORMAL
351	1	neither	ни один	1	1	NORMAL	NORMAL
373	1	adjust	настраивать	1	1	NORMAL	NORMAL
354	1	against	против	1	3	NORMAL	NORMAL
388	1	requirement	требование	1	3	NORMAL	NORMAL
349	1	infer	делать выводы	-1	-3	HARD	HARD
345	1	desirable	желательный	1	2	NORMAL	NORMAL
343	1	compatible	совместимый	-1	1	HARD	NORMAL
416	4	break out of the loop	вырваться из цикла	0	0	NORMAL	NORMAL
417	4	i’m looking forward	жду с нетерпением, с нетерпением жду	0	0	NORMAL	NORMAL
418	4	grab attention	захватить внимание, захватывать внимание	0	0	NORMAL	NORMAL
419	4	 the former… the latter	первый из них…  второй из них	0	0	NORMAL	NORMAL
421	4	by doing so	тем самым	0	0	NORMAL	NORMAL
422	4	get rid of	избавляться, избавиться	0	0	NORMAL	NORMAL
423	4	to get out of the slump 	выходит из подавленного состояния	0	0	NORMAL	NORMAL
424	4	staple of	основной элемент	0	0	NORMAL	NORMAL
425	4	call it off	отменить	0	0	NORMAL	NORMAL
426	4	speaking about	говоря об	0	0	NORMAL	NORMAL
427	4	in the long run	в долгосрочной перспективе	0	0	NORMAL	NORMAL
428	4	thanks to it	благодаря этому	0	0	NORMAL	NORMAL
429	4	rate of	уровень, показатель	0	0	NORMAL	NORMAL
430	4	to start with	начну с	0	0	NORMAL	NORMAL
431	4	completion rate of	проце	0	0	NORMAL	NORMAL
432	4	leave on good terms	уходить на хороших условиях	0	0	NORMAL	NORMAL
433	4	in the beginning	в начале	0	0	NORMAL	NORMAL
434	4	first of all	прежде всего	0	0	NORMAL	NORMAL
435	4	check out	проверить, проверять	0	0	NORMAL	NORMAL
436	4	dive into	погружаться	0	0	NORMAL	NORMAL
437	4	take advantage of something	извлекать выгоду	0	0	NORMAL	NORMAL
438	4	i am up for it	я готов к этому	0	0	NORMAL	NORMAL
439	4	sort of	вроде	0	0	NORMAL	NORMAL
440	4	assumes that	предполагается что	0	0	NORMAL	NORMAL
441	4	it depends on	в зависимости от	0	0	NORMAL	NORMAL
442	4	to save from	обезопасить	0	0	NORMAL	NORMAL
443	4	it is obligatory to do smth	обязательно делать что-либо	0	0	NORMAL	NORMAL
444	4	ask for advice from someone	попросить совета у кого-то	0	0	NORMAL	NORMAL
445	4	follow a routine	придерживаться рутины, распорядка дня	0	0	NORMAL	NORMAL
446	4	to stick to a routine	придерживаться рутины, распорядка дня	0	0	NORMAL	NORMAL
448	4	i can be wrong but	я могу ошибаться, но	0	0	NORMAL	NORMAL
449	4	i tend to think	я склонен думать	0	0	NORMAL	NORMAL
450	4	i feel doubtful but 	я сомневаюсь, но	0	0	NORMAL	NORMAL
451	4	as far as i know	на сколько я знаю	0	0	NORMAL	NORMAL
452	4	i'm quite sure	я совершенно уверен	0	0	NORMAL	NORMAL
453	4	i'm certain	я уверен	0	0	NORMAL	NORMAL
454	4	i cant stand	я терпеть не могу	0	0	NORMAL	NORMAL
455	4	i am not in the mood of doing it	мне не нравится делать это	0	0	NORMAL	NORMAL
456	4	i don’t mind doing it	мне всё равно	0	0	NORMAL	NORMAL
457	4	i am bot against	я не против делать что-то	0	0	NORMAL	NORMAL
458	4	i quite like	мне  нравится  (немного)	0	0	NORMAL	NORMAL
459	4	can be defined	можно определить	0	0	NORMAL	NORMAL
460	4	be aware of each other 	знать друг о друге	0	0	NORMAL	NORMAL
461	4	figure out	выяснять	0	0	NORMAL	NORMAL
462	4	to quite on me	покинул (сломался, бросил)	0	0	NORMAL	NORMAL
463	4	crunch a vast amount data	обрабатывать огромное количество данных	0	0	NORMAL	NORMAL
464	4	headed off	отправился, направился	0	0	NORMAL	NORMAL
465	4	apart from this	помимо	0	0	NORMAL	NORMAL
466	4	in addition to	в дополнение к	0	0	NORMAL	NORMAL
467	4	in short	вкратце	0	0	NORMAL	NORMAL
468	4	in summary	в целом	0	0	NORMAL	NORMAL
469	4	in a nutshell	в двух словах	0	0	NORMAL	NORMAL
470	4	in conclusion	в заключении	0	0	NORMAL	NORMAL
471	4	due to	в связи с	0	0	NORMAL	NORMAL
472	4	owning to	благодаря	0	0	NORMAL	NORMAL
473	4	as a result	в результате	0	0	NORMAL	NORMAL
474	5	Can you tell me a little about yourself?	To start with, I relocated to Germany in May with my wife because her grandparents were Germans.I am proficient in Java and Kotlin. I have been doing it for 2 years and have one and a half years of commercial experience. I worked at RTK IT. It was one of the three largest providers in RussiaI developed a server side of personal account. We used Java, Kotlin, Spring, PostgresSQL, Oracle Data Base.Before that I worked as an engineer.I also had an experience being a leader in a different sphere. I know what is the “Complete Employee Solution” and I always have a couple of courses in my line to study.	0	0	NORMAL	NOT_SUPPORTED
475	5	What is “Complete Employ Solution”?	If you have a problem, you have to bring 3 ways to solve it. It was the first thing that I taught my new employees, when I was working at a manager role.	0	0	NORMAL	NOT_SUPPORTED
476	5	What was your job when you had a leader role?	My last job in this field was a manager of a big restaurant. I was responsible for all that happened there. I had 20-25 people in my team. For 3 months I showed not a bad result. We increased our income to 15%. It was a very stressful job, But Since that time, Nothing can destroy my calmness.Before that I opened and managed 5 cafes in my city. We brewed and told people about good coffee.	0	0	NORMAL	NOT_SUPPORTED
420	4	one more time	ещё один раз	0	1	NORMAL	NORMAL
477	5	What did you do at your last job?	I developed and maintained the server side of personal account. It was a one of the three largest providers in Russia. I used Java, Kotlin, Spring, PostgresSQL, Oracle Data Base.My duties included:- Created and maintained technical documentation- Found efficient solutions of different issues(Находить эффективные решения задач)- Fixed software bugs from production(с продакшена)- Prepared data base scripts for executing and backup(Подготовка скриптов наката и отката для базы данных)- Created tickets to backlog in case you identify a snippet of code which is not optimized(Создавать тикет, если увидел кусок кода, который нужно оптимизировать)- Collaborate with testers and analytics to develop solutions	0	0	NORMAL	NOT_SUPPORTED
480	5	Can you tell us about a difficult work situation and how you overcame it?	I didn’t drop data base. Unfortunately or fortunately. But I had an interesting task on the code review. (S) Our management wanted to increase completion rate of code reviews without pushing our developers(T) We worked out a service and I did it.(A) It was a service with integration to Upsource. It notified our developers about having uncompleted reviews via Discord.It really simplified our work, because sometimes we were fascinated by the task and forgot about Upsource. (R) In conclusion it doubled the completion rate of code reviews /  and my employer increased my salary.	0	0	NORMAL	NOT_SUPPORTED
231	1	desire	желание	2	2	NORMAL	NORMAL
196	1	pass	пройти	5	6	NORMAL	COMPLETED
242	1	prepare	подготовить	5	6	NORMAL	COMPLETED
153	1	refer	ссылаться	6	7	COMPLETED	COMPLETED
279	1	predicting	прогноз, предсказание, предсказывание	6	6	COMPLETED	COMPLETED
494	1	ampersand	@	0	2	NORMAL	NORMAL
496	1	dash	-	0	2	NORMAL	NORMAL
375	1	benefit	получать выгоду	-1	6	HARD	COMPLETED
491	1	greater than	>	1	-1	NORMAL	HARD
355	1	toward	к	4	4	NORMAL	NORMAL
107	1	set	установить	5	3	NORMAL	NORMAL
492	1	Exclamation mark	!	-1	-1	HARD	HARD
181	1	approach	подход	6	6	COMPLETED	COMPLETED
394	1	pay	оплата	6	7	COMPLETED	COMPLETED
493	1	Question mark	?	0	2	NORMAL	NORMAL
110	1	launch	запустить	6	7	COMPLETED	COMPLETED
138	1	unlike	в отличие от	-1	-3	HARD	HARD
487	1	square brackets	[]	0	0	NORMAL	NORMAL
504	8	What is OOP?	OOP is a methodology or paradigm to design programs with simulating real-world problems. It simplifies software development and maintenance by providing some concepts: (9) object, class, inheritance, polymorphism, abstraction, encapsulation, association, aggregation, composition.	0	0	NORMAL	NOT_SUPPORTED
505	8	Name main principles of OOP	Class is a logical entity, so it doesn’t consume any space in memory. Class is a blueprint for objects.Object can be defined as an instance of a class.Inheritance allows create a new object based on an existing one. The inherited class has all properties and behaviors as a parent class.Polymorphism is a special case of using Inheritance . It is when the program can use objects with a similar interfaces without information about internal structure of the objects. We can use abstraction classes in our methods and choose a realization in runtime.Abstraction class can have abstract fields and abstract functions without implementation. Child class has to implement these. However Abstraction class can have fields and function with standard values and behaviors. Child class can overriding these.We can’t create instance of this class, because it is abstract. Interface is specification of class without realization. We can implement many interfaces in one class whilst the inheritance only one time.Binding code and data together into a single unit are know as encapsulation	0	0	NORMAL	NOT_SUPPORTED
506	8	What is difference between Composition and Aggregation? 	Composition is that the one of objects is a part-of another object. This object can’t be without owners objectSpeaking about aggregation. The object can be part-of another object, but it can be separately.	0	0	NORMAL	NOT_SUPPORTED
483	1	braces, curly brackets	{}	0	2	NORMAL	NORMAL
182	1	previous	предыдущий	5	5	NORMAL	NORMAL
94	1	benefits and drawbacks	преимущества и недостатки	1	1	NORMAL	NORMAL
325	1	definition	определение	4	4	NORMAL	NORMAL
529	7	Что такое класс Object? Какие в нем есть методы?	It is a basic class in Java. Object contains methods: Equals, hashCode, toString, getClass, cloneIt also has methods notify, notifyAll, wait and finalize 	0	0	NORMAL	NOT_SUPPORTED
497	1	underscore	_	0	-1	NORMAL	HARD
158	1	however	однако	4	5	NORMAL	NORMAL
488	1	less than	<	0	1	NORMAL	NORMAL
485	1	right brace	}	1	2	NORMAL	NORMAL
495	1	star	*	1	2	NORMAL	NORMAL
486	1	round brackets	()	1	2	NORMAL	NORMAL
176	1	via	через	6	6	COMPLETED	COMPLETED
241	1	predictable	предсказуемый, прогнозируемый	2	-1	NORMAL	HARD
291	1	introduction	введение	5	7	NORMAL	COMPLETED
484	1	left brace	{	1	2	NORMAL	NORMAL
85	1	facilitate	облегчить, содействовать	-2	2	HARD	NORMAL
286	1	startling	поразительный	4	4	NORMAL	NORMAL
171	1	snippet	фрагмент	6	7	COMPLETED	COMPLETED
219	1	hiring	наём	5	5	NORMAL	NORMAL
130	1	dive	погружаться, погружение	6	1	COMPLETED	NORMAL
500	1	comma	,	1	1	NORMAL	NORMAL
333	1	violate	нарушать	1	-1	NORMAL	HARD
249	1	warm up	разминка	5	4	NORMAL	NORMAL
507	8	What is SOLID? 	Single Responsibility principleIt means one class - one task - one zone of responsibility.It has antipattern as like God Object. It is a class that has a lot of code, tasks and reasons for changes.We always try decompose our classes.It simplifies reading, maintaining, editing our code and using version of control.Open-Closed principleClasses are opened for expansion and closed for modification.If we want to add a new functionality, we should use an inheritance. It simplifies regression testing and decreases the amount of issues Liskov Substitution principleEvery subclass or derived class should be substitutable for their base or parent class.It means that a child class has to use all methods from parent.(if class A is a subtype of class B, we should be able to replace B with A without disrupting the behavior of our program.)Interface segregation principleLarger interfaces should be split into smaller ones. By doing so, we can ensure that implementing classes only need to be concerned about the methods that are of interest to them.It decreases coupling.Dependency InversionHigh level modules have not to depend on low level modules. All of them have to depend on abstraction.	0	0	NORMAL	NOT_SUPPORTED
508	1	absent	отсутствует	0	0	NORMAL	NORMAL
510	7	Какие существуют модификаторы доступа?	private - in classdefault - this packageprotected - this package and inherited packages public - public	0	0	NORMAL	NOT_SUPPORTED
511	7	О чем говорит ключевое слово final?	Class can’t have childMethod can’t be overridingField is immutable	0	0	NORMAL	NOT_SUPPORTED
512	7	Что вы знаете о функции main()?	It is a point of startingProgram can have few main() functions and always takes one argument called args It is array of Strings 	0	0	NORMAL	NOT_SUPPORTED
513	7	Что такое тернарный оператор выбора?	It equivalent if-then-else function Condition then question mark then first way then colon then second way	0	0	NORMAL	NOT_SUPPORTED
514	7	Где и для чего используется модификатор abstract?	You can create abstraction class but you can’t have instance this class. This class can have default behavior and fields, can have abstraction methods and properties that you should override it in child class.	0	0	NORMAL	NOT_SUPPORTED
515	7	Дайте определение понятию «интерфейс». Какие модификаторы по умолчанию имеют поля и методы интерфейсов?	It is specification for class. We declare what behavior and properties class will have. Inherited class have to realize they.	0	0	NORMAL	NOT_SUPPORTED
516	7	Чем абстрактный класс отличается от интерфейса? В каких случаях следует использовать абстрактный класс, а в каких интерфейс?	Java class can realize a few interfaces whilst inherited use only once.Abstraction class are used for similar classes, but classes that realize the same interfaces can be very differentAbstraction class is like basic class	0	0	NORMAL	NOT_SUPPORTED
517	7	Почему в некоторых интерфейсах вообще не определяют методов?	This interfaces is called marked interfaces. For example: Mappable. We can mark our classes that can be marshaled	0	0	NORMAL	NOT_SUPPORTED
518	7	Почему нельзя объявить метод интерфейса с модификатором final?	Because the interface can’t have declared fields but the final var has to have declaration	0	0	NORMAL	NOT_SUPPORTED
519	7	Что имеет более высокий уровень абстракции - класс, абстрактный класс или интерфейс?	Interface because the abstraction class can have default behaviors and parameters 	0	0	NORMAL	NOT_SUPPORTED
520	7	Каков порядок вызова конструкторов и блоков инициализации с учётом иерархии классов?	Parent static blocks → Child static blocks → parent non-static blocks → parent constructor → child non-static blocks →  child constructor	0	0	NORMAL	NOT_SUPPORTED
521	7	Может ли статический метод быть переопределён или перегружен?	overload yes, overriding - no (compilation level)	0	0	NORMAL	NOT_SUPPORTED
522	7	Какие типы классов бывают в java?	Abstract, final, Enum, Interface, Nested, Anonymous	0	0	NORMAL	NOT_SUPPORTED
523	7	Что такое «анонимные классы»? Где они применяются?	It is a class without name. We use it when we need create class only once and we don’t need refer to it 	0	0	NORMAL	NOT_SUPPORTED
524	7	Для чего нужен сборщик мусора?	Garbage collector finds unused objects. We have two aproaches:Reference countingIt means, a program entities don’t have references to these objectsEach object has reference counting. If it equals 0, it means that we can delete this object.Minus - Two unused objects can refer on each over Tracing (HotSpot VW uses it)If we can get access to object from root points or living objects It isn’t a garbage Root points- local parameters ( in main() )- Treads ( main() )- Static variable- References from JNIAfter that, collector cleans up memory.Copying collectorsCopy in new place only living objects (it necessary stopping program)Mark-and-sweep We mark only living objects. Least objects are saved in free list	0	0	NORMAL	NOT_SUPPORTED
525	7	Что такое Heap и Stack память в Java? Какая разница между ними?	HeapIt is used in Java Runtime for objects and classesObjects are created in HeapGarbage collector clears it All objects in heap has public modificatoryOutOfMemoryErrorStack It is RAMIt saves only primitive types and references New executable method creates new block in memory. This block is deleted when method will be completed.It uses LIFO StackOverFLowError	0	0	NORMAL	NOT_SUPPORTED
526	7	Какие есть особенности класса String?	String is immutable and finalized typeThis type is saved in pool StringsDifferent variables with similar values have one reference We can use String variable in a different threads Our program executes method hashCode only once (It is a good key for HashMap)	0	0	NORMAL	NOT_SUPPORTED
528	7	Почему строка является популярным ключом в HashMap в Java?	String is immutable and we have cashed the result of hashCode() method	0	0	NORMAL	NOT_SUPPORTED
509	1	compute	вычислить	0	2	NORMAL	NORMAL
530	7	Что такое Reflection?	It is mechanism to getting information about program in runtime. We can:- determine class- get information about class modifiers, fields, methods- determine interfaces- create instance of class- get and assign fields- invoke methods	0	0	NORMAL	NOT_SUPPORTED
531	7	Расскажите про клонирование объектов	We should use cloning When we want to create a object with a different reference. I prefer use cloning by constructor because in this case we don’t need to override clone methods	0	0	NORMAL	NOT_SUPPORTED
532	7	Зачем нужен equals(). Чем он отличается от операции ==?	Equals method returns true when our objects are similar by our logic whilst double equals returns true When our objects refer in one part of memory.	0	0	NORMAL	NOT_SUPPORTED
533	7	Если equals() переопределен, есть ли какие-либо другие методы, которые следует переопределить?	Similar objects have to return similar hash codes If we want to override equals method we have to override hashCode method. If we didn’t do it it would lead to problems with working with hashMap	0	0	NORMAL	NOT_SUPPORTED
534	7	Для чего нужен метод hashCode()?	This method returns a object hashCode. If hash Codes are different These objects are differentIf hash codes are equivalent These objects can be equivalent and not equivalent	0	0	NORMAL	NOT_SUPPORTED
535	7	What will happen if hashCode function will return only one value?	We will get hashSet with one filled bucket and our execution time will be O(n) (depend on amount of values)	0	0	NORMAL	NOT_SUPPORTED
536	7	What will happen if hashCode function always returns random value?	We can’t find our value because we will get wrong number of bucket	0	0	NORMAL	NOT_SUPPORTED
537	7	We want to get the same result from equals function with a difference values. How we can realize it?	We have to override hashcode method that it will return the same result	0	0	NORMAL	NOT_SUPPORTED
538	7	Что такое generics?	It is when we can use substitution types in our codeIt allows to work with our objects more flexibleThe first example that comes to my mind When we use JPA, We should assign class and id type in interface It implements by generics. We also can use substitutable methodsIt bounders with Liskov substitutions principle We can use objects with similar interfaces and parents.	0	0	NORMAL	NOT_SUPPORTED
539	7	Что такое «функциональные интерфейсы»?	It is interface that has only one abstraction methodBut this interface can have a lot of default methods	0	0	NORMAL	NOT_SUPPORTED
540	7	Что такое «лямбда»? Какова структура и особенности использования лямбда-выражения?	It is like anonymous class or method. We use lamda for invoking abstraction method from functional interface.Lamda’s body can be in one line or if it has more multiply logic, we should use brackets and return keyIf abstraction method has parameters we have to assign them in brackets	0	0	NORMAL	NOT_SUPPORTED
541	7	Что такое «ссылка на метод»?	It is special case of using lamdaWe can use method that has all necessary internal information by using double colon ::For example: new (on constructor), static methods and on instance of class	0	0	NORMAL	NOT_SUPPORTED
542	7	Что такое default методы интрефейса?	This functional is available after Java 8We can add in our interfaces default behaviors. It means. We can realize methods with default logic If we want to use this method, We should use super key	0	0	NORMAL	NOT_SUPPORTED
543	7	Что такое Optional?	It is a container for object that can contain null value This wrapper allows to work with nullable parameters safer and decrease amount of try-catch blocks	0	0	NORMAL	NOT_SUPPORTED
544	7	Что такое Stream?	It is interface that simplifies working with threads of elementsStream methods can be intermediate and terminal Intermediate stream returns stream and we can build chain of invokingWe can use a special streams IntStream and DoubleStreamThey allow to use methods such as sum and average	0	0	NORMAL	NOT_SUPPORTED
545	7	Какие существуют способы создания стрима?	We can create stream by - Arrays.asList or Arrays.stream- Stream.of- From string by using method chars- Stream.builder	0	0	NORMAL	NOT_SUPPORTED
546	7	В чем разница между Collection и Stream	Collections allows to work with data separately whilst Stream works with elements in one moment.Collections is structure of data whilst Stream is abstraction that allows realize pipeline	0	0	NORMAL	NOT_SUPPORTED
547	7	Для чего в стримах применяются методы forEach() и forEachOrdered()?	forEach method works with stream randomly whilst forEachOrdered saves queue 	0	0	NORMAL	NOT_SUPPORTED
548	7	Для чего в стримах предназначены методы flatMap(), flatMapToInt(), flatMapToDouble(), flatMapToLong()?	These methods return set of listsOne element can return a plenty objects	0	0	NORMAL	NOT_SUPPORTED
549	7	Какие конечные методы работы со стримами вы знаете?	collect, findFirst, findAny, count, anyMatch, allMatch, min, max, toArray	0	0	NORMAL	NOT_SUPPORTED
550	7	Какие промежуточные методы работы со стримами вы знаете?	filter, map, skip, limitdistinct - returns stream without duplicates (by equals)peek - does something with each elements and returns streamsorted - we can use Comparator	0	0	NORMAL	NOT_SUPPORTED
551	7	Для чего нужны computeIfPresent(), computeIfAbsent(),  putIfAbsent() merge()	- putIfAbsent - if it isn’t present object will be added- computeIfPresent - If it is present Object will be updated- computeIfAbsent - if it isn’t present Object will be created and added- merge - takes function and check If it isn’t present function will be executed and the result will be added	0	0	NORMAL	NOT_SUPPORTED
552	9	Назовите основные интерфейсы JCF и их реализации	Java Collection Framwork has two basic interfaces:- Collection is set of elements- Map is set of key-value pairsCollection contains - List (ArrayLIst, LinkedList (has 2 ref.), Stack(LIFO) is simple collection that has duplicates- Set (HashSet, LinkedHashSet, TreeSet) has only unique elements- Queue (PriorityQueue, ArrayDequeMap- HashTable - non nullable, disordered- HashMap - nullable, disordered- LinkedHashMap - ordered- TreeMap - uses red-black tree, ordered- WeakHashMap -	0	0	NORMAL	NOT_SUPPORTED
553	9	В чем разница между классами java.util.Collection и java.util.Collections?	Collections is set of methods for working with collectionsCollection is main interface Java Collections Framework	0	0	NORMAL	NOT_SUPPORTED
554	9	Чем отличается ArrayList от LinkedList? В каких случаях лучше использовать первый, а в каких второй?	ArrayList - finding by index - O(1)- finding by value - O(N)- finding first or last - O(N) - O(1)- inserting in the end - O(1)- Inserting in the middle - much time because all elements at the second part update indexes- removing - much time because all elements at the second part update indexes- uses minimum memoryLinkedLIst- finding by index - O(N)- finding by index value - O(N)- finding first or last - O(1)- inserting in the end - O(1)- Inserting in the middle - O(1)- removing - O(1)- uses more memory for pointersWe should use LinkedList only When we need a lot of inserting or removing operations	0	0	NORMAL	NOT_SUPPORTED
555	9	Какое худшее время работы метода contains() для элемента, который есть в LinkedList и ArrayList?	O(N) for a both	0	0	NORMAL	NOT_SUPPORTED
556	9	Какое худшее время работы метода add() для LinkedList и ArrayList?	LinkedList - O(N). However, if we add element to the end, it takes O(1) timeArrayList - O)(N). However, if we add element to the end and don’t need expand this List, it takes O(1)	0	0	NORMAL	NOT_SUPPORTED
557	9	Как происходит удаление элементов из ArrayList? Как меняется в этом случае размер ArrayList?	Size doesn’t update (shrink). Elements in a second part update their indexes	0	0	NORMAL	NOT_SUPPORTED
558	9	What is HashSet and how it wotks?	HashSet is structure of data that allows to use methods contains for O(1). Execution time isn’t depend on amount of values. When we declare our HashSet by using “new” We create hashSet with 5 buckets. When we add new value, for example some nameHashSet invokes method hashCode and this method returns numberHashSet divides this number to amount of bucketsResult is a number of bucket that our value will be save When we use contains method. We use method hashCode to this object and we get number of bucket. We try to find in this bucket our object and we get true or false Method remove in hashSet works a similar approachAdding elementIf (amount of buckets == amount of elements) hashSet creates new map with double rate of buckets, inserts old elements with new indexes of bucket then add new element	0	0	NORMAL	NOT_SUPPORTED
559	9	В чем отличия TreeSet и HashSet?	TreeSet - ordered, has O(log(N)) timeTreeSet strives to balance by updating core and color units	0	0	NORMAL	NOT_SUPPORTED
560	9	Зачем нужен HashMap, если есть Hashtable?	HasTable is synchronized. It takes more resourcesHastable can’t contain null valuesHashMap uses Iterator whilst HashTable Enumeration. Iterator allows use faul-fast approach (trouws exception when we have a problem with data)HashTable is a deprecated realization	0	0	NORMAL	NOT_SUPPORTED
561	9	Как устроен HashMap?	HashMap contains buckets. They are like Array of elements that save list of references. When we add a new element HashMap executes hashCode method and finds number of bucket by dividingAdding elementIf 75% of our buckets contain elements hashMap creates new map with double rate of buckets, inserts old elements with new indexes of bucket then add new element	0	0	NORMAL	NOT_SUPPORTED
562	9	Как работает HashMap при попытке сохранить в него два элемента по ключам с одинаковым hashCode(), но для которых equals() == false?	Our HashMap can contain a few elements in one bucket.	0	0	NORMAL	NOT_SUPPORTED
563	9	Какое начальное количество корзин в HashMap?	16, We can create HashMap with assigning number of buckets	0	0	NORMAL	NOT_SUPPORTED
564	9	Какова оценка временной сложности операций над элементами из HashMap? Гарантирует ли HashMap указанную сложность выборки элемента?	If hashMap has less elements then buckets So Adding, finding and removing operations have O(1) time.If amount of elements in hashMap equals amount of buckets we need to expand our hashMap. It needs O(N) time	0	0	NORMAL	NOT_SUPPORTED
565	9	Каково максимальное число значений hashCode()?	2^32 because hashCode returns int	0	0	NORMAL	NOT_SUPPORTED
566	9	Как и когда происходит увеличение количества корзин в HashMap?	If our hashMap is 75% full It doubles rate of buckets	0	0	NORMAL	NOT_SUPPORTED
567	10	What is Spring? What are Benefits of using Spring?	It is most popular framework for java. It simplifies and development.Benefits:- Lightweight- Using Inversion of Control and IoC container (manages beans)- Aspect-Orientd Programming- MVC framework- Transaction management- Exception Handling	0	0	NORMAL	NOT_SUPPORTED
568	10	What Spring sub-projects do you know?	- Core - IoC and DI- JDBC - abstraction layuer- ORM integration - JPA, Hibernate,- Web - all tools that help to develop we applications	0	0	NORMAL	NOT_SUPPORTED
569	10	What is IOC and Dependency Injection?	https://habr.com/ru/articles/720794/IOC - Framework can invoke custom codeDI - is a design pattern, that divide creation objects from using. It is aspect of IOC. IOC container - is realization of IOC and DI together. We allows to manage our dependences to Spring. We use annotations (@Service, @Repository, @Component) in this class, that will be in our context in program.	0	0	NORMAL	NOT_SUPPORTED
570	10	How can we inject beans in Spring? Which is the best way?	We can inject our bean in class by constructor, fields, settersWe can do it by XML or annotations	0	0	NORMAL	NOT_SUPPORTED
571	10	Tell me about BeanFactory, BeanDefinition ApplicationContext, BeanFactoryPostProcessor, BeanPostProcessor, ApplicationContextEvent, ApplicationListener	https://www.baeldung.com/spring-interview-questions#Q7https://habr.com/ru/articles/720794/BeanDefinition - define beans by meta infoBeanFactory - creates, provides and manages beans. It is a core of ApplicationContextApplicationContext - hold all information about beans and metadata. It is a main interface that provides:- using BeanFactory- uploading resources- public events (ApplicationContextEvent)- registration BeanPostProcessor and BeanFactoryPostProcessorBeanFactoryPostProcessor - is interface that allows to set definition for beansBeanPostProcessor - we can integrate custom logic of creation beans by itApplicationContextEvent - main class of life cycle bean eventsApplicationListener - allows to use @EventListener	0	0	NORMAL	NOT_SUPPORTED
572	10	What is Spring Bean?	Beans are Java objects that are initialized by IoC	0	0	NORMAL	NOT_SUPPORTED
573	10	What are beans scopes available in Spring? How to define them?	- Singleton - we always get one instance of object.- Prototype - we always get new object- Request - Bean will be available only in one HTTP-request- Session - Bean will be available only in one session- Global-session	0	0	NORMAL	NOT_SUPPORTED
527	7	Что такое «пул строк»?	It is a set of Strings in the Heap. It saves memoryPool of Strings is pattern “Flyweight”Thanks to it similar chars can refer on one object	0	0	NORMAL	NOT_SUPPORTED
574	1	stub	заглушка	0	0	NORMAL	NORMAL
579	13	Status after skip	Status isn't shown after the last element after skip	0	0	NORMAL	NOT_SUPPORTED
\.


--
-- Data for Name: card_stock; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.card_stock (id, storage_id, card_stock_name, description, key_type, value_type, max_point, test_mode_is_available, only_from_key) FROM stdin;
1	1	English words	All words I should remember	ENG	RUS	5	t	f
4	1	English phrases	.	ENG	RUS	5	t	f
5	1	HR Interview	Q&A	Q	A	5	f	t
7	1	Java Core	Core	Q	A	5	f	t
8	1	OOP, SOLID and etc	https://www.javatpoint.com/java-oops-concepts	Q	A	5	f	t
9	1	Collections	...	Q	A	5	f	t
10	1	Spring	https://www.baeldung.com/spring-interview-questions	Q	A	5	f	t
13	1	Backlog	for tasks	Name	Description	5	f	t
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	001	init schema	SQL	V001__init_schema.sql	-1503680912	root	2023-11-28 15:27:37.840737	41	t
2	002	add constraint	SQL	V002__add_constraint.sql	811273410	root	2023-11-28 15:27:37.907842	13	t
\.


--
-- Data for Name: storage; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.storage (id, user_id, storage_name) FROM stdin;
1	864725660	chunarevsa
\.


--
-- Name: card_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.card_id_seq', 583, true);


--
-- Name: card_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.card_stock_id_seq', 18, true);


--
-- Name: storage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.storage_id_seq', 1, true);


--
-- Name: card card_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_pkey PRIMARY KEY (id);


--
-- Name: card_stock card_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.card_stock
    ADD CONSTRAINT card_stock_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: storage storage_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.storage
    ADD CONSTRAINT storage_pkey PRIMARY KEY (id);


--
-- Name: card unique_card_stock_key; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT unique_card_stock_key UNIQUE (card_stock_id, card_key);


--
-- Name: card_stock unique_storage_id_card_stock_name; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.card_stock
    ADD CONSTRAINT unique_storage_id_card_stock_name UNIQUE (storage_id, card_stock_name);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: root
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: card card_card_stock_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.card
    ADD CONSTRAINT card_card_stock_id_fkey FOREIGN KEY (card_stock_id) REFERENCES public.card_stock(id);


--
-- Name: card_stock card_stock_storage_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.card_stock
    ADD CONSTRAINT card_stock_storage_id_fkey FOREIGN KEY (storage_id) REFERENCES public.storage(id);


--
-- PostgreSQL database dump complete
--

