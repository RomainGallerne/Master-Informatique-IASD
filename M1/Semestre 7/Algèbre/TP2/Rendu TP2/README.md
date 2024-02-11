EXERCICE 3 :
	VECTEUR
	- produitscalaire : On applique simplement la forumle du produit scalaire entre deux vecteurs
	- cosinus : On utilise la forume du cosinus entre deux angles : prodscalaire(u,v)/|u|*|v|
	- norme : On applique la formule de la norme : sqrt(somme des coords au carrés)
	
	VECTEUR2D
	- Etant une spécialisation de la classe VECTEUR, on appelle souvent les méthodes de VECTEUR
	- sinus : On utilise la forume 1 = cos² + sin² avec le cosinus connu par la méthode de VECTEUR
	- det : On applique simplement ad-bc
	- cosinus : appelle à la methode de VECTEUR
	- tengente : On utilise que tan=sin/cos, si cos=0 -> retourne 0
	- angle : On utilise arcos(cos)
	
EXERCICE 5 :
	- Moyenne : On calcul simplement la moyenne terme à terme de chaque matrice du tableau de matrice
	- Determinant :
		Matrice 1x1 -> On retourne l'unique élément
		Matrice 2x2 -> det=ad - bc
		Pour une matrice >2x2 -> Développement de laplace
			> On réalise toujours le développement par rapport à la 1ere ligne (ligne 0 dans le programme)
	        > On ne gère pas ici le problème de savoir quelle ligne est la plus simple à développer (contient le plus de 0) puisque c'est le programme qui calcul tout
			> On créer une fonction annexe "cofacteur" pour calculer facilement les cofacteurs successifs, on passe en paramètre la position (i,j) du coefecient par rapport auquel on effectue le développement de laplace (sur une matrice 3x3, si cofacteur reçoit en params (1,0) il retourne la matrice :
			(0,0),(0,1),(0,2)           (0,1),(0,2)
            (1,0),(1,1),(1,2)  ------>	(2,1),(2,2)
			(2,0),(2,1),(2,2)   
			> On calcule de façon récustive les déterminant des matrices obtenus
	- Inverse :
		On applique le pivot de Gauss avec l'algo suivant (après avoir vérifier que det!=0) :
			Pour tout les éléments diagonaux :
				Si l'élément est 0 : on swap de ligne jusqu'à trouver un élément non-zéro
				On divise toute la ligne par le pivot choisi
				On soustrait à toutes les autres lignes (facteur de la colonne traité)x(ligne du pivot)
			Finalement, on retourner la matrice inverse
	- ApplicationLin :
		On vérifie que la dim du vecteur est compatible avec celle de la matrice
		On effectue le produit correspondant
		On retourne le vecteur résultant