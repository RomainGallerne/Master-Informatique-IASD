-- ############## Schéma exploité

-- select table_name from dba_tables where owner='P00000009432';
-- select * from P00000009432.COMMUNE;
-- select * from P00000009432.DEPARTEMENT;
DROP TABLE COMMUNE;
DROP TABLE DEPARTEMENT;

-- 1.1 Question 1
CREATE TABLE COMMUNE AS select * from P00000009432.COMMUNE;
CREATE TABLE DEPARTEMENT AS select * from P00000009432.DEPARTEMENT;
alter table COMMUNE add constraint COMMUNE_PK primary key (codeinsee);
alter table DEPARTEMENT add constraint DEPARTEMENT_PK primary key (numdep);
alter table COMMUNE add constraint COMMUNE_NUMDEP foreign key (numdep) references DEPARTEMENT(numdep) on delete cascade;

-- 1.2 Question 2
CREATE INDEX numdep_idx ON COMMUNE (numdep);

-- ############## Rappel sur la consultation des vues du méta-schéma relatives aux index

-- Consulter les index, leurs hauteurs et la table associé
SELECT index_name, blevel as hauteur, table_name FROM USER_INDEXES;

-- mettre a jour les statistiques
ANALYZE TABLE COMMUNE COMPUTE STATISTICS;
ANALYZE TABLE DEPARTEMENT COMPUTE STATISTICS;

ANALYZE INDEX DEPARTEMENT_PK VALIDATE STRUCTURE;
ANALYZE INDEX COMMUNE_PK VALIDATE STRUCTURE;
ANALYZE INDEX NUMDEP_IDX VALIDATE STRUCTURE;

SELECT name, btree_space, most_repeated_key, lf_rows, br_rows, height FROM INDEX_STATS;

-- 1.3 Question 3.1
    SELECT blevel as hauteur FROM USER_INDEXES WHERE index_name='COMMUNE_PK' AND table_name='COMMUNE';
    -- La hauteur est de 1

-- 1.3 Question 3.2
    ANALYZE INDEX COMMUNE_PK VALIDATE STRUCTURE;
    SELECT name, lf_blks, br_blks FROM INDEX_STATS;
    -- Il y a 79 blocs de feuilles et 1 bloc de branche

-- 1.3 Question 3.3
    SELECT 
        name, --Nom de l'index
        lf_blks,  --Nombre de blocs feuilles
        br_blks,  --Nombre de blocs branches
        btree_space, --Taille totale disponible
        used_space, --Taille utilisée
        lf_blk_len, --Taille moyenne d'un bloc de feuille en octet
        lf_rows --Nombre de ligne dans les blocs de feuille
    FROM INDEX_STATS;
    -- Taille de chaque tuple : 
    -- Taille moyenne d'un bloc de feuille / Nombre de tuples dans un bloc de feuille

    SELECT name, lf_rows_len / lf_rows as avg_taille_tuple from index_stats;
    -- Chaque tuple d'index fait 16 octets

-- 1.3 Question 3.4
    SELECT table_name as tabl, avg_row_len as len_tuple FROM user_tables WHERE table_name='COMMUNE';
    -- Chaque tuple de la table fait 53 octets en moyenne

    SELECT value as taille from v$parameter where name='db_block_size';
    -- Un bloc fait 8192 octets
    SELECT pct_free from user_tables;
    -- 10% du bloc reste toujours libre

    SELECT (value * 0.9) / avg_row_len as facteur_blocage 
    FROM user_tables, v$parameter 
    WHERE name='db_block_size' and table_name='COMMUNE';
    -- Facteur de blocage : 139,109434 -> 140 tuples / blocs

-- 1.3 Question 3.5
    SELECT index_name, blevel as h FROM user_indexes WHERE index_name='NUMDEP_IDX' AND table_name='COMMUNE';
    -- L'index a une hauteur de 1

-- 1.3 Question 3.6
    ANALYZE INDEX NUMDEP_IDX VALIDATE STRUCTURE;
    SELECT distinct_keys, most_repeated_key from index_stats;
    --DISTINCT KEYS
        --Ce champ indique le nombre de valeurs distinctes (ou uniques) 
        --présentes dans les clés de l'index.
        --Une valeur élevée pour DISTINCT KEYS signifie que l'index a une bonne 
        --cardinalité, ce qui peut être bénéfique pour les performances des 
        --requêtes, car un index avec de nombreuses valeurs distinctes 
        --peut améliorer l'efficacité des opérations de recherche et de filtrage.

    --MOST REPEATED KEY
        --Cet attribut représente le nombre d'occurrences de la clé la plus répétée.
        --Une clé fréquemment répétée peut indiquer que certaines valeurs dans 
        --la colonne indexée sont très communes, ce qui peut affecter les 
        --performances de l'index. Si une valeur est répétée de manière 
        --excessive, cela peut rendre l'index moins efficace pour certaines requêtes.

-- ############## Manipulation du paquetage DMBS_ROWID

-- Exemple
    DECLARE 
        object_no integer;
        row_no integer;
        row_id ROWID;
    BEGIN
        SELECT ROWID INTO row_id FROM commune WHERE codeInsee = '34172';
        object_no := DBMS_ROWID.ROWID_OBJECT(row_id);
        row_no := DBMS_ROWID.ROWID_ROW_NUMBER(row_id);
        DBMS_OUTPUT.PUT_LINE('The obj. # is '||object_no||' '||row_no);
    END;
    /

    SELECT 
        DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid) as num_bloc, 
        DBMS_ROWID.ROWID_OBJECT(rowid) as num_objet, 
        nomcommaj as nom
    FROM Commune where codeInsee = '34172';

    -- nom de la structure de table ou d'index
    select owner, object_name from dba_objects where data_object_id =

-- 3.1 Question 4.1
    select rowid, rownum, codeinsee from commune;
    -- rowid : 
        -- c'est un identifiant unique pour chaque ligne dans la table. 
        -- Il indique l'emplacement physique de la ligne dans la base de données.

    -- rownum : 
        -- c'est un pseudo-colonne qui attribue un numéro de ligne 
        -- à chaque ligne renvoyée par la requête. rownum commence à 1 
        -- et augmente de 1 pour chaque ligne. 
        -- Si tu ne spécifies pas de clause ORDER BY, l'ordre des lignes 
        -- est indéfini, donc les numéros de ligne ne sont pas garantis 
        -- d'être dans un ordre particulier.

-- 3.1 Question 4.2
    CREATE OR REPLACE PROCEDURE MEMEBLOCQUE(CODEI IN VARCHAR) IS
    blocid NUMBER;
    nbville NUMBER;
    CURSOR enregistrements IS 
        SELECT codeInsee, nomcommaj AS nom 
        FROM commune 
        WHERE DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid) = blocid;
    BEGIN
        SELECT DISTINCT DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid) INTO blocid 
        FROM commune WHERE codeInsee = CODEI;

        DBMS_OUTPUT.PUT_LINE('Numero du bloc : ' || TO_CHAR(blocid));

        FOR e IN enregistrements LOOP
            DBMS_OUTPUT.PUT_LINE(TO_CHAR(e.codeInsee) || ' ' || TO_CHAR(e.nom));
        END LOOP;

        SELECT DISTINCT count(*) INTO nbville 
        FROM commune WHERE DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid) = blocid;

        DBMS_OUTPUT.PUT_LINE('Nombre de ville : ' || TO_CHAR(nbville));
    END;
    /
    
    EXECUTE MEMEBLOCQUE('34172');
    
-- 3.1 Question 4.3
    select blocks, avg_row_len from user_tables where table_name ='COMMUNE';
    -- 284 blocs de longueur moyenne 53

    CREATE OR REPLACE PROCEDURE NBRETUPLESPARBLOC IS
    nbville NUMBER;
    CURSOR blocs_id IS 
        SELECT DISTINCT DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid) AS bloc_id FROM commune ;
    BEGIN
         FOR b IN blocs_id 
        LOOP
            SELECT DISTINCT count(*) INTO nbville 
            FROM commune WHERE DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid) = b.bloc_id;
            DBMS_OUTPUT.PUT_LINE(TO_CHAR(b.bloc_id) || ' ' || TO_CHAR(nbville));
        END LOOP;
    END;
    /
    
    EXECUTE NBRETUPLESPARBLOC;
    -- Le facteur de blocage calculé est de 140 tuples / blocs
    -- Les valeurs ici tournent toutes autour de cette valeur, puisque le
    -- facteur de blocage se calcule à partir d'une moyenne, il est logique que
    -- le nombre de tuples par bloc effectif ne soit pas toujours parfaitement égal
    -- à 140 même si toutes les valeurs se rapprochent de 140.

    -- Il y a bien 248 blocs

-- 3.2 Question 5.1
    CREATE OR REPLACE PROCEDURE BLOCSDUDEPARTEMENT(ndep IN VARCHAR) IS
    nbville NUMBER;
    CURSOR blocs_id IS 
        SELECT DISTINCT DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid) AS bloc_id FROM commune where numdep = ndep ;
    BEGIN
        FOR b IN blocs_id 
        LOOP
            SELECT DISTINCT count(*) INTO nbville 
            FROM commune WHERE DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid) = b.bloc_id;
            DBMS_OUTPUT.PUT_LINE(TO_CHAR(b.bloc_id) || ' ' || TO_CHAR(nbville));
        END LOOP;
    END;
    /
    
    EXECUTE BLOCSDUDEPARTEMENT('34');

-- 3.2 Question 5.2
    CREATE OR REPLACE PROCEDURE DANSCACHE IS
    CURSOR blocs_id IS 
        SELECT 
            DBMS_ROWID.ROWID_BLOCK_NUMBER(commune.rowid) as blocid,
            commune.codeInsee, 
            commune.nomcommaj as nom
        FROM commune, v$bh 
        WHERE v$bh.block# = DBMS_ROWID.ROWID_BLOCK_NUMBER(commune.rowid);
    BEGIN
        FOR b IN blocs_id 
        LOOP
            DBMS_OUTPUT.PUT_LINE(
                TO_CHAR(b.blocid) || ' ' || 
                TO_CHAR(b.codeInsee) || ' ' || 
                TO_CHAR(b.nom));
        END LOOP;
    END;
    /
    
    EXECUTE DANSCACHE;



CREATE or REPLACE package infos AS
    procedure INFOS_TABLE(TABLENAME IN VARCHAR);
end infos;
/

CREATE or REPLACE package body infos AS
    PROCEDURE INFOS_TABLE(TABLENAME IN VARCHAR) 
    IS
        cursor indexes_names is 
            select index_name from USER_INDEXES 
            where table_name = TABLENAME;

        taille_tuple number;
        blocs_table number;
        blocs_vide_table number;
        nom_segment_table varchar(20);
        nombre_extent_table number;
        taille_octet_table number;

        blocs_index number;
        bloc_vide_index number;
        taille_octet_index number;

        taille_feuille_index number;
        taille_branche_index number;
        nombre_blocs_feuille number;
        nombre_blocs_branche number;
        hauteur number;
    BEGIN
        -- Partie table
        select 
            avg_row_len,
            blocks,
            empty_blocks 
        into taille_tuple, blocs_table, blocs_vide_table
        from user_tables where table_name = TABLENAME;
    -- 30
        select 
            (value*blocks) into taille_octet_table
        from v$parameter,user_tables 
        where name='db_block_size' and table_name =TABLENAME;
    -- 35
        select 
            segment_name,
            extents 
        into nom_segment_table,nombre_extent_table
        from user_segments where segment_name=TABLENAME;
    -- 41
        DBMS_OUTPUT.PUT_LINE('Taille d un tuple de la table : ' || TO_CHAR(taille_tuple));
        DBMS_OUTPUT.PUT_LINE('Blocs dans la table : ' || TO_CHAR(blocs_table));
        DBMS_OUTPUT.PUT_LINE('Blocs vides dans la table : ' || TO_CHAR(blocs_vide_table));
        DBMS_OUTPUT.PUT_LINE('Segment de la table : ' || TO_CHAR(nom_segment_table));
        DBMS_OUTPUT.PUT_LINE('Extent contennant la table : ' || TO_CHAR(nombre_extent_table));
        DBMS_OUTPUT.PUT_LINE('Taille totale de la table : ' || TO_CHAR(taille_octet_table) || 'octets');

        -- Partie index
    -- 50
        for ind in indexes_names
        LOOP

            DBMS_OUTPUT.PUT_LINE('Index : ' || ind.index_name);

            select 
                lf_blks+br_blks,
                lf_blks, 
                br_blks,
                lf_rows_len / lf_rows, 
                br_rows_len / br_rows 
            into blocs_index,nombre_blocs_feuille, nombre_blocs_branche, taille_feuille_index, taille_branche_index 
            from index_stats;

            select (value*(lf_blks+br_blks)) into taille_octet_index from v$parameter,index_stats where v$parameter.name='db_block_size';

            select blevel into hauteur from user_indexes where index_name=ind.index_name;

            DBMS_OUTPUT.PUT_LINE('Blocs dans l index : ' || TO_CHAR(blocs_index));
            DBMS_OUTPUT.PUT_LINE('Taille de l index : ' || TO_CHAR(taille_octet_index) || 'octets');
            DBMS_OUTPUT.PUT_LINE('Taille feuilles/branches : ' || TO_CHAR(taille_feuille_index) || '/' || TO_CHAR(taille_branche_index));
            DBMS_OUTPUT.PUT_LINE('Blocs feuilles/branches : ' || TO_CHAR(nombre_blocs_feuille) || '/' || TO_CHAR(nombre_blocs_branche));
            DBMS_OUTPUT.PUT_LINE('Taille totale de l index : ' || TO_CHAR(taille_octet_index));

        END LOOP;
    END;

END infos;
/
    -- A REMETRE DANS FICHIER TP RENDU TXT

    -- A REMETRE DANS FICHIER TP RENDU TXT
    ANALYZE TABLE ABC COMPUTE STATISTICS;
    EXECUTE infos.INFOS_TABLE('ABC');
    -- A REMETRE DANS FICHIER TP RENDU TXT

    -- Facteur de blocage (table)
        -- Tuples / blocs (table) OK
        -- segment (table) OK
        -- Nombre d'extents (table) OK
        -- Nombre de blocs vides (table) OK

        -- Taille d'un tuple (table & index) OK
        -- Nombre de blocs (table & index) OK
        -- Espace mémoire total utilisé (table & index) OK
        
        -- Hauteur (index)
