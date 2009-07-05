package min.lang;

import java.util.ArrayList;

public class Call extends MinObject {
  static public MinObject object;
  
  ArrayList<Message> args;
  MinObject receiver;
  MinObject base;
  Message message;
  
  public Call(Message message, MinObject receiver, MinObject base, ArrayList<Message> args) {
    super(MinObject.call);
    this.message = message;
    this.receiver = receiver;
    this.base = base;
    this.args = args;
  }
  
  public MinObject evalArg(int at) throws MinException {
    return args.get(at).evalOn(base);
  }
}
