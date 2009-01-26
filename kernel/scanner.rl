#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "min.h"
#include "grammar.h"

#define TOKEN_V(id,v)  MinParser(pParser, MIN_TOK_##id, v, &state); last = MIN_TOK_##id
#define TOKEN_UNIQ(id) if (last != PN_TOK_##id) { TOKEN(id); }
#define TOKEN(id)      TOKEN_V(id, 0)

#define MAX_INDENT     30
#define INDENT_PUSH(i) (assert(pind < MAX_INDENT-1), inds[++pind] = i)
#define INDENT_POP()   inds[--pind]

#define BUFFER(str,l)  ({ \
  if (buf) free(buf); \
  buf = MIN_ALLOC_N(char, (l)); \
  MIN_MEMCPY_N(buf, (str), char, (l)); \
  buf[(l)] = '\0'; \
  buf; \
})

%%{
  machine min;
  
  newline     = "\r"? "\n" %{ state.curline++; };
  whitespace  = " " | "\f" | "\t" | "\v";

  indent      = newline whitespace+ newline?;
  term        = (newline | ".");
  id          = [a-zA-Z_]+;
  int         = [0-9]+;
  string      = '"' (any - '"')* '"' | "'" (any - "'")* "'";
  comment     = "#"+ (any - newline)* newline;
  
  assign      = '=' | '+=' | '-=';
  un_op       = '!' | '?';
  op          = '==' | '!=' | '||' | '&&' | '|' | '&' | '<' | '<=' | '>' | '>=' | '<<' | '>>' | '**' | '*' | '/' | '%' | '+' | '-' | '@@' | '@' | '..';
  
  symbol      = id | op | term;
  
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
    #     TOKEN_UNIQ(TERM);
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
    
    symbol      => { TOKEN_V(SYMBOL, MinMessage(lobby, MIN_STR(BUFFER(ts, te-ts)), 0, 0)); };
    assign      => { TOKEN_V(ASSIGN, MinMessage(lobby, MIN_STR(BUFFER(ts, te-ts)), 0, 0)); };
    string      => { TOKEN_V(STRING, MinMessage(lobby, MIN_STR(BUFFER(ts, te-ts)), 0, MIN_STR(BUFFER(ts+1, te-ts-2)))); };
    int         => { TOKEN_V(INT, MinMessage(lobby, MIN_STR(BUFFER(ts, te-ts)), 0, INT2FIX(atoi(BUFFER(ts, te-ts))))); };
    
    # ponctuation
    ","         => { TOKEN(COMMA); };
    # ":"         => { TOKEN(COLON); };
    "("         => { TOKEN(O_PAR); };
    ")"         => { TOKEN(C_PAR); };
    # "{"         => { TOKEN(O_BRA); };
    # "}"         => { TOKEN(C_BRA); };
    "["         => { TOKEN(O_SQ_BRA); };
    "]"         => { TOKEN(C_SQ_BRA); };
  *|;
  
  write data nofinal;
}%%

OBJ min_parse(LOBBY, char *string, char *filename) {
  int cs, act;
  char *p, *pe, *ts, *te, *eof = 0;
  void *pParser = MinParserAlloc(malloc);
  int last = 0;
  char *buf = 0;
  
  /* int inds[MAX_INDENT], pind = 0, ind = 0;
  inds[0] = 0; */

  struct MinParseState state;
  state.curline = 0;
  state.lobby = lobby;
  state.message = 0;
  
  p = string;
  pe = p + strlen(string) + 1;
  
  %% write init;
  %% write exec;
  
  /* close all open indent on stack */
  /* while(pind > 0) {
    INDENT_POP();
    TOKEN(DEDENT);
  } */
  
  MinParser(pParser, 0, 0, &state);
  MinParserFree(pParser, free);
  if (buf) free(buf);

  return state.message;
}
