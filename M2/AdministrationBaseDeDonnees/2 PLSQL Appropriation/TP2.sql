set serveroutput on

-- Q2 #################################################################

CREATE or REPLACE PROCEDURE suppTrigger (trigger_name in varchar)
IS
BEGIN
    EXECUTE IMMEDIATE 'drop trigger '||trigger_name;
EXCEPTION
    when others 
        then dbms_output.put_line('Erreur de suppression');
END;
/


CREATE or REPLACE PROCEDURE suppAllTriggers
IS
    cursor trigger_names is select trigger_name from user_triggers;
BEGIN
    for t_name in trigger_names
    LOOP
        suppTrigger(t_name.trigger_name);
        dbms_output.put_line('Suppression de '||t_name.trigger_name);
    END LOOP;
    dbms_output.put_line('Tous les triggers ont ete supprimes.');
END;
/

EXECUTE suppAllTriggers;

-- Q3.1.1  #################################################################

CREATE or REPLACE PROCEDURE EMPLOYESDUDEPARTEMENT (NUM_DEPT IN NUMBER, EMP_NAMES OUT VARCHAR)
IS
    cursor noms_employes is 
        select nom from EMP where N_DEPT=NUM_DEPT;
BEGIN
    for n_emp in noms_employes
    LOOP
        EMP_NAMES := EMP_NAMES||' '||n_emp.nom;
    END LOOP;
END;
/

-- Q3.1.2  #################################################################

CREATE or REPLACE PROCEDURE COUTSALARIALDUDEPARTEMENT(NUM_DEPT IN NUMBER, COUT_TOTAL OUT NUMBER)
IS
    cursor salaires is 
        select SALAIRE from EMP where N_DEPT=NUM_DEPT;
BEGIN
    COUT_TOTAL := 0;
    for sal in salaires
    LOOP
        COUT_TOTAL := COUT_TOTAL+sal.SALAIRE;
    END LOOP;
END;
/

-- Q3.2  #################################################################

CREATE or REPLACE package Supervision AS
    function TAUX_UTILISATION return number;
    procedure NB_TABLE_PAR_USER;
    procedure INFOS_USERS ();
    --procedure LOGED_USER ();
    --procedure ROLE_ET_PRIVILEGE ();
end Supervision;
/

CREATE or REPLACE package body Supervision AS

    --Procedure TAUX_UTILISATION
    function TAUX_UTILISATION return number IS
            connecte number;
            connus number;
        BEGIN
            select count(DISTINCT username) into connecte from v$session;
            select count(DISTINCT username) into connus from dba_users;

            return (round(connecte / connus * 100, 2));
            Exception when OTHERS then return null;
        END;

    --Procedure NB_TABLE_PAR_USER
    procedure NB_TABLE_PAR_USER IS
            cursor users is 
                select distinct username from v$session;
            numb_tables number;
            numb_rows number;
        BEGIN
            for u in users
            LOOP
                select count(*) into numb_tables from all_tables where user=u.username;
                select sum(num_rows) into numb_rows from all_tables where user=u.username;
                dbms_output.put_line(to_char(u.username)||' : '||to_char(numb_tables)||' tables | '||to_char(numb_rows)||' lignes.');
            END LOOP;
        END;

    --Procedure INFOS_USER

    --select username,sid,MACHINE,LOGON_TIME from v$session;
    --select username from v$process;

    --PAS FINI

    procedure INFOS_USER IS
            cursor users is 
                select distinct username from v$session;
            numb_tables number;
            numb_rows number;
        BEGIN
            for u in users
            LOOP
                select count(*) into numb_tables from all_tables where user=u.username;
                select sum(num_rows) into numb_rows from all_tables where user=u.username;
                dbms_output.put_line(to_char(u.username)||' : '||to_char(numb_tables)||' tables | '||to_char(numb_rows)||' lignes.');
            END LOOP;
        END;

END Supervision;
/

-- Programme Principal  #################################################################

DECLARE
    nom_emp varchar(2000);
    cout_total NUMBER(7,2);
    taux_utilisation NUMBER(7,2);
BEGIN
    EMPLOYESDUDEPARTEMENT(10, nom_emp);
    dbms_output.put_line('La liste des employes du departement 10 est :'||nom_emp);

    COUTSALARIALDUDEPARTEMENT(10, cout_total);
    dbms_output.put_line('Le cout salariale du departement 10 est de '||to_char(cout_total)||' euros.');

    taux_utilisation := SUPERVISION.TAUX_UTILISATION;
    dbms_output.put_line('Le taux d utilisation est de '||to_char(taux_utilisation)||'%.');

    SUPERVISION.NB_TABLE_PAR_USER;
END;
/