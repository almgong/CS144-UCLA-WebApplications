CREATE TABLE geom (item_id int PRIMARY KEY, g GEOMETRY NOT NULL) ENGINE=MyISAM;
CREATE SPATIAL INDEX sp_index ON geom (g);
INSERT INTO geom select item_id, Point(latitude, longitude) from Item where latitude != 0 and longitude != 0;
