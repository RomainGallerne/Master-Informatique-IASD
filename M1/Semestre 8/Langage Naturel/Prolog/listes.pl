appartient(X,[X|_]).
appartient(X,[_|L]) :- appartient(X,L).

non_appartient(X,L) :- appartient(X,L),!,fail.
non_appartient(_,_).

sans_repetition_elem(X,[X|L]) :- non_appartient(X,L).
sans_repetition_elem(X,[I|L]) :-sans_repetition_elem(X,L), I\==X.

sans_repetition([X|L]) :- non_appartient(X,L),sans_repetition(L).
sans_repetition([]).

ajout_tete(X,L,[X|L]).
ajout_queue(X,[],[X]).
ajout_queue(X,[I|L1],[I|L2]) :- ajout_queue(X,L1,L2).

supprimer(X,[X|L1],L2) :- L1==L2.
supprimer(X,[I|L1],[I|L2]) :- supprimer(X,L1,L2).

supprimer_fin([_],[]).
supprimer_fin([I|L1],[I|L2]) :- supprimer_fin(L1,L2).

fusion(L1,L2,L3) :- fusion_1(L1,L2,L3).
fusion_1([A|L1],L2,[A|L3]) :- fusion_2(L1,L2,L3).
fusion_1([],L2,L3) :- L2==L3.
fusion_2(L1,[B|L2],[B|L3]) :- fusion_1(L1,L2,L3).
fusion_2(L1,[],L3) :- L1==L3.