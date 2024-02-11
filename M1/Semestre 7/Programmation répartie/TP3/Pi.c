#include <ctype.h>
#include <netinet/in.h>
#include <pthread.h>
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

struct message{
  int msg;
  int estampille[4];
};

struct varPar{
  int hMat[20];
  int monNum;
  int nbThread;
  pthread_mutex_t ver_hMat;
  pthread_cond_t condi_hMat;
  int voisins[20];
  int nbVoisins;
};

struct params {
  int idThread;
  struct varPar * varPartagees;
};

void * envoiMSG (void * p){ 
    pthread_t thread = pthread_self();

    struct params * args = (struct params *) p;
    struct varPar * vars = args -> varPartagees;

    int send,msg;

    printf("Thread recepetion : Début de l'éxecution.\n");
    
    while(1){
      sleep(rand()%4);

      //DEBUT SECTION CRITIQUE
      if(pthread_mutex_lock(&(vars -> ver_hMat)) != 0){  //Verrouillage
        perror("Thread recepetion : Erreur lors de la demande du verrou.\n");
        exit(1);
      } 

      (vars -> hMat)[vars -> monNum] += 1;
      int msg = vars -> monNum;

      send = sendTCP((vars -> voisins)[rand()%(vars -> nbVoisins)],(void *)msg,sizeof(int));
      if(send<=0){
        perror("Client : Erreur à l'envoi\n");
        close(ds);
        exit(1);
      } else {
        std::cout << "Client : " << strlen(msg)+1 << " octets envoyés.\n";
      }

      if(pthread_mutex_unlock(&(vars -> ver_hMat)) != 0){ //Dévérouillage
        perror("Erreur lors du dévérouillage du verrou.\n");
        exit(1);
      }
      //FIN SECTION CRITIQUE
    }

    

    printf("Thread %ld : Fin de l'execution.\n",thread);
    pthread_exit(NULL);
  }

int main(int argc, char *argv[]) {

  if (argc != 5){
    printf("utilisation : ./Pi numProcess ip_config port_config port_pi\n");
    exit(0);
  }

  struct site{
    int id;
    struct sockaddr_in addr;
  };

  int monNum = atoi(argv[1]);

    //ETAPE 1 : CREATION DE LE SOCKET SERVEUR (TCP)

    socklen_t lgA = sizeof(struct sockaddr_in);
  
    int dsSERVEUR = socket(PF_INET, SOCK_STREAM, 0);
    if (dsSERVEUR == -1){
      perror("Pi : pb creation socket :");
      exit(1);     
    }
  
    printf("P%i : creation de la socket TCP réussie \n",monNum);
  
    struct sockaddr_in addrTCP;
    addrTCP.sin_family = AF_INET;
    addrTCP.sin_addr.s_addr = INADDR_ANY;
    addrTCP.sin_port = htons(atoi(argv[4]));

    int bindReturn = bind(dsSERVEUR,(struct sockaddr*)&addrTCP, lgA);
    if(bindReturn==-1){
      perror("Pi : Erreur à l'attribution du port\n");
      close(dsSERVEUR);
      exit(1);
    } else {
      printf("P%i : Attribution du port réussie\n",monNum);
    }

    //On pourra au maximum avoir tailleFile client dans la file d'attente
    int tailleFile = 1;
    
    //Mise en mode écoute
    int listenReturn = listen(dsSERVEUR,tailleFile);
    if(listenReturn==-1){
      perror("Pi : Erreur à l'écoute\n");
      close(dsSERVEUR);
      exit(1);
    } else {
      printf("P%i : Mise sur écoute du serveur\n\n",monNum);
    }






    //ETAPE 2 : CONNEXION A Pconfig (UDP)

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

    struct site mesinfosTCP;
    mesinfosTCP.id = monNum;
    mesinfosTCP.addr = addrTCP;

    int voisins_entrants[20] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int voisins_sortants[20] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int id=0;
    int send, recv, connex;
    struct site receptionSite;
    struct sockaddr_in recpetionAddr;
    int ds;

    //PREMIER CONTACT AVEC PCONFIG
      send = sendto(dsUDP,&mesinfosTCP,sizeof(struct site),0, (struct sockaddr*)&addrConfig, lgA);
      if(send==-1){
        perror("Pi : Erreur à l'envoi\n");
        close(dsUDP);
        close(dsSERVEUR);
        exit(1);
      }
      else {
        printf("P%i : Numéro et informations envoyés à Pconfig\n",monNum);
      }


    //CREATION DES VOISINS
    while(id != -1){
      recv = recvfrom(dsUDP, &receptionSite, sizeof(struct site),0,(struct sockaddr *)&addrConfig,&lgA);
      if(recv==-1){
          perror("Pi : Erreur du retour\n");
          close(dsUDP);
          close(dsSERVEUR);
          exit(1);
      }
      else {
          id = receptionSite.id;
          if(id == -1){break;}
          printf("P%i : Récéption du voisin sortant : P%i\n",monNum,receptionSite.id);
          ds = socket(PF_INET, SOCK_STREAM, 0);
          if (ds == -1){
            perror("Pi : pb creation socket");
            exit(1);     
          }
          connex = connect(ds,(struct sockaddr*)&receptionSite.addr, lgA);
          if(connex==-1){
            perror("Serveur : Erreur à la connexion\n");
            close(ds);
            exit(1);
          } else {
            printf("P%i : Connexion réussi à mon voisin sortant : P%i\n",monNum,receptionSite.id);
          }
          voisins_sortants[receptionSite.id] = ds;
      }
    }

    //RECEPTION DU NOMBRE DE VOISINS ENTRANT PAR PCONFIG
    int nbVoisins;
    recv = recvfrom(dsUDP, &nbVoisins, sizeof(int),0,(struct sockaddr *)&addrConfig,&lgA);
    if(recv==-1){
        perror("Pi : Erreur du retour\n");
        close(dsUDP);
        close(dsSERVEUR);
        exit(1);
    }

    //CONNEXION AUX VOISINS
    for(int i=0; i<nbVoisins; i++){
      ds = accept(dsSERVEUR,(struct sockaddr *)&recpetionAddr,&lgA); /*On accepte la connexion*/
      if(ds==-1){
        perror("Serveur : Erreur à l'acceptation de la connexion\n");
        close(ds);
        exit(1);
      } else {
        printf("P%i : Connexion Acceptée de mon voisin entrant\n",monNum);
        voisins_entrants[i] = ds;
      }
    }

    printf("Liste des mes voisins sortants :\n");
    for(int i=0;i<20;i++){
      if(voisins_sortants[i]!=-1){
        printf(".%i:%d. ",i,voisins_sortants[i]);
      }
    }printf("\n");
    printf("Liste des mes voisins entrants :\n");
    for(int i=0;i<20;i++){
      if(voisins_entrants[i]!=-1){
        printf(".%d. ",voisins_entrants[i]);
      }
    }
    
    printf("\n\n------------------\n");
    







    //ECHANGES ENTRE PIs

    int hMat[4] = {0,0,0,0};















  return 0;
}