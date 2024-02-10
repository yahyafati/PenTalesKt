-- create a variable to hold a text value
DROP FUNCTION IF EXISTS insert_user;

CREATE OR REPLACE FUNCTION insert_user(
    new_email TEXT,
    new_username varchar(255),
    new_role_id INTEGER,
    new_first_name varchar(255),
    new_last_name varchar(255),
    new_gender varchar(255) = 'MALE'
) RETURNS INTEGER AS
$$
DECLARE
    new_user_id   INTEGER;
    password_hash TEXT := '$2a$10$vIZIjOCLYq3BTHIndWv6A.UuwaeLpE29KsxECW8xqegjomFEYs.RG';
BEGIN
    INSERT INTO users (created_at, updated_at, email, is_account_non_expired, is_account_non_locked, is_enabled,
                       password, username, role_id)
    VALUES (NOW(), NOW(), new_email, true, true, true, password_hash, new_username, new_role_id)
    RETURNING id INTO new_user_id;

    INSERT INTO user_profile (created_at, updated_at, user_id, first_name, last_name, gender, bio, date_of_birth,
                              display_name)
    VALUES (NOW(), NOW(), new_user_id, new_first_name, new_last_name, new_gender, 'I am a new user', '1990-01-01',
            new_first_name || ' ' || new_last_name);
    RETURN new_user_id;
END;
$$ LANGUAGE plpgsql;

-- call the function
SELECT insert_user('yahya@rr.com', 'yahya', 1, 'Yahya', 'Kacem');
SELECT insert_user('munir@rr.com', 'munir', 1, 'Munir', 'Kirmani');
SELECT insert_user('hanan@rr.com', 'hanan', 1, 'Hanan', 'Smith');
SELECT insert_user('mohammed@rr.com', 'mame', 1, 'Mohammed', 'Jones');
SELECT insert_user('ilham@rr.com', 'ilu', 1, 'Ilham', 'Fati');
SELECT insert_user('fati@rr.com', 'fati', 1, 'Fati', 'Haji');
SELECT insert_user('nuria@rr.com', 'nuria', 1, 'Nuria', 'Musa');

-- from chatgpt
SELECT insert_user('john.smith123@example.com', 'johnsmith123', 1, 'John', 'Smith', 'MALE');
SELECT insert_user('emily.johnson94@example.com', 'emily.johnson94', 1, 'Emily', 'Johnson', 'FEMALE');
SELECT insert_user('michael.williams87@example.com', 'michaelw87', 1, 'Michael', 'Williams', 'MALE');
SELECT insert_user('sbrown56@example.com', 'sarahb_56', 1, 'Sarah', 'Brown', 'FEMALE');
SELECT insert_user('david.jones2023@example.com', 'djones2023', 1, 'David', 'Jones', 'MALE');
SELECT insert_user('sam.davis79@example.com', 'sam.davis79', 1, 'Samantha', 'Davis', 'FEMALE');
SELECT insert_user('chris.miller2020@example.com', 'chrismill2020', 1, 'Christopher', 'Miller', 'MALE');
SELECT insert_user('ashley.wilson123@example.com', 'ashwilson_123', 1, 'Ashley', 'Wilson', 'FEMALE');
SELECT insert_user('jessica.martinez@example.com', 'jessica.mtz', 1, 'Jessica', 'Martinez', 'FEMALE');
SELECT insert_user('mtaylor88@example.com', 'matt_taylor88', 1, 'Matthew', 'Taylor', 'MALE');

-- from chatgpt
SELECT insert_user('rachel.green@example.com', 'rachel_green', 1, 'Rachel', 'Green', 'FEMALE');
SELECT insert_user('ross.geller@example.com', 'ross_geller', 1, 'Ross', 'Geller', 'MALE');
SELECT insert_user('monica.bing@example.com', 'monica_bing', 1, 'Monica', 'Bing', 'FEMALE');
SELECT insert_user('chandler.bing@example.com', 'chandler_bing', 1, 'Chandler', 'Bing', 'MALE');
SELECT insert_user('joey.tribbiani@example.com', 'joey_tribbiani', 1, 'Joey', 'Tribbiani', 'MALE');
SELECT insert_user('phoebe.buffay@example.com', 'phoebe_buffay', 1, 'Phoebe', 'Buffay', 'FEMALE');
SELECT insert_user('janice.hosenstein@example.com', 'janice_hosenstein', 1, 'Janice', 'Hosenstein', 'FEMALE');
SELECT insert_user('gunther@example.com', 'gunther', 1, 'Gunther', 'Unknown', 'MALE');


-- turkish sounding names

SELECT insert_user('ali.ozdemir@example.com', 'ali_ozdemir', 1, 'Ali', 'Özdemir', 'MALE');
SELECT insert_user('ayse.yilmaz@example.com', 'ayse_yilmaz', 1, 'Ayşe', 'Yılmaz', 'FEMALE');
SELECT insert_user('mehmet.kaya@example.com', 'mehmet_kaya', 1, 'Mehmet', 'Kaya', 'MALE');
SELECT insert_user('elif.demir@example.com', 'elif_demir', 1, 'Elif', 'Demir', 'FEMALE');
SELECT insert_user('mustafa.aras@example.com', 'mustafa_aras', 1, 'Mustafa', 'Aras', 'MALE');
SELECT insert_user('sevgi.ozturk@example.com', 'sevgi_ozturk', 1, 'Sevgi', 'Öztürk', 'FEMALE');
SELECT insert_user('ahmet.yildiz@example.com', 'ahmet_yildiz', 1, 'Ahmet', 'Yıldız', 'MALE');
SELECT insert_user('fatma.koc@example.com', 'fatma_koc', 1, 'Fatma', 'Koç', 'FEMALE');
SELECT insert_user('murat.erdem@example.com', 'murat_erdem', 1, 'Murat', 'Erdem', 'MALE');
SELECT insert_user('elif.akgun@example.com', 'elif_akgun', 1, 'Elif', 'Akgün', 'FEMALE');

-- african sounding names

SELECT insert_user('kwame.adjei@example.com', 'kwame_adjei', 1, 'Kwame', 'Adjei', 'MALE');
SELECT insert_user('fatoumata.diallo@example.com', 'fatoumata_diallo', 1, 'Fatoumata', 'Diallo', 'FEMALE');
SELECT insert_user('olamide.adewale@example.com', 'olamide_adewale', 1, 'Olamide', 'Adewale', 'MALE');
SELECT insert_user('chinyere.okonkwo@example.com', 'chinyere_okonkwo', 1, 'Chinyere', 'Okonkwo', 'FEMALE');
SELECT insert_user('diallo.kamara@example.com', 'diallo_kamara', 1, 'Diallo', 'Kamara', 'MALE');
SELECT insert_user('ayanda.ndlovu@example.com', 'ayanda_ndlovu', 1, 'Ayanda', 'Ndlovu', 'FEMALE');
SELECT insert_user('moussa.traore@example.com', 'moussa_traore', 1, 'Moussa', 'Traoré', 'MALE');
SELECT insert_user('adenike.adeyemi@example.com', 'adenike_adeyemi', 1, 'Adenike', 'Adeyemi', 'FEMALE');
SELECT insert_user('yacouba.diarra@example.com', 'yacouba_diarra', 1, 'Yacouba', 'Diarra', 'MALE');
SELECT insert_user('chiamaka.onyeka@example.com', 'chiamaka_onyeka', 1, 'Chiamaka', 'Onyeka', 'FEMALE');


-- arabic sounding names

SELECT insert_user('ahmed.abu@example.com', 'ahmed_abu', 1, 'Ahmed', 'Abu', 'MALE');
SELECT insert_user('fatima.al@example.com', 'fatima_al', 1, 'Fatima', 'Al', 'FEMALE');
SELECT insert_user('muhammad.el@example.com', 'muhammad_el', 1, 'Muhammad', 'El', 'MALE');
SELECT insert_user('sara.hammad@example.com', 'sara_hammad', 1, 'Sara', 'Hammad', 'FEMALE');
SELECT insert_user('youssef.ahmad@example.com', 'youssef_ahmad', 1, 'Youssef', 'Ahmad', 'MALE');
SELECT insert_user('layla.saleh@example.com', 'layla_saleh', 1, 'Layla', 'Saleh', 'FEMALE');
SELECT insert_user('omar.nasr@example.com', 'omar_nasr', 1, 'Omar', 'Nasr', 'MALE');
SELECT insert_user('leila.ahmed@example.com', 'leila_ahmed', 1, 'Leila', 'Ahmed', 'FEMALE');
SELECT insert_user('ibrahim.hassan@example.com', 'ibrahim_hassan', 1, 'Ibrahim', 'Hassan', 'MALE');
SELECT insert_user('nour.abdel@example.com', 'nour_abdel', 1, 'Nour', 'Abdel', 'FEMALE');


-- famous sounding names

SELECT insert_user('nelson.mandela@example.com', 'nelson_mandela', 1, 'Nelson', 'Mandela', 'MALE');
SELECT insert_user('mother.teresa@example.com', 'mother_teresa', 1, 'Mother', 'Teresa', 'FEMALE');
SELECT insert_user('mahatma.gandhi@example.com', 'mahatma_gandhi', 1, 'Mahatma', 'Gandhi', 'MALE');
SELECT insert_user('martin.luther.king@example.com', 'martin_luther_king', 1, 'Martin Luther', 'King', 'MALE');
SELECT insert_user('malala.yousafzai@example.com', 'malala_yousafzai', 1, 'Malala', 'Yousafzai', 'FEMALE');
SELECT insert_user('rosa.parks@example.com', 'rosa_parks', 1, 'Rosa', 'Parks', 'FEMALE');
SELECT insert_user('winston.churchill@example.com', 'winston_churchill', 1, 'Winston', 'Churchill', 'MALE');
SELECT insert_user('joan.of.arc@example.com', 'joan_of_arc', 1, 'Joan', 'of Arc', 'FEMALE');
SELECT insert_user('abraham.lincoln@example.com', 'abraham_lincoln', 1, 'Abraham', 'Lincoln', 'MALE');
SELECT insert_user('albert.einstein@example.com', 'albert_einstein', 1, 'Albert', 'Einstein', 'MALE');

-- weird sounding names

SELECT insert_user('xanthe.xerxes@example.com', 'xanthe_xerxes', 1, 'Xanthe', 'Xerxes', 'FEMALE');
SELECT insert_user('zephyr.ziggurat@example.com', 'zephyr_ziggurat', 1, 'Zephyr', 'Ziggurat', 'MALE');
SELECT insert_user('quasar.quicksilver@example.com', 'quasar_quicksilver', 1, 'Quasar', 'Quicksilver', 'MALE');
SELECT insert_user('nyx.nightingale@example.com', 'nyx_nightingale', 1, 'Nyx', 'Nightingale', 'FEMALE');
SELECT insert_user('vortex.vandenberg@example.com', 'vortex_vandenberg', 1, 'Vortex', 'Vandenberg', 'MALE');
SELECT insert_user('sable.silversmith@example.com', 'sable_silversmith', 1, 'Sable', 'Silversmith', 'FEMALE');
SELECT insert_user('zeppelin.zodiac@example.com', 'zeppelin_zodiac', 1, 'Zeppelin', 'Zodiac', 'MALE');
SELECT insert_user('quinn.quagmire@example.com', 'quinn_quagmire', 1, 'Quinn', 'Quagmire', 'FEMALE');
SELECT insert_user('xerxes.xanadu@example.com', 'xerxes_xanadu', 1, 'Xerxes', 'Xanadu', 'MALE');
SELECT insert_user('zephyr.zirconium@example.com', 'zephyr_zirconium', 1, 'Zephyr', 'Zirconium', 'MALE');


-- character names

SELECT insert_user('harry.potter@example.com', 'harry_potter', 1, 'Harry', 'Potter', 'MALE');
SELECT insert_user('hermione.granger@example.com', 'hermione_granger', 1, 'Hermione', 'Granger', 'FEMALE');
SELECT insert_user('frodo.baggins@example.com', 'frodo_baggins', 1, 'Frodo', 'Baggins', 'MALE');
SELECT insert_user('aragorn.elessar@example.com', 'aragorn_elessar', 1, 'Aragorn', 'Elessar', 'MALE');
SELECT insert_user('katniss.everdeen@example.com', 'katniss_everdeen', 1, 'Katniss', 'Everdeen', 'FEMALE');
SELECT insert_user('luke.skywalker@example.com', 'luke_skywalker', 1, 'Luke', 'Skywalker', 'MALE');
SELECT insert_user('leia.organa@example.com', 'leia_organa', 1, 'Leia', 'Organa', 'FEMALE');
SELECT insert_user('bilbo.baggins@example.com', 'bilbo_baggins', 1, 'Bilbo', 'Baggins', 'MALE');
SELECT insert_user('jon.snow@example.com', 'jon_snow', 1, 'Jon', 'Snow', 'MALE');
SELECT insert_user('daenerys.targaryen@example.com', 'daenerys_targaryen', 1, 'Daenerys', 'Targaryen', 'FEMALE');


-- cartoon names
SELECT insert_user('mickey.mouse@example.com', 'mickey_mouse', 1, 'Mickey', 'Mouse', 'MALE');
SELECT insert_user('minnie.mouse@example.com', 'minnie_mouse', 1, 'Minnie', 'Mouse', 'FEMALE');
SELECT insert_user('spongebob.squarepants@example.com', 'spongebob_squarepants', 1, 'SpongeBob', 'SquarePants', 'MALE');
SELECT insert_user('patrick.star@example.com', 'patrick_star', 1, 'Patrick', 'Star', 'MALE');
SELECT insert_user('homer.simpson@example.com', 'homer_simpson', 1, 'Homer', 'Simpson', 'MALE');
SELECT insert_user('marge.simpson@example.com', 'marge_simpson', 1, 'Marge', 'Simpson', 'FEMALE');
SELECT insert_user('bugs.bunny@example.com', 'bugs_bunny', 1, 'Bugs', 'Bunny', 'MALE');
SELECT insert_user('daffy.duck@example.com', 'daffy_duck', 1, 'Daffy', 'Duck', 'MALE');
SELECT insert_user('scooby.doo@example.com', 'scooby_doo', 1, 'Scooby', 'Doo', 'MALE');
SELECT insert_user('shaggy.rogers@example.com', 'shaggy_rogers', 1, 'Shaggy', 'Rogers', 'MALE');


-- russian names

SELECT insert_user('ivan.ivanov@example.com', 'ivan_ivanov', 1, 'Ivan', 'Ivanov', 'MALE');
SELECT insert_user('anna.petrova@example.com', 'anna_petrova', 1, 'Anna', 'Petrova', 'FEMALE');
SELECT insert_user('vladimir.smirnov@example.com', 'vladimir_smirnov', 1, 'Vladimir', 'Smirnov', 'MALE');
SELECT insert_user('olga.kuznetsova@example.com', 'olga_kuznetsova', 1, 'Olga', 'Kuznetsova', 'FEMALE');
SELECT insert_user('dmitry.popov@example.com', 'dmitry_popov', 1, 'Dmitry', 'Popov', 'MALE');
SELECT insert_user('svetlana.ivanova@example.com', 'svetlana_ivanova', 1, 'Svetlana', 'Ivanova', 'FEMALE');
SELECT insert_user('sergei.kozlov@example.com', 'sergei_kozlov', 1, 'Sergei', 'Kozlov', 'MALE');
SELECT insert_user('elena.sokolova@example.com', 'elena_sokolova', 1, 'Elena', 'Sokolova', 'FEMALE');
SELECT insert_user('alexei.fedorov@example.com', 'alexei_fedorov', 1, 'Alexei', 'Fedorov', 'MALE');
SELECT insert_user('natalia.pavlova@example.com', 'natalia_pavlova', 1, 'Natalia', 'Pavlova', 'FEMALE');


-- mixed

SELECT insert_user('liam.nguyen@example.com', 'liam_nguyen', 1, 'Liam', 'Nguyen', 'MALE');
SELECT insert_user('sophia.santos@example.com', 'sophia_santos', 1, 'Sophia', 'Santos', 'FEMALE');
SELECT insert_user('noah.kim@example.com', 'noah_kim', 1, 'Noah', 'Kim', 'MALE');
SELECT insert_user('olivia.garcia@example.com', 'olivia_garcia', 1, 'Olivia', 'Garcia', 'FEMALE');
SELECT insert_user('william.silva@example.com', 'william_silva', 1, 'William', 'Silva', 'MALE');
SELECT insert_user('emma.chen@example.com', 'emma_chen', 1, 'Emma', 'Chen', 'FEMALE');
SELECT insert_user('james.hernandez@example.com', 'james_hernandez', 1, 'James', 'Hernandez', 'MALE');
SELECT insert_user('ava.lopez@example.com', 'ava_lopez', 1, 'Ava', 'Lopez', 'FEMALE');
SELECT insert_user('oliver.jackson@example.com', 'oliver_jackson', 1, 'Oliver', 'Jackson', 'MALE');
SELECT insert_user('isabella.khan@example.com', 'isabella_khan', 1, 'Isabella', 'Khan', 'FEMALE');
SELECT insert_user('lucas.nguyen@example.com', 'lucas_nguyen', 1, 'Lucas', 'Nguyen', 'MALE');
SELECT insert_user('mia.gomez@example.com', 'mia_gomez', 1, 'Mia', 'Gomez', 'FEMALE');
SELECT insert_user('mason.chow@example.com', 'mason_chow', 1, 'Mason', 'Chow', 'MALE');
SELECT insert_user('amelia.patel@example.com', 'amelia_patel', 1, 'Amelia', 'Patel', 'FEMALE');
SELECT insert_user('ethan.wong@example.com', 'ethan_wong', 1, 'Ethan', 'Wong', 'MALE');
SELECT insert_user('harper.gonzalez@example.com', 'harper_gonzalez', 1, 'Harper', 'Gonzalez', 'FEMALE');
SELECT insert_user('michael.kumar@example.com', 'michael_kumar', 1, 'Michael', 'Kumar', 'MALE');
SELECT insert_user('sofia.lee@example.com', 'sofia_lee', 1, 'Sofia', 'Lee', 'FEMALE');
SELECT insert_user('benjamin.singh@example.com', 'benjamin_singh', 1, 'Benjamin', 'Singh', 'MALE');
SELECT insert_user('abigail.wong@example.com', 'abigail_wong', 1, 'Abigail', 'Wong', 'FEMALE');
