#ifndef _MIN_H_
#define _MIN_H_

#include <stdlib.h>
#include <limits.h>
#include "kvec.h"
#include "khash.h"

#define MIN_ALLOC(T)          (T *)malloc(sizeof(T))
#define MIN_ALLOC_N(T,N)      (T *)malloc(sizeof(T)*(N))

#define MIN_MEMZERO(X,T)      memset((X), 0, sizeof(T))
#define MIN_MEMZERO_N(X,T,N)  memset((X), 0, sizeof(T)*(N))
#define MIN_MEMCPY(X,Y,T)     memcpy((X), (Y), sizeof(T))
#define MIN_MEMCPY_N(X,Y,T,N) memcpy((X), (Y), sizeof(T)*(N))

#define MIN_STR(x)            MinString2(vm, (x))
#define MIN_STR_PTR(x)        ((struct MinString *)(x))->ptr
#define MIN_STR_LEN(x)        ((struct MinString *)(x))->len
#define MIN_ARRAY_PUSH(x,i)   kv_push(OBJ, ((struct MinArray *)(x))->kv, (i))
#define MIN_ARRAY_AT(x,i)     kv_A(((struct MinArray *)(x))->kv, (i))
#define MIN_VTABLE(x)         ((struct MinVTable *)(x))
#define MIN_CLOSURE(x)        ((struct MinClosure *)(x))
#define MIN_MESSAGE(x)        ((struct MinMessage *)(x))
#define MIN_OBJ(x)            ((struct MinObject *)(x))
#define MIN_VT(x)             (MIN_VTABLE(x)->vtable)
#define MIN_VT_FOR(T)         (vm->vtables[MIN_T_##T])
#define MIN_IS_TYPE(x,T)      (MIN_OBJ(x)->type == MIN_T_##T)

#define MIN_NIL               ((OBJ)0)
#define MIN_SHIFT             8
#define MIN_NUM_FLAG          0x01

#define INT2NUM(i)            (OBJ)((i) << TR_SHIFT | MIN_NUM_FLAG)
#define NUM2INT(i)            (int)((i) >> TR_SHIFT)

#define MIN                   struct MinVM *vm, OBJ closure, OBJ self
#define VM                    struct MinVM *vm
#define MIN_OBJ_HEADER        OBJ vtable; int type

#define min_send(RCV, MSG, ARGS...) ({  \
    OBJ r = (OBJ)(RCV);  \
    struct MinClosure *c = (struct MinClosure *) min_bind(vm, r, (MSG));  \
    c->method(vm, (OBJ)c, r, ##ARGS);  \
  })
#define min_send2(RCV, MSG, ARGS...) min_send((RCV), MinString2(vm, (MSG)), ##ARGS)

#define min_def(VT, MSG, FUNC) \
  MinVTable_add_cmethod(vm, 0, (VT), MinString2(vm, (MSG)), (MinCMethod)(FUNC));

typedef unsigned long OBJ;

KHASH_MAP_INIT_INT(OBJ, OBJ)

enum MIN_T {
  MIN_T_OBJECT, MIN_T_VTABLE, MIN_T_MESSAGE, MIN_T_CLOSURE, MIN_T_STRING, MIN_T_ARRAY,
  MIN_T_MAX /* keep last */
};

struct MinVM {
  OBJ lobby;
  OBJ vtables[MIN_T_MAX];
  OBJ strings;
  size_t cf; /* current frame */
};

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

struct MinString {
  MIN_OBJ_HEADER;
  size_t len;
  char  *ptr;
};

struct MinMessage {
  MIN_OBJ_HEADER;
  OBJ name;
  OBJ arguments;
  OBJ next;
  OBJ previous;
};

struct MinArray {
  MIN_OBJ_HEADER;
  kvec_t(OBJ) kv;
};

extern OBJ MIN_lookup;

/* vm */
struct MinVM *MinVM();
void MinVM_destroy(VM);

/* message */
OBJ MinMessage(VM, OBJ name, OBJ arguments, OBJ previous);
void MinMessage_init(VM);

/* vtable */
OBJ MinVTable_delegated(MIN);
OBJ MinVTable_allocate(MIN);
OBJ MinVTable_lookup(MIN, OBJ name);
OBJ MinVTable_add_closure(MIN, OBJ name, OBJ clos);
OBJ MinVTable_add_cmethod(MIN, OBJ name, MinCMethod method);

/* object */
OBJ min_bind(VM, OBJ receiver, OBJ msg);
OBJ min_inspect(MIN);
void MinObject_init(VM);

/* string */
OBJ MinString(VM, const char *str, size_t len);
OBJ MinString2(VM, const char *str);
void MinStringTable_init(VM);
void MinString_init(VM);

/* array */
OBJ MinArray(VM);
void MinArray_init(VM);

/* lemon */
void min_parse(VM, const char *string, const char *filename);
void *MinParserAlloc(void *(*)(size_t));
void MinParser(void *, int, OBJ, VM);
void MinParserFree(void *, void (*)(void*));

#endif /* _MIN_H_ */
