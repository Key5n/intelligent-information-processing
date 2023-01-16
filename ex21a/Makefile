.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex3a/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)

all: $(CLASS)

test: $(CLASS)
	$(JAVA) ex3a/MinMaxSearch
	$(JAVA) ex3a/AlphaBetaSearch

$(CLASS): $(SRC)
	$(JAVAC) $(SRC)

#$(CLASS): $(SRC)

#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

