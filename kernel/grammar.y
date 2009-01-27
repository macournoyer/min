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

message(A) ::= STRING(B). { A = B; }
message(A) ::= INT(B). { A = B; }
message(A) ::= SYMBOL(B). { A = B; }
message(A) ::= SYMBOL(B) O_PAR arguments(C) C_PAR. { MIN_MESSAGE(A = B)->arguments = C; }
message(A) ::= O_SQ_BRA arguments(C) C_SQ_BRA. { A = MinMessage(state->lobby, state->lobby->String_sq_bra, C, 0); }
message(A) ::= SYMBOL(B) ASSIGN(C) message(D). { MIN_MESSAGE(A = C)->arguments = MinArray2(state->lobby, 2, B, D); }
message(A) ::= OP(B) message(C). { MIN_MESSAGE(A = B)->arguments = MinArray2(state->lobby, 1, C); }

arguments(A) ::= messages(B). { A = MinArray2(state->lobby, 1, B); }
arguments(A) ::= arguments(B) COMMA messages(C). { MIN_ARRAY_PUSH(A = B, C); }
arguments(A) ::= . { A = MinArray(state->lobby); }
