package min;

import min.lang.MinObject;
import min.lang.Message;
import min.lang.Method;

public class main {
  public static void main(String[] args) throws Exception {
    // bootstrap base objects
    MinObject base = new MinObject();
    MinObject object = base.clone();
    MinObject lobby = base.clone();
    object.prependProto(lobby);
    
    // introduce objects into Lobby
    MinObject.lobby = lobby.setSlot("Lobby", lobby);
    MinObject.object = lobby.setSlot("Object", object);
    MinObject.string = lobby.setSlot("String", object.clone().with(""));
    MinObject.number = lobby.setSlot("Number", object.clone().with(0));
    MinObject.nil = lobby.setSlot("nil", object.clone().with(null));
    MinObject._true = lobby.setSlot("nil", object.clone().with(true));
    
    lobby.setSlot("test", string.clone().with("ohaie"));
    
    string.setSlot("println", new Method() {
      public MinObject activate(MinObject context) {
        System.out.println(context.getData());
        return MinObject.nil;
      }
    });
    
    // test
    System.out.println( Message.parse(args[0]).evalOn(lobby).getData() );
  }
}