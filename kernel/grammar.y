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

/*%right ASSIGN.
%left OP.*/

/* rules */
root ::= messages(B). { state->message = B; }

messages(A) ::= message(B). { A = B; }
messages(A) ::= message(B) messages(C). { /* HACK right-recursion. Bad! Find a better way */
  A = B;
  MIN_MESSAGE(B)->next = C;
  MIN_MESSAGE(C)->previous = B;
}

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
