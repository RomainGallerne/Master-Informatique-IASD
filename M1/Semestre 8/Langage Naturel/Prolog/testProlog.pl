/*BASE*/
det(X,Y) :- entre(le,X,Y).
det(X,Y) :- entre(un,X,Y).
vt(X,Y) :- entre(regarde,X,Y).
n(X,Y) :- entre(chat,X,Y).
n(X,Y) :- entre(chien,X,Y).

/*REGLES*/
gn(X,Z) :- det(X,Y),n(Y,Z).
gv(X,Z) :- vt(X,Y),gn(Y,Z).
gv(X,Y) :- vt(X,Y).
/*Si j'ai un morceau de X à Y qui est un GN et de Y à Z qui est un GV alors de X à Z on a une phrase S*/
s(X,Z) :- gn(X,Y), gv(Y,Z).

/*DONNES*/
entre(le,0,1).
entre(chat,1,2).
entre(regarde,2,3).
entre(un,3,4).
entre(chien,4,5).