package min.lang;

import java.util.LinkedList;
import java.util.Iterator;

public class Scheduler {
  static Scheduler current;
  LinkedList<Evaluator> evaluators;

  public Scheduler() {
    evaluators = new LinkedList<Evaluator>();
  }

  public Evaluator schedule(Message message, MinObject on, MinObject base) {
    Evaluator evaluator = new Evaluator(message, on, base);
    evaluators.push(evaluator);
    return evaluator;
  }

  public Evaluator schedule(Message message) {
    return schedule(message, MinObject.lobby, MinObject.lobby);
  }

  public MinObject runAndWaitForReturn(Message message, MinObject on, MinObject base) throws MinException {
    Evaluator evaluator = schedule(message, on, base);
    while (!evaluator.isDone()) runOnce();
    return evaluator.result();
  }

  public void run() {
    while (!evaluators.isEmpty()) runOnce();
  }

  public void runOnce() {
    // TODO any better way to allow additions to evaluators while iterating it?
    LinkedList<Evaluator> it = new LinkedList<Evaluator>(evaluators);
    for (Evaluator evaluator : it) {
      evaluator.yield();
      if (evaluator.isDone()) evaluators.remove(evaluator);
    }

    // Iterator<Evaluator> it = evaluators.iterator();
    // while (it.hasNext()) {
    //   Evaluator evaluator = it.next();

    //   evaluator.yield();

    //   if (evaluator.isDone()) it.remove();
    // }
  }

  public static Scheduler current() {
    if (current == null) current = new Scheduler();
    return current;
  }
}