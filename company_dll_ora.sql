CREATE SEQUENCE   "QUERY_SEQ"  
MINVALUE 1 MAXVALUE 99999 
INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE


CREATE TABLE QUERY (
 ID  NUMBER(10),
 NAME VARCHAR(50) NOT NULL,
 SQL VARCHAR2(3000) NOT NULL,
 MAP VARCHAR2(3000), 
 constraint pk_query_id PRIMARY KEY(id)
);

CREATE OR REPLACE TRIGGER  "BI_QUERY"
 before INSERT ON "QUERY"               
 FOR each ROW  
BEGIN   
   SELECT "QUERY_SEQ".nextval INTO :NEW.ID FROM dual;
end;

INSERT INTO QUERY (name,sql,map) VALUES ('SELECT DB_NAME()','SELECT * FROM ACCOUNT WHERE BALANCE < 1000',null);
INSERT INTO QUERY (name,sql,map) VALUES ('rufina test','select * from address where city = ''toronto''',null);
INSERT INTO QUERY (name,sql,map) VALUES ('area check',' select * from client c inner join account a on c.client_id = a.client_id where a.PRODUCT_NAME = ''check''',null);
INSERT INTO QUERY (name,sql,map) VALUES ('bounce test','SELECT * FROM ACCOUNT WHERE BALANCE > {BALANCE} AND PRODUCT_NAME LIKE ''%{PRODUCT}%''','{"BALANCE":"","PRODUCT":""}');

SELECT * FROM QUERY