#include <stdio.h>
#include <assert.h>
#include "min.h"
#include "object.h"
#include "opcode.h"

OBJ MIN_lookup;

#define NEXT_OP        (*++ip)
#define LITERAL        literals[NEXT_OP]
#define STACK_POP      stack[sp--]
#define STACK_PUSH(v)  (assert(sp < MIN_MAX_STACK), stack[++sp] = (v))

OBJ min_run(VM, struct MinCode *code) {
  MinOpCode *ip;
  MinOpCode *end = code->opcodes + code->len;
  OBJ *literals = code->literals;
  struct MinFrame *frame = VM_FRAME;
  OBJ *stack = frame->stack;
  size_t sp = frame->sp;
  
  for(ip = code->opcodes; ip < end; NEXT_OP) {
    switch(*ip) {
      case MIN_OP_SELF:
        STACK_PUSH(frame->self);
        break;
      case MIN_OP_LITERAL:
        STACK_PUSH(LITERAL);
        break;
      case MIN_OP_SEND:
        /* TODO pass args */
        STACK_PUSH(min_send(STACK_POP, LITERAL));
        break;
      case MIN_OP_SELFSEND:
        STACK_PUSH(min_send(frame->self, LITERAL));
        break;
      default:
        fprintf(stderr, "Unknown opcode: %d\n", (int)*ip);
    }
  }
  
  return stack[sp];
}

struct MinVM *min_create() {
  struct MinVM *vm = MIN_ALLOC(struct MinVM);
  OBJ vtable_vt, object_vt;
  
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
  
  vm->lobby = min_vtable_allocate(vm, 0, object_vt);
  vm->cf = 0;
  VM_FRAME->self = vm->lobby;
  
  /* some often used symbols */
  MIN_lookup = MIN_STR("lookup");
  
  /* objects boot, this is where core methods are added */
  min_object_init(vm);
  min_str_init(vm);
  
  /* DEBUG sending: Lobby inspect println */
  min_send2(vm->lobby, "set_slot", MIN_STR("inspect"), MIN_STR("Lobby"));
  min_send2(min_send2(vm->lobby, "inspect"), "println");
  
  return vm;
}

void min_destroy(struct MinVM *vm) {
  free(vm);
}
