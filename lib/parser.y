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

prechigh
  right    '!'
  left     '||' '&&'
preclow

rule
  Root:
    /* nothing */ { result = Block.new([]) }
  | Statements
  ;
  
  Statements:
    StatementWithBlock                { result = Block.new([val[0]]) }
  | Statements SEP StatementWithBlock { result = Block.new([val[0].nodes, val[2]].flatten) }
  ;

  Statement:
    Call
  | Assign
  | Literal
  | Closure
  ;

  StatementWithBlock:
    Statement
  | CallWithBlock
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
    '=='
  | '+'
  | '-'
  | '<<'
  | '<'
  | '>'
  | '<='
  | '>='
  | '&&'
  | '||'
  ;
  
  UnaryOp:
    '!'
  | '-'
  ;
  
  AssignOp:
    '='
  | '+='
  | '-='
  | '*='
  | '/='
  ;
  
  Call:
    ID ArgList                       { result = Call.new(nil, val[0], val[1]) }
  | Statement '.' ID ArgList         { result = Call.new(val[0], val[2], val[3]) }
  | Statement '.' ID '=' Statement   { result = Call.new(val[0], :"#{val[2]}=", [Arg.new(val[4])]) }
  | Statement BinaryOp Statement     { result = Call.new(val[0], val[1].to_sym, [Arg.new(val[2])]) }
  | UnaryOp Statement                { result = Call.new(val[1], val[0].to_sym, []) }
  | Statement '[' Statement ']'      { result = Call.new(val[0], :[], [Arg.new(val[2])]) }
  | Statement '[' Statement ']'      
    '=' Statement                    { result = Call.new(val[0], :[]=, [Arg.new(val[2]), Arg.new(val[5])]) }
  ;
  
  CallWithBlock:
    ID ArgList Block                 { result = Call.new(nil, val[0], val[1] << Arg.new(val[2])) }
  | Statement '.' ID ArgList Block   { result = Call.new(val[0], val[2], val[3] << Arg.new(val[4])) }
  ;
  
  Block:
    ':'
      INDENT Statements
      DEDENT                         { result = Closure.new(val[2], []) }
  | ':' ParamList '|'                
      INDENT Statements              
      DEDENT                         { result = Closure.new(val[4], val[1]) }
  | ':' Statement                    { result = Closure.new(Block.new([val[1]]), []) }
  | ':' ParamList '|' Statement      { result = Closure.new(Block.new([val[3]]), val[1]) }
  ;
  
  Closure:
    '{' Statements '}'               { result = Closure.new(val[1], []) }
  | '{' ParamList '|' Statements '}' { result = Closure.new(val[3], val[1]) }
  ;

  Assign:
    ID '=' StatementWithBlock { result = Assign.new(val[0], val[2]) }
  ;
  
  ArgList:
    /* nothing */         { result = [] }
  | '*' Statement         { result = [Arg.new(val[1], true)] }
  | Statement             { result = [Arg.new(val[0])] }
  | Statement ',' ArgList { result = [Arg.new(val[0]), val[2]].flatten }
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
