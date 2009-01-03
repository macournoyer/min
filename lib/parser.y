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
token EQ PLS MIN RSH LT GT LET GET AND OR NOT

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
  | Closure
  ;
  
  Literal:
    NUMBER              { result = Min::Number.new(val[0]) }
  | STRING              { result = Min::String.new(val[0]) }
  | SYMBOL              { result = Min::Symbol.new(val[0]) }
  | '[' ArrayItems ']'  { result = Min::Array.new(val[1]) }
  | '[' HashItems ']'   { result = Min::Hash.new(val[1]) }
  ;
  
  ArrayItems:
    /* empty */              { result = [] }
  | Statement                { result = [val[0]] }
  | Statement ',' ArrayItems { result = [val[0], val[2]].flatten }
  ;
  
  HashItem:
    Statement ':' Statement  { result = { val[0] => val[2] } }
  | ID ':' Statement         { result = { Min::Symbol.new(val[0]) => val[2] } }
  ;

  HashItems:
    ':'                    { result = {} }
  | HashItem
  | HashItem ',' HashItems { result = val[2].merge(val[0]) }
  ;
  
  BinaryOp:
    EQ
  | PLS
  | MIN
  | RSH
  | LT
  | GT
  | LET
  | GET
  | AND
  | OR
  ;
  
  UnaryOp:
    NOT
  | MIN
  ;
  
  Call:
    ID ArgList                       { result = Call.new(nil, val[0], val[1]) }
  | ID ArgList Block                 { result = Call.new(nil, val[0], val[1] << val[2]) }
  | Statement '.' ID ArgList         { result = Call.new(val[0], val[2], val[3]) }
  | Statement '.' ID ArgList Block   { result = Call.new(val[0], val[2], val[3] << val[4]) }
  | Statement '.' ID '=' Statement   { result = Call.new(val[0], :"#{val[2]}=", [val[4]]) }
  | Statement BinaryOp Statement     { result = Call.new(val[0], val[1].to_sym, [val[2]]) }
  | UnaryOp Statement                { result = Call.new(val[1], val[0].to_sym, []) }
  | Statement '[' Statement ']'      { result = Call.new(val[0], :[], [val[2]]) }
  | Statement '[' Statement ']'      
    '=' Statement                    { result = Call.new(val[0], :[]=, [val[2], val[5]]) }
  ;
  
  Block:
    ':'
      INDENT Statements
      DEDENT                         { result = Closure.new(val[2], []) }
  | ':' ParamList '|'                
      INDENT Statements              
      DEDENT                         { result = Closure.new(val[4], val[1]) }
  | ':' Statements                   { result = Closure.new(val[1], []) }
  | ':' ParamList '|' Statements     { result = Closure.new(val[3], val[1]) }
  ;
  
  Closure:
    '{' Statements '}'               { result = Closure.new(val[1], []) }
  | '{' ParamList '|' Statements '}' { result = Closure.new(val[3], val[1]) }
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
  | Statement ',' ArgList { result = [val[0], val[2]].flatten }
  | '(' ArgList ')'       { result = val[1] }
  ;
  
  ParamList:
    /* nothing */         { result = [] }
  | ID                    { result = [Param.new(val[0], nil, false)] }
  | '*' ID                { result = [Param.new(val[1], nil, true)] }
  | ID '=' Literal        { result = [Param.new(val[0], val[2], false)] }
  | ID ',' ParamList      { result = [Param.new(val[0]), val[2]].flatten }
  ;
end
