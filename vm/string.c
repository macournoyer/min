#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "min.h"
#include "khash.h"

KHASH_MAP_INIT_STR(str, OBJ);

struct MinStrTable {
  kh_str_t *kh;
};

OBJ MinString_lookup(VM, const char *str) {
  struct MinStrTable *t = (struct MinStrTable *) vm->strings;
  khiter_t k = kh_get(str, t->kh, str);
  if (k != kh_end(t->kh)) return kh_value(t->kh, k);
  return MIN_NIL;
}

void MinString_add(VM, const char *str, OBJ id) {
  int ret;
  struct MinStrTable *t = (struct MinStrTable *) vm->strings;
  khiter_t k = kh_put(str, t->kh, str, &ret);
  if (!ret) kh_del(str, t->kh, k);
  kh_value(t->kh, k) = id;
}

OBJ MinString(VM, const char *str, size_t len) {
  OBJ id = MinString_lookup(vm, str);
  if (!id) {
    struct MinString *s = MIN_ALLOC(struct MinString);
    s->vtable   = MIN_VT_FOR(STRING);
    s->type     = MIN_T_STRING;
    s->ptr      = MIN_ALLOC_N(char, len);
    MIN_MEMCPY_N(s->ptr, str, char, len);
    s->ptr[len] = '\0';
    s->len      = len;
    
    id = (OBJ)s;
    MinString_add(vm, s->ptr, id);
  }
  return id;
}

OBJ MinString2(VM, const char *str) {
  return MinString(vm, str, strlen(str));
}

OBJ MinString_print(MIN) {
  printf("%s", MIN_STR_PTR(self));
  return MIN_NIL;
}

OBJ MinString_println(MIN) {
  printf("%s\n", MIN_STR_PTR(self));
  return MIN_NIL;
}

void MinStringTable_init(VM) {
  struct MinStrTable *tbl = MIN_ALLOC(struct MinStrTable);
  tbl->kh = kh_init(str);
  vm->strings = (OBJ) tbl;
}

void MinString_init(VM) {
  OBJ vt = MIN_VT_FOR(STRING);
  min_def(vt, "print", MinString_print);
  min_def(vt, "println", MinString_println);
}
