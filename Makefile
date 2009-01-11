SRC = vm/ast.c vm/parser.c vm/tokenizer.c vm/vm.c vm/min.c
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

vm/tokenizer.c: vm/tokenizer.rl
	${RAGEL} vm/tokenizer.rl -C -o $@

vm/parser.c: tools/lemon vm/parser.y
	${LEMON} vm/parser.y

tools/lemon: tools/lemon.c
	${CC} -o tools/lemon tools/lemon.c

clean:
	rm -f vm/*.o vm/tokenizer.c vm/parser.c
