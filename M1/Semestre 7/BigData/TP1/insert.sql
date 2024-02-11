-- Insérer des utilisateurs
INSERT INTO utilisateur (mail, nom_utilisateur, mdp)
VALUES
   ('utilisateur1@mail.com', 'Utilisateur 1', 'motdepasse1'),
   ('utilisateur2@mail.com', 'Utilisateur 2', 'motdepasse2'),
   ('utilisateur3@mail.com', 'Utilisateur 3', 'motdepasse3'),
   ('utilisateur4@mail.com', 'Utilisateur 4', 'motdepasse4'),
   ('utilisateur5@mail.com', 'Utilisateur 5', 'motdepasse5');

-- Insérer du contenu numérique
INSERT INTO contenu_numerique (code, date_publie)
VALUES
   ('photo1', '2023-09-10'),
   ('photo2', '2023-09-11'),
   ('photo3', '2023-09-12'),
   ('photo4', '2023-09-13'),
   ('photo5', '2023-09-14');

-- Insérer des appareils photo
INSERT INTO appareil_photo (numero, modele, marque)
VALUES
   (1, 'Modèle 1', 'Marque A'),
   (2, 'Modèle 2', 'Marque B');

-- Insérer des configurations
INSERT INTO configuration (ouverture_focal, temps_exposition, flash, distance_focal)
VALUES
   (1, 1, 'TRUE', 'TRUE'),
   (2, 2, 'FALSE', 'TRUE'),
   (3, 3, 'TRUE', 'FALSE'),
   (4, 4, 'FALSE', 'FALSE'),
   (5, 5, 'TRUE', 'TRUE');

-- Insérer des photographies
INSERT INTO photographie (code, licence, lieu, date_realisation_, mail, ouverture_focal, temps_exposition, flash, distance_focal, numero)
VALUES
   ('photo1', 'tous_droits_réservés', 'Lieu 1', '2023-09-10', 'utilisateur1@mail.com', 1, 1, 'TRUE', 'TRUE', 1),
   ('photo2', 'utilisation_commerciale_autorisée', 'Lieu 2', '2023-09-11', 'utilisateur2@mail.com', 2, 2, 'FALSE', 'TRUE', 2),
   ('photo3', 'modification_de_l image_autorisées', 'Lieu 3', '2023-09-12', 'utilisateur3@mail.com', 3, 3, 'TRUE', 'FALSE', 1),
   ('photo4', 'tous_droits_réservés', 'Lieu 4', '2023-09-13', 'utilisateur4@mail.com', 4, 4, 'FALSE', 'FALSE', 2),
   ('photo5', 'utilisation_commerciale_autorisée', 'Lieu 5', '2023-09-14', 'utilisateur5@mail.com', 5, 5, 'TRUE', 'TRUE', 1);