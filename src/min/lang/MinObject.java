package min.lang;

import java.util.HashMap;
import java.util.ArrayList;

public class MinObject {
  static public MinObject base;
  static public MinObject lobby;
  static public MinObject object;
  static public MinObject call;
  static public MinObject message;
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
    this.data = data;
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
    // If name starts w/ a capital letter, we're creating a kind.
    // Automaticly set the slot accordingly.
    if (name.length() > 0 && Character.isUpperCase(name.charAt(0))) {
      value.asKind(name);
    }
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
    throw new SlotNotFound("Slot '" + name + "' not found on " + kind());
  }
  
  public MinObject removeSlot(String name) {
    return slots.remove(name);
  }
  
  public String kind() {
    try {
      return getSlot("kind").getDataAsString();
    } catch (SlotNotFound e) {
      return "?";
    }
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
    return (ArrayList<MinObject>)data;
  }
  
  public String toString() {
    return "<" + kind() + "#" + getObjectID() +
           (data == null ? "" : " data:" + data.toString()) +
           // (slots.isEmpty() ? "" : " slots:" + slots.toString()) +
           ">";
  }
  
  public Integer getObjectID() {
    return System.identityHashCode(this);
  }
  
  public MinObject activate(Call call) throws MinException {
    return this;
  }
  
  public MinObject clone() {
    Object data = this.data;
    if (data instanceof ArrayList) {
      data = new ArrayList<MinObject>();
      // TODO copy array content?
      // Collections.copy(data, this.data);
    }
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
  
  public static MinObject require(String file) throws MinException {
    ArrayList<MinObject> loadPath = MinObject.lobby.getSlot("load_path").getDataAsArray();
    String code = null;
    for (MinObject path : loadPath) {
      String filePath = path.getDataAsString() + "/" + file + ".min";
      if (File.exists(filePath)) {
        file = filePath;
        code = File.read(filePath);
        break;
      }
    }
    if (code == null) throw new MinException("File not found: " + file);
    return Message.parse(code, file).evalOn(MinObject.lobby);
  }
  
  public static MinObject newString(String str) {
    return MinObject.string.clone().with(str);
  }
  
  public static MinObject newBool(Boolean value) {
    return value ? _true : _false;
  }

  public static MinObject newNumber(Integer i) {
    return MinObject.number.clone().with(i);
  }
  
  public static MinObject newArray(ArrayList<MinObject> items) {
    return MinObject.array.clone().with(items);
  }

  public static MinObject newArray(MinObject[] items) {
    ArrayList<MinObject> array = new ArrayList<MinObject>();
    for (MinObject o : items) array.add(o);
    return newArray(array);
  }
}
