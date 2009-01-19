/* object model based on "Open, extensible object models"
   by Ian Piumarta <ian@vpri.org> */
#include <stdio.h>
#include "min.h"

/* closure */

static OBJ MinClosure(VM, MinCMethod method) {
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

OBJ MinVTable_add_cmethod(MIN, OBJ name, MinCMethod method) {
  return MinVTable_add_closure(vm, closure, self, name, MinClosure(vm, method));
}

OBJ MinVTable_lookup(MIN, OBJ name) {
  struct MinVTable *vt = MIN_VTABLE(self);
  kh_OBJ_t *h = MIN_VTABLE(self)->kh;
  khiter_t k = kh_get(OBJ, h, name);
  if (k != kh_end(h)) return kh_value(h, k);
  if (vt->parent) return min_send(vt->parent, MIN_lookup, name);
  return MIN_NIL;
}

/* message sending */

OBJ min_bind(VM, OBJ receiver, OBJ msg) {
  OBJ vt = MIN_VT(receiver);
  OBJ clos = (msg == MIN_lookup && MIN_IS_TYPE(receiver, VTable))
    ? MinVTable_lookup(vm, 0, vt, msg)
    : min_send(vt, MIN_lookup, msg);
  if (!clos) fprintf(stderr, "Slot not found: %s", MIN_STR_PTR(msg));
  return clos;
}

/* object */
/* TODO all can be rewritten in Min at some point */
OBJ min_getter(MIN) {
  return MIN_CLOSURE(closure)->data;
}

OBJ MinObject_get_slot(MIN, OBJ name) {
  return MIN_CLOSURE(min_bind(vm, self, name))->data;
}

OBJ MinObject_set_slot(MIN, OBJ name, OBJ value) {
  if (MIN_IS_TYPE(value, Closure)) {
    MinVTable_add_closure(vm, closure, self, name, value);
  } else {
    OBJ cl = MinVTable_add_cmethod(vm, 0, MIN_VT(self), name, (MinCMethod)min_getter);
    MIN_CLOSURE(cl)->data = value;
  }
  return value;
}

OBJ MinObject_inspect(MIN) {
  char str[20];
  sprintf(str, "#<Object:%p>", (void*)self);
  return MinString2(vm, str);
}

void MinObject_init(VM) {
  OBJ vt = MIN_VT_FOR(Object);
  min_def(vt, "inspect", MinObject_inspect);
  min_def(vt, "get_slot", MinObject_get_slot);
  min_def(vt, "set_slot", MinObject_set_slot);
  MIN_REGISTER_TYPE(Object, vt);
}
