.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	=  $(wildcard ../ex22a/ex4a/*.java) $(wildcard ex4d/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex4d/Game


test: $(CLASS)
	$(JAVA) -classpath .:../ex22a $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) -classpath .:../ex22a $(SRC)


#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

