package min.lang;

import java.util.ArrayList;
import java.util.Collection;

public class Message extends MinObject {
  String name;
  Message prev;
  Message next;
  ArrayList<Message> args;
  MinObject cachedResponse;
  
  public Message(String name, MinObject cachedResponse) {
    this.name = name;
    this.next = null;
    this.prev = null;
    this.args = new ArrayList<Message>();
    this.cachedResponse = cachedResponse;
  }
  
  public Message(String name) {
    this(name, null);
  }
  
  public void setNext(Message next) {
    this.next = next;
    if (next != null) next.prev = this;
  }
  
  public boolean isTerminator() {
    return this.name.equals("\n") || this.name.equals("\r\n") || this.name.equals(".");
  }

  public boolean isTernaryOperator() {
    return this.name.equals("=");
  }
  
  public boolean isBinaryOperator() {
    // Same as in Scanne.rl
    return this.name.equals("+") || this.name.equals("-") || this.name.equals("*") || this.name.equals("/") ||
           this.name.equals("**") || this.name.equals("^") || this.name.equals("%") || this.name.equals("||") ||
           this.name.equals("|") || this.name.equals("&&") || this.name.equals("&") || this.name.equals("<<") ||
           this.name.equals("<=") || this.name.equals("<") || this.name.equals(">=") || this.name.equals(">") ||
           this.name.equals("==") || this.name.equals("!=") || this.name.equals("!");
  }
  
  public Message replace(Message with) {
    this.name = with.name;
    this.next = with.next;
    this.prev = with.prev;
    this.args = with.args;
    return this;
  }

  public Message pop(boolean untilTerm) {
    Message tail = this.next;
    Message prev = null;
    if (untilTerm) {
      while (tail != null && !tail.isTerminator()) tail = tail.next;
    }
    if (tail != null) prev = tail.prev;
    this.prev.next = tail;
    this.prev = null;
    if (tail != null) prev.next = null;
    return this;
  }
  
  public Message shuffle() {
    if (isTernaryOperator()) {
      Message var = this.prev.clone();
      var.next = null;
      this.args = new ArrayList<Message>();
      this.args.add(var);
      this.args.add(this.next.pop(true));
      this.prev.replace(this);
    } else if (isBinaryOperator()) {
      this.args = new ArrayList<Message>();
      this.args.add(this.next.pop(false));
    }
    for (Message arg : this.args) arg.shuffle();
    if (this.next != null) this.next.shuffle();
    return this;
  }
  
  public MinObject evalOn(MinObject on, MinObject base) throws MinException {
    if (this.isTerminator()) {
      if (this.next == null) return on;
      return this.next.evalOn(base, base);
    }
    
    MinObject response = null;
    if (this.cachedResponse == null)
      response = on.getSlot(this.name).activate(new Call(this, on, base, args));
    else
      response = this.cachedResponse;
    
    if (this.next == null) return response;
    return this.next.evalOn(response, base);
  }
  
  public MinObject evalOn(MinObject on) throws MinException {
    return evalOn(on, on);
  }
  
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("<`");
    b.append(this.name);
    b.append("`");
    if (this.args.size() > 0) {
      b.append("(");
      for (Message arg : this.args) {
        if (arg != this.args.get(0)) b.append(", ");
        b.append(arg.toString());
      }
      b.append(")");
    }
    b.append(">");
    if (this.next != null) {
      b.append(" ");
      b.append(this.next.toString());
    }
    return b.toString();
  }
  
  public Message clone() {
    Message m = new Message(this.name, this.cachedResponse);
    m.next = this.next;
    m.prev = this.prev;
    m.args = this.args;
    return m;
  }
  
  static public Message parse(String code) throws ParsingException {
    return new Scanner(code).scan().shuffle();
  }
}
