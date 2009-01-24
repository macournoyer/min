#include <stdio.h>
#include "min.h"

OBJ MinMessage(LOBBY, OBJ name, OBJ value) {
  struct MinMessage *m = MIN_ALLOC(struct MinMessage);
  m->vtable    = MIN_VT_FOR(Message);
  m->type      = MIN_T_Message;
  m->name      = name;
  m->arguments = MIN_NIL;
  m->previous  = MIN_NIL;
  m->next      = MIN_NIL;
  m->value     = value;
  return (OBJ)m;
}

OBJ MinMessage_inspect(MIN) {
  struct MinMessage *m = MIN_MESSAGE(self);
  if (m->next) {
    return min_sprintf(lobby, "%s %s",
                       MIN_STR_PTR(m->name),
                       MIN_STR_PTR(MinMessage_inspect(lobby, 0, m->next)));
  } else {
    return m->name;
  }
}

OBJ MinMessage_eval_on(MIN, OBJ context, OBJ receiver) {
  struct MinMessage *m = MIN_MESSAGE(self);
  
  /* TERM token, skip & drop receiver */
  if (m->name == lobby->String_newline || m->name == lobby->String_dot)
    return MinMessage_eval_on(lobby, 0, m->next, context, context);
  
  OBJ ret;
  if (m->value) {
    ret = m->value; /* cached literal */
  } else {
    if (m->arguments) {
      ret = min_send(receiver, m->name, m->arguments);
    } else {
      ret = min_send(receiver, m->name);
    }
  }
  
  if (m->next)
    return MinMessage_eval_on(lobby, 0, m->next, context, ret);
  
  return ret;
}

void MinMessage_init(LOBBY) {
  OBJ vt = MIN_CREATE_TYPE(Message);
  min_def(vt, "inspect", MinMessage_inspect);
}