#include <stdio.h>
#include <assert.h>
#include "min.h"

/* object */
/* TODO all can be rewritten in Min at some point */
OBJ min_getter(MIN) {
  return MIN_CLOSURE(closure)->data;
}

OBJ MinObject_get_slot(MIN, OBJ _name) {
  OBJ name = MIN_EVAL_ARG(_name);
  return MIN_CLOSURE(min_bind(lobby, self, name))->data;
}

OBJ MinObject_set_slot(MIN, OBJ _name, OBJ _value) {
  OBJ name = MIN_EVAL_ARG(_name);
  OBJ value = MIN_EVAL_ARG(_value);
  if (MIN_IS_TYPE(value, Closure)) {
    MinVTable_add_closure(lobby, 0, self, name, value);
  } else {
    OBJ cl = MinVTable_add_method(lobby, 0, MIN_VT(self), name, (MinMethod)min_getter);
    MIN_CLOSURE(cl)->data = value;
  }
  return value;
}

OBJ MinObject_assign(MIN, OBJ name, OBJ value) {
  return MinObject_set_slot(lobby, closure, self, MIN_MESSAGE(name)->name, value);
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
  return min_sprintf(lobby, "#<Object:%p>", (void*)self);
}

OBJ MinObject_println(MIN) {
  min_send2(min_send2(self, "inspect"), "println");
  return MIN_NIL;
}

/* message sending */

OBJ min_bind(LOBBY, OBJ receiver, OBJ msg) {
  OBJ vt = MIN_VT(receiver);
  OBJ clos = (msg == lobby->String_lookup && MIN_IS_TYPE(receiver, VTable))
    ? MinVTable_lookup(lobby, 0, vt, msg)
    : min_send(vt, lobby->String_lookup, msg);
  if (!clos) {
    fprintf(stderr, "Slot not found '%s' in:\n", MIN_STR_PTR(msg));
    MinObject_dump(lobby, 0, receiver);
    assert(0);
  }
  return clos;
}

void MinObject_init(LOBBY) {
  OBJ vt = MIN_VT_FOR(Object);
  min_add_method(vt, "inspect", MinObject_inspect);
  min_add_method(vt, "println", MinObject_println);
  min_add_method(vt, "get_slot", MinObject_get_slot);
  min_add_method(vt, "set_slot", MinObject_set_slot);
  min_add_method(vt, "=", MinObject_assign);
  min_add_method(vt, "dump", MinObject_dump);
  MIN_REGISTER_TYPE(Object, vt);
}
