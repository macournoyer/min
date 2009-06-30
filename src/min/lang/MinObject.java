package min.lang;

import java.util.HashMap;
import java.util.ArrayList;

public class MinObject {
  static public MinObject lobby;
  static public MinObject object;
  static public MinObject string;
  static public MinObject number;
  static public MinObject nil;
  
  ArrayList<MinObject> protos;
  HashMap<String, MinObject> slots;
  Object data;
  
  public MinObject(MinObject proto, Object data) {
    this.protos = new ArrayList<MinObject>();
    if (proto != null) this.protos.add(proto);
    this.slots = new HashMap<String, MinObject>();
    this.data = data;
  }
  
  public MinObject(MinObject proto) {
    this(proto, null);
  }
  
  public MinObject() {
    this(null, null);
  }
  
  public void appendProto(MinObject proto) {
    this.protos.add(proto);
  }
  
  public void prependProto(MinObject proto) {
    this.protos.add(0, proto);
  }
  
  public MinObject setSlot(String name, MinObject value) {
    this.slots.put(name, value);
    return value;
  }

  public MinObject getSlot(String name) throws SlotNotFound {
    if (this.slots.containsKey(name)) return this.slots.get(name);
    for (MinObject proto : this.protos) {
      if (proto.slots.containsKey(name)) return proto.slots.get(name);
    }
    throw new SlotNotFound("Slot " + name + " not found");
  }
  
  public Object getData() {
    return this.data;
  }
  
  public MinObject activate(MinObject context) throws MinException {
    return this;
  }
  
  public MinObject clone() {
    Object data = this.data; // maybe clone data?
    return new MinObject(this, data);
  }
  
  public MinObject with(Object data) {
    this.data = data;
    return this;
  }
}
