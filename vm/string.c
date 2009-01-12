#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "min.h"

OBJ min_str(const char *str, size_t len) {
  struct MinString *s = MIN_ALLOC(struct MinString);
  s->ptr = MIN_ALLOC_N(char, len);
  MIN_MEMCPY_N(s->ptr, str, char, len);
  s->ptr[len] = '\0';
  s->len = len;
  return (OBJ)s;
}

OBJ min_str_print(OBJ str) {
  printf("%s", MIN_STR_PTR(str));
  return MIN_NIL;
}
