SRC = kernel/vtable.c kernel/object.c kernel/message.c kernel/string.c kernel/number.c kernel/array.c kernel/lobby.c kernel/grammar.c kernel/scanner.c kernel/min.c
OBJ = ${SRC:.c=.o}
OBJ_MIN = kernel/min.o

CC = gcc
CFLAGS = -Wall -fno-strict-aliasing -DDEBUG -g -O2
INCS = -Ikernel
LEMON = tools/lemon
LIBS = -lm
RAGEL = ragel

all: min

.c.o:
	${CC} -c ${CFLAGS} ${INCS} -o $@ $<

min: ${OBJ_MIN} ${OBJ}
	${CC} ${CFLAGS} ${OBJ_POTION} ${OBJ} ${LIBS} -o min

kernel/scanner.c: kernel/scanner.rl
	${RAGEL} kernel/scanner.rl -C -o $@

kernel/grammar.c: tools/lemon kernel/grammar.y
	${LEMON} kernel/grammar.y

tools/lemon: tools/lemon.c
	${CC} -o tools/lemon tools/lemon.c

clean:
	rm -f kernel/*.o kernel/scanner.c kernel/grammar.{c,h,out}

sloc: clean
	@cp kernel/scanner.rl kernel/scanner.rl.c
	sloccount kernel
	@rm kernel/scanner.rl.c

size: clean
	@ruby -e 'puts "%0.2fK" % (Dir["kernel/**.{c,rb,h}"].inject(0) {|s,f| s += File.size(f)} / 1024.0)'

rebuild: clean min

.PHONY: all clean sloc size rebuild
