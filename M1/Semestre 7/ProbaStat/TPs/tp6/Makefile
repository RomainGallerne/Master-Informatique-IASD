########################################
#~ définitions
########################################

# Executables à générer
BIN=bin/test_grey	bin/histo	bin/dpp		bin/blur	bin/noise

# liste des fichiers sources 
SRCS0=test_grey.cpp
SRCS1=histo.cpp
SRCS2=dpp.cpp
SRCS3=blur.cpp
SRCS4=noise.cpp



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

bin/blur: $(SRCS3:%.cpp=obj/%.o)
	g++ -o $@ $+

bin/noise: $(SRCS4:%.cpp=obj/%.o)
	g++ -o $@ $+

clean:
	rm -f $(BIN) obj/*.o *~
