CREATE or REPLACE procedure remplissage (borneInf in number, borneSup in number) is
    i number;
    comme char(97);
BEGIN
    comme := 'cot_';
    for i in borneInf .. borneSup
    LOOP
        comme := dbms_random.value || i ;
        insert into test values (i,comme);
    END LOOP;
    commit;
    exception when others then dbms_output.put_line(SQLCODE||'  '||SQLERRM);
END;
/

exec remplissage(1,100)