set serveroutput on;

--Q3.2.2

CREATE or REPLACE PROCEDURE USER_ACTIVITY(USER_SCHEMA IN varchar) IS
        cursor user_activity is 
            select sql_text,disk_reads,executions,buffer_gets from v$sqlarea where parsing_schema_name=USER_SCHEMA;
    BEGIN
        for activite in user_activity
        LOOP
            dbms_output.put_line('Requete SQL : '||to_char(activite.sql_text));
            dbms_output.put_line('Acces disque : '||to_char(activite.disk_reads));
            dbms_output.put_line('Nombre executions : '||to_char(activite.executions));
            dbms_output.put_line('Nombre de blocs memoires exploites : '||to_char(activite.buffer_gets));
            dbms_output.put_line('----------------');
        END LOOP;
    END;
/


CREATE or REPLACE PROCEDURE COSTLY_CURSORS IS
        cursor requetes_les_plus_couteuses is 
            select sql_text as requete,disk_reads*cpu_time*elapsed_time as cout from v$sqlarea order by cout desc FETCH FIRST 10 ROWS ONLY;
    BEGIN
        for requete in requetes_les_plus_couteuses
        LOOP
            dbms_output.put_line('Requete SQL : '||to_char(requete.requete));
            dbms_output.put_line('Cout : '||to_char(requete.cout));
            dbms_output.put_line('----------------');
        END LOOP;
    END;
/
