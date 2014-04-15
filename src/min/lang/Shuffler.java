package min.lang;

//import min.lang.ParsingException;
//import java.util.Queue;
//import java.util.Stack;

import java.util.ArrayList;
import java.util.LinkedList;

// The fancy message shuffler.
// Based on the Shunting yard algo.
public class Shuffler {

    public void shuffleAllInPlace(ArrayList<Message> messages) throws ParsingException {
        for (Message m : messages) {
            messages.set(messages.indexOf(m), shuffle(m));
        }
    }

    public Message shuffle(Message m) throws ParsingException {
        LinkedList<Message> outputQueue = new LinkedList<Message>();
        LinkedList<Message> stack = new LinkedList<Message>();

        // System.out.println("Before shuffling: '" + m.fullName() + "'");

        Message m2;

        while (m != null) {
            if (m.isOperator()) {
                m2 = stack.peek();
                while (m2 != null &&
                        ((m.operator.isLeftToRight() && m.operator.precedence <= m2.operator.precedence) ||
                                (m.operator.isRightToLeft() && m.operator.precedence < m2.operator.precedence))) {
                    outputQueue.add(stack.pop());
                    m2 = stack.peek();
                }
                stack.push(m);
            } else {
                outputQueue.add(m);
                // Advance to the next operator
                // TODO perhaps could refactor to combine w/ parent while?
                shuffleAllInPlace(m.args);
                while (m.next != null && !m.next.isOperator()) {
                    m = m.next;
                    shuffleAllInPlace(m.args);
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
                    Message after = null, before = null;
                    if (!stack.isEmpty()) after = stack.pop();
                    if (!stack.isEmpty()) before = stack.pop();
                    if (after != null) m.append(after);
                    if (before != null) {
                        before.append(m);
                        stack.push(before);
                    } else {
                        stack.push(m);
                    }
                } else if (m.operator.isUnary()) {
                    Message arg = stack.pop();
                    m.args.add(arg);
                    stack.push(m);
                } else if (m.operator.isBinary()) {
                    m.args.add(stack.pop());
                    Message receiver = stack.pop();
                    receiver.append(m);
                    stack.push(receiver);
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

        return m;
    }
}