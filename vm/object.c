#include <stdio.h>
#include "min.h"
#include "object.h"

static OBJ min_closure_new(MIN_, MinCMethod method, OBJ data) {
  struct MinClosure *c = MIN_ALLOC(struct MinClosure);
  c->vtable = MIN_VT_FOR(CLOSURE);
  c->type   = MIN_T_CLOSURE;
  c->method = method;
  c->data   = data;
  return (OBJ)c;
}

/* vtable */

OBJ min_vtable_allocate(MIN) {
  struct MinObject *obj = MIN_ALLOC(struct MinObject);
  obj->vtable = self;
  obj->type   = MIN_T_OBJECT;
  return (OBJ)obj;
}

OBJ min_vtable_delegated(MIN) {
  struct MinVTable *child = MIN_ALLOC(struct MinVTable);
  child->vtable = (OBJ) self ? MIN_VTABLE(self)->vtable : 0;
  child->type   = MIN_T_VTABLE;
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
  kh_OBJ_t *h = MIN_VTABLE(self)->kh;
  khiter_t k = kh_get(OBJ, h, name);
  if (k != kh_end(h)) return kh_value(h, k);
  return MIN_NIL;
}

/* message sending */

OBJ min_bind(MIN_, OBJ receiver, OBJ msg) {
  OBJ vt = MIN_VT(receiver);
  OBJ clos = (msg == MIN_lookup && MIN_IS_TYPE(receiver, VTABLE))
    ? min_vtable_lookup(vm, 0, vt, msg)
    : min_send(vt, MIN_lookup, msg);
  return clos;
}

/* object */

OBJ min_object_inspect(MIN) {
  return min_str2(vm, "#<Object>");
}

void min_object_init(MIN_) {
  OBJ vt = MIN_VT_FOR(OBJECT);
  min_def(vt, "inspect", min_object_inspect);
}
