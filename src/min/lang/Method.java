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
    originatorCall = call;
    message = call.message.args.get(call.message.args.size() - 1);
    int nArgs = call.message.args.size() - 1;
    argNames = new String[nArgs];
    for (int i = 0; i < nArgs; i++) argNames[i] = call.message.args.get(i).name;
  }
  
  public MinObject activate(Call call) throws MinException {
    return message.evalOn(makeContext(call));
  }
  
  private MinObject makeContext(Call call) throws MinException {
    MinObject context = call.base.clone();
    context.setSlot("self", originatorCall.receiver);
    context.setSlot("@", originatorCall.receiver);
    context.setSlot("call", call);
    context.setSlot("kind", MinObject.newString("MethodContext"));
    for (int i = 0; i < argNames.length; i++)
      context.setSlot(argNames[i], call.evalArg(i));
    return context;
  }
}