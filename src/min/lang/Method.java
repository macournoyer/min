package min.lang;

public class Method extends MinObject {
  Call originatorCall;
  Message message;
  String[] argNames;
  
  public Method() {
    mimics(MinObject.method);
  }
  
  public Method(Call call) {
    this();
    this.originatorCall = call;
    this.message = call.message.args.get(call.message.args.size() - 1);
    int nArgs = call.message.args.size() - 1;
    this.argNames = new String[nArgs];
    for (int i = 0; i < nArgs; i++) this.argNames[i] = call.message.args.get(i + 1).name;
  }
  
  public MinObject activate(Call call) throws MinException {
    return this.message.evalOn(makeContext(call));
  }
  
  private MinObject makeContext(Call call) throws MinException {
    MinObject context = call.base.clone();
    context.setSlot("self", this.originatorCall.receiver);
    context.setSlot("call", call);
    context.setSlot("kind", MinObject.newString("MethodContext"));
    for (int i = 0; i < this.argNames.length; i++) context.setSlot(this.argNames[i], call.evalArg(i));
    return context;
  }
}