#include <stdio.h>
#include "min.h"

OBJ MIN_lookup;

struct MinVM *MinVM() {
  struct MinVM *vm = MIN_ALLOC(struct MinVM);
  OBJ vtable_vt, object_vt;
  
  /* bootstrap the object model */
  vtable_vt = MIN_VT_FOR(VTABLE) = MinVTable_delegated(vm, 0, 0);
  MIN_VT(vtable_vt) = vtable_vt;
  
  object_vt = MIN_VT_FOR(OBJECT) = MinVTable_delegated(vm, 0, 0);
  MIN_VT(object_vt) = vtable_vt;
  MIN_VTABLE(vtable_vt)->parent = object_vt;
  
  MIN_VT_FOR(STRING) = MinVTable_delegated(vm, 0, object_vt);
  MIN_VT_FOR(CLOSURE) = MinVTable_delegated(vm, 0, object_vt);
  
  MinStringTable_init(vm);
  
  min_def(vtable_vt, "lookup", MinVTable_lookup);
  min_def(vtable_vt, "allocate", MinVTable_allocate);
  min_def(vtable_vt, "delegated", MinVTable_delegated);
  
  /* init VM */
  vm->lobby = MinVTable_allocate(vm, 0, object_vt);
  
  /* cache some often used symbols */
  MIN_lookup = MIN_STR("lookup");
  
  /* objects boot, this is where core methods are added */
  MinObject_init(vm);
  MinString_init(vm);
  
  /* Lobby init */
  min_send2(vm->lobby, "set_slot", MIN_STR("inspect"), MIN_STR("Lobby"));
  
  return vm;
}

void MinVM_destroy(struct MinVM *vm) {
  free(vm);
}
