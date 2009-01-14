#ifndef _OPCODE_H_
#define _OPCODE_H_

/* Opcodes, inspired/copied from Self:
   "An Efficient Implementation of SELF, a Dynamically-Typed Object-Oriented
   Language Based on Prototypes" by CHAMBERS, UNGAR, AND LEE. */

enum {
  MIN_OP_SELF = 0,
  /* push self onto the execution stack */
    
  MIN_OP_LITERAL, /* <value index> */
  /* push a literal value onto the execution stack */
    
  MIN_OP_SEND, /* <message name index> */
  /* send a message, popping the receiver and arguments off the execution
     stack and pushing the result */
  
  MIN_OP_SELFSEND, /* <message name index> */
  /* send a message to self, popping the arguments off the execution stack 
     and pushing the result */
  
  MIN_OP_SUPERSEND, /* <message name index> */
  /* send a message to self, delegated to all parents, popping the 
     arguments off the execution stack and pushing the result */
};

#endif /* _OPCODE_H_ */
