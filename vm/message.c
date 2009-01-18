#include "min.h"

OBJ MinMessage(VM, OBJ name, OBJ arguments, OBJ previous) {
  struct MinMessage *m = MIN_ALLOC(struct MinMessage);
  m->vtable      = MIN_VT_FOR(MESSAGE);
  m->type        = MIN_T_MESSAGE;
  m->name        = name;
  m->arguments   = arguments;
  m->previous    = previous;
  m->next        = MIN_NIL;
  MIN_MESSAGE(previous)->next = (OBJ)m;
  return (OBJ)m;
}

void MinMessage_init(VM) {
  MIN_VT_FOR(MESSAGE) = MinVTable_delegated(vm, 0, MIN_VT_FOR(OBJECT));
}