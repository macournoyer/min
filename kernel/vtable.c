/* object model based on "Open, extensible object models"
   by Ian Piumarta <ian@vpri.org> */
#include <stdio.h>
#include <assert.h>
#include "min.h"

/* closure */

static OBJ MinClosure(LOBBY, MinMethod method) {
  struct MinClosure *c = MIN_ALLOC(struct MinClosure);
  c->vtable = MIN_VT_FOR(Closure);
  c->type   = MIN_T_Closure;
  c->method = method;
  c->data   = (OBJ)c;
  return (OBJ)c;
}

/* vtable */

OBJ MinVTable_allocate(MIN) {
  struct MinObject *obj = MIN_ALLOC(struct MinObject);
  obj->vtable = self;
  obj->type   = MIN_T_Object;
  return (OBJ)obj;
}

OBJ MinVTable_delegated(MIN) {
  struct MinVTable *child = MIN_ALLOC(struct MinVTable);
  child->vtable = (OBJ) self ? MIN_VTABLE(self)->vtable : 0;
  child->type   = MIN_T_VTable;
  child->parent = self;
  child->kh     = kh_init(OBJ);
  return (OBJ)child;
}

OBJ MinVTable_add_closure(MIN, OBJ name, OBJ clos) {
  int ret;
  kh_OBJ_t *h = MIN_VTABLE(self)->kh;
  khiter_t k  = kh_put(OBJ, h, name, &ret);
  /* TODO if (!ret) free closure */
  kh_value(h, k) = clos;
  return clos;
}

OBJ MinVTable_add_method(MIN, OBJ name, MinMethod method) {
  return MinVTable_add_closure(lobby, closure, self, name, MinClosure(lobby, method));
}

OBJ MinVTable_lookup(MIN, OBJ _name) {
  OBJ name = MIN_EVAL_ARG(_name);
  struct MinVTable *vt = MIN_VTABLE(self);
  kh_OBJ_t *h = MIN_VTABLE(self)->kh;
  khiter_t k = kh_get(OBJ, h, name);
  if (k != kh_end(h)) return kh_value(h, k);
  if (vt->parent) return min_send(vt->parent, lobby->String_lookup, name);
  return MIN_NIL;
}

OBJ MinVTable_dump(MIN) {
  struct MinVTable *vt = MIN_VTABLE(self);
  khiter_t k;
  
  printf("vtable:   %p\n", vt);
  for (k = kh_begin(vt->kh); k != kh_end(vt->kh); ++k)
    if (kh_exist(vt->kh, k))
      printf("        - %s => %p\n", MIN_STR_PTR(kh_key(vt->kh, k)), (void*)kh_val(vt->kh, k));
  if (vt->parent) {
    printf("parent:\n");
    MinVTable_dump(lobby, 0, vt->parent);
  }
  return self;
}

void MinVTable_init(LOBBY) {
  OBJ vt = MIN_VT_FOR(VTable);
  min_add_method(vt, "lookup", MinVTable_lookup);
  min_add_method(vt, "allocate", MinVTable_allocate);
  min_add_method(vt, "delegated", MinVTable_delegated);  
}
