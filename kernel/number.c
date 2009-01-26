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

void MinFixnum_init(LOBBY) {
  OBJ vt = MIN_CREATE_TYPE(Fixnum);
  min_add_method(vt, "inspect", MinFixnum_inspect);
}
