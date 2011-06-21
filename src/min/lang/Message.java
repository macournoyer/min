package min.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Message extends MinObject {
  String name;
  String file;
  int line;
  Message prev;
  Message next;
  ArrayList<Message> args;
  MinObject cachedResponse;
  
  // Same as in Scanner.rl
  // IMPORTANT: needs to be sorted!
  static String terminators[]      = new String[] { "\n", "\r\n", "." };
  static String ternaryOperators[] = new String[] { "=" };
  static String binaryOperators[]  = new String[] { "!", "!=", "%", "&", "&&", "*", "**",
                                                    "+", "-", "/", "<", "<<", "<=", "=",
                                                    "==", ">", ">=", "?", "^", "|", "||" };
  
  public Message(String name, String file, int line, MinObject cachedResponse) {
    super(MinObject.message);
    this.name = name;
    this.file = file;
    this.line = line;
    this.next = null;
    this.prev = null;
    this.args = new ArrayList<Message>();
    this.cachedResponse = cachedResponse;
  }
  
  public Message(String name, String file, int line) {
    this(name, file, line, null);
  }
  
  public void setNext(Message next) {
    this.next = next;
    if (next != null) next.prev = this;
  }
  
  public boolean isTerminator() {
    return Arrays.binarySearch(terminators, name) >= 0;
  }

  public boolean isTernaryOperator() {
    return Arrays.binarySearch(ternaryOperators, name) >= 0;
  }
  
  public boolean isBinaryOperator() {
    return Arrays.binarySearch(binaryOperators, name) >= 0;
  }
  
  public Message replace(Message with) {
    this.name = with.name;
    this.next = with.next;
    this.prev = with.prev;
    this.args = with.args;
    return this;
  }

  // Remove the message from the chain
  public Message pop() {
    Message tail = this.next;
    Message prev = null;
    while (tail != null && !tail.isTerminator()) tail = tail.next;
    if (tail != null) prev = tail.prev;
    this.prev.next = tail;
    this.prev = null;
    if (tail != null) prev.next = null;
    return this;
  }
  
  public Message shuffle() throws ParsingException {
    if (isTernaryOperator()) {
      if (prev == null) throw new ParsingException("Missing variable name before =", file, line);
      Message var = prev.clone();
      var.next = null;
      args = new ArrayList<Message>();
      args.add(var);
      if (next == null) throw new ParsingException(String.format("Missing value after %s =", var.name), file, line);
      args.add(next.pop());
      prev.replace(this);
    } else if (isBinaryOperator()) {
      this.args = new ArrayList<Message>();
      if (next == null) throw new ParsingException(String.format("Missing value after %s", name), file, line);
      args.add(next.pop());
    }
    for (Message arg : args) arg.shuffle();
    if (next != null) next.shuffle();
    return this;
  }
  
  public MinObject evalOn(MinObject on, MinObject base) throws MinException {
    if (this.isTerminator()) {
      if (this.next == null) return on;
      return this.next.evalOn(base);
    }
    
    MinObject response = null;
    if (this.cachedResponse == null) {
      try {
        response = on.getSlot(this.name).activate(new Call(this, on, base, args));
        // Handle some Java null => Min nil conversion
        if (response == null) response = MinObject.nil;
      } catch (Exception e) {
        // TODO Handle catching exception
        if (e instanceof MinException && e.getCause() != null) throw (MinException)e;
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
    return toString("");
  }
  
  public String toString(String indent) {
    StringBuilder b = new StringBuilder();
    
    b.append(this.name);
    if (isTerminator()) b.append(indent);
    
    if (this.args.size() > 0) {
      b.append("(");
      String argIndent = indent + "  ";
      for (Message arg : this.args) {
        if (arg != this.args.get(0)) b.append(", ");
        b.append(arg.toString(argIndent));
      }
      b.append(")");
    }
    
    if (this.next != null) {
      if (!isTerminator()) b.append(" ");
      b.append(this.next.toString());
    }
    
    return b.toString();
  }
  
  public Message clone() {
    Message m = new Message(name, file, line, cachedResponse);
    m.next = this.next;
    m.prev = this.prev;
    m.args = this.args;
    return m;
  }
  
  static public Message parse(String code, String file) throws ParsingException {
    return new Scanner(code, file).scan().shuffle();
  }
  
  static public Message parse(String code) throws ParsingException {
    return new Scanner(code, "<eval>").scan().shuffle();
  }
}
