vt(regarde).
vt(regardent).
vi(court).
vi(courent).
n(chevre).
n(chevres).
n(renard).
n(renards).
de(le).
de(la).
de(les).

s(X,Y) :- gn(X,I),gv(I,Y).

gn(X,Y) :- entre(N,X,Y),n(N), Y is X+1.
gn(X,Z) :- entre(A,X,Y),de(A),entre(N,Y,Z),n(N),Y is X+1,Z is X+2.

gv(X,Y) :- entre(V,X,Y),vi(V),Y is X+1.
gv(X,Z) :- entre(V,X,Y),vt(V),gn(Y,Z),Y is X+1.

/* PHRASE */
entre(le,0,1).
entre(renard,1,2).
entre(regarde,2,3).
entre(la,3,4).
entre(chevre,4,5).