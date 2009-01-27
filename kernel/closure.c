#include "min.h"

OBJ MinClosure(LOBBY, MinMethod method, OBJ data) {
  struct MinClosure *c = MIN_ALLOC(struct MinClosure);
  c->vtable = MIN_VT_FOR(Closure);
  c->type   = MIN_T_Closure;
  c->method = method;
  c->data   = data ? data : (OBJ)c;
  c->self   = 0;
  return (OBJ)c;
}

OBJ MinClosure_eval(MIN) {
  /* TODO handle args */
  struct MinClosure *c = MIN_CLOSURE(closure);
  OBJ context = c->self ? c->self : self;
  return MinMessage_eval_on(lobby, 0, c->data, context, context);
}

OBJ MinClosure_bind(MIN, OBJ _receiver) {
  MIN_CLOSURE(closure)->self = MIN_EVAL_ARG(_receiver);
  return self;
}

void MinClosure_init(LOBBY) {
  OBJ vt = MIN_CREATE_TYPE(Closure);
  min_add_method(vt, "bind", MinClosure_bind);
}