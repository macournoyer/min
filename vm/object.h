#ifndef _OBJECT_H_
#define _OBJECT_H_

#include <assert.h>
#include "min.h"
#include "khash.h"

#define min_send(RCV, MSG, ARGS...) ({  \
    OBJ r = (OBJ)(RCV);  \
    struct MinClosure *c = (struct MinClosure *) min_bind(vm, r, (MSG));  \
    assert(c && "failed to bind to a closure"); \
    c->method(vm, (OBJ)c, r, ##ARGS);  \
  })

#define min_def(VT, MSG, FUNC) \
  min_vtable_add_cmethod(vm, 0, (VT), min_str2(vm, (MSG)), (MinCMethod)(FUNC));

KHASH_MAP_INIT_INT(OBJ, OBJ)

typedef OBJ (*MinCMethod)(MIN, ...);

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

OBJ min_bind(MIN_, OBJ receiver, OBJ msg);

void min_object_init(MIN_);

#endif /* _OBJECT_H_ */
