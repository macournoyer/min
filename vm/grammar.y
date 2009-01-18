/* doc: http://www.hwaci.com/sw/lemon/lemon.html */

%include {
#include <assert.h>
#include <string.h>
#include "min.h"
}

%name           MinParser
%token_type     { OBJ }
%token_prefix   MIN_TOK_
%extra_argument { struct MinVM *vm }

%parse_failure {
  printf("Syntax error!\n");
}

/* rules */
root ::= expressions.

expressions ::= expression.
expressions ::= expressions TERM expression.

expression ::= message.
expression ::= expression message.

message ::= literal.
message ::= call.

literal(A) ::= STRING(B). { A = B; }

call(A) ::= ID(B). { A = B; }
call(A) ::= ID(B) O_PAR C_PAR. { A = B; }
call(A) ::= ID(B) O_PAR arguments C_PAR. { A = B; }

arguments ::= expression.
arguments ::= arguments COMMA expression.
