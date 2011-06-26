package min.lang;

import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedList;

public class Message extends MinObject {
  String name;
  String file;
  int line;
  Message prev;
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
    if (next != null) next.prev = this;
  }
  
  public boolean isTerminator() {
    // Same as in Scanner.rl
    return name.equals("\n") || name.equals("\r\n") || name.equals(".");
  }

  public boolean isOperator() {
    return operator != Operator.nullOperator;
  }
  
  public Message replace(Message with) {
    this.name = with.name;
    this.next = with.next;
    this.prev = with.prev;
    this.args = with.args;
    return this;
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
    Message tail = this.next;
    Message prev = null;
    while (tail != null && !tail.isTerminator()) tail = tail.next;
    if (tail != null) prev = tail.prev;
    if (this.prev != null) this.prev.next = tail;
    this.prev = null;
    if (tail != null) prev.next = null;
    return this;
  }
  
  public Message shuffle() throws ParsingException {
    Message m = this, m2 = null;
    Stack<Message> stack = new Stack<Message>();
    LinkedList<Message> queue = new LinkedList<Message>();
    
    while (m != null) {
      if (m.isOperator()) {
        if (!stack.isEmpty()) m2 = stack.peek();
        while (!stack.isEmpty() && ((m.operator.isLeftToRight() && m.operator.precedence <= m2.operator.precedence) ||
                                   (m.operator.isRightToLeft() && m.operator.precedence < m2.operator.precedence))) {
           queue.add(stack.pop());
           if (!stack.isEmpty()) m2 = stack.peek();
        }
        stack.push(m);
        
      } else if (m.prev == null || m.prev.isOperator()) { // Non-operator
        queue.add(m);
        
      }
      
      for (Message arg : m.args) arg.shuffle();
      m = m.next;
    }
    
    while (!stack.isEmpty()) {
      queue.add(stack.pop());
    }
    
    // Convert from the queue RPN to a message chain.
    while(!queue.isEmpty()) {
      m = queue.remove();
      
      Message operand1, operand2;
      
      if (m.isOperator()) {
        if (m.operator.isNullary()) {
          operand1 = stack.pop();
          
          if (!stack.isEmpty()) {
            operand2 = stack.pop();
            m.setNext(operand1); // post terminator message
            m2 = operand2.pop();
          } else {
            m2 = operand1;
          }
          m2.append(m);
          
          stack.push(m2);
          
        } else if (m.operator.isUnary()) {
          m.pop();
          m.args.add(stack.pop().pop());
          stack.push(m);
          
        } else if (m.operator.isBinary()) {
          m.pop();
          m.args.add(stack.pop().pop());
          m2 = stack.pop().pop();
          m2.append(m);
          stack.push(m2);
          
        } else if (m.operator.isTernary()) {
          m.pop();
          m2 = stack.pop().pop();
          m.args.add(stack.pop().pop());
          m.args.add(m2);
          stack.push(m);
          
        }
        
      } else {
        stack.push(m.pop());
        
      }
    }
    
    return stack.pop();
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
