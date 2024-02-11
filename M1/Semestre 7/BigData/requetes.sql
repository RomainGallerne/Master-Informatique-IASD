--1.1) Les requetes de Montpellier
select * from photographie where lieu='Montpellier';

--1.2) Les photos prisent par l'utilisateur nomme "Bob"
select * from photographie join utilisateur on utilisateur.mail=photographie.mail where utilisateur.nom_utilisateur='Bob';

--1.3) Les photos n'ayant pas la licence "tous droits réservés" et n'ayant pas été prises à Montpellier
select * from photographie where licence!='tous_droits_réserves' and lieu!='Montpellier';

--2) Requête qui permet de trouver les photos les plus appréciées avec la licence de distribution ’tous droits réservés’.
select * from photographie join apprecie on photographie.code=apprecie.code where licence='tous_droits_réserves' group by photographie.code 
having count(*)>=all(select count(*) from photographie join apprecie on photographie.code=apprecie.code where licence='tous_droits_réserves' group by apprecie.code);

--3) Requête qui permet de trouver les photos incluses dans le plus grande nombre de galeries
select code from appartient group by code having count(*)>=all(select count(*) from appartient group by code);