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

OBJ MinVTable_lookup(MIN, OBJ name) {
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

/* message sending */

OBJ min_bind(LOBBY, OBJ receiver, OBJ msg) {
  OBJ vt = MIN_VT(receiver);
  OBJ clos = (msg == lobby->String_lookup && MIN_IS_TYPE(receiver, VTable))
    ? MinVTable_lookup(lobby, 0, vt, msg)
    : min_send(vt, lobby->String_lookup, msg);
  if (!clos) {
    fprintf(stderr, "Slot not found '%s' (%p) in VTable %p\n", MIN_STR_PTR(msg), (void*)msg, (void*)vt);
    assert(0);
  }
  return clos;
}

/* object */
/* TODO all can be rewritten in Min at some point */
OBJ min_getter(MIN) {
  return MIN_CLOSURE(closure)->data;
}

OBJ MinObject_get_slot(MIN, OBJ name) {
  return MIN_CLOSURE(min_bind(lobby, self, name))->data;
}

OBJ MinObject_set_slot(MIN, OBJ name, OBJ value) {
  if (MIN_IS_TYPE(value, Closure)) {
    MinVTable_add_closure(lobby, 0, self, name, value);
  } else {
    OBJ cl = MinVTable_add_method(lobby, 0, MIN_VT(self), name, (MinMethod)min_getter);
    MIN_CLOSURE(cl)->data = value;
  }
  return value;
}

OBJ MinObject_dump(MIN) {
  struct MinObject *o = MIN_OBJ(self);
  static const char *type_names[] = { "Object", "VTable", "Message", "Closure", "String", "Array" };
  printf("address:  %p\n", o);
  printf("type:     %s\n", type_names[o->type]);
  MinVTable_dump(lobby, 0, MIN_VT(self));
  return self;
}

OBJ MinObject_inspect(MIN) {
  char str[20];
  sprintf(str, "#<Object:%p>", (void*)self);
  return MinString2(lobby, str);
}

OBJ MinObject_println(MIN) {
  min_send2(min_send2(self, "inspect"), "println");
  return MIN_NIL;
}

void MinObject_init(LOBBY) {
  OBJ vt = MIN_VT_FOR(Object);
  min_add_method(vt, "inspect", MinObject_inspect);
  min_add_method(vt, "println", MinObject_println);
  min_add_method(vt, "get_slot", MinObject_get_slot);
  min_add_method(vt, "set_slot", MinObject_set_slot);
  MIN_REGISTER_TYPE(Object, vt);
}
