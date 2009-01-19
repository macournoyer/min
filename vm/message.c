#include "min.h"

OBJ MinMessage(VM, OBJ name, OBJ arguments, OBJ value) {
  struct MinMessage *m = MIN_ALLOC(struct MinMessage);
  m->vtable    = MIN_VT_FOR(MESSAGE);
  m->type      = MIN_T_MESSAGE;
  m->name      = name;
  m->arguments = arguments ? arguments : MinArray(vm);
  m->previous  = MIN_NIL;
  m->next      = MIN_NIL;
  m->value     = value;
  return (OBJ)m;
}

OBJ MinMessage_inspect(MIN) {
  return MinString_concat(vm, 0,
    MinString_concat(vm, 0,
      MIN_MESSAGE(self)->name,
      MIN_STR(" ")
    ),
    MIN_MESSAGE(MIN_MESSAGE(self)->next)->name
  );
}

OBJ MinMessage_eval_on(MIN, OBJ receiver) {
  struct MinMessage *m = MIN_MESSAGE(self);
  OBJ ret;
  if (m->value)
    ret = m->value;
  else
    ret = min_send(receiver, m->name);
  if (m->next)
    return MinMessage_eval_on(vm, closure, m->next, ret);
  return ret;
}

void MinMessage_init(VM) {
  OBJ vt = MIN_VT_FOR(MESSAGE) = MinVTable_delegated(vm, 0, MIN_VT_FOR(OBJECT));
  min_def(vt, "inspect", MinMessage_inspect);
}