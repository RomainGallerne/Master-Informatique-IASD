CREATE TABLE utilisateur(
   mail CHAR(50),
   nom_utilisateur CHAR(50) NOT NULL,
   mdp CHAR(50) NOT NULL,
   PRIMARY KEY(mail)
);

CREATE TABLE contenu_numerique(
   code CHAR(50),
   date_publie CHAR(50) NOT NULL,
   PRIMARY KEY(code)
);

CREATE TABLE appareil_photo(
   numero INT,
   modele CHAR(50) NOT NULL,
   marque CHAR(50) NOT NULL,
   PRIMARY KEY(numero)
);

CREATE TABLE configuration(
   ouverture_focal INT,
   temps_exposition INT,
   flash LOGICAL,
   distance_focal LOGICAL,
   PRIMARY KEY(ouverture_focal, temps_exposition, flash, distance_focal)
);

CREATE TABLE photographie(
   code CHAR(50),
   licence ENUM('tous_droits_réservés', 'utilisation_commerciale_autorisée', 'modification_de_l image_autorisées') NOT NULL,
   lieu CHAR(50) NOT NULL,
   date_realisation_ DATE NOT NULL,
   mail CHAR(50) NOT NULL,
   ouverture_focal INT NOT NULL,
   temps_exposition INT NOT NULL,
   flash LOGICAL NOT NULL,
   distance_focal LOGICAL NOT NULL,
   numero INT NOT NULL,
   PRIMARY KEY(code),
   FOREIGN KEY(code) REFERENCES contenu_numerique(code),
   FOREIGN KEY(date_publie) REFERENCES contenu_numerique(date_publie),
   FOREIGN KEY(mail) REFERENCES utilisateur(mail),
   FOREIGN KEY(ouverture_focal, temps_exposition, flash, distance_focal) REFERENCES configuration(ouverture_focal, temps_exposition, flash, distance_focal),
   FOREIGN KEY(numero) REFERENCES appareil_photo(numero)
);