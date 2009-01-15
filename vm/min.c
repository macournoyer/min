#include "min.h"
#include "object.h"
#include "compiler.h"

/* void test_indent() {
  min_parse("ohaie(1, \"me\"):\n  @deep\n  # hi\n    down\n  1\n\n");
} */

int main (int argc, char const *argv[]) {
  struct MinVM *vm = min_create();

  /* min_parse("print(1)\nohaie"); */
  
  struct MinCode *code = min_compiler("test");

  min_compile_lit(code, MIN_STR("ohaie"));
  min_compile_call(code, MIN_STR("println"));
  
  min_compiler_finish(code);
  min_compiler_dump(code);
  min_run(vm, code);
  
  min_destroy(vm);
  return 0;
}