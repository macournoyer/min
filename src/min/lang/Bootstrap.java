package min.lang;

import java.util.ArrayList;

public class Bootstrap {
  static public void run() throws MinException {
    // Bootstrap base objects
    MinObject base = new MinObject();
    MinObject object = base.clone();
    MinObject lobby = base.clone();
    object.prependProto(lobby);
    
    // Introduce objects into Lobby
    MinObject.lobby = lobby.setSlot("Lobby", lobby);
    MinObject.object = lobby.setSlot("Object", object);
    MinObject.string = lobby.setSlot("String", object.clone().with(""));
    base.asKind("Base");
    MinObject.lobby.asKind("Lobby");
    MinObject.object.asKind("Object");
    MinObject.string.asKind("String");
    MinObject.number = lobby.setSlot("Number", object.clone().with(0).asKind("Number"));
    MinObject.array = lobby.setSlot("Array", object.clone().with(new ArrayList<MinObject>()).asKind("Array"));
    MinObject.nil = lobby.setSlot("nil", object.clone().with(null).asKind("nil"));
    MinObject._true = lobby.setSlot("true", object.clone().with(true).asKind("true"));
    MinObject._false = lobby.setSlot("false", object.clone().with(false).asKind("false"));
    
    // Add core slots to Object
    MinObject.object.setSlot("=", new Method() {
      public MinObject activate(Call call) throws MinException {
        return call.receiver.setSlot(call.args.get(0).name, call.evalArg(1));
      }
    });
    MinObject.object.setSlot("clone", new Method() {
      public MinObject activate(Call call) {
        return call.receiver.clone();
      }
    });
    MinObject.object.setSlot("println", new Method() {
      public MinObject activate(Call call) {
        System.out.println(call.receiver.getData());
        return call.receiver;
      }
    });
    
    // Array
    MinObject.array.setSlot("at", new Method() {
      public MinObject activate(Call call) throws MinException {
        return call.receiver.getDataAsArray().get(call.evalArg(0).getDataAsNumber());
      }
    });
    MinObject.array.setSlot("<<", new Method() {
      public MinObject activate(Call call) throws MinException {
        MinObject obj = call.evalArg(0);
        call.receiver.getDataAsArray().add(obj);
        return obj;
      }
    });
  }
}