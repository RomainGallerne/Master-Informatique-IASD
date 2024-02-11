#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>
#include <sys/stat.h>

#define MAX_BUFFER_SIZE 146980

void printstruct(struct sockaddr_in socket){
  char str[80];
  printf("-----Print Socket Struct------\n");
  //printf("Famille : %i\n",socket.sin_family);
  printf("Adresse : %s\n",inet_ntop(AF_INET, &socket.sin_addr.s_addr, str, sizeof(str)));
  printf("Port : %i\n",ntohs(socket.sin_port));
  printf("------------------------------\n");
}

int main(int argc, char *argv[]) {

  if (argc != 3){
    printf("utilisation : monPort nbProcess\n");
    exit(0);
  }

  struct infos{
    int num;
    struct sockaddr_in socket;
  };
  
  int nbProcess = atoi(argv[2]);

  int dsUDP = socket(PF_INET, SOCK_DGRAM, 0);
  if (dsUDP == -1){
    perror("Pconfig : pb creation socket d'écoute\n");
    close(dsUDP);
    exit(1); // Arrêt du prog car le reste dépend de la réussite de cette instruction
  } else {printf("Pconfig : creation de la socket réussie \n");}

  socklen_t lgA = sizeof(struct sockaddr_in);

  int recvfromsize,sendtos;

  struct sockaddr_in sockaddrRetour;
  socklen_t lgRetour = sizeof(sockaddrRetour);

  struct infos infoReception;
  struct sockaddr_in socketTCPPi[nbProcess];
  struct sockaddr_in socketUDPPi[nbProcess];

  struct sockaddr_in addrConfig;
  addrConfig.sin_family = AF_INET;
  addrConfig.sin_addr.s_addr = INADDR_ANY;
  addrConfig.sin_port = htons(atoi(argv[1]));


  bind(dsUDP,(struct sockaddr*)&addrConfig, lgRetour);

  for(int i=0;i<nbProcess;i++){
    //On attend l'arrivée d'un client UDP
    recvfromsize = recvfrom(dsUDP, &infoReception, sizeof(infoReception),0,(struct sockaddr*)&sockaddrRetour,&lgRetour);
    if(recvfromsize==-1){
        perror("Pconfig : Erreur du retour\n");
        exit(1);
    } else {
        printf("Pconfig : P%i a envoyé son numéro et ses informations\n",infoReception.num);
        
        socketUDPPi[infoReception.num] = sockaddrRetour;
        socketTCPPi[infoReception.num] = infoReception.socket;
        printstruct(socketTCPPi[infoReception.num]);
    }
  }

  printf("-------------------\n");

  for(int i=0;i<nbProcess-1;i++){
    //On envoie à tous le monde la socket du suivant
    sendtos = sendto(dsUDP,&socketTCPPi[i+1],sizeof(struct sockaddr_in),0, (struct sockaddr*)&socketUDPPi[i], lgA);
    if(sendtos==-1){
        perror("Moi : Erreur à l'envoi\n");
        exit(1);
    }
    else {
        printf("Pconfig : Successeur (%i) envoyé à P%i\n",i+1,i); 
    }
  }
  
  sendtos = sendto(dsUDP,&socketTCPPi[0],sizeof(struct sockaddr_in),0, (struct sockaddr*)&socketUDPPi[nbProcess-1], lgA);
    if(sendtos==-1){
        perror("Moi : Erreur à l'envoi\n");
        exit(1);
    }
    else {
        printf("Pconfig : Successeur (0) envoyé à P%i\n",nbProcess-1); 
    }

    printf("Pconfig : J'ai terminé mon travail, je termine\n");
    close(dsUDP);
}