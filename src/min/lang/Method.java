package min.lang;

public class Method extends MinObject {

    Message message;
    String[] argNames;

    public Method() {
        super(MinObject.method);
    }

    public Method(Call call) {
        this();
        message = call.message.args.get(call.message.args.size() - 1);
        int nArgs = call.message.args.size() - 1;
        argNames = new String[nArgs];
        for (int i = 0; i < nArgs; i++) argNames[i] = call.message.args.get(i).name;
    }

    public MinObject activate(Call call) throws MinException {
        return message.evalOn(makeContext(call));
    }

    public MinObject callOn(MinObject on) throws MinException {
        return activate(new Call(on));
    }

    private MinObject makeContext(Call call) throws MinException {
        MinObject context = call.receiver.clone();
        context.asKind("MethodContext").
                slot("self", call.receiver).
                slot("@", call.receiver).
                slot("call", call).
                slot("context", context);
        for (int i = 0; i < argNames.length; i++)
            context.setSlot(argNames[i], call.evalArg(i));
        return context;
    }

}