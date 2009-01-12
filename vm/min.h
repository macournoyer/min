#include <stdlib.h>
#include "kvec.h"

#define MIN_ALLOC(T)          (T *)malloc(sizeof(T))
#define MIN_ALLOC_N(T,N)      (T *)malloc(sizeof(T)*(N))

#define MIN_MEMZERO(X,T)      memset((X), 0, sizeof(T))
#define MIN_MEMZERO_N(X,T,N)  memset((X), 0, sizeof(T)*(N))
#define MIN_MEMCPY(X,Y,T)     memcpy((X), (Y), sizeof(T))
#define MIN_MEMCPY_N(X,Y,T,N) memcpy((X), (Y), sizeof(T)*(N))

#define MIN_STR_PTR(x) ((struct MinString *)(x))->ptr
#define MIN_STR_LEN(x) ((struct MinString *)(x))->len
#define MIN_TABLE(x)   ((struct MinTable *)(x))

typedef unsigned long OBJ;

#define MIN_NIL  ((OBJ)0)

struct MinString {
  size_t len;
  char  *ptr;
};

struct MinTable {
  kvec_t(OBJ) vec;
};

/* string */
OBJ min_str(const char *str, size_t len);
OBJ min_str_print(OBJ str);

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
