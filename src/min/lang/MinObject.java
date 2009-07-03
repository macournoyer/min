package min.lang;

import java.util.HashMap;
import java.util.ArrayList;

public class MinObject {
  static public MinObject base;
  static public MinObject lobby;
  static public MinObject object;
  static public MinObject call;
  static public MinObject method;
  static public MinObject string;
  static public MinObject number;
  static public MinObject array;
  static public MinObject nil;
  static public MinObject _true;
  static public MinObject _false;
  
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
  
  public boolean hasSlot(String name) {
    if (this.slots.containsKey(name)) return true;
    for (MinObject proto : this.protos) {
      if (proto.hasSlot(name)) return true;
    }
    return false;
  }

  public MinObject getSlot(String name) throws SlotNotFound {
    if (this.slots.containsKey(name)) return this.slots.get(name);
    for (MinObject proto : this.protos) {
      if (proto.hasSlot(name)) return proto.getSlot(name);
    }
    throw new SlotNotFound("Slot '" + name + "' not found");
  }
  
  public void mimics(MinObject obj) {
    this.slots = obj.slots;
    this.protos = obj.protos;
  }
  
  public Object getData() {
    return this.data;
  }
  
  public Integer getDataAsNumber() {
    return (this.data instanceof Integer) ? (Integer)this.data : null;
  }
  
  public String getDataAsString() {
    return (this.data instanceof String) ? (String)this.data : null;
  }
  
  @SuppressWarnings("unchecked")
  public ArrayList<MinObject> getDataAsArray() {
    return (this.data instanceof ArrayList) ? (ArrayList<MinObject>)this.data : null;
  }
  
  public String toString() {
    return "<data:" + (this.data == null ? "null" : this.data.toString()) +
           " slots:" + this.slots.toString() + ">";
  }
  
  public MinObject activate(Call call) throws MinException {
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

  public MinObject asKind(String kind) {
    setSlot("kind", newString(kind));
    return this;
  }
  
  public static MinObject newString(String str) {
    return MinObject.string.clone().with(str);
  }

  public static MinObject newNumber(Integer i) {
    return MinObject.number.clone().with(i);
  }
}
