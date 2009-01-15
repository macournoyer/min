#include "min.h"
#include "object.h"

/* void test_indent() {
  min_parse("ohaie(1, \"me\"):\n  @deep\n  # hi\n    down\n  1\n\n");
} */

int main (int argc, char const *argv[]) {
  struct MinVM *vm = min_create();

  /* min_parse("print(1)\nohaie"); */
  
  struct MinCode code;
  /*
  LITERAL 0
  SEND 1
  RETURN
  */
  kv_init(code.opcodes);
  kv_push(MinOpCode, code.opcodes, 1);
  kv_push(MinOpCode, code.opcodes, 0);
  kv_push(MinOpCode, code.opcodes, 2);
  kv_push(MinOpCode, code.opcodes, 1);
  kv_push(MinOpCode, code.opcodes, 5);
  
  kv_init(code.literals);
  kv_push(OBJ, code.literals, MIN_STR("ohaie"));
  kv_push(OBJ, code.literals, MIN_STR("println"));
  
  code.filename = "test";
  min_run(vm, &code);
  
  min_destroy(vm);
  return 0;
}