-- Insérer des utilisateurs
INSERT INTO utilisateur VALUES('alice@mail.com', 'Alice', 'motdepasse1');
INSERT INTO utilisateur VALUES('bob@mail.com', 'Bob', 'motdepasse2');
INSERT INTO utilisateur VALUES('charlie@mail.com', 'Charlie', 'motdepasse3');
INSERT INTO utilisateur VALUES('daniel@mail.com', 'Daniel', 'motdepasse4');
INSERT INTO utilisateur VALUES('ellie@mail.com', 'Ellie', 'motdepasse5');

-- Insérer du contenu numérique
INSERT INTO contenu_numerique VALUES('photo1', '2023-09-10');
INSERT INTO contenu_numerique VALUES('photo2', '2023-09-11');
INSERT INTO contenu_numerique VALUES('photo3', '2023-09-12');
INSERT INTO contenu_numerique VALUES('photo4', '2023-09-13');
INSERT INTO contenu_numerique VALUES('photo5', '2023-09-14');

-- Insérer des appareil_photo
INSERT INTO appareil_photo VALUES(1, 'Modèle 1', 'Marque A');
INSERT INTO appareil_photo VALUES(2, 'Modèle 2', 'Marque B');
INSERT INTO appareil_photo VALUES(3, 'Modèle 3', 'Marque C');
INSERT INTO appareil_photo VALUES(4, 'Modèle 4', 'Marque D');
INSERT INTO appareil_photo VALUES(5, 'Modèle 5', 'Marque E');

-- Insérer des configuration
INSERT INTO configuration VALUES(1, 1, 1, 12);
INSERT INTO configuration VALUES(2, 2, 0, 8);
INSERT INTO configuration VALUES(3, 3, 1, 4);
INSERT INTO configuration VALUES(4, 4, 0, 6);
INSERT INTO configuration VALUES(5, 5, 1, 10);

-- Insérer des photographies
INSERT INTO photographie VALUES('photo1', TO_DATE('2023-09-10','YYYY-MM-DD'), 'tous_droits_réserves', 2, 'Montpellier', TO_DATE('2023-08-10','YYYY-MM-DD'), 'alice@mail.com', 1, 1, 1, 12);
INSERT INTO photographie VALUES('photo2', TO_DATE('2023-09-11','YYYY-MM-DD'), 'utilisation_commerciale_autorisee', 1, 'Paris', TO_DATE('2023-08-11','YYYY-MM-DD'),  'bob@mail.com', 2, 2, 0, 8);
INSERT INTO photographie VALUES('photo3', TO_DATE('2023-09-12','YYYY-MM-DD'), 'modification_de_l_image_autorisees', 1, 'Marseille', TO_DATE('2023-08-12','YYYY-MM-DD'),  'bob@mail.com', 3, 3, 1, 4);
INSERT INTO photographie VALUES('photo4', TO_DATE('2023-09-13','YYYY-MM-DD'), 'tous_droits_réserves', 3, 'Lille', TO_DATE('2023-08-13','YYYY-MM-DD'),  'daniel@mail.com', 4, 4, 0, 6);
INSERT INTO photographie VALUES('photo5', TO_DATE('2023-09-14','YYYY-MM-DD'), 'utilisation_commerciale_autorisee', 4, 'Brest', TO_DATE('2023-08-14','YYYY-MM-DD'),  'ellie@mail.com', 5, 5, 1, 10);



INSERT INTO lieu VALUES(0,"Arc de Triomphe de la place de l'Etoile",48.8738,2.295,'Paris','Ile-de-France',75117,'France');
INSERT INTO lieu VALUES(1,"Chateau de Versaille",45.748112,4.860201,'Versaille','Ile-de-France',78000,'France');
INSERT INTO lieu VALUES(2,"Palais des Papes",,,'Avignon','Vaucluse',84000,'France');