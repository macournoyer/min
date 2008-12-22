class Min::GeneratedParser

/* literals */
token NIL TRUE FALSE

/* Terminal types */
token BLOCK
token NUMBER
token STRING
token IDENT
token DEDENT
token ID

rule
  Script:
    Statement
  ;
  
  Statement:
    Literal
  ;
  
  Literal:
    NIL
  | NUMBER
  | STRING
  ! ID
  ;
end