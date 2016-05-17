CREATE SEQUENCE   "QUERY_SEQ"  
MINVALUE 1 MAXVALUE 99999 
INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE


CREATE TABLE QUERY (
 ID  INTEGER,
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

INSERT INTO QUERY (name,sql,map) VALUES ('products','SELECT * FROM products ',null);
INSERT INTO QUERY (name,sql,map) VALUES ('lstr report','select * from lstr_rpt where nature_of_swap_activity <> ''{swap_activity}''',null);

SELECT * FROM QUERY

