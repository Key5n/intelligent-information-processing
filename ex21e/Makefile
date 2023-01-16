.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex3e/*.java) $(wildcard ../ex21c/*.java) $(wildcard ../ex21b/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex3e/MyAlphaBetaGameParser


test: $(CLASS)
	$(JAVA) -classpath .:../ex21b:../ex21c:../ex21d $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) -classpath .:../ex21b:../ex21c:../ex21d $(SRC)

#$(CLASS): $(SRC)

#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

