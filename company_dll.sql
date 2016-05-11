INSERT INTO "company"."dbo"."ADDRESS" (STREET_1,STREET_2,CITY,PROVINCE,COUNTRY,POSTAL_CODE) VALUES ('181 ANTHONY AVE','TEST SUITE','MISSISSAUGA                       ','ON                  ','CA','L4Z 3V3   ');
INSERT INTO "company"."dbo"."ADDRESS" (STREET_1,STREET_2,CITY,PROVINCE,COUNTRY,POSTAL_CODE) VALUES ('181 YOUNGE AVE','TEST SUITE','TORONTO                       ','ON                  ','CA','M4Z 3V3   ');
INSERT INTO "company"."dbo"."ADDRESS" (STREET_1,STREET_2,CITY,PROVINCE,COUNTRY,POSTAL_CODE) VALUES ('200 ROLLING ST','TEST SUITE','RICHMOND HILL','ON                  ','CA','U4Z 3V3   ');

INSERT INTO "company"."dbo"."CLIENT" (FIRST_NAME,LAST_NAME,EMAIL,PHONE,ADDRESS_ID) VALUES ('JAMES','DEAN','JAMES.DEAN@GMAIL.COM','1-419-333-0000',1);
INSERT INTO "company"."dbo"."CLIENT" (FIRST_NAME,LAST_NAME,EMAIL,PHONE,ADDRESS_ID) VALUES ('MARIA','DELLA','MARIA.DELLA@GMAIL.COM','1-419-444-0000',2);
INSERT INTO "company"."dbo"."CLIENT" (FIRST_NAME,LAST_NAME,EMAIL,PHONE,ADDRESS_ID) VALUES ('UPAL','SAMBARE','UTPAL.SAM@HOT.COM','1-419-555-0000',3);

INSERT INTO "company"."dbo"."ACCOUNT" (PRODUCT_NAME,BALANCE) VALUES ('CHECK     ',1,100.0000);
INSERT INTO "company"."dbo"."ACCOUNT" (PRODUCT_NAME,BALANCE) VALUES ('SAVING    ',2,1030.0000);
INSERT INTO "company"."dbo"."ACCOUNT" (PRODUCT_NAME,BALANCE) VALUES ('RRSP      ',3,10030.0000);
-- RESET QUERY TABLE WITH PRIMARY KEY
DELETE FROM QUERY;
dbcc checkident (query, reseed, 0);

INSERT INTO QUERY (name,sql,map) VALUES ('SELECT DB_NAME()','SELELCT COUNT(*) FROM ACCOUNT','{"position":"Founder","age":35.0,"name":"mkyong","salary":10000.0}');
INSERT INTO QUERY (name,sql,map) VALUES ('SELECT DB_NAME()','SELELCT COUNT(*) FROM CLIENT','{"position":"Founder","age":35.0,"name":"mkyong","salary":10000.0}');
INSERT INTO QUERY (name,sql,map) VALUES ('SELECT DB_NAME()','SELELCT COUNT(*) FROM ADDRESS','{"position":"Founder","age":35.0,"name":"mkyong","salary":10000.0}');
INSERT INTO QUERY (name,sql,map) VALUES ('SELECT DB_NAME()','SELELCT COUNT(*) FROM ACCOUNT','{"position":"Founder","age":35.0,"name":"mkyong","salary":10000.0}');
INSERT INTO QUERY (name,sql,map) VALUES ('SELECT DB_NAME()','SELELCT COUNT(*) FROM CLIENT','{"position":"Founder","age":35.0,"name":"mkyong","salary":10000.0}');
INSERT INTO QUERY (name,sql,map) VALUES ('SELECT DB_NAME()','SELELCT COUNT(*) FROM ADDRESS','{"position":"Founder","age":35.0,"name":"mkyong","salary":10000.0}');


select * from query;
