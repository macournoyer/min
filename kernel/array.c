#include <stdarg.h>
#include "min.h"

OBJ MinArray(LOBBY) {
  struct MinArray *a = MIN_ALLOC(struct MinArray);
  a->vtable = MIN_VT_FOR(Array);
  a->type   = MIN_T_Array;
  kv_init(a->kv);
  return (OBJ)a;
}

OBJ MinArray2(LOBBY, int argc, ...) {
  OBJ a = MinArray(lobby);
  va_list argp;
  size_t  i;
  va_start(argp, argc);
  for (i = 0; i < argc; ++i)
    MIN_ARRAY_PUSH(a, va_arg(argp, OBJ));
  va_end(argp);
  return a;
}

void MinArray_init(LOBBY) {
  MIN_CREATE_TYPE(Array);
}