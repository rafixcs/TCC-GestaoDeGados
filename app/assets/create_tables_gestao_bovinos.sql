CREATE TABLE app_animal_life_phase (
 id_life_phase INT NOT NULL PRIMARY KEY,
 phase_name VARCHAR(50) NOT NULL
);

CREATE TABLE app_farm (
 id_farm INT NOT NULL PRIMARY KEY,
 name VARCHAR(50) NOT NULL,
 location VARCHAR(20)
);

CREATE TABLE app_loot (
 id_loot INT NOT NULL PRIMARY KEY,
 name VARCHAR(10) NOT NULL
);

CREATE TABLE app_link_farm_loot (
 id_loot INT NOT NULL,
 id_farm INT NOT NULL,
 PRIMARY KEY(id_loot, id_farm),
 FOREIGN KEY(id_loot) REFERENCES app_loot(id_loot)
 FOREIGN KEY(id_farm) REFERENCES app_farm(id_farm)
);

CREATE TABLE app_producer (
 id_producer CHAR(128) NOT NULL PRIMARY KEY,
 name VARCHAR(30) NOT NULL,
 email VARCHAR(50) NOT NULL,
 password CHAR(12) NOT NULL
);

CREATE TABLE app_animal_race (
 id_race INT NOT NULL PRIMARY KEY,
 race_name CHAR(50) NOT NULL
);

CREATE TABLE app_tasks (
 id_task INT NOT NULL PRIMARY KEY,
 task_name VARCHAR(50) NOT NULL,
 description VARCHAR(120),
 done INT DEFAULT 0 NOT NULL
);

CREATE TABLE app_link_farm_task (
 id_farm INT NOT NULL,
 id_task INT NOT NULL,
 PRIMARY KEY (id_farm, id_task),
 FOREIGN KEY(id_farm) REFERENCES app_farm(id_farm)
 FOREIGN KEY(id_task) REFERENCES app_tasks(id_task)
);

CREATE TABLE app_animal_type (
 id_type INT NOT NULL PRIMARY KEY,
 type_name CHAR(50) NOT NULL
);

CREATE TABLE app_vaccine (
 id_vaccine CHAR(128) NOT NULL PRIMARY KEY,
 date DATE NOT NULL,
 name VARCHAR(50) NOT NULL,
 description VARCHAR(120),
 done INT DEFAULT 0 NOT NULL
);

CREATE TABLE app_animal_sex_type (
    id_sex INT NOT NULL PRIMARY KEY,
    sex_type CHAR(10) NOT NULL
);

CREATE TABLE app_animals (
 id_animal CHAR(128) NOT NULL PRIMARY KEY,
 sequence_number INT NOT NULL,
 name CHAR(50),
 age INT,
 birth_date DATE,
 img_src VARCHAR(100)
);

CREATE TABLE app_link_producer_farm (
 id_producer CHAR(128) NOT NULL,
 id_farm INT NOT NULL,
 PRIMARY KEY(id_producer, id_farm),
 FOREIGN KEY(id_producer) REFERENCES app_producer(id_producer)
 FOREIGN KEY(id_farm) REFERENCES app_farm(id_farm)
);

CREATE TABLE app_link_animal_tasks (
 id_task INT NOT NULL,
 id_animal CHAR(128) NOT NULL,
 PRIMARY KEY(id_task, id_animal),
 FOREIGN KEY(id_task) REFERENCES app_tasks(id_task),
 FOREIGN KEY(id_animal) REFERENCES app_animals(id_animal)
);

CREATE TABLE app_link_animal_vaccine (
 id_vaccine CHAR(128) NOT NULL,
 id_animal CHAR(128) NOT NULL,
 PRIMARY KEY(id_vaccine, id_animal),
 FOREIGN KEY(id_vaccine) REFERENCES app_vaccine(id_vaccine)
 FOREIGN KEY(id_animal) REFERENCES app_animals(id_animal)
);

CREATE TABLE app_link_loot_animals (
 id_loot INT NOT NULL,
 id_animal CHAR(128) NOT NULL,
 PRIMARY KEY(id_loot, id_animal),
 FOREIGN KEY(id_loot) REFERENCES app_loot(id_loot)
 FOREIGN KEY(id_animal) REFERENCES app_animals(id_animal)
);

ALTER TABLE app_animals ADD COLUMN FK_id_race REFERENCES app_animal_race(id_race);
ALTER TABLE app_animals ADD COLUMN FK_id_type REFERENCES app_animal_type(id_type);
ALTER TABLE app_animals ADD COLUMN FK_id_life_phase REFERENCES app_animal_life_phase(id_life_phase);
ALTER TABLE app_animals ADD COLUMN FK_id_sex_type REFERENCES app_animal_sex_type(id_sex);

INSERT INTO app_animal_sex_type VALUES (0, 'MASCULINO');
INSERT INTO app_animal_sex_type VALUES (1, 'FEMININO');
INSERT INTO app_animal_race VALUES (0, 'ANGUS');
INSERT INTO app_animal_race VALUES (1, 'HEREFORD');
INSERT INTO app_animal_race VALUES (2, 'LIMOUSIM');
INSERT INTO app_animal_race VALUES (3, 'AZUL BELGA');
INSERT INTO app_animal_race VALUES (4, 'BRAFORD');
INSERT INTO app_animal_type VALUES (0, 'GADO DE CORTE');
INSERT INTO app_animal_type VALUES (1, 'GADO LEITEIRO');
INSERT INTO app_animal_life_phase VALUES (0, 'CRIA');
INSERT INTO app_animal_life_phase VALUES (1, 'RECRIA (DESENVOLVIMENTO)');
INSERT INTO app_animal_life_phase VALUES (2, 'ENGORDA (TERMINAÇÃO)');

