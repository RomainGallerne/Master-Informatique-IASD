-- 2. Examen

    -- 2.1 Q1
    -- A l’aide de la vue dba_tab_privs, retrouver la table recherchée, ainsi que les privilèges accordés à differents usagers par Gandalf.

        select grantee,privilege,table_name from dba_tab_privs where grantor='GANDALF' order by grantee;
        --> Les privilèges Update, Select et Insert ont été données à FRODON sur la table FONCTION.
        --> Le privilège INHERIT PRIVILEGES a été donné à PUBLIC sur la table GANDALF.
        --> Le privilège Select a été dibbé à PUBLIC sur la table FONCTION

    -- 2.2 Question 2
    -- Afficher le contenu de la table consultable de Gandalf pour la fonction ’ouvrier’.
    
        select * from GANDALF.FONCTION where NOM_F='ouvrier';
        --> NOM_F     SAL_MIN    SAL_MAX
        --> ouvrier    1000       3000

    -- 2.3 Question 3
    -- Lister les blocs de données montés en mémoire vive pour la table consultable de Gandalf (à l’aide de v$bh et dba_objects). 
    -- Y a t’il des blocs présents en plusieurs copies ? Y a t’il des blocs qui ont été accédés en écriture (voir attribut dirty) ?
    -- Quel est le nombre de blocs de données en mémoire vive pour cette table ?
    
        select block#, dirty from dba_objects join v$bh on v$bh.OBJD=dba_objects.OBJECT_ID where owner='GANDALF' and OBJECT_NAME='FONCTION';
        select count(block#) as nb_blocks from dba_objects join v$bh on v$bh.OBJD=dba_objects.OBJECT_ID where owner='GANDALF' and OBJECT_NAME='FONCTION';

    -- 2.4 Question 4
    -- Consulter la vue dba_tables pour obtenir des informations (nombre de blocs, nombre de tuples,
    -- taille moyenne des tuples) sur l’organisation physique pour le schéma Gandalf et la table consultable.
    -- les statistiques sur la table ont déjà été collectées par l’usager Gandalf.

    select 
        blocks as nb_blocks,
        num_rows as nb_tuples,
        avg_row_len as taille_moy_tuples
    from dba_tables where owner='GANDALF' and TABLE_NAME='FONCTION';

    --> 5 blocs de données
    --> 18 tuples
    --> Taille moyenne de 17 o.

    -- 2.5 Question 5
    -- Exploiter à bon escient le paquetage dbms_rowid pour voir dans quel(s) bloc(s) sont hébergés les tuples de la table.
    
        select 
            dbms_rowid.rowid_block_number(rowid) as block_number, 
            dbms_rowid.rowid_row_number(rowid) as row_number 
        from GANDALF.FONCTION order by block_number, row_number;
        --> Bloc 112110

    -- 2.6 Question 6
    -- Exploiter la vue dba_segments et vérifier que le bloc d’en-tête est bien celui indiqué via l’attribut class# de v$bh
    
    

    -- 2.7 Question 7
    -- Consulter le ou les verrous posés avec la vue v$transaction (attributs addr, name et start_time).
    -- Donner le nom des transactions si il y a lieu.
    


    -- 2.8 Question 8
    -- Un verrou a été posé sur la table consultable de Gandalf, retrouver par qui a été posé le verrou et
    -- sur quel object à l’aide des vues v$locked_object et dba_objects.
    


    -- 2.9 Question 9
    -- Ecrivez une requête exploitant v$lock et v$session pour identifier les sessions bloquantes et sessions
    -- bloquées, qui est bloqué ? depuis combien de temps, quels sont les types de verrous ?
    


    -- 2.10 Question 10
    -- Faîtes en sorte d’utiliser également les vues dba_objects et v$locked_object, pour enrichir la requête précédente.
    


    -- 2.11 Question 11
    -- La vue v$session permet de retrouver les tuples verrouillés avec les attributs row_wait_obj#,
    -- row_wait_file#, row_wait_block#, row_wait_row#. Vous construirez une requête tirant parti de
    -- v$session, v$locked_object et dba_objects pour retrouver le numéro du tuple verrouillé. Une fois ce
    -- numéro connu, vous pourrez exploiter la fonction rowid_row_number du paquetage dbms_rowid et
    -- la table impactée pour retrouver le tuple concerné