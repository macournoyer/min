#include "min.h"
#include "object.h"
#include "compiler.h"

int main (int argc, char const *argv[]) {
  struct MinVM *vm = min_create();

  struct MinCode *code = min_compile(vm, "\"ohaie\" println", "min.c");
  /* min_compiler_dump(code); */
  min_run(vm, code);
  
  min_destroy(vm);
  return 0;
}