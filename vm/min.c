#include "min.h"

int main (int argc, char const *argv[]) {
  struct MinVM *vm = MinVM();
  
  /* OBJ msg = min_parse(vm, "String type println.\"ohaie\" println", "min.c"); */
  OBJ msg = min_parse(vm, "\"(%s)\" sprintf(\"oye!\") println", "min.c");
  /* MinObject_dump(vm, 0, msg); */
  MinMessage_eval_on(vm, 0, msg, vm->lobby, vm->lobby);
  /* min_send2(min_send2(msg, "inspect"), "println"); */
  
  MinVM_destroy(vm);
  return 0;
}