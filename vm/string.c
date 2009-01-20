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

OBJ MinString(VM, char *str, size_t len) {
  char c = str[len];
  str[len] = '\0';
  OBJ s = MinString2(vm, str);
  str[len] = c;
  return s;
}

OBJ MinString2(VM, const char *str) {
  OBJ id = MinString_lookup(vm, str);
  
  if (!id) {
    size_t len = strlen(str);
    struct MinString *s = MIN_ALLOC(struct MinString);
    s->vtable   = MIN_VT_FOR(String);
    s->type     = MIN_T_String;
    s->ptr      = MIN_ALLOC_N(char, len+1);
    MIN_MEMCPY_N(s->ptr, str, char, len);
    s->ptr[len] = '\0';
    s->len      = len;
    
    id = (OBJ)s;
    MinString_add(vm, s->ptr, id);
  }
  return id;
}

OBJ MinString_print(MIN) {
  printf("%s", MIN_STR_PTR(self));
  return MIN_NIL;
}

OBJ MinString_println(MIN) {
  printf("%s\n", MIN_STR_PTR(self));
  return MIN_NIL;
}

OBJ MinString_inspect(MIN) {
  return self;
}

OBJ MinString_concat(MIN, OBJ other) {
  struct MinString *s = (struct MinString *) self;
  struct MinString *o = (struct MinString *) other;
  size_t len = s->len + o->len;
  char *str = MIN_ALLOC_N(char, len+1);
  MIN_MEMCPY_N(str, s->ptr, char, s->len);
  MIN_MEMCPY_N(str + s->len, o->ptr, char, o->len);
  return MinString(vm, str, len);
}

void MinStringTable_init(VM) {
  struct MinStrTable *tbl = MIN_ALLOC(struct MinStrTable);
  tbl->kh = kh_init(str);
  vm->strings = (OBJ) tbl;
}

void MinString_init(VM) {
  OBJ vt = MIN_VT_FOR(String);
  MIN_REGISTER_TYPE(String, vt);
  min_def(vt, "print", MinString_print);
  min_def(vt, "println", MinString_println);
  min_def(vt, "inspect", MinString_inspect);
  min_def(vt, "+", MinString_concat);
}
