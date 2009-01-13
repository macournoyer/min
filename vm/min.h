#ifndef _MIN_H_
#define _MIN_H_

#include <stdlib.h>
#include "kvec.h"

#define MIN_ALLOC(T)          (T *)malloc(sizeof(T))
#define MIN_ALLOC_N(T,N)      (T *)malloc(sizeof(T)*(N))

#define MIN_MEMZERO(X,T)      memset((X), 0, sizeof(T))
#define MIN_MEMZERO_N(X,T,N)  memset((X), 0, sizeof(T)*(N))
#define MIN_MEMCPY(X,Y,T)     memcpy((X), (Y), sizeof(T))
#define MIN_MEMCPY_N(X,Y,T,N) memcpy((X), (Y), sizeof(T)*(N))

#define MIN_STR_PTR(x)        ((struct MinString *)(x))->ptr
#define MIN_STR_LEN(x)        ((struct MinString *)(x))->len
#define MIN_TABLE(x)          ((struct MinTable *)(x))
#define MIN_VTABLE(x)         ((struct MinVTable *)(x))
#define MIN_OBJ(x)            ((struct MinObject *)(x))
#define MIN_VT(x)             (MIN_VTABLE(x)->vtable)
#define MIN_VT_FOR(T)         (vm->vtables[MIN_T_##T])
#define MIN_IS_TYPE(x,T)      (MIN_OBJ(x)->type == MIN_T_##T)

#define MIN_NIL               ((OBJ)0)
#define MIN_SHIFT             8

#define MIN                   struct MinVM *vm, OBJ closure, OBJ self
#define MIN_                  struct MinVM *vm
#define MIN_OBJ_HEADER        OBJ vtable; int type

typedef unsigned long OBJ;

enum MIN_T {
  MIN_T_VTABLE, MIN_T_OBJECT, MIN_T_CLOSURE, MIN_T_STRING,
  MIN_T_MAX
};

struct MinString {
  MIN_OBJ_HEADER;
  size_t len;
  char  *ptr;
};

struct MinTable {
  MIN_OBJ_HEADER;
  kvec_t(OBJ) vec;
};

struct MinVM {
  OBJ lobby;
  OBJ vtables[MIN_T_MAX];
  OBJ strings;
};

extern OBJ MIN_lookup;

/* vm */
struct MinVM *min_create();
void min_destroy(struct MinVM *vm);

/* string */
OBJ min_str(MIN_, const char *str, size_t len);
OBJ min_str2(MIN_, const char *str);
void min_str_table_init(MIN_);
void min_str_init(MIN_);

/* table */
OBJ min_table();
OBJ min_table_push(OBJ self, OBJ item);
OBJ min_table_print(OBJ self);

/* parsing */
void min_parse(char *code);

/* lemon stuff */
void *MinParserAlloc(void *(*)(size_t));
void MinParser(void *, int, OBJ);
void MinParserFree(void *, void (*)(void*));

#endif /* _MIN_H_ */
