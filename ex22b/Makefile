.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex4b/*.java) $(wildcard ../ex22a/ex4a/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex4b/Game


test: $(CLASS)
	$(JAVA) -classpath .:../ex22a $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) -classpath .:../ex22a $(SRC)


#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

