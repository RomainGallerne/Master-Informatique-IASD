% oedipe-family-factbase.lp

% les caractères accentués ne sont pas admis par clingo
% ou alors il faut les mettre entre guillemets

% BASE DE FAITS 

% personnage est un prédicat unaire
% on utilise une écriture raccourcie permise par Clingo: 
% p(a;b). est un raccourci pour p(a). p(b). 
% Ne pas confondre p(a;b) et p(a,b) : écriture raccourcie pour prédicat unaire, et prédicat binaire !
personnage(agenor;cadmos;harmonie;ino;agave;polydore;labdacos;nicteis;penthe;oclasos).
personnage(menecee;jocaste;creon;hemon;eurydice;laios;oedipe).
personnage(antigone;ismene;eteocle;polynice;thersandre;ladamos).

% sexe des personnages
homme(agenor). % agénor
homme(cadmos).
femme(harmonie).
femme(ino).
femme(agave).	  % agavé
homme(polydore).
homme(labdacos).
femme(nicteis).   % nictéis
homme(penthe). % penthé
homme(oclasos). 
homme(menecee). % ménécée
femme(jocaste).
homme(creon).   % créon
homme(hemon). % hémon
femme(eurydice).
homme(laios).
homme(oedipe).
femme(antigone).
femme(ismene).   % ismène
homme(eteocle). % étéocle
homme(polynice).
homme(thersandre).
homme(ladamos).

% relations parent-enfant
aEnfant(agenor,cadmos).
aEnfant(cadmos,ino).
aEnfant(cadmos,agave).
aEnfant(cadmos,polydore).
aEnfant(harmonie,ino).
aEnfant(harmonie,agave).
aEnfant(harmonie,polydore).
aEnfant(polydore,labdacos).
aEnfant(nicteis,labdacos).
aEnfant(agave,penthe).
aEnfant(labdacos,laios).
aEnfant(penthe,oclasos).
aEnfant(oclasos,menecee).
aEnfant(menecee,jocaste).
aEnfant(menecee,creon).
aEnfant(creon,hemon).
aEnfant(eurydice,hemon). 
aEnfant(laios,oedipe).
aEnfant(jocaste,oedipe).
aEnfant(jocaste,antigone).
aEnfant(jocaste,eteocle).
aEnfant(jocaste,ismene).
aEnfant(jocaste,polynice).
aEnfant(oedipe,antigone).
aEnfant(oedipe,eteocle).
aEnfant(oedipe,ismene).
aEnfant(oedipe,polynice).
aEnfant(polynice, thersandre). 
aEnfant(eteocle,ladamos). 

% les rois
roi(agenor,tyr).
roi(cadmos,thebes).	% thèbes
roi(penthe,thebes).
roi(creon, thebes).
roi(oedipe,thebes).
roi(eteocle,thebes).
roi(ladamos,thebes). 

% BASE DE REGLES (A ECRIRE)

% #show personnage/1.