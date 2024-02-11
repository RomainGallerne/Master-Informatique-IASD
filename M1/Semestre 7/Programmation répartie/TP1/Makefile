########################################
#~ définitions
########################################

# Executables à générer
BIN=bin/Pconfig  bin/Pi

# liste des fichiers sources 
SRCS0=Pconfig.c
SRCS1=Pi.c

default: $(BIN)

########################################
#~ regles pour l'executable
########################################

obj/%.o: %.c
	gcc -Wall -Iinclude -c $< -o $@

bin/Pconfig: $(SRCS0:%.c=obj/%.o)
	gcc -o $@ $+

bin/Pi: $(SRCS1:%.c=obj/%.o)
	gcc -o $@ $+

clean:
	rm -f $(BIN) obj/*.o *~
