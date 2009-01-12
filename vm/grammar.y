/* doc: http://www.hwaci.com/sw/lemon/lemon.html */

%include {
#include <assert.h>
#include <string.h>
#include "min.h"
}

%name         MinParser
%token_type   { OBJ }
%token_prefix MIN_TOK_

%parse_failure {
  printf("Syntax error!\n");
}

/* rules */
root ::= expressions.

expressions ::= expression.
expressions ::= expressions TERM expression.

expression(A) ::= literal(B). { A = B; }
expression(A) ::= call(B). { A = B; }

literal(A) ::= STRING(B). { A = B; }
literal(A) ::= INT(B). { A = B; }

call ::= ID(B). { printf("call: %s\n", MIN_STR_PTR(B)); }
call ::= ID(B) O_PAR C_PAR. { printf("call: %s\n", MIN_STR_PTR(B)); }
call ::= ID(B) O_PAR arguments(C) C_PAR. { printf("call: %s", MIN_STR_PTR(B)); min_table_print(C); }

arguments(A) ::= expression(B). { A = min_table(); min_table_push(A, B); }
arguments(A) ::= arguments(B) COMMA expression(C). { A = B; min_table_push(B, C); }
