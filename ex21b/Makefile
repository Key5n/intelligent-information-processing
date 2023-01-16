.SUFFIXES: .java .class

JAVAC	= javac
JAVA	= java

SRC	= $(wildcard ex3b/*.java)
CLASS	= $(SRC:.java=.class)
PROGRAM	= $(shell pwd)
MAIN	= ex3b/Game


test: $(CLASS)
	$(JAVA) $(MAIN)

$(CLASS): $(SRC)
	$(JAVAC) $(SRC)

#$(CLASS): $(SRC)

#.java.class:
#	$(JAVAC) $<

clean:;	\rm -f **/*.class

