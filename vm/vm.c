#include <stdio.h>
#include <assert.h>
#include "min.h"
#include "object.h"
#include "opcode.h"

OBJ MIN_lookup;

#define POP_OP         (*++ip)
#define LITERAL        literals[ind + POP_OP]
#define STACK_POP      kv_pop(frame->stack)
#define STACK_PUSH(v)  kv_push(OBJ, frame->stack, v)
#define RESET_IND      (ind = 0)

#ifdef MIN_THREADED_DISPATCH
#define OPCODES        goto *labels[*ip]
#define END_OPCODES    
#define OP(name)       op_##name
#define DISPATCH       RESET_IND; goto *labels[POP_OP]
#else
#define OPCODES        for(;;) { switch(*ip) {
#define END_OPCODES    default: printf("unknown opcode: %d\n", (int)*ip); }}
#define OP(name)       case MIN_OP_##name
#define DISPATCH       RESET_IND; POP_OP; break
#endif

OBJ min_run(VM, struct MinCode *code) {
  MinOpCode *ip = &kv_A(code->opcodes, 0);
  MinOpCode ind = 0;
  OBJ *literals = &kv_A(code->literals, 0);
  struct MinFrame *frame = VM_FRAME;
  
#ifdef MIN_THREADED_DISPATCH
  static void *labels[] = { MIN_OP_LABELS };
#endif
  
  OPCODES;
    OP(SELF):        STACK_PUSH(frame->self); DISPATCH;
    OP(LITERAL):     STACK_PUSH(LITERAL); DISPATCH;
    OP(SEND):        STACK_PUSH(min_send(STACK_POP, LITERAL)); /* TODO pass args */ DISPATCH;
    OP(SELF_SEND):   STACK_PUSH(min_send(frame->self, LITERAL)); DISPATCH;
    OP(SUPER_SEND):  assert(0 && "unimplemented"); DISPATCH;
    OP(RETURN):      return STACK_POP;
    OP(INDEX_EXT):   ind += POP_OP; DISPATCH;
  END_OPCODES;
}

struct MinVM *min_create() {
  struct MinVM *vm = MIN_ALLOC(struct MinVM);
  OBJ vtable_vt, object_vt;
  
  /* bootstrap the object model */
  vtable_vt = MIN_VT_FOR(VTABLE) = min_vtable_delegated(vm, 0, 0);
  MIN_VT(vtable_vt) = vtable_vt;
  
  object_vt = MIN_VT_FOR(OBJECT) = min_vtable_delegated(vm, 0, 0);
  MIN_VT(object_vt) = vtable_vt;
  MIN_VTABLE(vtable_vt)->parent = object_vt;
  
  MIN_VT_FOR(STRING) = min_vtable_delegated(vm, 0, object_vt);
  MIN_VT_FOR(CLOSURE) = min_vtable_delegated(vm, 0, object_vt);
  
  min_str_table_init(vm);
  
  min_def(vtable_vt, "lookup", min_vtable_lookup);
  min_def(vtable_vt, "allocate", min_vtable_allocate);
  min_def(vtable_vt, "delegated", min_vtable_delegated);
  
  /* init VM */
  vm->lobby = min_vtable_allocate(vm, 0, object_vt);
  vm->cf = 0;
  VM_FRAME->self = vm->lobby;
  kv_init(VM_FRAME->stack);
  
  /* cache some often used symbols */
  MIN_lookup = MIN_STR("lookup");
  
  /* objects boot, this is where core methods are added */
  min_object_init(vm);
  min_str_init(vm);
  
  /* Lobby init */
  min_send2(vm->lobby, "set_slot", MIN_STR("inspect"), MIN_STR("Lobby"));
  
  return vm;
}

void min_destroy(struct MinVM *vm) {
  free(vm);
}
