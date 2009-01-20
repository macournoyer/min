#include <stdio.h>
#include "min.h"

OBJ MIN_lookup;

struct MinVM *MinVM() {
  struct MinVM *vm = MIN_ALLOC(struct MinVM);
  OBJ vtable_vt, object_vt;
  
  /* bootstrap the object model */
  vtable_vt = MIN_VT_FOR(VTable) = MinVTable_delegated(vm, 0, 0);
  MIN_VT(vtable_vt) = vtable_vt;
  
  object_vt = MIN_VT_FOR(Object) = MinVTable_delegated(vm, 0, 0);
  MIN_VT(object_vt) = vtable_vt;
  MIN_VTABLE(vtable_vt)->parent = object_vt;
  
  MIN_VT_FOR(String) = MinVTable_delegated(vm, 0, object_vt);
  MIN_VT_FOR(Closure) = MinVTable_delegated(vm, 0, object_vt);
  
  MinStringTable_init(vm);
  
  /* cache some often used symbols */
  MIN_lookup = MIN_STR("lookup");
  
  /* init VM */
  vm->lobby = MinVTable_allocate(vm, 0, MinVTable_delegated(vm, 0, object_vt));
  
  /* register core object into the lobby */
  min_def(vtable_vt, "lookup", MinVTable_lookup);
  min_def(vtable_vt, "allocate", MinVTable_allocate);
  min_def(vtable_vt, "delegated", MinVTable_delegated);
  MinObject_init(vm);
  MIN_REGISTER_TYPE(VTable, vtable_vt);
  /* Lobby init */
  min_send2(vm->lobby, "set_slot", MIN_STR("inspect"), MIN_STR("Lobby"));
  min_send2(vm->lobby, "set_slot", MIN_STR("type"), MIN_STR("Lobby"));
  min_send2(vm->lobby, "set_slot", MIN_STR("Lobby"), vm->lobby);
  
  /* this is where core objects are initialized */
  MinString_init(vm);
  MinMessage_init(vm);
  MinArray_init(vm);
  
  return vm;
}

void MinVM_destroy(struct MinVM *vm) {
  free(vm);
}
