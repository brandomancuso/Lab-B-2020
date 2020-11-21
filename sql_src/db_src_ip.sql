CREATE TABLE ip_user (
	nickname VARCHAR(30) NOT NULL PRIMARY KEY,
	email VARCHAR(255) NOT NULL UNIQUE,
	password CHAR(32) NOT NULL,
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	adminstrator BOOLEAN NOT NULL DEFAULT false,
	activation_code CHAR(8) NOT NULL,
        active BOOLEAN NOT NULL DEFAULT false
);
CREATE TABLE game (
	id INTEGER NOT NULL SERIAL PRIMARY KEY,
	name VARCHAR(30) NOT NULL
);
CREATE TABLE manche (
	id INTEGER SERIAL NOT NULL PRIMARY KEY,
	grid VARCHAR(32) NOT NULL,
	game_key INTEGER NOT NULL REFERENCES game
);
CREATE TABLE word (
	word VARCHAR(32) NOT NULL PRIMARY KEY,
	points INTEGER NOT NULL DEFAULT 0
);
CREATE TABLE partecipate (
	game_key INTEGER NOT NULL REFERENCES game,
	user_key INTEGER NOT NULL REFERENCES user_ip,
	total_points INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY (game_id, user_key)
);
CREATE TABLE find (
	user_key INTEGER NOT NULL REFERENCES user_ip,
	word_key INTEGER NOT NULL REFERENCES word,
	manche_key INTEGER NOT NULL REFERENCES manche,
	duplicate BOOLEAN NOT NULL DEFAULT false,
	correct	BOOLEAN NOT NULL DEFAULT false,
	PRIMARY KEY (user_key, word_key, manche_key)
);
CREATE TABLE def_req (
	user_key INTEGER NOT NULL REFERENCES user_ip,
	word_key INTEGER NOT NULL REFERENCES word,
	manche_key INTEGER NOT NULL REFERENCES manche,
	PRIMARY KEY (user_key, word_key, manche_key)
);
CREATE TABLE play (
	user_key INTEGER NOT NULL REFERENCES user_ip,
	manche_key INTEGER NOT NULL REFERENCES manche,
        points INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY (user_key, manche_key)
);