// test_couleur.cpp : Seuille une image en niveau de gris

#include <stdio.h>
#include "image_ppm.h"
#include <math.h>

double moyenne(OCTET *ImgIn, int nTaille){
  double somme = 0;
  for(int i=0; i<nTaille; i++){
    somme += ImgIn[i];
   }
   return somme/(double)nTaille;
}

double variance(OCTET *ImgIn, int nTaille){
  double moy = moyenne(ImgIn, nTaille);
  double somme = 0;
  for(int i=0; i<nTaille; i++){
    somme += ((double)ImgIn[i]-moy)*((double)ImgIn[i]-moy);
   }
   return somme/((double)nTaille-1.0);
}

double ecart_type(OCTET *ImgIn, int nTaille){
  double var = variance(ImgIn, nTaille);
  return sqrt(var);
}

int main(int argc, char* argv[])
{
  char cNomImgLue[250];
  int nH, nW, nTaille;
  int histo[256];
  
  if (argc != 2) {
       printf("Usage: ImageIn.pgm\n"); 
       exit (1);
     }
   
   sscanf (argv[1],"%s",cNomImgLue);

   OCTET *ImgIn;
   
   lire_nb_lignes_colonnes_image_pgm(cNomImgLue, &nH, &nW);
   nTaille = nH * nW;
  
   allocation_tableau(ImgIn, OCTET, nTaille);
   lire_image_pgm(cNomImgLue, ImgIn, nH * nW);
	
   printf("MoyenneX %f\n",moyenne(ImgIn,nTaille));
   printf("Variance %f\n",variance(ImgIn,nTaille));
   printf("Eca-Type %f\n",ecart_type(ImgIn,nTaille));

   return 1;
}
