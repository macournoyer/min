#include "min.h"

OBJ MinClosure(LOBBY, MinMethod method, OBJ data) {
  struct MinClosure *c = MIN_ALLOC(struct MinClosure);
  c->vtable = MIN_VT_FOR(Closure);
  c->type   = MIN_T_Closure;
  c->method = method;
  c->data   = data ? data : (OBJ)c;
  return (OBJ)c;
}

OBJ MinClosure_eval(MIN) {
  /* TODO handle args */
  /* min_send2(closure, "set_slot", MIN_STR("call"), MinCall(lobby)); */
  return MinMessage_eval_on(lobby, 0, MIN_CLOSURE(closure)->data, lobby->lobby, lobby->lobby);
}

void MinClosure_init(LOBBY) {
  MIN_CREATE_TYPE(Closure);
}