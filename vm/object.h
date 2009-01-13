#ifndef _OBJECT_H_
#define _OBJECT_H_

#include "min.h"
#include "khash.h"

KHASH_MAP_INIT_INT(OBJ, OBJ)

typedef struct OBJ (*MinCMethod)(MIN, ...);

struct MinVTable {
  MIN_OBJ_HEADER;
  OBJ parent;
  kh_OBJ_t *kh;
};

struct MinObject {
  MIN_OBJ_HEADER;
};

struct MinClosure {
  MIN_OBJ_HEADER;
  MinCMethod method;
  OBJ data;
};

OBJ min_vtable_delegated(MIN);
OBJ min_vtable_allocate(MIN);
OBJ min_vtable_lookup(MIN, OBJ name);
OBJ min_vtable_add_cmethod(MIN, OBJ name, MinCMethod method);

#endif /* _OBJECT_H_ */
