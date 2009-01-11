#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "min.h"
#include "parser.h"

/* debug */
#define TOKENV(name, v, len) \
  memset(sbuf, 0, 1024); \
  strncpy(sbuf, v, len); \
  printf("<%s:%s> ", name, sbuf)
#define TOKEN(name) \
  printf("<%s> ", name)

#define MAX_INDENT     30
#define INDENT_PUSH(i) (assert(pind < MAX_INDENT-1), inds[++pind] = i)
#define INDENT_POP()   inds[--pind]

%%{
  machine min;
  
  newline     = "\r"? "\n" %{ printf(" (line %d)\n", curline); curline++; };
  whitespace  = " " | "\f" | "\t" | "\v";

  indent      = newline whitespace+ newline?;
  term        = (newline | ".");
  id          = [a-z]+;
  int         = [0-9]+;
  string      = '"' (any - '"')* '"' | "'" (any - "'")* "'";
  comment     = "#"+ (any - newline)* newline;
  
  main := |*
    
    # indentation magic
    indent      => {
      if (ts[0] == '\r') ts++;
      if (ts[0] == '\n') ts++;
      
      if (te-ts > ind) {
        ind = te-ts;
        INDENT_PUSH(ind);
        TOKEN("term");
        TOKEN("indent");
      } else if (te-ts < ind) {
        ind = te-ts;
        INDENT_POP();
        TOKEN("dedent");
        TOKEN("term");
      } else {
        TOKEN("term");
      }
    };
    newline     => {
      while(pind > 0) {
        INDENT_POP();
        TOKEN("dedent");
      }
      ind = 0;
      TOKEN("term");
    };
    
    whitespace;
    comment;
    
    # literals
    id          => { TOKENV("id", ts, te-ts); };
    int         => { TOKENV("int", ts, te-ts); };
    string      => { TOKENV("string", ts+1, te-ts-2); };
    term        => { TOKEN("term"); };
    
    # ponctuation
    ","         => { TOKEN("comma"); };
    ":"         => { TOKEN("colon"); };
    "("         => { TOKEN("o_par"); };
    ")"         => { TOKEN("c_par"); };
    "{"         => { TOKEN("o_bra"); };
    "}"         => { TOKEN("c_bra"); };
    "["         => { TOKEN("o_sqbra"); };
    "]"         => { TOKEN("c_sqbra"); };
    
    # assign
    "="         => { TOKEN("assign"); };
    "+="        => { TOKEN("assign_plus"); };
    "-="        => { TOKEN("assign_minus"); };
    
    # operators
    "=="        => { TOKEN("eq"); };
    "!="        => { TOKEN("neq"); };
    "!"         => { TOKEN("not"); };
    "||"        => { TOKEN("or"); };
    "&&"        => { TOKEN("and"); };
    "<"         => { TOKEN("lt"); };
    "<="        => { TOKEN("lte"); };
    ">"         => { TOKEN("gt"); };
    ">="        => { TOKEN("gte"); };
    "<<"        => { TOKEN("lsh"); };
    ">>"        => { TOKEN("rsh"); };
    "*"         => { TOKEN("mult"); };
    "**"        => { TOKEN("pow"); };
    "/"         => { TOKEN("div"); };
    "%"         => { TOKEN("mod"); };
    "+"         => { TOKEN("plus"); };
    "-"         => { TOKEN("minus"); };
    "@"         => { TOKEN("at"); };
    "@@"        => { TOKEN("atat"); };
    "?"         => { TOKEN("ques"); };
    ".."        => { TOKEN("to"); };
  *|;
  
  write data nofinal;
}%%

void min_parse(char *code) {
  int cs, act;
  char *p, *pe, *ts, *te, *eof = 0;
  int curline = 1;
  int ind = 0;
  int inds[MAX_INDENT]; /* max indent level */
  int pind = 0;
  char sbuf[1024]; /* debug */
  void *pParser = MinParserAlloc(malloc);
  
  inds[0] = 0;
  p = code;
  pe = p + strlen(code) + 1;
  
  %% write init;
  %% write exec;
  
  /* close all open indent on stack */
  while(pind > 0) {
    INDENT_POP();
    TOKEN("dedent");
  }
  
  MinParser(pParser, 0, 0);
  MinParserFree(pParser, free);
}
