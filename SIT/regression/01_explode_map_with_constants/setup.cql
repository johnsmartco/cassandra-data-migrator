DROP TABLE IF EXISTS origin.feature_explode_map_with_constants;
CREATE TABLE origin.feature_explode_map_with_constants(key text, time text, fruits map<text,int>, PRIMARY KEY (key));
INSERT INTO origin.feature_explode_map_with_constants(key,time,fruits) VALUES ('key1','1087383600000', {'apples': 3, 'oranges': 5, 'bananas': 2, 'grapes': 11});
INSERT INTO origin.feature_explode_map_with_constants(key,time,fruits) VALUES ('key2','1087383600000', {'apples': 4, 'oranges': 6, 'bananas': 3, 'pears': 7});
INSERT INTO origin.feature_explode_map_with_constants(key,time,fruits) VALUES ('key3','1087383600000', {'apples': 5, 'oranges': 7, 'bananas': 4, 'kiwi': 42});

DROP TABLE IF EXISTS target.feature_explode_map_with_constants;
CREATE TABLE target.feature_explode_map_with_constants(const1 text, const2 int, key text, fruit text, time timestamp, fruit_qty int, PRIMARY KEY ((const1,const2,key), fruit));
