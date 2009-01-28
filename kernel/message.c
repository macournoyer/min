#include <stdio.h>
#include <assert.h>
#include "min.h"

OBJ MinMessage(LOBBY, OBJ name, OBJ arguments, OBJ value) {
  struct MinMessage *m = MIN_ALLOC(struct MinMessage);
  m->vtable    = MIN_VT_FOR(Message);
  m->type      = MIN_T_Message;
  m->name      = name;
  m->arguments = arguments;
  m->previous  = MIN_NIL;
  m->next      = MIN_NIL;
  m->value     = value;
  return (OBJ)m;
}

OBJ MinMessage_inspect_args(MIN) {
  struct MinMessage *m = MIN_MESSAGE(self);
  size_t i;
  OBJ str = MinMessage_inspect(lobby, 0, MIN_ARRAY_AT(m->arguments, 0));
  for (i = 1; i < MIN_ARRAY_SIZE(m->arguments); ++i)
    str = min_sprintf(lobby, "%s, %s",
                      MIN_STR_PTR(str),
                      MIN_STR_PTR(MinMessage_inspect(lobby, 0, MIN_ARRAY_AT(m->arguments, i))));
  return str;
}

OBJ MinMessage_inspect(MIN) {
  struct MinMessage *m = MIN_MESSAGE(self);
  if (m->next) {
    if (m->arguments)
      return min_sprintf(lobby, "%s(%s) %s",
                         MIN_STR_PTR(m->name),
                         MIN_STR_PTR(MinMessage_inspect_args(lobby, 0, self)),
                         MIN_STR_PTR(MinMessage_inspect(lobby, 0, m->next)));
      
    return min_sprintf(lobby, "%s %s",
                       MIN_STR_PTR(m->name),
                       MIN_STR_PTR(MinMessage_inspect(lobby, 0, m->next)));
  } else {
    return m->name;
  }
}

OBJ MinMessage_eval_on(MIN, OBJ context, OBJ receiver) {
  struct MinMessage *m = MIN_MESSAGE(self);
  OBJ ret;
  
  if (m->value) {
    ret = m->value; /* cached literal */
  } else {
    if (m->arguments) {
      switch (MIN_ARRAY_SIZE(m->arguments)) {
        case 0: ret = min_send(receiver, m->name); break;
        case 1: ret = min_send(receiver, m->name, MIN_ARRAY_AT(m->arguments, 0)); break;
        case 2: ret = min_send(receiver, m->name, MIN_ARRAY_AT(m->arguments, 0), MIN_ARRAY_AT(m->arguments, 1)); break;
        case 3: ret = min_send(receiver, m->name, MIN_ARRAY_AT(m->arguments, 0), MIN_ARRAY_AT(m->arguments, 1), MIN_ARRAY_AT(m->arguments, 2)); break;
        case 4: ret = min_send(receiver, m->name, MIN_ARRAY_AT(m->arguments, 0), MIN_ARRAY_AT(m->arguments, 1), MIN_ARRAY_AT(m->arguments, 2), MIN_ARRAY_AT(m->arguments, 3)); break;
        default: assert(0 && "too many arguments");
      }
    } else {
      ret = min_send(receiver, m->name);
    }
  }
  
  if (m->next) {
    struct MinMessage *next = MIN_MESSAGE(m->next);
    while (next->name == lobby->String_newline || next->name == lobby->String_dot) {
      if (!next->next) return ret;
      ret = context;
      next = MIN_MESSAGE(next->next);
    }
    return MinMessage_eval_on(lobby, 0, (OBJ)next, context, ret);
  }
  
  return ret;
}

void MinMessage_init(LOBBY) {
  OBJ vt = MIN_CREATE_TYPE(Message);
  min_add_method(vt, "inspect", MinMessage_inspect);
}