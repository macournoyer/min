#include "min.h"
#include "object.h"

struct MinVM *min_create() {
  struct MinVM *vm = MIN_ALLOC(struct MinVM);
  OBJ vtable_vt, object_vt;
  
  vtable_vt = MIN_VT_FOR(VTABLE) = min_vtable_delegated(vm, 0, 0);
  MIN_VT(vtable_vt) = vtable_vt;
  
  object_vt = MIN_VT_FOR(OBJECT) = min_vtable_delegated(vm, 0, 0);
  MIN_VT(object_vt) = vtable_vt;
  MIN_VTABLE(vtable_vt)->parent = object_vt;
  
  MIN_VT_FOR(STRING) = min_vtable_delegated(vm, 0, object_vt);
  MIN_VT_FOR(CLOSURE) = min_vtable_delegated(vm, 0, object_vt);
  
  min_vtable_add_cmethod(vm, 0, vtable_vt, min_str2(vm, "lookup"), (MinCMethod) min_vtable_lookup);
  min_vtable_add_cmethod(vm, 0, vtable_vt, min_str2(vm, "allocate"), (MinCMethod) min_vtable_allocate);
  min_vtable_add_cmethod(vm, 0, vtable_vt, min_str2(vm, "delegated"), (MinCMethod) min_vtable_delegated);
  
  return vm;
}

void min_destroy(struct MinVM *vm) {
  free(vm);
}
