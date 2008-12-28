class Min::GeneratedParser

/* Terminal types */
token NUMBER
token STRING
token INDENT
token DEDENT
token SEP
token ID
token CONST
token SYMBOL

/* Operators */
token EQ ADD REM RSH

rule
  Root:
    /* nothing */ { result = Block.new([]) }
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
    NUMBER { result = Min::Number.new(val[0]) }
  | STRING { result = Min::String.new(val[0]) }
  | SYMBOL { result = Min::Symbol.new(val[0]) }
  ;
  
  Op:
    EQ
  | ADD
  | REM
  | RSH
  ;
  
  Call:
    ID CallArgList                       { result = Call.new(nil, val[0], val[1]) }
  | ID CallArgList Closure               { result = Call.new(nil, val[0], val[1] << val[2]) }
  | Statement '.' ID CallArgList         { result = Call.new(val[0], val[2], val[3]) }
  | Statement '.' ID CallArgList Closure { result = Call.new(val[0], val[2], val[3] << val[4]) }
  | Statement '.' ID '=' Statement       { result = Call.new(val[0], :"#{val[2]}=", [val[4]]) }
  | Statement Op Statement               { result = Call.new(val[0], val[1].to_sym, [val[2]]) }
  | Statement '[' Statement ']'          { result = Call.new(val[0], :[], [val[2]]) }
  | Statement '[' Statement ']'          
    '=' Statement                        { result = Call.new(val[0], :[]=, [val[2], val[5]]) }
  ;
  
  Closure:
    ':'
      INDENT Statements
      DEDENT                        { result = Closure.new(val[2], []) }
  | ':' ArgList '|'                 
      INDENT Statements             
      DEDENT                        { result = Closure.new(val[4], val[1]) }
  | '{' Statements '}'              { result = Closure.new(val[1], []) }
  | '{' ArgList '|' Statements '}'  { result = Closure.new(val[3], val[1]) }
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
  ;
  
  CallArgList:
    ArgList
  | '(' ArgList ')' { result = val[1] }
end
