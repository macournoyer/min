#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* debug */
#define TOKENV(name, v, len) \
  memset(sbuf, 0, 1024); \
  strncpy(sbuf, v, len); \
  printf("[%d] <%s:%s>\n", curline, name, sbuf)
#define TOKEN(name) \
  printf("[%d] <%s>\n", curline, name)

%%{
  machine min;
  
  newline     = "\r"? "\n";
  whitespace  = " " | "\f" | "\t" | "\v";

  indent      = newline whitespace+;
  term        = (newline | ".");
  id          = [a-z]+;
  int         = [0-9]+;
  string      = '"' (any - '"')* '"';
  
  main := |*
    # indentation magic
    indent      => {
      if (ts[0] == '\r') ts++;
      if (ts[0] == '\n') ts++;
      TOKENV("indent", ts, te-ts);
    };
    newline     => {
      TOKEN("dedent");
      curline++;
    };
    
    whitespace;
    
    id          => { TOKENV("id", ts, te-ts); };
    int         => { TOKENV("int", ts, te-ts); };
    string      => { TOKENV("string", ts+1, te-ts-2); };
    term        => { TOKEN("term"); };
    ","         => { TOKEN("comma"); };
    "("         => { TOKEN("opar"); };
    ")"         => { TOKEN("cpar"); };
  *|;
  
  write data nofinal;
}%%

void min_parse(char *code) {
  int cs, act;
  char *p, *pe, *ts, *te, *eof = 0;
  int curline = 0;
  char sbuf[1024];

  p = code;
  pe = p + strlen(code) + 1;

  %% write init;
  %% write exec;
}
