#include "min.h"

OBJ MinArray(VM) {
  struct MinArray *a = MIN_ALLOC(struct MinArray);
  a->vtable = MIN_VT_FOR(ARRAY);
  a->type   = MIN_T_ARRAY;
  kv_init(a->kv);
  return (OBJ)a;
}

void MinArray_init(VM) {
  MIN_VT_FOR(ARRAY) = MinVTable_delegated(vm, 0, MIN_VT_FOR(OBJECT));
}