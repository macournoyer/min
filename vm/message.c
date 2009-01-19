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

void MinMessage_init(VM) {
  OBJ vt = MIN_VT_FOR(MESSAGE) = MinVTable_delegated(vm, 0, MIN_VT_FOR(OBJECT));
}