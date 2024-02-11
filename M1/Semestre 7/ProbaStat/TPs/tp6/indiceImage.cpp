#include <stdio.h>

int indiceImage(int x, int y, int width, int height){
  //if(x >= width){printf("x trop grand");}
  //if(x < 0){printf("x trop petit");}
  //if(y >= height){printf("y trop grand");}
  //if(y < 0){printf("y trop petit");}
  return x * width + y;
}