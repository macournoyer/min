package min.lang;

import java.util.ArrayList;

public class Message extends MinObject {
  String name;
  Message prev;
  Message next;
  ArrayList<Message> args;
  
  public Message(String name, Message next) {
    this.name = name;
    this.setNext(next);
    this.prev = null;
    this.args = new ArrayList<Message>();
  }
  
  public Message(String name) {
    this(name, null);
  }
  
  public void setNext(Message next) {
    this.next = next;
    if (next != null) next.prev = this;
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
      while (tail != null && tail.name != ".") tail = tail.next;
    }
    if (tail != null) prev = tail.prev;
    this.prev.next = tail;
    this.prev = null;
    if (tail != null) prev.next = null;
    return this;
  }
  
  public Message shuffle() {
    return this;
  }
  
  public boolean isTerm() {
    return this.name.equals("\n") || this.name.equals("\r\n") || this.name.equals(".");
  }
  
  public MinObject evalOn(MinObject on, MinObject base) throws MinException {
    if (this.isTerm()) {
      if (this.next == null) return on;
      return this.next.evalOn(base, base);
    }
    MinObject response = on.getSlot(this.name).activate(on);
    if (this.next == null) return response;
    return this.next.evalOn(response, base);
  }
  
  public MinObject evalOn(MinObject on) throws MinException {
    return evalOn(on, on);
  }
  
  public String toString() {
    return "<" + this.name + "> " + (this.next == null ? "" : this.next.toString());
  }
  
  static public Message parse(String code) throws ParsingException {
    return new Scanner(code).scan();
  }
}
