// test_couleur.cpp : Seuille une image en niveau de gris

#include <stdio.h>
#include "image_ppm.h"

int main(int argc, char* argv[])
{
  char cNomImgLue[250];
  int nH, nW, nTaille;
  int histo[256];
  
  if (argc != 2) {
       printf("Usage: ImageIn.pgm\n"); 
       exit (1) ;
     }
   
   sscanf (argv[1],"%s",cNomImgLue);

   OCTET *ImgIn, *ImgOut;
   
   lire_nb_lignes_colonnes_image_pgm(cNomImgLue, &nH, &nW);
   nTaille = nH * nW;
  
   allocation_tableau(ImgIn, OCTET, nTaille);
   lire_image_pgm(cNomImgLue, ImgIn, nH * nW);
	
   //   for (int i=0; i < nTaille; i++)
   // {
   //  if ( ImgIn[i] < S) ImgOut[i]=0; else ImgOut[i]=255;
   //  }

   for(int i=0; i<256; i++){
    histo[i]=0;
   }

   for (int i=0; i < nH; i++){ //Pour chaque ligne
        for (int j=0; j < nW; j++){ //Pour chaque colonne
            histo[ImgIn[i*nW+j]] += 1;
        }
    }

    for(int i=0; i<256; i++){
        printf("%i %f\n",i,(double)histo[i]/(double)nTaille);
    }

    

   /*ecrire_image_pgm(cNomImgEcrite, ImgOut,  nH, nW);
   free(ImgIn); free(ImgOut);*/

   return 1;
}
