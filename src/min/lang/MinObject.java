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
    protos = new ArrayList<MinObject>();
    if (proto != null) protos.add(proto);
    slots = new HashMap<String, MinObject>();
    data = data;
  }
  
  public MinObject(MinObject proto) {
    this(proto, null);
  }
  
  public MinObject() {
    this(null, null);
  }
  
  public void appendProto(MinObject proto) {
    protos.add(proto);
  }
  
  public void prependProto(MinObject proto) {
    protos.add(0, proto);
  }
  
  public MinObject setSlot(String name, MinObject value) {
    slots.put(name, value);
    return value;
  }
  
  // Set slot and return self. For nicer DSL.
  public MinObject slot(String name, MinObject value) {
    setSlot(name, value);
    return this;
  }
  
  public boolean hasSlot(String name) {
    if (slots.containsKey(name)) return true;
    for (MinObject proto : protos) {
      if (proto.hasSlot(name)) return true;
    }
    return false;
  }

  public MinObject getSlot(String name) throws SlotNotFound {
    if (slots.containsKey(name)) return slots.get(name);
    for (MinObject proto : protos) {
      if (proto.hasSlot(name)) return proto.getSlot(name);
    }
    throw new SlotNotFound("Slot '" + name + "' not found");
  }
  
  public void mimics(MinObject obj) {
    slots = obj.slots;
    protos = obj.protos;
  }
  
  public Object getData() {
    return data;
  }
  
  public Integer getDataAsNumber() {
    return (data instanceof Integer) ? (Integer)data : null;
  }
  
  public String getDataAsString() {
    return (data instanceof String) ? (String)data : null;
  }
  
  @SuppressWarnings("unchecked")
  public ArrayList<MinObject> getDataAsArray() {
    return (data instanceof ArrayList) ? (ArrayList<MinObject>)data : null;
  }
  
  public String toString() {
    return "<data:" + (data == null ? "null" : data.toString()) +
           " slots:" + slots.toString() + ">";
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
