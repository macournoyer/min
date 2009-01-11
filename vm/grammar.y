/* see http://www.hwaci.com/sw/lemon/lemon.html */

%include {
#include <assert.h>
#include <string.h>
#include "min.h"
}

%token_prefix MIN_TOK_

%name MinParser

/* rules */
root ::= expressions.

expressions ::= expression. { printf("expression\n"); }
expressions ::= expressions TERM expression. { printf("expressions\n"); }

expression ::= STRING.
expression ::= INT.
expression ::= ID.
