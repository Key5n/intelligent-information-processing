.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex3c/*.java) $(wildcard ../ex21b/ex3b/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex3d/perfectParser


test: $(CLASS)
	$(JAVA) -classpath .:../ex21b $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) -classpath .:../ex21b $(SRC)


#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

