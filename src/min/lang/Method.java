package min.lang;

public class Method extends MinObject {
  Call originatorCall;
  Message message;
  
  public Method() {
    mimics(MinObject.method);
  }
  
  public Method(Call call) {
    this();
    this.originatorCall = call;
    this.message = call.message.args.get(0);
  }
  
  public MinObject activate(Call call) throws MinException {
    return this.message.evalOn(makeContext());
  }
  
  private MinObject makeContext() {
    MinObject context = call.base.clone();
    context.setSlot("self", this.originatorCall.receiver);
    context.setSlot("call", call);
    context.setSlot("kind", MinObject.newString("MethodContext"));
    return context;
  }
}