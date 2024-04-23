/* REGLES */
s(X,Y) :- gn(X,I),gv(I,Y).
s(X,Y) :- gn(X,I),rel(I,J),gv(J,Y).

gn(X,Y) :- a(X,I),n(I,Y).

gv(X,Y) :- vi(X,Y).
gv(X,Y) :- vt(X,I),gn(I,Y).

grel(X,Y) :- rel(X,X1),X1 is X+1, gn(X1,I), vt(I,Y).
grel(X,Y) :- rel(X,X1),entre(que,X,X1),X1 is X+1, gn(X1,I), rel(I,J), vt(J,Y).

n(X,Y) :- entre(renard,X,Y).
n(X,Y) :- entre(renards,X,Y).
n(X,Y) :- entre(chevre,X,Y).
n(X,Y) :- entre(chevres,X,Y).

a(X,Y) :- entre(le,X,Y).
a(X,Y) :- entre(la,X,Y).
a(X,Y) :- entre(les,X,Y).

vt(X,Y) :- entre(regarde,X,Y).
vt(X,Y) :- entre(regardent,X,Y).

vi(X,Y) :- entre(court,X,Y).
vi(X,Y) :- entre(courent,X,Y).

rel(X,Y) :- entre(que,X,X1).

/* PHRASE */
entre(le,0,1).
entre(renard,1,2).
entre(que,2,3).
entre(la,3,4).
entre(chevre,4,5).
entre(que,5,6).
entre(le,6,7).
entre(renard,7,8).
entre(regarde,8,9).
entre(regarde,9,10).
entre(court,10,11).
