CREATE TABLE geom (item_id int PRIMARY KEY, g GEOMETRY NOT NULL, SPATIAL INDEX(g)) ENGINE=MyISAM;
INSERT INTO geom select item_id, Point(latitude, longitude) from Item where latitude != 0 and longitude != 0;
