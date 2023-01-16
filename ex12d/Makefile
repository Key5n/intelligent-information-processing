.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex2d/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex2d/Main

all: $(CLASS)

test: $(CLASS)
	$(JAVA) $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) $(SRC)

#$(CLASS): $(SRC)

#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

