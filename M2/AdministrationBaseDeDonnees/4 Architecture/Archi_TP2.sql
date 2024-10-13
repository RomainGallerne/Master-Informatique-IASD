--###################### PARTIE 2 : Serveur Oracle


--Q2.1
select banner_full from v$version;

--Q2.2
select server from v$session;

--Q2.3
select parameter from v$option;


--###################### PARTIE 3.1 : Structures mémoire


--Q3.0
select host_name,startup_time from v$instance;

--Q3.1.1
select sum(bytes)/1000000 as Mo from v$sgastat where pool='shared pool';
--462,38 Mo

--Q3.1.2
select sum(bytes)/1000000 as Mo from v$sgastat where name='buffer_cache';
--436,21 Mo

--Q3.1.3
select sum(bytes)/1000000 as Mo from v$sgastat where name='log_buffer';
--7,59 Mo

--Q3.1.4
select sum(bytes)/1000000000 as Go from v$sgastat;
--1,06 Go


--###################### PARTIE 3.2 : LibraryCache


--Q3.2.1
select sum(pins-reloads)/sum(pins) as library_cache_hit_ratio from v$librarycache;
--Si la valeur est proche de 1, cela indique que le SGBD effectue peut de rechargement par rapport aux pins, ce qui indique une bonne performance.
--Si la valeur est très inférieur à 1, cela signifie qu'il faut augmenter la taille du cache car celui-ci est insuffisant.

--Q3.2.2
-- Voir fichier Procedure_Archi_Tp2.sql

EXECUTE USER_ACTIVITY('E20200007056');

EXECUTE COSTLY_CURSORS

-- Tous les schéma utilisateurs
select parsing_schema_name from v$sqlarea where parsing_schema_name!='SYS' and parsing_schema_name!='SYSTEM' and parsing_schema_name!='WMSYS';
-- Afficher les requêtes faites par un utilisateur
select sql_text from v$sqlarea where parsing_schema_name='E20240010508';
-- Information d'utilisateur sur un usager
select disk_reads as acces_disque,cpu_time,executions,buffer_gets as nb_blocs from v$sqlarea where parsing_schema_name='E20240010508';


--###################### PARTIE 3.3 : Data Buffer Cache

select 1 - (phy.value / (cons.value + db.value - phy.value)) as buffer_hit_ratio
from v$sysstat phy, v$sysstat cons, v$sysstat db
where 
    phy.name ='physical reads' and 
    cons.name ='consistent gets' and 
    db.name ='db block gets';

/*
Is s'agot du pourcentage de pages trouvées dans le cache tampon sans avoir à lire à partir du disque. 
Le rapport correspond au nombre total d'accès au cache divisé par le nombre total de recherches 
dans le cache au cours des derniers milliers d'accès aux pages. 

Après une longue période, le ratio évolue très peu. 
La lecture à partir du cache étant beaucoup moins coûteuse que la lecture à partir du disque, 
on souhaite que ce ratio soit élevé. 
En règle générale, on peut augmenter le taux de réussite du cache tampon en augmentant 
la quantité de mémoire disponible pour SQL Server.
*/

select block#, class#, dirty, objd, object_name, owner from v$bh, dba_objects where dirty = 'Y' and objd = object_id;

/*
Cette requête permet d'afficher toutes les procédures présentes
en cache ainsi que différentes informations sur celles-ci.
(Taille, propriétaire...)
*/

select owner, sum(block#) as utilisation from v$bh, dba_objects where dirty = 'Y' and objd = object_id group by owner order by utilisation desc FETCH FIRST 10 ROWS ONLY;


--###################### PARTIE 3.4 : Processus


select p.pid, bg.name, bg.description, p.program from v$bgprocess bg, v$process p where bg.paddr = p.addr order by p.pid;

select p.pid, bg.name, bg.description from v$process p right outer join v$bgprocess bg on p.addr=bg.paddr;
-- Il semble s'agir de processus système.


--###################### PARTIE 3.5 : Structures logiques et physiques

--Q3.5.1
select tablespace_name from dba_tablespaces;

--Q3.5.2
select name from v$datafile;
/*
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/PMASTER/system01.dbf
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/PMASTER/sysaux01.dbf
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/PMASTER/undotbs01.dbf
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/PMASTER/users01.dbf
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/PMASTER/DATA_ETUD.DBF
*/

--Q3.5.3
select member from v$logfile;
/*
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/redo03a.log
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/redo03b.log
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/redo02a.log
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/redo02b.log
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/redo01a.log
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/redo01b.log
*/

--Q3.5.4
select name from v$controlfile;
/*
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/control01.ctl
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/control02.ctl
/data/bases/PRODPEDA/CMASTER/oracle/CMASTER/control03.ctl
*/

--Q3.5.5
select tablespace_name, file_name from dba_data_files;
-- Il y a un fichier par tablespace.