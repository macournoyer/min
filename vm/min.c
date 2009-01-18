#include "min.h"

int main (int argc, char const *argv[]) {
  struct MinVM *vm = MinVM();
  
  /* Lobby inspect println */
  min_send2(min_send2(vm->lobby, "inspect"), "println");
  
  MinVM_destroy(vm);
  return 0;
}