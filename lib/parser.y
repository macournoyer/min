class Min::GeneratedParser

/* Terminal types */
token NUMBER
token STRING
token INDENT
token DEDENT
token SEP
token ID
token CONST

/* Operators */
token EQ ADD REM RSH

rule
  Root:
    /* nothing */ { result = Block.new }
  | Statements
  ;
  
  Statements:
    Statement                { result = Block.new([val[0]]) }
  | Statements SEP Statement { result = Block.new([val[0].nodes, val[2]].flatten) }
  ;

  Statement:
    Call
  | Assign
  | Literal
  | Constant
  | AssignConstant
  ;
  
  Literal:
    NUMBER { result = Number.new(val[0]) }
  | STRING { result = String.new(val[0]) }
  ;
  
  Op:
    EQ
  | ADD
  | REM
  | RSH
  ;
  
  Call:
    ID ArgList                      { result = Call.new(nil, val[0], val[1]) }
  | ID ArgList Block                { result = Call.new(nil, val[0], val[1] << val[2]) }
  | Statement '.' ID ArgList        { result = Call.new(val[0], val[2], val[3]) }
  | Statement '.' ID ArgList Block  { result = Call.new(val[0], val[2], val[3] << val[4]) }
  | Statement '.' ID '=' Statement  { result = Call.new(val[0], val[2] + "=", [val[4]]) }
  | Statement Op Statement          { result = Call.new(val[0], val[1], [val[2]]) }
  | Statement '[' Statement ']'     { result = Call.new(val[0], "[]", [val[2]]) }
  | Statement '[' Statement ']'
    '=' Statement                   { result = Call.new(val[0], "[]=", [val[2], val[5]]) }
  ;
  
  Block:
    ':'
      INDENT Statements
      DEDENT            { result = val[2] }
  | '{' Statements '}'  { result = val[1] }
  ;

  Assign:
    ID '=' Statement { result = Assign.new(val[0], val[2]) }
  ;

  AssignConstant:
    CONST '=' Statement { result = AssignConstant.new(val[0], val[2]) }
  ;
  
  Constant:
    CONST { result = Constant.new(val[0]) }
  ;
  
  ArgList:
    /* nothing */         { result = [] }
  | Statement             { result = [val[0]] }
  | Statement "," ArgList { result = [val[0], val[2]].flatten }
  | '(' ArgList ')'       { result = val[1] }
  ;
end
