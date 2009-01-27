#include "min.h"

OBJ MinClosure(LOBBY, MinMethod method, OBJ data) {
  struct MinClosure *c = MIN_ALLOC(struct MinClosure);
  c->vtable = MIN_VT_FOR(Closure);
  c->type   = MIN_T_Closure;
  c->method = method;
  c->data   = data ? data : (OBJ)c;
  return (OBJ)c;
}

void MinClosure_init(LOBBY) {
  MIN_CREATE_TYPE(Closure);
}