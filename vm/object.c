#include "min.h"
#include "object.h"

static OBJ min_closure_new(MIN_, MinCMethod method, OBJ data) {
  struct MinClosure *c = MIN_ALLOC(struct MinClosure);
  c->vtable = MIN_VT_FOR(CLOSURE);
  c->method = method;
  c->data   = data;
  return (OBJ)c;
}

OBJ min_vtable_allocate(MIN) {
  struct MinObject *obj = MIN_ALLOC(struct MinObject);
  obj->vtable = self;
  return MIN_NIL;
}

OBJ min_vtable_delegated(MIN) {
  struct MinVTable *child = MIN_ALLOC(struct MinVTable);
  child->vtable = (OBJ) self ? MIN_VTABLE(self)->vtable : 0;
  child->parent = self;
  child->kh     = kh_init(OBJ);
  return (OBJ)child;
}

OBJ min_vtable_add_cmethod(MIN, OBJ name, MinCMethod method) {
  OBJ clos = min_closure_new(vm, method, 0);
  int ret;
  kh_OBJ_t *h = MIN_VTABLE(self)->kh;
  khiter_t k  = kh_put(OBJ, h, name, &ret);
  if (!ret) kh_del(OBJ, h, k);
  kh_value(h, k) = clos;
  return clos;
}

OBJ min_vtable_lookup(MIN, OBJ name) {
  int ret;
  kh_OBJ_t *h = MIN_VTABLE(self)->kh;
  khiter_t k = kh_put(OBJ, h, name, &ret);
  if (ret) return MIN_NIL;
  return kh_value(h, k);
}

