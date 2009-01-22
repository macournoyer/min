%include {
#include <assert.h>
#include <string.h>
#include "min.h"
}

%name           MinParser
%token_type     { OBJ }
%token_prefix   MIN_TOK_
%extra_argument { struct MinParseState *state }

%token_destructor {
  (void) state;
}

%syntax_error {
  printf("Syntax error:\n");
  printf("  %s unexpected at line %d\n", yyTokenName[yymajor], state->curline);
}

/* rules */
root ::= messages(A). { state->message = A; }

messages(A) ::= message(B). { state->message = A = B; }
messages(A) ::= messages(B) message(C). {
  A = B;
  if (state->message) {
    MIN_MESSAGE(state->message)->next = C;
    MIN_MESSAGE(C)->previous = state->message;
  }
  state->message = C;
}
messages ::= messages error message.

message(A) ::= STRING(B). { A = MinMessage(state->vm, B, 0, B); }
message(A) ::= SYMBOL(B). { A = MinMessage(state->vm, B, 0, 0); }
message(A) ::= SYMBOL(B) O_PAR arguments(C) C_PAR. { A = MinMessage(state->vm, B, C, 0); }

arguments(A) ::= message(B). { A = MinArray(state->vm); MIN_ARRAY_PUSH(A, B); }
arguments(A) ::= arguments(B) COMMA message(C). { A = B; MIN_ARRAY_PUSH(A, C); }
arguments(A) ::= . { A = MinArray(state->vm); }
