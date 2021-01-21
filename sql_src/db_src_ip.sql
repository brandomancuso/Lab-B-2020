CREATE TABLE ip_user (
	nickname VARCHAR(30) NOT NULL PRIMARY KEY,
	email VARCHAR(255) NOT NULL UNIQUE,
	password CHAR(32) NOT NULL,
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	administrator BOOLEAN NOT NULL DEFAULT false,
	activation_code CHAR(8) NOT NULL,
        active BOOLEAN NOT NULL DEFAULT false
);
CREATE TABLE game (
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(30) NOT NULL
);
CREATE TABLE manche (
	id INTEGER NOT NULL PRIMARY KEY,
	grid VARCHAR(32) NOT NULL,
	game_key INTEGER NOT NULL REFERENCES game
);
CREATE TABLE word (
        id INTEGER NOT NULL PRIMARY KEY,
	word VARCHAR(32) NOT NULL,
	points INTEGER NOT NULL DEFAULT 0
);
CREATE TABLE partecipate (
	game_key INTEGER NOT NULL REFERENCES game,
	user_key VARCHAR(30) NOT NULL REFERENCES ip_user ON UPDATE CASCADE,
	total_points INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY (game_key, user_key)
);
CREATE TABLE find (
	user_key VARCHAR(30) NOT NULL REFERENCES ip_user ON UPDATE CASCADE,
	word_key INTEGER NOT NULL REFERENCES word,
	manche_key INTEGER NOT NULL REFERENCES manche,
	duplicate BOOLEAN NOT NULL DEFAULT false,
	correct	BOOLEAN NOT NULL DEFAULT false,
	PRIMARY KEY (user_key, word_key, manche_key)
);
CREATE TABLE def_req (
	user_key VARCHAR(30) NOT NULL REFERENCES ip_user ON UPDATE CASCADE,
	word_key INTEGER NOT NULL REFERENCES word,
	manche_key INTEGER NOT NULL REFERENCES manche,
	PRIMARY KEY (user_key, word_key, manche_key)
);
CREATE TABLE play (
	user_key VARCHAR(30) NOT NULL REFERENCES ip_user ON UPDATE CASCADE,
	manche_key INTEGER NOT NULL REFERENCES manche,
        points INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY (user_key, manche_key)
);

CREATE VIEW manche_play_stats (player, num_manches, avg_points) AS 
    SELECT user_key, count(*), AVG(points) FROM play GROUP BY user_key;

CREATE VIEW game_play_stats (player, avg_points) AS 
    SELECT user_key, AVG(total_points) FROM partecipate GROUP BY user_key;

CREATE VIEW game_stats (id, num_players, num_sessions) AS 
    SELECT game.id, COUNT(DISTINCT user_key) AS num_players, COUNT(DISTINCT manche_key) AS num_sessions 
    FROM game INNER JOIN manche ON game.id = manche.game_key INNER JOIN play ON manche.id = play.manche_key 
    GROUP BY game.id;