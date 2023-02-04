.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ../ex4b/*.java) $(wildcard ../ex22a/ex4a/*.java) $(wildcard ex4e/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex4e/Game


test: $(CLASS)
	$(JAVA) -classpath .:../ex22a:../ex22b $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) -classpath .:../ex22a:../ex22b $(SRC)


#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

