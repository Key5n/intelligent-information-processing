test:
	@for i in *; do \
		if [ -d $$i -a -f $$i/Makefile ]; then \
			make -C $$i test; \
		fi; \
	done

$(CLASS): $(SRC)
	$(JAVAC) $(SRC)

clean:
	@for i in * ; do \
		if [ -d $$i -a -f $$i/Makefile ]; then \
			make  -C $$i clean; \
		fi; \
	done