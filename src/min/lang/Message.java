package min.lang;

//import min.lang.ParsingException;

import java.util.ArrayList;

public class Message extends MinObject {

    String name;
    String file;
    int line;
    Message next, prev;
    ArrayList<Message> args;
    MinObject cachedResponse;
    Operator operator;

    public Message(String name, String file, int line, MinObject cachedResponse) {
        super(MinObject.message);
        this.name = name;
        this.file = file;
        this.line = line;
        this.next = null;
        this.prev = null;
        this.args = new ArrayList<Message>();
        this.cachedResponse = cachedResponse;
        this.operator = Operator.table.get(name);
        if (operator == null) this.operator = Operator.nullOperator;
    }

    public Message(String name, String file, int line) {
        this(name, file, line, null);
    }

    public void setNext(Message next) {
        this.next = next;
    }

    public boolean isLast() {
        return this.next == null; //|| isTerminator();
    }

    public boolean isTerminator() {
        // Same as in Scanner.rl
        return name.equals("\n") || name.equals("\r\n") || name.equals(";");
    }

    public boolean isNoop() {
        // Same as in Scanner.rl
        return name.equals(".");
    }

    public boolean isOperator() {
        return operator != Operator.nullOperator;
    }

    // Get the tail of this message chain
    public Message tail() {
        if (isLast()) {
            return this;
        } else {
            return next.tail();
        }
    }

    // Insert `m` right after the current message.
    // Before: <prev> <self> <next>
    // After:  <prev> <self> <m> <next>
    public Message insert(Message m) {
        if (this.next != null) this.next.prev = m;
        this.next = m;
        return m;
    }

    // Append the message at the tail of the chain
    // Before: <self> <next> ... <last> <terminator?> ...
    // After:  <self> <next> ... <last> <m> <terminator?> ...
    public Message append(Message m) {
        tail().insert(m);
        return m;
    }

    // Detatch the next message chain and returns it.
    public Message detatch() {
        Message m = next;
        this.next = null;
        return m;
    }

    // Detach the last message from the chain
    public Message pop() {
        if (isLast()) {
            return null;
        } else if (next.isLast()) {
            return detatch();
        } else {
            return next.pop();
        }
    }

    public MinObject evalOn(MinObject on, MinObject base) throws MinException {
        // Noop operator just pass the message down the chain.
        if (isNoop()) {
            if (next == null) return on;
            return next.evalOn(on, base);
        }

        // Terminators reset the receiver to base
        if (isTerminator()) {
            if (next == null) return on;
            return next.evalOn(base);
        }

        MinObject response;
        if (this.cachedResponse == null) {
            try {
                response = on.getSlot(this.name).activate(new Call(this, on, base, args));
                // Handle some Java null => Min nil conversion
                if (response == null) response = MinObject.nil;
            } catch (Exception e) {
                // TODO Handle catching exception
                if (e instanceof MinException && e.getCause() != null) throw (MinException) e;
                throw new MinException(String.format("%s in %s:%d", e.getMessage(), file, line), e);
            }
        } else {
            response = this.cachedResponse;
        }

        if (this.next == null) return response;
        return this.next.evalOn(response, base);
    }

    public MinObject evalOn(MinObject on) throws MinException {
        return evalOn(on, on);
    }

    public String toString() {
        return fullName();
    }

    public String fullName() {
        return fullName("");
    }

    private String fullName(String indent) {
        StringBuilder b = new StringBuilder();

        // b.append(name.equals("\n") ? "<\\n>" : name);
        b.append(name);
        if (isTerminator()) b.append(indent);

        if (this.args.size() > 0) {
            b.append("(");
            String argIndent = indent + "  ";
            for (Message arg : this.args) {
                if (arg != this.args.get(0)) b.append(", ");
                b.append(arg.fullName(argIndent));
            }
            b.append(")");
        }

        if (this.next != null) {
            if (!isNoop() && !next.isNoop()) b.append(" ");
            b.append(this.next.fullName());
        }

        return b.toString();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Message clone() {
        Message m = new Message(name, file, line, cachedResponse);
        m.next = this.next;
        m.args = this.args;
        return m;
    }

    static public Message parse(String code, String file) throws ParsingException {
        Message m = new Scanner(code, file).scan();
        m = new Shuffler().shuffle(m);
        return m;
    }

    static public Message parse(String code) throws ParsingException {
        return parse(code, "<eval>");
    }

}
