-- Création des publications
CREATE TABLE publication(
   id_publication INT,
   id_appareilphoto INT NOT NULL,
   id_date INT NOT NULL,
   id_heure INT NOT NULL,
   id_lieu INT NOT NULL,
   nb_vues INT NOT NULL,
   nb_jaime INT NOT NULL,
   PRIMARY KEY(ID_Publication)
   FOREIGN KEY(id_appareilphoto) REFERENCES type_appareil_photo(id_appareilphoto),
   FOREIGN KEY(id_date) REFERENCES datation(id_date),
   FOREIGN KEY(id_heure) REFERENCES heure(id_heure),
   FOREIGN KEY(id_lieu) REFERENCES lieu(id_lieu)
);

-- Création des type d'appareil photo
CREATE TABLE type_appareil_photo(
   id_appareilphoto INT,
   modele CHAR(100) NOT NULL,
   serie CHAR(50) NOT NULL,
   anne_parution INT NOT NULL,
   couleur CHAR(50),
   prix INT NOT NULL,
   gamme CHAR(50) NOT NULL,
   nom_createur CHAR(100),
   modele_parent INT,
   PRIMARY KEY(numero),
   FOREIGN KEY(modele_parent) REFERENCES type_appareil_photo(id_appareilphoto)
);

-- Création des heures
CREATE TABLE heure(
   id_heure INT,
   nb_heure INT NOT NULL,
   nb_minutes INT NOT NULL,
   jour_nuit CHAR(30) CHECK(jour_nuit IN ('Jour', 'Nuit')) NOT NULL,
   periode CHAR(50) CHECK(periode IN ('Matin', 'Midi', 'Apres-Midi', 'Soir', 'Nocture')) NOT NULL,
   PRIMARY KEY(id_heure)
);

-- Création des datations
CREATE TABLE datation(
   id_date INT,
   jour_semaine CHAR(50) CHECK(jour_nuit IN ('Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche')) NOT NULL,
   numero_semaine INT CHECK(numero_semaine < 53) NOT NULL,
   numero_dans_mois INT CHECK(numero_semaine < 31) NOT NULL,
   mois CHAR(50) CHECK(saison IN ('Janvier', 'Fevrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juiller', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Decembre')) NOT NULL,
   anne INT NOT NULL,
   vacance CHAR(30) CHECK(vacance IN ('Oui', 'Non')) NOT NULL,
   weed_end CHAR(30) CHECK(weed_end IN ('Oui', 'Non')) NOT NULL,
   saison CHAR(50) CHECK(saison IN ('Printemps', 'Ete', 'Automne', 'Hiver')) NOT NULL,
   periode CHAR(50) CHECK(saison IN ('Paques', 'Vacances estivales', 'Toussaint', 'Halloween', 'Noel')),
   PRIMARY KEY(id_date)
);

-- Création des lieux
CREATE TABLE lieu(
   id_lieu INT,
   nom_lieu CHAR(100),
   latitude FLOAT NOT NULL,
   longitude FLOAT NOT NULL,
   ville CHAR(100),
   region CHAR(100),
   code_postal INT,
   pays CHAR(100),
   PRIMARY KEY(id_heure)
);