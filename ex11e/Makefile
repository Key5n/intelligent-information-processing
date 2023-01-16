.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex1e/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex1e/MisCanProblem

all: $(CLASS)

test: $(CLASS)
	$(JAVA) $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) $(SRC)

#$(CLASS): $(SRC)

#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

