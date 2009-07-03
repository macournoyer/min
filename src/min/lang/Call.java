package min.lang;

import java.util.ArrayList;

public class Call extends MinObject {
  static public MinObject object;
  
  ArrayList<Message> args;
  MinObject receiver;
  MinObject base;
  Message message;
  
  public Call(Message message, MinObject receiver, MinObject base, ArrayList<Message> args) {
    this.message = message;
    this.receiver = receiver;
    this.base = base;
    this.args = args;
    
    mimics(MinObject.call);
  }
  
  public MinObject evalArg(int at) throws MinException {
    return this.args.get(at).evalOn(this.base, this.base);
  }
}
