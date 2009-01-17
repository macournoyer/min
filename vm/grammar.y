/* doc: http://www.hwaci.com/sw/lemon/lemon.html */

%include {
#include <assert.h>
#include <string.h>
#include "min.h"
#include "compiler.h"
}

%name           MinParser
%token_type     { OBJ }
%token_prefix   MIN_TOK_
%extra_argument { struct MinCode *code }

%parse_failure {
  printf("Syntax error!\n");
}

/* rules */
root ::= expressions.

expressions ::= expression.
expressions ::= expressions TERM expression.

expression ::= message.
expression ::= expression message.

message ::= literal(B). { min_compile_lit(code, B); }
message ::= call(B). { min_compile_call(code, B); }

literal(A) ::= STRING(B). { A = B; }

call(A) ::= ID(B). { A = B; }
call(A) ::= ID(B) O_PAR C_PAR. { A = B; }
call(A) ::= ID(B) O_PAR arguments C_PAR. { A = B; }

arguments(A) ::= expression(B). { A = min_table(); min_table_push(A, B); }
arguments(A) ::= arguments(B) COMMA expression(C). { A = B; min_table_push(B, C); }
