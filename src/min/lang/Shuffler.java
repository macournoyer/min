package min.lang;

import min.lang.ParsingException;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

// The fancy message shuffler.
// Based on the Shunting yard algo.
public class Shuffler {
  public Message shuffle(Message m) throws ParsingException {
    LinkedList<Message> outputQueue = new LinkedList<Message>();
    LinkedList<Message> stack = new LinkedList<Message>();
    
    Message m2;

    while (m != null) {
      if (m.isOperator()) {
        m2 = stack.peek();
        while (m2 != null &&
               (m.operator.isLeftToRight() && m.operator.precedence <= m2.operator.precedence) ||
               (m.operator.isRightToLeft() && m.operator.precedence < m2.operator.precedence)) {
          outputQueue.add(stack.pop());
          m2 = stack.peek();
        }
        stack.push(m);
      } else {
        outputQueue.add(m);
        // Advance to the next operator
        // TODO perhaps could refactor to combine w/ parent while?
        while (m.next != null && !m.next.isOperator()) {
          m = m.next;
        }
      }

      // Cut it from the message chain
      m = m.detatch();
    }

    // Pop the operators from the queue
    while (!stack.isEmpty()) outputQueue.add(stack.pop());

    // Reorder messages from RPN in queue.
    stack.clear();
    while (!outputQueue.isEmpty()) {
      m = outputQueue.pop();
      if (m.isOperator()) {
        if (m.operator.isNullary()) {
          Message after = stack.pop();
          Message before = stack.pop();
          before.append(m);
          m.append(after);
          stack.push(before);
        } else if (m.operator.isUnary()) {
          Message arg = stack.pop();
          m.insert(arg.detatch());
          m.args.add(arg);
          stack.push(m);
        } else if (m.operator.isBinary()) {
          m.args.add(stack.pop());
          m2 = stack.pop();
          m2.append(m);
          stack.push(m2);
        } else if (m.operator.isTernary()) {
          Message val = stack.pop();
          Message receiver = stack.pop();
          Message name = receiver.pop();
          if (name != null) {
            m.args.add(name);
            m.args.add(val);
            receiver.append(m);
            stack.push(receiver);
          } else {
            m.args.add(receiver);
            m.args.add(val);
            stack.push(m);
          }
        }
      } else {
        stack.push(m);
      }
    }

    // Empty the stack
    m = null;
    while (!stack.isEmpty()) {
      Message n = m;
      m = stack.pop();
      if (n != null) m.append(n);
    }

    System.out.println("ok");

    return m;
  }
}