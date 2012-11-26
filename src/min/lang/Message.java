package min.lang;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

public class Message extends MinObject {
  String name;
  String file;
  int line;
  Message next;
  ArrayList<Message> args;
  MinObject cachedResponse;
  Operator operator;
  
  public Message(String name, String file, int line, MinObject cachedResponse) {
    super(MinObject.message);
    this.name = name;
    this.file = file;
    this.line = line;
    this.next = null;
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
    return this.next == null;
  }

  public Message detatch() {
    Message m = next;
    this.next = null;
    return m;
  }
  
  public boolean isTerminator() {
    // Same as in Scanner.rl
    return name.equals("\n") || name.equals("\r\n") || name.equals(".");
  }

  public boolean isOperator() {
    return operator != Operator.nullOperator;
  }
  
  public Message tail() {
    Message tail = this;
    while (tail.next != null && !tail.next.isTerminator()) tail = tail.next;
    return tail;
  }
  
  public Message append(Message m) {
    tail().setNext(m);
    return this;
  }

  // Remove the message from the chain
  public Message pop() {
    if (isLast()) {
      return null;
    } else if (next.isLast()) {
      return detatch();
    } else {
      return next.pop();
    }
  }
  
  public Message shuffle() throws ParsingException {
    Queue<Message> outputQueue = new LinkedList<Message>();
    Stack<Message> stack = new Stack<Message>();
    
    Message m = this,
            m2;

    while (m != null) {
      if (m.isOperator()) {
        m2 = stack.peek();
        while (m2 && ((m.operator.isLeftToRight() && m.operator.precedence <= m2.operator.precedence) ||
                      (m.operator.isRightToLeft() && m.operator.precedence < m2.operator.precedence))) {
          output_queue.push(stack.pop());
          m2 = stack.peek();
        }
        stack.push(m);
      } else {
        output_queue.push(m);
        // Advance to the next operator
        // TODO perhaps could refactor to combine w/ parent while?
        while (m.next != null && !m.next.isOperator()) {
          m = m.next
        }
      }
    }

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
  
  // public String toString() {
  //   return name;
  // }

  public String toString() {
    return fullName();
  }
  
  public String fullName() {
    return fullName("");
  }
  
  private String fullName(String indent) {
    StringBuilder b = new StringBuilder();
    
    b.append(this.name);
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
      if (!isTerminator()) b.append(" ");
      b.append(this.next.fullName());
    }
    
    return b.toString();
  }
  
  public Message clone() {
    Message m = new Message(name, file, line, cachedResponse);
    m.next = this.next;
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
