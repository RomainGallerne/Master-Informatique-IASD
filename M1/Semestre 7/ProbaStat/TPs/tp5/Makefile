########################################
#~ définitions
########################################

# Executables à générer
BIN=bin/test_grey	bin/histo	bin/dpp		bin/stats_image

# liste des fichiers sources 
SRCS0=test_grey.cpp
SRCS1=histo.cpp
SRCS2=dpp.cpp
SRCS3=stats_image.cpp



default: $(BIN)

########################################
#~ regles pour l'executable
########################################

obj/%.o: %.cpp
	g++ -Wall -Iinclude -c $< -o $@

bin/test_grey: $(SRCS0:%.cpp=obj/%.o)
	g++ -o $@ $+

bin/histo: $(SRCS1:%.cpp=obj/%.o)
	g++ -o $@ $+

bin/dpp: $(SRCS2:%.cpp=obj/%.o)
	g++ -o $@ $+

bin/stats_image: $(SRCS3:%.cpp=obj/%.o)
	g++ -o $@ $+

clean:
	rm -f $(BIN) obj/*.o *~
