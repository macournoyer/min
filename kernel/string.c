#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include "min.h"
#include "khash.h"

KHASH_MAP_INIT_STR(str, OBJ);

struct MinStrTable {
  kh_str_t *kh;
};

OBJ MinString_lookup(LOBBY, const char *str) {
  struct MinStrTable *t = (struct MinStrTable *) lobby->strings;
  khiter_t k = kh_get(str, t->kh, str);
  if (k != kh_end(t->kh)) return kh_value(t->kh, k);
  return MIN_NIL;
}

void MinString_add(LOBBY, const char *str, OBJ id) {
  int ret;
  struct MinStrTable *t = (struct MinStrTable *) lobby->strings;
  khiter_t k = kh_put(str, t->kh, str, &ret);
  if (!ret) kh_del(str, t->kh, k);
  kh_value(t->kh, k) = id;
}

OBJ MinString(LOBBY, char *str, size_t len) {
  char c = str[len];
  str[len] = '\0';
  OBJ s = MinString2(lobby, str);
  str[len] = c;
  return s;
}

OBJ MinString2(LOBBY, const char *str) {
  OBJ id = MinString_lookup(lobby, str);
  
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
    MinString_add(lobby, s->ptr, id);
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

OBJ min_sprintf(LOBBY, const char *fmt, ...) {
  va_list arg;
  va_start(arg, fmt);
  int len = vsnprintf(NULL, 0, fmt, arg);
  char *ptr = MIN_ALLOC_N(char, len);
  va_end(arg);
  va_start(arg, fmt);
  vsprintf(ptr, fmt, arg);
  va_end(arg);
  /* TODO do not allocate twice */
  OBJ str = MinString(lobby, ptr, len);
  free(ptr);
  return str;
}

OBJ MinString_sprintf(MIN, OBJ arg) {
  OBJ str = MinMessage_eval_on(lobby, 0, MIN_ARRAY_AT(arg, 0), self, self);
  return min_sprintf(lobby, MIN_STR_PTR(self), MIN_STR_PTR(str));
}

OBJ MinString_inspect(MIN) {
  return self;
}

void MinStringTable_init(LOBBY) {
  struct MinStrTable *tbl = MIN_ALLOC(struct MinStrTable);
  tbl->kh = kh_init(str);
  lobby->strings = (OBJ) tbl;
}

void MinString_init(LOBBY) {
  OBJ vt = MIN_VT_FOR(String);
  MIN_REGISTER_TYPE(String, vt);
  min_def(vt, "print", MinString_print);
  min_def(vt, "println", MinString_println);
  min_def(vt, "inspect", MinString_inspect);
  min_def(vt, "sprintf", MinString_sprintf);
}
