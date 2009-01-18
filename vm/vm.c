#include <stdio.h>
#include "min.h"
#include "object.h"

OBJ MIN_lookup;

struct MinVM *min_create() {
  struct MinVM *vm = MIN_ALLOC(struct MinVM);
  OBJ vtable_vt, object_vt;
  
  /* bootstrap the object model */
  vtable_vt = MIN_VT_FOR(VTABLE) = min_vtable_delegated(vm, 0, 0);
  MIN_VT(vtable_vt) = vtable_vt;
  
  object_vt = MIN_VT_FOR(OBJECT) = min_vtable_delegated(vm, 0, 0);
  MIN_VT(object_vt) = vtable_vt;
  MIN_VTABLE(vtable_vt)->parent = object_vt;
  
  MIN_VT_FOR(STRING) = min_vtable_delegated(vm, 0, object_vt);
  MIN_VT_FOR(CLOSURE) = min_vtable_delegated(vm, 0, object_vt);
  
  min_str_table_init(vm);
  
  min_def(vtable_vt, "lookup", min_vtable_lookup);
  min_def(vtable_vt, "allocate", min_vtable_allocate);
  min_def(vtable_vt, "delegated", min_vtable_delegated);
  
  /* init VM */
  vm->lobby = min_vtable_allocate(vm, 0, object_vt);
  
  /* cache some often used symbols */
  MIN_lookup = MIN_STR("lookup");
  
  /* objects boot, this is where core methods are added */
  min_object_init(vm);
  min_str_init(vm);
  
  /* Lobby init */
  min_send2(vm->lobby, "set_slot", MIN_STR("inspect"), MIN_STR("Lobby"));
  
  return vm;
}

void min_destroy(struct MinVM *vm) {
  free(vm);
}
