#include "min.h"

int main (int argc, char const *argv[]) {
  struct MinVM *vm = MinVM();
  
  /* Lobby inspect println */
  min_send2(min_send2(vm->lobby, "inspect"), "println");
  
  OBJ msg = min_parse(vm, "\"ohaie\" println", "min.c");
  min_send2(MIN_MESSAGE(MIN_MESSAGE(msg)->next)->name, "println");
  
  MinVM_destroy(vm);
  return 0;
}