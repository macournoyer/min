#include "min.h"

OBJ MinFixnum(LOBBY, int value) {
  struct MinFixnum *n = MIN_ALLOC(struct MinFixnum);
  n->vtable = MIN_VT_FOR(Fixnum);
  n->type   = MIN_T_Fixnum;
  n->value  = value;
  return (OBJ)n;
}

OBJ MinFixnum_inspect(MIN) {
  return min_sprintf(lobby, "%d", MIN_FIXNUM(self)->value);
}

OBJ MinFixnum_add(MIN, OBJ _other) {
  OBJ other = MIN_EVAL_ARG(_other);
  /* TODO optimize possible not to convert self to Object ? */
  return INT2FIX(MIN_FIXNUM(self)->value + FIX2INT(other));
}

void MinFixnum_init(LOBBY) {
  OBJ vt = MIN_CREATE_TYPE(Fixnum);
  min_add_method(vt, "inspect", MinFixnum_inspect);
  min_add_method(vt, "+", MinFixnum_add);
}
