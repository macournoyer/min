package min.lang;

import java.util.ArrayList;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class Bootstrap {
  public void run() throws MinException {
    // Bootstrap base objects
    MinObject base = new MinObject();
    MinObject object = base.clone();
    MinObject lobby = base.clone();
    object.prependProto(lobby);
    
    // Introduce objects into Lobby
    MinObject.base = lobby.setSlot("Base", base);
    MinObject.lobby = lobby.setSlot("Lobby", lobby);
    lobby.setSlot("$", lobby);
    MinObject.object = lobby.setSlot("Object", object);
    MinObject.string = lobby.setSlot("String", object.clone().with(""));
    MinObject.base.asKind("Base");
    MinObject.lobby.asKind("Lobby");
    MinObject.object.asKind("Object");
    MinObject.string.asKind("String");
    MinObject.call = lobby.setSlot("Call", object.clone().asKind("Call"));
    MinObject.method = lobby.setSlot("Method", object.clone().asKind("Method"));
    MinObject.number = lobby.setSlot("Number", object.clone().with(0).asKind("Number"));
    MinObject.array = lobby.setSlot("Array", object.clone().with(new ArrayList<MinObject>()).asKind("Array"));
    MinObject.nil = lobby.setSlot("nil", object.clone().with(null).asKind("nil"));
    MinObject._true = lobby.setSlot("true", object.clone().with(true).asKind("true"));
    MinObject._false = lobby.setSlot("false", object.clone().with(false).asKind("false"));
    
    // Load properties
    Properties properties = loadProperties();
    
    // Set load path
    MinObject.lobby.slot("load_path", MinObject.newArray(new MinObject[] {
      MinObject.newString("."),
      MinObject.newString(properties.getProperty("lib.path"))
    }));
    
    /////////// Add core slots to objects ///////////
    
    // Base
    MinObject.base.
      slot("=", new Method() {
        public MinObject activate(Call call) throws MinException {
          return call.receiver.setSlot(call.args.get(0).name, call.evalArg(1));
        }
      }).
      slot("set_slot", new Method() {
        public MinObject activate(Call call) throws MinException {
          return call.receiver.setSlot(call.evalArg(0).getDataAsString(), call.evalArg(1));
        }
      }).
      slot("inspect", new Method() {
        public MinObject activate(Call call) throws MinException {
          return MinObject.newString(call.receiver.toString());
        }
      }).
      slot("method", new Method() {
        public MinObject activate(Call call) throws MinException {
          return new Method(call);
        }
      }).
      slot("do", new Method() {
        public MinObject activate(Call call) throws MinException {
          call.args.get(0).evalOn(call.receiver);
          return call.receiver;
        }
      }).
      slot("require", new Method() {
        public MinObject activate(Call call) throws MinException {
          String file = call.evalArg(0).getDataAsString();
          return MinObject.require(file);
        }
      });
    
    // Object
    MinObject.object.
      slot("clone", new Method() {
        public MinObject activate(Call call) {
          return call.receiver.clone();
        }
      }).
      slot("print", new Method() {
        public MinObject activate(Call call) {
          System.out.print(call.receiver.getData());
          return call.receiver;
        }
      }).
      slot("println", new Method() {
        public MinObject activate(Call call) {
          System.out.println(call.receiver.getData());
          return call.receiver;
        }
      }).
      slot("new", new Method() {
        public MinObject activate(Call call) throws MinException {
          MinObject c = call.receiver.clone();
          if (c.hasSlot("initialize")) c.getSlot("initialize").activate(call);
          return c;
        }
      });
    
    // String
    MinObject.string.
      slot("+", new Method() {
        public MinObject activate(Call call) throws MinException {
          return MinObject.newString(call.receiver.getDataAsString() + call.evalArg(0).getDataAsString());
        }
      });

    // Array
    MinObject.array.
      slot("at", new Method() {
        public MinObject activate(Call call) throws MinException {
          return call.receiver.getDataAsArray().get(call.evalArg(0).getDataAsNumber());
        }
      }).
      slot("<<", new Method() {
        public MinObject activate(Call call) throws MinException {
          MinObject obj = call.evalArg(0);
          call.receiver.getDataAsArray().add(obj);
          return obj;
        }
      });
    
    // Call
    MinObject.call.
      slot("eval_arg", new Method() {
        public MinObject activate(Call call) throws MinException {
          Call c = (Call)call.receiver;
          return c.evalArg(call.evalArg(0).getDataAsNumber());
        }
      });
    
    MinObject.require("bootstrap");
  }
  
  private Properties loadProperties() throws MinException {
    Properties properties = new Properties();
    try {
      InputStream in = getClass().getResourceAsStream("default.properties");
      properties.load(in);
      return properties;
    } catch (IOException e) {
      throw new MinException(e);
    }
  }
}