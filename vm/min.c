#include "min.h"

int main (int argc, char const *argv[]) {
  struct MinVM *vm = MinVM();
  
  OBJ msg = min_parse(vm, "\"ohaie\" println", "min.c");
  /* OBJ msg = min_parse(vm, "Lobby inspect println", "min.c"); */
  MinMessage_eval_on(vm, 0, msg, vm->lobby);
  /* min_send2(min_send2(msg, "inspect"), "println"); */
  
  MinVM_destroy(vm);
  return 0;
}