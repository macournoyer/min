#include <stdio.h>
#include "min.h"
#include "khash.h"
#include "kvec.h"

OBJ min_table() {
  struct MinTable *t = MIN_ALLOC(struct MinTable);
  kv_init(t->vec);
  return (OBJ)t;
}

OBJ min_table_push(OBJ self, OBJ item) {
  kv_push(OBJ, MIN_TABLE(self)->vec, item);
  return item;
}

OBJ min_table_print(OBJ self) {
  struct MinTable *t = MIN_TABLE(self);
  size_t i, size = kv_size(t->vec);
  char *str;
  
  printf("(");
  for(i = 0; i < size; ++i) {
    /* HACK, just str for now */
    str = MIN_STR_PTR(kv_A(t->vec, i));
    printf("%s, ", str);
  }
  printf(")");
  return MIN_NIL;
}
