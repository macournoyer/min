package min.lang;

public class Evaluator {
  Message head;
  Message message;
  MinObject on;
  MinObject base;
  MinObject result;
  MinException exception;
  Boolean busy;

  public Evaluator(Message message, MinObject on) {
    this(message, on, on);
  }

  public Evaluator(Message message, MinObject on, MinObject base) {
    this.head = message;
    this.message = message;
    this.on = on;
    this.base = base;
    busy = false;
  }

  public void yield() {
    if (busy || isDone()) return;

    result = null;

    try {
      busy = true;
      result = evaluate();
    } catch (MinException e) {
      exception = e;
    } finally {
      busy = false;
    }
  }

  public Boolean isDone() {
    return result != null || message == null || exception != null;
  }

  public MinObject result() throws MinException {
    if (exception != null) throw exception;
    if (result == null) throw new MinException("No result");
    return result;
  }

  // Evaluate one message.
  // Returns a MinObject if immediate result available
  // Returns null if not.
  // `message` will be set to next message to evaluate.
  private MinObject evaluate() throws MinException {
    // Noop operator just pass the message down the chain.
    if (message.isNoop()) {
      if (message.next == null) return on;
      message = message.next;
      return null;
    }
    
    // Terminators reset the receiver to base
    if (message.isTerminator()) {
      if (message.next == null) return on;
      on = base; // reset to base
      message = message.next;
      return null;
    }

    MinObject response = null;
    if (message.cachedResponse == null) {
      try {
        response = on.getSlot(message.name).activate(new Call(message, on, base, message.args));
        // Handle some Java null => Min nil conversion
        if (response == null) response = MinObject.nil;
      } catch (Exception e) {
        // TODO Handle catching exception
        if (e instanceof MinException && e.getCause() != null) throw (MinException)e;
        throw new MinException(String.format("%s in %s:%d", e.getMessage(), message.file, message.line), e);
      }
    } else {
      response = message.cachedResponse;
    }
    
    if (message.next == null) return response;
    on = response;
    message = message.next;
    return null;
  }
}