#Exercice 1
n <- 15
5 -> n
x <- 1
X <- 10
#n
#x
#X
n <- 10 + 2
#n
n <- 3 + rnorm(1)
#(10 + 2)*5
name <- "Carmen"; n1 <- 10; n2 <- 100; m<- 0.5
#ls()

#Exercice 2
M <- data.frame(n1, n2, m)
#ls.str(pat = "M")
rm(x)

#Exercice 3
#?rm()
ls()
rm(list=ls(pattern=""))
ls()

#Exercice 4
x11(); x11(); pdf();
dev.list()
dev.cur()
dev.set(3)
dev.cur()
dev.off(2)+
  
dev.list()
dev.off()
dev.off()

#Exercice 5
#?hist()
#?plot()
x <- rnorm(1000)
y <- rnorm(1000)
plot(x,y,xlab="Mille valeurs au hasard", ylab="Mille autres valeurs",xlim=c(-2,2), ylim=c(-2,2), pch=22, col="red", bg="yellow", bty="l",tcl=0.4, main="Configurer les graphiques en R", las=1, cex=1.5)

#Exercice 7
#X -> Bin(n,p) P(X<=k) = pbinom(k,n,p)
pbinom(3,18,1/6) - pbinom(2,18,1/6)
pbinom(3,18,1/6)
1-pbinom(3,18,1/6)
pbinom(16,18,1/6)

#Exercice 8
#X -> N(0,1) P(U<=k) = pnorm(k)
pnorm(1.41)
pnorm(-2.07)
1-pnorm(-1.26)

#X -> N(0,1) P(U<=qnorm(k)) = k
qnorm(0.95)
qnorm(0.1)
qnorm(1-0.99)

#Exercice 11
# N observations de la loi binomiale Bin(n,p) -> rbinom(N,n,p)
rBin100 <- rbinom(100,20,0.4)
rBin10000 <- rbinom(10000,20,0.4)
hist(rBin100)
hist(rBin10000)

#Exercice 14
xMoy10 <- rbinom(1000,10,0.2)/10
xMoy100 <- rbinom(1000,100,0.2)/100


hist(xMoy10,col="red")
curve(dnorm(x,0.2,0.016)*12,col="blue",add=T)

hist(xMoy100,col="red")
curve(dnorm(x,0.2,0.0016)/1.3,col="blue",add=T)
