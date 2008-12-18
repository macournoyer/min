class Min::Parser

/* literals */
token NIL TRUE FALSE

/* keywords */
token BLOCK

/* Terminal types */
/* token REGEXP */
token NUMBER
token STRING
token IDENT

/* precedance table */
/* prechigh
  nonassoc ELSE
preclow */

rule
  SourceElement:
    NIL
  ;

end