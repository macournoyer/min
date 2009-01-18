#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "min.h"
#include "khash.h"

KHASH_MAP_INIT_STR(str, OBJ);

struct MinStrTable {
  kh_str_t *kh;
};

OBJ min_str_lookup(VM, const char *str) {
  struct MinStrTable *t = (struct MinStrTable *) vm->strings;
  khiter_t k = kh_get(str, t->kh, str);
  if (k != kh_end(t->kh)) return kh_value(t->kh, k);
  return MIN_NIL;
}

void min_str_add(VM, const char *str, OBJ id) {
  int ret;
  struct MinStrTable *t = (struct MinStrTable *) vm->strings;
  khiter_t k = kh_put(str, t->kh, str, &ret);
  if (!ret) kh_del(str, t->kh, k);
  kh_value(t->kh, k) = id;
}

OBJ min_str(VM, const char *str, size_t len) {
  OBJ id = min_str_lookup(vm, str);
  if (!id) {
    struct MinString *s = MIN_ALLOC(struct MinString);
    s->vtable   = MIN_VT_FOR(STRING);
    s->type     = MIN_T_STRING;
    s->ptr      = MIN_ALLOC_N(char, len);
    MIN_MEMCPY_N(s->ptr, str, char, len);
    s->ptr[len] = '\0';
    s->len      = len;
    
    id = (OBJ)s;
    min_str_add(vm, s->ptr, id);
  }
  return id;
}

OBJ min_str2(VM, const char *str) {
  return min_str(vm, str, strlen(str));
}

OBJ min_str_print(MIN) {
  printf("%s", MIN_STR_PTR(self));
  return MIN_NIL;
}

OBJ min_str_println(MIN) {
  printf("%s\n", MIN_STR_PTR(self));
  return MIN_NIL;
}

void min_str_table_init(VM) {
  struct MinStrTable *tbl = MIN_ALLOC(struct MinStrTable);
  tbl->kh = kh_init(str);
  vm->strings = (OBJ) tbl;
}

void min_str_init(VM) {
  OBJ vt = MIN_VT_FOR(STRING);
  min_def(vt, "print", min_str_print);
  min_def(vt, "println", min_str_println);
}
