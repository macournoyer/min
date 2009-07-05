package min.lang;

public class MinException extends Exception {
  public MinException(String message) {
    super(message);
  }

  public MinException(Exception inner) {
    super(inner);
  }
}