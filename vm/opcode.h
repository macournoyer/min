#ifndef _OPCODE_H_
#define _OPCODE_H_

/* Opcodes, inspired/copied from Self:
   "An Efficient Implementation of SELF, a Dynamically-Typed Object-Oriented
   Language Based on Prototypes" by CHAMBERS, UNGAR, AND LEE.

   NOTE: if you add an opcode, make sure you add it to the threaded labels down
   there too. */

enum {
  MIN_OP_SELF = 0,
  /* push self onto the execution stack */
  
  MIN_OP_LITERAL, /* <value index> */
  /* push a literal value onto the execution stack */
    
  MIN_OP_SEND, /* <message name index> */
  /* send a message, popping the receiver and arguments off the execution
     stack and pushing the result */
  
  MIN_OP_SELF_SEND, /* <message name index> */
  /* send a message to self, popping the arguments off the execution stack 
     and pushing the result */
  
  MIN_OP_SUPER_SEND, /* <message name index> */
  /* send a message to self, delegated to all parents, popping the 
     arguments off the execution stack and pushing the result */
  
  MIN_OP_RETURN,
  /* returns the first element on the execution stack */
  
  MIN_OP_INDEX_EXT, /* <index extension> */
  /* extend the next index by prepending the index extension */
};

/* Direct threaded dispatch labels */
#define MIN_OP_LABELS \
  &&op_SELF, \
  &&op_LITERAL, \
  &&op_SEND, \
  &&op_SELF_SEND, \
  &&op_SUPER_SEND, \
  &&op_RETURN, \
  &&op_INDEX_EXT

#endif /* _OPCODE_H_ */
