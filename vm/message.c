#include <stdio.h>
#include "min.h"

OBJ MinMessage(VM, OBJ name, OBJ arguments, OBJ value) {
  struct MinMessage *m = MIN_ALLOC(struct MinMessage);
  m->vtable    = MIN_VT_FOR(Message);
  m->type      = MIN_T_Message;
  m->name      = name;
  m->arguments = arguments ? arguments : MinArray(vm);
  m->previous  = MIN_NIL;
  m->next      = MIN_NIL;
  m->value     = value;
  return (OBJ)m;
}

OBJ MinMessage_inspect(MIN) {
  struct MinMessage *m = MIN_MESSAGE(self);
  if (m->next) {
    return MinString_concat(vm, 0,
      MinString_concat(vm, 0,
        m->name,
        MIN_STR(" ")
      ),
      MinMessage_inspect(vm, 0, m->next)
    );
  } else {
    return m->name;
  }
}

OBJ MinMessage_eval_on(MIN, OBJ receiver) {
  struct MinMessage *m = MIN_MESSAGE(self);
  OBJ ret;
  if (m->value)
    ret = m->value;
  else
    ret = min_send(receiver, m->name);
  if (m->next)
    return MinMessage_eval_on(vm, 0, m->next, ret);
  return ret;
}

void MinMessage_init(VM) {
  OBJ vt = MIN_CREATE_TYPE(Message);
  min_def(vt, "inspect", MinMessage_inspect);
}