#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>

#define MAX_BUFFER_SIZE 146980

int sendTCP(int sock, void *msg, int sizeMsg){
  int w = send(sock, msg, sizeMsg, 0);
  if(w == 0){return 0;} //Socket fermée
  else if(w == -1){return -1;}  //Erreur du dépôt
  else if(w == sizeMsg){return w;} //Dépôt réussi
  else {return w+sendTCP(sock,msg+w,sizeMsg-w);} //Dépôt incomplet
}

int recvTCP(int sock, void *msg, int sizeMsg){
  int r = recv(sock, msg, sizeMsg, 0);
  if(r == 0){return 0;} //Socket fermée
  else if(r == -1){return -1;}  //Erreur de lecture
  else return r; //Lecture réussi
}

void printstruct(struct sockaddr_in socket){
  char str[80];
  printf("-----Print Socket Struct------\n");
  //printf("Famille : %i\n",socket.sin_family);
  printf("Adresse : %s\n",inet_ntop(AF_INET, &socket.sin_addr.s_addr, str, sizeof(str)));
  printf("Port : %i\n",ntohs(socket.sin_port));
  printf("------------------------------\n");
}

int main(int argc, char *argv[]) {

  if (argc != 4){
    printf("utilisation : numProcess ip_config port_config port_Pi\n");
    exit(0);
  }

  struct infos{
    int num;
    struct sockaddr_in socket;
  };

  int monNum = atoi(argv[1]);

    //ETAPE 1 : CREATION DE LE SOCKET D'ECOUTE (TCP)

    socklen_t lgA = sizeof(struct sockaddr_in);
  
    int dsPREC = socket(PF_INET, SOCK_STREAM, 0);
    if (dsPREC == -1){
      perror("Pi : pb creation socket :");
      exit(1);     
    }
  
    printf("P%i : creation de la socket TCP réussie \n",monNum);
  
    struct sockaddr_in addrTCP;
    addrTCP.sin_family = AF_INET;
    addrTCP.sin_addr.s_addr = INADDR_ANY;
    addrTCP.sin_port = htons(atoi(argv[4]));

    int bindReturn = bind(dsPREC,(struct sockaddr*)&addrTCP, lgA);
    if(bindReturn==-1){
      perror("Pi : Erreur à l'attribution du port\n");
      close(dsPREC);
      exit(1);
    } else {
      printf("P%i : Attribution du port réussie\n",monNum);
    }

    //On pourra au maximum avoir tailleFile client dans la file d'attente
    int tailleFile = 1;
    
    //Mise en mode écoute
    int listenReturn = listen(dsPREC,tailleFile);
    if(listenReturn==-1){
      perror("Pi : Erreur à l'écoute\n");
      close(dsPREC);
      exit(1);
    } else {
      printf("P%i : Mise sur écoute du serveur\n\n",monNum);
    }




    //ETAPE 2 : CREATION DE L'ANNEAU - CONNEXION A Pconfig (UDP)

    int dsUDP = socket(PF_INET, SOCK_DGRAM, 0);
    if (dsUDP == -1){
      perror("Pi : pb creation socket d'écoute\n");
      close(dsUDP);
      exit(1); // Arrêt du prog car le reste dépend de la réussite de cette instruction
    } else {printf("P%i : creation de la socket UDP réussie \n",monNum);}

    struct sockaddr_in addrConfig;
    addrConfig.sin_family = AF_INET;
    addrConfig.sin_addr.s_addr = inet_addr((argv[2]));
    addrConfig.sin_port = htons((short)atoi(argv[3]));

    struct infos mesinfosTCP;
    mesinfosTCP.num = monNum;
    mesinfosTCP.socket = addrTCP;

    //printstruct(addrConfig);

    //Envoi des informations à Pconfig
    int send = sendto(dsUDP,&mesinfosTCP,sizeof(struct infos),0, (struct sockaddr*)&addrConfig, lgA);
    if(send==-1){
        perror("Pi : Erreur à l'envoi\n");
        exit(1);
    }
    else {
        printf("P%i : Numéro et informations envoyés à Pconfig\n",monNum); 
    }

    struct sockaddr_in socketSuiant;

    //Récéption de la socket du Pi+1
    int recv = recvfrom(dsUDP, &socketSuiant, sizeof(socketSuiant),0,(struct sockaddr *)&addrConfig,&lgA);
    if(recv==-1){
        perror("Pi : Erreur du retour\n");
        exit(1);
        close(dsUDP);
    } else {
        printf("P%i : Récéption des informations de mon successeur : \n",monNum);
        printstruct(socketSuiant);
        printf("\n");
        close(dsUDP);
    }






    //ETAPE 3 : CALCUL DE LA TAILLE DE L'ANNEAU (TCP)

    int dsSUIV = socket(PF_INET, SOCK_STREAM, 0);
    if (dsSUIV == -1){
      perror("Pi : pb creation socket :");
      exit(1);     
    }

    int ConnectReturn = connect(dsSUIV,(struct sockaddr*)&socketSuiant, lgA);
    if(ConnectReturn==-1){
      perror("Pi : Erreur à la connexion\n");
      close(dsSUIV);
      close(dsPREC);
      exit(1);
    } else {
      printf("P%i : Connexion Réussie\n\n",monNum);
    }

    struct sockaddr_in sockaddrRetour;
    socklen_t lgRetour = sizeof(sockaddrRetour);

    int dSC = accept(dsPREC,(struct sockaddr *)&sockaddrRetour,&lgRetour); /*On accepte la connexion*/
    if(dSC==-1){
      perror("Serveur : Erreur à l'acceptation de la connexion\n");
      close(dsPREC);
      exit(1);
    }

    int msg[2] = {monNum,0};
    int rep[2];

    //Envoi de mon message
    send = sendTCP(dsSUIV,(void *)msg,2*sizeof(int));
    if(send<=0){
      perror("Pi : Erreur à l'envoi\n");
      close(dsSUIV);
      close(dsPREC);
      exit(1);
    } else {
      printf("P%i : Envoi de mon message %ls\n",monNum,msg);
    }

    //Boucle de récpetion et de réenvoi des messages des autres Pi
    while(rep[0] != monNum){
      recv = recvTCP(dsPREC, &rep, 2*sizeof(int));
      if(recv<=0){
        perror("Pi : Erreur du retour\n");
        close(dsPREC);
        close(dsSUIV);
        exit(1);
      } else {
        send = sendTCP(dsSUIV,(void *)rep,2*sizeof(int));
        if(send<=0){
          perror("Pi : Erreur à l'envoi\n");
          close(dsSUIV);
          close(dsPREC);
          exit(1);
        } else {
          printf("P%i : Retransmission du message %ls\n",monNum,rep);
        }
      }
    }

    printf("P%i : Taille de l'anneau : %i\n",monNum,rep[1]);
}