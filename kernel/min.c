#include "min.h"

int main (int argc, char const *argv[]) {
  struct MinLobby *lobby = MinLobby();
  
  /* OBJ msg = min_parse(lobby, "String type println.\"ohaie\" println", "min.c"); */
  /* OBJ msg = min_parse(lobby, "\"(%s)\" sprintf(\"oye!\") println", "min.c"); */
  /* OBJ msg = min_parse(lobby, "1 println", "min.c"); */
  OBJ msg = min_parse(lobby, "x = 1. x println", "min.c");
  /* MinObject_dump(lobby, 0, msg); */
  MinMessage_eval_on(lobby, 0, msg, lobby->lobby, lobby->lobby);
  min_send2(min_send2(msg, "inspect"), "println");
  
  MinLobby_destroy(lobby);
  return 0;
}