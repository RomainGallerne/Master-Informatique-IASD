DROP TABLE ABC;
CREATE TABLE ABC (A number, B varchar (20) , C varchar (20)) ;

-- programme principal
declare 
i number ;
begin
for i in 1..1000000
loop
INSERT INTO ABC VALUES
(i, dbms_random.string('L',20), dbms_random.string('U',20)) ;
end loop ;
commit ;
end ;
/

ALTER TABLE ABC ADD CONSTRAINT ABC_PK PRIMARY KEY (A);

ANALYZE TABLE ABC COMPUTE STATISTICS ;

SELECT avg_row_len, num_rows, blocks, avg_space, empty_blocks 
FROM USER_TABLES WHERE table_name = 'ABC';

ANALYZE INDEX ABC_PK VALIDATE STRUCTURE ;
