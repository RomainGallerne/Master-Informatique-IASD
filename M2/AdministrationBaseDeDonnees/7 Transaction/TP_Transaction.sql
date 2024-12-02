-- 2.2 Rappel : afficher les informations concernant les sessions utilisateurs en cours

select username, osuser, process, terminal, to_char(logon_time, 'YYYY-MM-DD HH24:MI') as logon_time 
from v$session where type='USER';

select osuser, to_char(logon_time, 'YYYY-MM-DD HH24:MI') as logon_time, v$process.program, cpu_used
from v$session join v$process on v$process.addr=v$session.paddr where type='USER';

-- 2.3 Vues dynamiques et mécanismes transactionnels

    -- 2.3.1 Exemple de la vue v$lock

    -- Afficher tous les verrous.
    select * from v$lock;

    -- Faire ressortir les transactions bloquantes.
    select a.SID as blockingSession, b.SID as blockedSession, b.request
    from v$lock a, v$lock b
    where a.SID != b.SID 
        and a.ID1 = b.ID1 
        and a.ID2 = b.ID2 
        and b.request > 0 
        and a.block = 1;

        -- Verrou sans situation de blocage
        update dep set nom_d='atelier design' where nom_d='bureau dessin';

        -- Conflit bloquant
        update dep set nom_d='atelier design' where nom_d='bureau dessin';
        update dep set nom_d='bureau design' where nom_d='bureau dessin';
        select a.SID as blockingSession, b.SID as blockedSession, b.request from v$lock a, v$lock b where a.SID != b.SID and a.ID1 = b.ID1 and a.ID2 = b.ID2 and b.request > 0 and a.block = 1;
        rollback;

        -- Autre conflit bloquant
        select * from dep for update;
        update dep set nom_d='bureau design' where nom_d='bureau dessin';
        select a.SID as blockingSession, b.SID as blockedSession, b.request from v$lock a, v$lock b where a.SID != b.SID and a.ID1 = b.ID1 and a.ID2 = b.ID2 and b.request > 0 and a.block = 1;
        rollback;

    -- 2.3.3 Savoir qui bloque qui

    update dep set nom_d='atelier design' where nom_d='bureau dessin';
    update dep set nom_d='bureau design' where nom_d='bureau dessin';
   
    col object_name for a20
    col object_type for a10
    col oracle_username for a10
    col os_user_name for a20

    select oracle_username, os_user_name, locked_mode, object_name, object_type from v$locked_object a,dba_objects b where a.object_id = b.object_id;
    rollback;

    -- 2.3.4 Qui est en attente ?

        col username for a12
    select s.username, s.sid, w.seconds_in_wait as n_seconds from v$session s, v$session_wait w where s.sid = w.sid
    and s.username='E20200007056';

    -- 2.3.5 Qui a posé des verrous bloquants ?

    update dep set nom_d='atelier design' where nom_d='bureau dessin';
    update dep set nom_d='bureau design' where nom_d='bureau dessin';

    select sid,username from V$SESSION where sid in (select sid from V$LOCK where block=1);
        -- ou
    select username, v$lock.sid, lmode, request, block, v$lock.type from v$lock, v$session where v$lock.sid = v$session.sid
    where username='E20200007056';

    -- 2.3.6 Qui bloque qui ?

    update dep set nom_d='atelier design' where nom_d='bureau dessin';
    update dep set nom_d='bureau design' where nom_d='bureau dessin';

    select a.SID as blockingSession, b.SID as blockedSession, b.request from v$lock a, v$lock b
    where a.SID != b.SID and a.ID1 = b.ID1
        and a.ID2 = b.ID2 and b.request > 0
        and a.block = 1;

    -- 2.3.7 Procédure PL/SQL

    set serveroutput on
    CREATE or REPLACE PROCEDURE listeSessionConcurentes(this_sid in NUMBER)
    IS
        cursor blocking_session is select a.SID as blockingSession, b.SID as blockedSession, b.request 
            from v$lock a, v$lock b where a.SID != b.SID and a.ID1 = b.ID1 and a.ID2 = b.ID2 and b.request > 0 and a.block = 1
            and (a.SID=this_sid or b.SID=this_sid);
    BEGIN
        for tuple in blocking_session
        LOOP
            dbms_output.put_line('ATTENTION : '||to_char(tuple.blockingSession)||' bloque '||to_char(tuple.blockedSession)||'.');
        END LOOP;
    END;
    /

    CREATE or REPLACE trigger trigger_listeSessionConcurentes 
        before insert or update or delete on dep 
    DECLARE
        this_sid NUMBER;
    BEGIN
        select sys_context('USERENV','SID') into this_sid from dual;
        listeSessionConcurentes(this_sid);
    end;
    /

    update dep set nom_d='atelier design' where nom_d='bureau dessin';
    update dep set nom_d='bureau design' where nom_d='bureau dessin';