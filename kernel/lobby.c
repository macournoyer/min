#include <stdio.h>
#include "min.h"

struct MinLobby *MinLobby() {
  struct MinLobby *lobby = MIN_ALLOC(struct MinLobby);
  OBJ vtable_vt, object_vt;
  
  /* bootstrap the object model */
  vtable_vt = MinVTable_delegated(lobby, 0, 0);
  MIN_VT(vtable_vt) = vtable_vt;
  
  object_vt = MinVTable_delegated(lobby, 0, 0);
  MIN_VT(object_vt) = vtable_vt;
  MIN_VTABLE(vtable_vt)->parent = object_vt;
  
  /* register vtables into the lobby */
  MIN_VT_FOR(Object) = object_vt;
  MIN_VT_FOR(VTable) = vtable_vt;
  MIN_VT_FOR(String) = MinVTable_delegated(lobby, 0, object_vt);
  MIN_VT_FOR(Closure) = MinVTable_delegated(lobby, 0, object_vt);
  
  MinStringTable_init(lobby);
  
  /* cache some often used symbols */
  lobby->String_lookup = MIN_STR("lookup");
  lobby->String_newline = MIN_STR("\n");
  lobby->String_dot = MIN_STR(".");
  lobby->String_type = MIN_STR("type");
  
  /* init lobby */
  lobby->lobby = MinVTable_allocate(lobby, 0, MinVTable_delegated(lobby, 0, object_vt));
  
  /* register core object into the lobby */
  MinVTable_init(lobby);
  MinObject_init(lobby);
  MIN_REGISTER_TYPE(VTable, vtable_vt);
  /* Lobby init */
  min_send2(lobby->lobby, "set_slot", MIN_STR("inspect"), MIN_STR("Lobby"));
  min_send2(lobby->lobby, "set_slot", MIN_STR("type"), MIN_STR("Lobby"));
  min_send2(lobby->lobby, "set_slot", MIN_STR("Lobby"), lobby->lobby);
  
  /* this is where core objects are initialized */
  MinString_init(lobby);
  MinMessage_init(lobby);
  MinArray_init(lobby);
  
  return lobby;
}

void MinLobby_destroy(struct MinLobby *lobby) {
  free(lobby);
}
