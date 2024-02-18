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

    INSERT INTO authority_user (user_id, authority_id)
    VALUES (new_user_id, 7), -- USER_READ
           (new_user_id, 8), -- USER_WRITE
           (new_user_id, 9); -- USER_DELETE
    RETURN new_user_id;
END;
$$ LANGUAGE plpgsql;

-- call the function
SELECT insert_user('yahya@rr.com', 'yahya', 2, 'Yahya', 'Kacem');
SELECT insert_user('munir@rr.com', 'munir', 2, 'Munir', 'Kirmani');
SELECT insert_user('hanan@rr.com', 'hanan', 2, 'Hanan', 'Smith');
SELECT insert_user('mohammed@rr.com', 'mame', 2, 'Mohammed', 'Jones');
SELECT insert_user('ilham@rr.com', 'ilu', 2, 'Ilham', 'Fati');
SELECT insert_user('fati@rr.com', 'fati', 2, 'Fati', 'Haji');
SELECT insert_user('nuria@rr.com', 'nuria', 2, 'Nuria', 'Musa');

-- from chatgpt
SELECT insert_user('john.smith123@example.com', 'johnsmith123', 2, 'John', 'Smith', 'MALE');
SELECT insert_user('emily.johnson94@example.com', 'emily.johnson94', 2, 'Emily', 'Johnson', 'FEMALE');
SELECT insert_user('michael.williams87@example.com', 'michaelw87', 2, 'Michael', 'Williams', 'MALE');
SELECT insert_user('sbrown56@example.com', 'sarahb_56', 2, 'Sarah', 'Brown', 'FEMALE');
SELECT insert_user('david.jones2023@example.com', 'djones2023', 2, 'David', 'Jones', 'MALE');
SELECT insert_user('sam.davis79@example.com', 'sam.davis79', 2, 'Samantha', 'Davis', 'FEMALE');
SELECT insert_user('chris.miller2020@example.com', 'chrismill2020', 2, 'Christopher', 'Miller', 'MALE');
SELECT insert_user('ashley.wilson123@example.com', 'ashwilson_123', 2, 'Ashley', 'Wilson', 'FEMALE');
SELECT insert_user('jessica.martinez@example.com', 'jessica.mtz', 2, 'Jessica', 'Martinez', 'FEMALE');
SELECT insert_user('mtaylor88@example.com', 'matt_taylor88', 2, 'Matthew', 'Taylor', 'MALE');

-- from chatgpt
SELECT insert_user('rachel.green@example.com', 'rachel_green', 2, 'Rachel', 'Green', 'FEMALE');
SELECT insert_user('ross.geller@example.com', 'ross_geller', 2, 'Ross', 'Geller', 'MALE');
SELECT insert_user('monica.bing@example.com', 'monica_bing', 2, 'Monica', 'Bing', 'FEMALE');
SELECT insert_user('chandler.bing@example.com', 'chandler_bing', 2, 'Chandler', 'Bing', 'MALE');
SELECT insert_user('joey.tribbiani@example.com', 'joey_tribbiani', 2, 'Joey', 'Tribbiani', 'MALE');
SELECT insert_user('phoebe.buffay@example.com', 'phoebe_buffay', 2, 'Phoebe', 'Buffay', 'FEMALE');
SELECT insert_user('janice.hosenstein@example.com', 'janice_hosenstein', 2, 'Janice', 'Hosenstein', 'FEMALE');
SELECT insert_user('gunther@example.com', 'gunther', 2, 'Gunther', 'Unknown', 'MALE');


-- turkish sounding names

SELECT insert_user('ali.ozdemir@example.com', 'ali_ozdemir', 2, 'Ali', 'Özdemir', 'MALE');
SELECT insert_user('ayse.yilmaz@example.com', 'ayse_yilmaz', 2, 'Ayşe', 'Yılmaz', 'FEMALE');
SELECT insert_user('mehmet.kaya@example.com', 'mehmet_kaya', 2, 'Mehmet', 'Kaya', 'MALE');
SELECT insert_user('elif.demir@example.com', 'elif_demir', 2, 'Elif', 'Demir', 'FEMALE');
SELECT insert_user('mustafa.aras@example.com', 'mustafa_aras', 2, 'Mustafa', 'Aras', 'MALE');
SELECT insert_user('sevgi.ozturk@example.com', 'sevgi_ozturk', 2, 'Sevgi', 'Öztürk', 'FEMALE');
SELECT insert_user('ahmet.yildiz@example.com', 'ahmet_yildiz', 2, 'Ahmet', 'Yıldız', 'MALE');
SELECT insert_user('fatma.koc@example.com', 'fatma_koc', 2, 'Fatma', 'Koç', 'FEMALE');
SELECT insert_user('murat.erdem@example.com', 'murat_erdem', 2, 'Murat', 'Erdem', 'MALE');
SELECT insert_user('elif.akgun@example.com', 'elif_akgun', 2, 'Elif', 'Akgün', 'FEMALE');

-- african sounding names

SELECT insert_user('kwame.adjei@example.com', 'kwame_adjei', 2, 'Kwame', 'Adjei', 'MALE');
SELECT insert_user('fatoumata.diallo@example.com', 'fatoumata_diallo', 2, 'Fatoumata', 'Diallo', 'FEMALE');
SELECT insert_user('olamide.adewale@example.com', 'olamide_adewale', 2, 'Olamide', 'Adewale', 'MALE');
SELECT insert_user('chinyere.okonkwo@example.com', 'chinyere_okonkwo', 2, 'Chinyere', 'Okonkwo', 'FEMALE');
SELECT insert_user('diallo.kamara@example.com', 'diallo_kamara', 2, 'Diallo', 'Kamara', 'MALE');
SELECT insert_user('ayanda.ndlovu@example.com', 'ayanda_ndlovu', 2, 'Ayanda', 'Ndlovu', 'FEMALE');
SELECT insert_user('moussa.traore@example.com', 'moussa_traore', 2, 'Moussa', 'Traoré', 'MALE');
SELECT insert_user('adenike.adeyemi@example.com', 'adenike_adeyemi', 2, 'Adenike', 'Adeyemi', 'FEMALE');
SELECT insert_user('yacouba.diarra@example.com', 'yacouba_diarra', 2, 'Yacouba', 'Diarra', 'MALE');
SELECT insert_user('chiamaka.onyeka@example.com', 'chiamaka_onyeka', 2, 'Chiamaka', 'Onyeka', 'FEMALE');


-- arabic sounding names

SELECT insert_user('ahmed.abu@example.com', 'ahmed_abu', 2, 'Ahmed', 'Abu', 'MALE');
SELECT insert_user('fatima.al@example.com', 'fatima_al', 2, 'Fatima', 'Al', 'FEMALE');
SELECT insert_user('muhammad.el@example.com', 'muhammad_el', 2, 'Muhammad', 'El', 'MALE');
SELECT insert_user('sara.hammad@example.com', 'sara_hammad', 2, 'Sara', 'Hammad', 'FEMALE');
SELECT insert_user('youssef.ahmad@example.com', 'youssef_ahmad', 2, 'Youssef', 'Ahmad', 'MALE');
SELECT insert_user('layla.saleh@example.com', 'layla_saleh', 2, 'Layla', 'Saleh', 'FEMALE');
SELECT insert_user('omar.nasr@example.com', 'omar_nasr', 2, 'Omar', 'Nasr', 'MALE');
SELECT insert_user('leila.ahmed@example.com', 'leila_ahmed', 2, 'Leila', 'Ahmed', 'FEMALE');
SELECT insert_user('ibrahim.hassan@example.com', 'ibrahim_hassan', 2, 'Ibrahim', 'Hassan', 'MALE');
SELECT insert_user('nour.abdel@example.com', 'nour_abdel', 2, 'Nour', 'Abdel', 'FEMALE');


-- famous sounding names

SELECT insert_user('nelson.mandela@example.com', 'nelson_mandela', 2, 'Nelson', 'Mandela', 'MALE');
SELECT insert_user('mother.teresa@example.com', 'mother_teresa', 2, 'Mother', 'Teresa', 'FEMALE');
SELECT insert_user('mahatma.gandhi@example.com', 'mahatma_gandhi', 2, 'Mahatma', 'Gandhi', 'MALE');
SELECT insert_user('martin.luther.king@example.com', 'martin_luther_king', 2, 'Martin Luther', 'King', 'MALE');
SELECT insert_user('malala.yousafzai@example.com', 'malala_yousafzai', 2, 'Malala', 'Yousafzai', 'FEMALE');
SELECT insert_user('rosa.parks@example.com', 'rosa_parks', 2, 'Rosa', 'Parks', 'FEMALE');
SELECT insert_user('winston.churchill@example.com', 'winston_churchill', 2, 'Winston', 'Churchill', 'MALE');
SELECT insert_user('joan.of.arc@example.com', 'joan_of_arc', 2, 'Joan', 'of Arc', 'FEMALE');
SELECT insert_user('abraham.lincoln@example.com', 'abraham_lincoln', 2, 'Abraham', 'Lincoln', 'MALE');
SELECT insert_user('albert.einstein@example.com', 'albert_einstein', 2, 'Albert', 'Einstein', 'MALE');

-- weird sounding names

SELECT insert_user('xanthe.xerxes@example.com', 'xanthe_xerxes', 2, 'Xanthe', 'Xerxes', 'FEMALE');
SELECT insert_user('zephyr.ziggurat@example.com', 'zephyr_ziggurat', 2, 'Zephyr', 'Ziggurat', 'MALE');
SELECT insert_user('quasar.quicksilver@example.com', 'quasar_quicksilver', 2, 'Quasar', 'Quicksilver', 'MALE');
SELECT insert_user('nyx.nightingale@example.com', 'nyx_nightingale', 2, 'Nyx', 'Nightingale', 'FEMALE');
SELECT insert_user('vortex.vandenberg@example.com', 'vortex_vandenberg', 2, 'Vortex', 'Vandenberg', 'MALE');
SELECT insert_user('sable.silversmith@example.com', 'sable_silversmith', 2, 'Sable', 'Silversmith', 'FEMALE');
SELECT insert_user('zeppelin.zodiac@example.com', 'zeppelin_zodiac', 2, 'Zeppelin', 'Zodiac', 'MALE');
SELECT insert_user('quinn.quagmire@example.com', 'quinn_quagmire', 2, 'Quinn', 'Quagmire', 'FEMALE');
SELECT insert_user('xerxes.xanadu@example.com', 'xerxes_xanadu', 2, 'Xerxes', 'Xanadu', 'MALE');
SELECT insert_user('zephyr.zirconium@example.com', 'zephyr_zirconium', 2, 'Zephyr', 'Zirconium', 'MALE');


-- character names

SELECT insert_user('harry.potter@example.com', 'harry_potter', 2, 'Harry', 'Potter', 'MALE');
SELECT insert_user('hermione.granger@example.com', 'hermione_granger', 2, 'Hermione', 'Granger', 'FEMALE');
SELECT insert_user('frodo.baggins@example.com', 'frodo_baggins', 2, 'Frodo', 'Baggins', 'MALE');
SELECT insert_user('aragorn.elessar@example.com', 'aragorn_elessar', 2, 'Aragorn', 'Elessar', 'MALE');
SELECT insert_user('katniss.everdeen@example.com', 'katniss_everdeen', 2, 'Katniss', 'Everdeen', 'FEMALE');
SELECT insert_user('luke.skywalker@example.com', 'luke_skywalker', 2, 'Luke', 'Skywalker', 'MALE');
SELECT insert_user('leia.organa@example.com', 'leia_organa', 2, 'Leia', 'Organa', 'FEMALE');
SELECT insert_user('bilbo.baggins@example.com', 'bilbo_baggins', 2, 'Bilbo', 'Baggins', 'MALE');
SELECT insert_user('jon.snow@example.com', 'jon_snow', 2, 'Jon', 'Snow', 'MALE');
SELECT insert_user('daenerys.targaryen@example.com', 'daenerys_targaryen', 2, 'Daenerys', 'Targaryen', 'FEMALE');


-- cartoon names
SELECT insert_user('mickey.mouse@example.com', 'mickey_mouse', 2, 'Mickey', 'Mouse', 'MALE');
SELECT insert_user('minnie.mouse@example.com', 'minnie_mouse', 2, 'Minnie', 'Mouse', 'FEMALE');
SELECT insert_user('spongebob.squarepants@example.com', 'spongebob_squarepants', 2, 'SpongeBob', 'SquarePants', 'MALE');
SELECT insert_user('patrick.star@example.com', 'patrick_star', 2, 'Patrick', 'Star', 'MALE');
SELECT insert_user('homer.simpson@example.com', 'homer_simpson', 2, 'Homer', 'Simpson', 'MALE');
SELECT insert_user('marge.simpson@example.com', 'marge_simpson', 2, 'Marge', 'Simpson', 'FEMALE');
SELECT insert_user('bugs.bunny@example.com', 'bugs_bunny', 2, 'Bugs', 'Bunny', 'MALE');
SELECT insert_user('daffy.duck@example.com', 'daffy_duck', 2, 'Daffy', 'Duck', 'MALE');
SELECT insert_user('scooby.doo@example.com', 'scooby_doo', 2, 'Scooby', 'Doo', 'MALE');
SELECT insert_user('shaggy.rogers@example.com', 'shaggy_rogers', 2, 'Shaggy', 'Rogers', 'MALE');


-- russian names

SELECT insert_user('ivan.ivanov@example.com', 'ivan_ivanov', 2, 'Ivan', 'Ivanov', 'MALE');
SELECT insert_user('anna.petrova@example.com', 'anna_petrova', 2, 'Anna', 'Petrova', 'FEMALE');
SELECT insert_user('vladimir.smirnov@example.com', 'vladimir_smirnov', 2, 'Vladimir', 'Smirnov', 'MALE');
SELECT insert_user('olga.kuznetsova@example.com', 'olga_kuznetsova', 2, 'Olga', 'Kuznetsova', 'FEMALE');
SELECT insert_user('dmitry.popov@example.com', 'dmitry_popov', 2, 'Dmitry', 'Popov', 'MALE');
SELECT insert_user('svetlana.ivanova@example.com', 'svetlana_ivanova', 2, 'Svetlana', 'Ivanova', 'FEMALE');
SELECT insert_user('sergei.kozlov@example.com', 'sergei_kozlov', 2, 'Sergei', 'Kozlov', 'MALE');
SELECT insert_user('elena.sokolova@example.com', 'elena_sokolova', 2, 'Elena', 'Sokolova', 'FEMALE');
SELECT insert_user('alexei.fedorov@example.com', 'alexei_fedorov', 2, 'Alexei', 'Fedorov', 'MALE');
SELECT insert_user('natalia.pavlova@example.com', 'natalia_pavlova', 2, 'Natalia', 'Pavlova', 'FEMALE');


-- mixed

SELECT insert_user('liam.nguyen@example.com', 'liam_nguyen', 2, 'Liam', 'Nguyen', 'MALE');
SELECT insert_user('sophia.santos@example.com', 'sophia_santos', 2, 'Sophia', 'Santos', 'FEMALE');
SELECT insert_user('noah.kim@example.com', 'noah_kim', 2, 'Noah', 'Kim', 'MALE');
SELECT insert_user('olivia.garcia@example.com', 'olivia_garcia', 2, 'Olivia', 'Garcia', 'FEMALE');
SELECT insert_user('william.silva@example.com', 'william_silva', 2, 'William', 'Silva', 'MALE');
SELECT insert_user('emma.chen@example.com', 'emma_chen', 2, 'Emma', 'Chen', 'FEMALE');
SELECT insert_user('james.hernandez@example.com', 'james_hernandez', 2, 'James', 'Hernandez', 'MALE');
SELECT insert_user('ava.lopez@example.com', 'ava_lopez', 2, 'Ava', 'Lopez', 'FEMALE');
SELECT insert_user('oliver.jackson@example.com', 'oliver_jackson', 2, 'Oliver', 'Jackson', 'MALE');
SELECT insert_user('isabella.khan@example.com', 'isabella_khan', 2, 'Isabella', 'Khan', 'FEMALE');
SELECT insert_user('lucas.nguyen@example.com', 'lucas_nguyen', 2, 'Lucas', 'Nguyen', 'MALE');
SELECT insert_user('mia.gomez@example.com', 'mia_gomez', 2, 'Mia', 'Gomez', 'FEMALE');
SELECT insert_user('mason.chow@example.com', 'mason_chow', 2, 'Mason', 'Chow', 'MALE');
SELECT insert_user('amelia.patel@example.com', 'amelia_patel', 2, 'Amelia', 'Patel', 'FEMALE');
SELECT insert_user('ethan.wong@example.com', 'ethan_wong', 2, 'Ethan', 'Wong', 'MALE');
SELECT insert_user('harper.gonzalez@example.com', 'harper_gonzalez', 2, 'Harper', 'Gonzalez', 'FEMALE');
SELECT insert_user('michael.kumar@example.com', 'michael_kumar', 2, 'Michael', 'Kumar', 'MALE');
SELECT insert_user('sofia.lee@example.com', 'sofia_lee', 2, 'Sofia', 'Lee', 'FEMALE');
SELECT insert_user('benjamin.singh@example.com', 'benjamin_singh', 2, 'Benjamin', 'Singh', 'MALE');
SELECT insert_user('abigail.wong@example.com', 'abigail_wong', 2, 'Abigail', 'Wong', 'FEMALE');
