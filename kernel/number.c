#include "min.h"

OBJ MinFixnum(LOBBY, int v) {
  struct MinNumber *n = MIN_ALLOC(struct MinNumber);
  n->vtable = MIN_VT_FOR(Fixnum);
  n->type   = MIN_T_Fixnum;
  n->v.fix  = v;
  return (OBJ)n;
}

OBJ MinFixnum_inspect(MIN) {
  return min_sprintf(lobby, "%d", MIN_NUMBER(self)->v.fix);
}

void MinNumber_init(LOBBY) {
  OBJ vt = MIN_CREATE_TYPE(Fixnum);
  min_add_method(vt, "inspect", MinFixnum_inspect);
}
