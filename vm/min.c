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
  */
  code.opcodes = (MinOpCode *) "\1\0\2\1";
  code.len = 6;
  code.literals = MIN_ALLOC_N(OBJ, 2);
  code.literals[0] = MIN_STR("ohaie");
  code.literals[1] = MIN_STR("println");
  code.filename = "test";
  min_run(vm, &code);
  
  min_destroy(vm);
  return 0;
}