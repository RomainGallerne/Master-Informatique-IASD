--###################### PARTIE 1

--Q1.1.1
select distinct tablespace_name,table_name from user_tables;
--Il s'agit du tablespace DATA_ETUD

--Q1.1.2
select name, value from v$parameter where name like 'db_block%';
-- La taille du block est de 8192

--Q1.1.3
select table_name, tablespace_name, num_rows, blocks, empty_blocks from user_tables where table_name='DEPARTEMENT';
-- Cette table utiliser 5 blocs de données

--###################### PARTIE 2

CREATE TABLE TEST(NUM CHAR(3) CHECK ((NUM>0) AND (NUM<999)),COMMENTAIRE CHAR(97));

--Q1.2.1
analyze table test compute statistics;
select table_name, tablespace_name, num_rows, blocks, empty_blocks from user_tables where table_name='TEST';
-- CREATION : 0 blocs alloués
-- 50 LIGNES : 5 blocs alloués mais 3 blocs inutilisés, 7003 de libre
-- 150 LIGNES : 5 blocs alloués mais 3 blocs inutilisés, 4862 de libre
-- 250 LIGNES : 5 blocs alloués mais 3 blocs inutilisés, 2721 de libre
-- 350 LIGNES : 13 blocs alloués mais 3 blocs inutilisés, 5191 de libre

--Q1.2.2
delete from test where mod(num,3) = 0;
-- 235 LIGNES : 13 blocs alloués mais 3 blocs inutilisés, 6120 de libre
-- 0 LIGNES : 13 blocs alloués mais 3 blocs inutilisés, 8018 de libre
truncate table test;
-- 0 LIGNES : 0 blocs alloués
-- La table a été vidée

--Q1.3
call remplissage(1,600)/
select tablespace_name, segment_name, blocks, bytes/1024 as Koctets, extents from user_segments order by segment_name;
select segment_type, extent_id, bytes/1024 enKo, blocks from user_extents where segment_name = 'TEST';

/*
— Quel est le nombre de segments associés à la table TEST ?
    Il y a un seul segment associé.

— Est ce que le segment associé à la table TEST organise logiquement l’information d’une autre
table de la base ?
    Non, il y a un segment par table.

— Quel est le nom du segment associé à la table la table TEST ?
    Le segment se nomme TEST.

— Quel est le type du segment associé à la table TEST ?
    C'est un segment de type TABLE.

— Quel est le nom de l’espace de tables dans lequel se range ce segment ?
    L'espace de table est DATA_ETUD.

— De combien d’extensions (extents) se compose le segment ? Est que ce nombre d’extensions
peut  ́evoluer avec l’ajout de nouveaux tuples dans la table ?
    Le segment se compose de deux extensions. Le nombre d'extensions évolue en 
    fonction de la taille nécessaire puisque chaque extension contient 8 blocs. 
    Si on dépasse ces 8 blocs, une nouvelle extension sera ajoutée.


*/