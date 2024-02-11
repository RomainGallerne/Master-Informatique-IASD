########################################
#~ définitions
########################################

# Executables à générer
BIN=bin/Pi

# liste des fichiers sources 
SRCS0=Pi.c

default: $(BIN)

########################################
#~ regles pour l'executable
########################################

obj/%.o: %.c
	gcc -Wall -Iinclude -c $< -o $@

bin/Pi: $(SRCS0:%.c=obj/%.o)
	gcc -o $@ $+

clean:
	rm -f $(BIN) obj/*.o *~
