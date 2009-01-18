#include "min.h"

int main (int argc, char const *argv[]) {
  struct MinVM *vm = min_create();
  
  /* Lobby inspect println */
  min_send2(min_send2(vm->lobby, "inspect"), "println");
  
  min_destroy(vm);
  return 0;
}