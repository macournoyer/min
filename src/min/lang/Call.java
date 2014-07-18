package min.lang;

import java.util.ArrayList;

public class Call extends MinObject {

    // --Commented out by Inspection (17/07/2014 14:20):static public MinObject object;

    final ArrayList<Message> args;
    MinObject receiver;
    final MinObject base;
    final Message message;

    public Call(Message message, MinObject receiver, MinObject base, ArrayList<Message> args) {
        super(MinObject.call);
        this.message = message;
        this.receiver = receiver;
        this.base = base;
        this.args = args;
    }

    public Call(MinObject receiver) {
        this(null, receiver, receiver, new ArrayList<Message>());
    }

    public MinObject evalArg(int at) throws MinException {
        if (at >= args.size()) return MinObject.nil;
        return args.get(at).evalOn(base);
    }

    public String toString() {
        return "<" + kind() + "#" + getObjectID() +
                " message:" + message.toString() +
                " receiver:" + receiver.toString() +
                " base:" + base.toString() +
                " args:" + args.toString() +
                ">";
    }

}
