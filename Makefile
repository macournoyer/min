SRC = vm/table.c vm/grammar.c vm/scanner.c vm/string.c vm/vm.c vm/min.c
OBJ = ${SRC:.c=.o}
OBJ_MIN = vm/min.o

CC = gcc
CFLAGS = -Wall -fno-strict-aliasing
INCS = -Ivm
LEMON = tools/lemon
LIBS = -lm
RAGEL = ragel

all: min

.c.o:
	${CC} -c ${CFLAGS} ${INCS} -o $@ $<

min: ${OBJ_MIN} ${OBJ}
	${CC} ${CFLAGS} ${OBJ_POTION} ${OBJ} ${LIBS} -o min

vm/scanner.c: vm/scanner.rl
	${RAGEL} vm/scanner.rl -C -o $@

vm/grammar.c: tools/lemon vm/grammar.y
	${LEMON} vm/grammar.y

tools/lemon: tools/lemon.c
	${CC} -o tools/lemon tools/lemon.c

clean:
	rm -f vm/*.o vm/scanner.c vm/grammar.{c,h,out}
