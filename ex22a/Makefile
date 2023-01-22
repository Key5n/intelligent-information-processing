.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex4a/*.java) 
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex4a/Game


test: $(CLASS)
	$(JAVA)  $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) $(SRC)


#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

