.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex3d/*.java) $(wildcard ../ex21c/ex3c/*.java) $(wildcard ../ex21b/ex3b/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex3d/MyNegaMaxGame


test: $(CLASS)
	$(JAVA) -classpath .:../ex21b:../ex21c $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) -classpath .:../ex21b:../ex21c $(SRC)

#$(CLASS): $(SRC)

#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

