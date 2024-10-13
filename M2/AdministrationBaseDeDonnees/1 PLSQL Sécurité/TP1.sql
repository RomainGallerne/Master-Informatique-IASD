set serveroutput on

--Q3.1

CREATE or REPLACE trigger rennesMille 
    before insert or update of salaire on EMP 
    for each row
DECLARE
        num_dept DEPT.N_DEPT%type;
BEGIN
        select N_DEPT into num_dept from DEPT where LIEU='Rennes';
        if(:new.N_DEPT=num_dept and :new.SALAIRE<1000) then
            raise_application_error(-20022, :new.nom||' n''est pas assez paye.');
        end if;
end;
/

--Q3.2

create or replace procedure JoursEtHeuresOuvrables is
begin
    if (to_char(sysdate,'DY')='SAT') or (to_char(sysdate,'DY')='SUN') then
        raise_application_error(-20010, 'Modification interdite le '||to_char(sysdate,'DAY'));
    end if;
end;
/

create or replace trigger Ouvrable
    before delete or insert or update on EMP
begin
    JoursEtHeuresOuvrables();
end;
/

--Q3.3

DROP TABLE HISTORIQUE;
CREATE TABLE HISTORIQUE
    (DATE_OPERATION DATE,
	NOM_USAGER VARCHAR2(25),
    TYPE_OPERATION VARCHAR2(25));



CREATE or REPLACE trigger monitor 
    before insert or update or delete on DEPT 
    for each row
DECLARE
    typeOp varchar(15);
BEGIN
    if inserting then
        typeOp := 'Insertion';
    elsif updating then
        typeOp := 'Modification';
    elsif deleting then
        typeOp := 'Suppression';
    end if ;
    INSERT INTO HISTORIQUE VALUES(sysdate, user, typeOp);
end;
/

--Q3.4

create or replace procedure DeleteCascade(old_num IN number) is
begin
    DELETE FROM EMP WHERE N_DEPT=old_num;
end;
/

create or replace procedure UpdateCascade(old_num IN number, new_num IN number) is
begin
    UPDATE EMP SET N_DEPT=new_num WHERE N_DEPT=old_num; 
end;
/

CREATE or REPLACE trigger cascade 
    before update or delete on DEPT
    for each row
BEGIN
    if updating then
        UpdateCascade(:old.N_DEPT, :new.N_DEPT);
    elsif deleting then
        DeleteCascade(:old.N_DEPT);
    end if ;
    
end;
/

--Q4    TYPE_OPERATION VARCHAR2(25));


create or replace trigger majRefusee before create on e20200007056.schema
BEGIN
    dbms_output.put_line(sysdate||': un ordre de creation a ete emis.');
end ;
/

--Q5

DROP TABLE QuiSeConnecte;
CREATE TABLE QuiSeConnecte
    (OS_USER VARCHAR2(60),
	IP_ADDRESS VARCHAR2(30),
    SESSION_USER VARCHAR2(30),
    SYSTEM_DATE DATE);


CREATE or REPLACE trigger connexion 
    after logon on DATABASE 
DECLARE
    os_user VARCHAR2(60);
	ip_address VARCHAR2(30);
    session_user VARCHAR2(30);
BEGIN
    select sys_context('USERENV','OS_USER') into os_user from dual;
    select sys_context('USERENV','IP_ADDRESS') into ip_address from dual;
    select sys_context('USERENV','SESSION_USER') into session_user from dual;
    INSERT INTO QuiSeConnecte VALUES (os_user, session_user, ip_address, sysdate);
    commit;
end;
/