--
-- Create Tables
--
CREATE TABLE tblConfig (
	config_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	config_key TEXT NOT NULL,
	config_value TEXT NOT NULL
	);

CREATE TABLE tblArticles (
	article_id TEXT NOT NULL PRIMARY KEY,
	article_title TEXT NOT NULL,
	article_author TEXT NOT NULL,
	article_content TEXT NOT NULL,
	article_picture TEXT NOT NULL,
	article_type TEXT NOT NULL,
	article_registered TEXT NOT NULL
	);
