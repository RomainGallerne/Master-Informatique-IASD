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