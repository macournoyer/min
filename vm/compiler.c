#include <stdio.h>
#include <string.h>
#include "min.h"
#include "compiler.h"

#define PUSH_OP(op)  kv_push(MinOpCode, code->opcodes, (MinOpCode)(op))

struct MinCode *min_compiler(const char *filename) {
  struct MinCode *code = MIN_ALLOC(struct MinCode);
  kv_init(code->opcodes);
  kv_init(code->literals);
  code->filename = MIN_ALLOC_N(char, strlen(filename));
  strcpy(code->filename, filename);
  
  return code;
}

void min_compiler_finish(CODE) {
  PUSH_OP(MIN_OP_RETURN);
}

void min_compiler_dump(CODE) {
  size_t i;
  OBJ lit;
  printf("# bytecode for '%s'\n", code->filename);
  printf("# literals:\n");
  for(i = 0; i < kv_size(code->literals); ++i) {
    lit = kv_A(code->literals, i);
    printf("[%d] %s\n", (int)i, MIN_STR_PTR(lit));
  }
  printf("# opcodes:\n");
  for(i = 0; i < kv_size(code->opcodes); ++i) {
    switch(kv_A(code->opcodes, i)) {
      case MIN_OP_SELF:        printf("SELF\n"); break;
      case MIN_OP_LITERAL:     printf("LITERAL    %d\n", kv_A(code->opcodes, ++i)); break;
      case MIN_OP_SEND:        printf("SEND       %d\n", kv_A(code->opcodes, ++i)); break;
      case MIN_OP_SELF_SEND:   printf("SELF_SEND  %d\n", kv_A(code->opcodes, ++i)); break;
      case MIN_OP_SUPER_SEND:  printf("SUPER_SEND %d\n", kv_A(code->opcodes, ++i)); break;
      case MIN_OP_RETURN:      printf("RETURN\n"); break;
      case MIN_OP_INDEX_EXT:   printf("INDEX_EXT  %d\n", kv_A(code->opcodes, ++i)); break;
    }
  }
}

static int min_add_lit(CODE, OBJ lit) {
  /* TODO reuse */
  kv_push(OBJ, code->literals, lit);
  return kv_size(code->literals)-1; /* TODO split if too large */
}

void min_compile_lit(CODE, OBJ lit) {
  PUSH_OP(MIN_OP_LITERAL);
  PUSH_OP(min_add_lit(code, lit));
}

void min_compile_call(CODE, OBJ msg) {
  PUSH_OP(MIN_OP_SEND);
  PUSH_OP(min_add_lit(code, msg));
}

void min_compile_return(CODE) {
  PUSH_OP(MIN_OP_RETURN);
}
