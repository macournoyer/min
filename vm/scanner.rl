#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "min.h"
#include "grammar.h"

#define TOKEN_V(id,v)  MinParser(pParser, MIN_TOK_##id, v); last = MIN_TOK_##id
#define TOKEN_UNIQ(id) if (last != PN_TOK_##id) { TOKEN(id); }
#define TOKEN(id)      TOKEN_V(id, 0)

#define MAX_INDENT     30
#define INDENT_PUSH(i) (assert(pind < MAX_INDENT-1), inds[++pind] = i)
#define INDENT_POP()   inds[--pind]

%%{
  machine min;
  
  newline     = "\r"? "\n" %{ curline++; };
  whitespace  = " " | "\f" | "\t" | "\v";

  indent      = newline whitespace+ newline?;
  term        = (newline | ".");
  id          = [a-zA-Z_]+;
  int         = [0-9]+;
  string      = '"' (any - '"')* '"' | "'" (any - "'")* "'";
  comment     = "#"+ (any - newline)* newline;
  
  main := |*
    
    # indentation magic
    # indent      => {
    #   if (ts[0] == '\r') ts++;
    #   if (ts[0] == '\n') ts++;
    #   
    #   if (te-ts > ind) {
    #     ind = te-ts;
    #     INDENT_PUSH(ind);
    #     TOKEN(TERM);
    #     TOKEN(INDENT);
    #   } else if (te-ts < ind) {
    #     ind = te-ts;
    #     INDENT_POP();
    #     TOKEN(DEDENT);
    #     TOKEN(TERM);
    #   } else {
    #     TOKEN(TERM);
    #   }
    # };
    # newline     => {
    #   while(pind > 0) {
    #     INDENT_POP();
    #     TOKEN(DEDENT);
    #   }
    #   ind = 0;
    #   TOKEN(TERM);
    # };
    
    whitespace;
    comment;
    
    # literals
    id          => { TOKEN_V(ID, min_str(ts, te-ts)); };
    int         => { TOKEN_V(INT, min_str(ts, te-ts)); };
    string      => { TOKEN_V(STRING, min_str(ts+1, te-ts-2)); };
    term        => { TOKEN(TERM); };
    
    # ponctuation
    ","         => { TOKEN(COMMA); };
    # ":"         => { TOKEN(COLON); };
    "("         => { TOKEN(O_PAR); };
    ")"         => { TOKEN(C_PAR); };
    # "{"         => { TOKEN(O_BRA); };
    # "}"         => { TOKEN(C_BRA); };
    # "["         => { TOKEN(O_SQ_BRA); };
    # "]"         => { TOKEN(C_SQ_BRA); };
    
    # assign
    # "="         => { TOKEN(ASSIGN); };
    # "+="        => { TOKEN(ASSIGN_PLUS); };
    # "-="        => { TOKEN(ASSIGN_MINUS); };
    
    # operators
    # "=="        => { TOKEN(EQ); };
    # "!="        => { TOKEN(NEQ); };
    # "!"         => { TOKEN(NOT); };
    # "||"        => { TOKEN(OR); };
    # "&&"        => { TOKEN(AND); };
    # "|"         => { TOKEN(PIPE); };
    # "&"         => { TOKEN(AMP); };
    # "<"         => { TOKEN(LT); };
    # "<="        => { TOKEN(LTE); };
    # ">"         => { TOKEN(GT); };
    # ">="        => { TOKEN(GTE); };
    # "<<"        => { TOKEN(LSH); };
    # ">>"        => { TOKEN(RSH); };
    # "*"         => { TOKEN(MULT); };
    # "**"        => { TOKEN(POW); };
    # "/"         => { TOKEN(DIV); };
    # "%"         => { TOKEN(MOD); };
    # "+"         => { TOKEN(PLUS); };
    # "-"         => { TOKEN(MINUS); };
    # "@"         => { TOKEN(AT); };
    # "@@"        => { TOKEN(ATAT); };
    # "?"         => { TOKEN(QUES); };
    # ".."        => { TOKEN(TO); };
  *|;
  
  write data nofinal;
}%%

void min_parse(char *code) {
  int cs, act;
  char *p, *pe, *ts, *te, *eof = 0;
  int inds[MAX_INDENT], pind = 0, ind = 0;
  int curline = 1;
  void *pParser = MinParserAlloc(malloc);
  int last = 0;
  
  inds[0] = 0;
  p = code;
  pe = p + strlen(code) + 1;
  
  %% write init;
  %% write exec;
  
  /* close all open indent on stack */
  /* while(pind > 0) {
    INDENT_POP();
    TOKEN(DEDENT);
  } */
  
  MinParser(pParser, 0, 0);
  MinParserFree(pParser, free);
}
