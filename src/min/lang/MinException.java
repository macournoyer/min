package min.lang;

@SuppressWarnings("serial")
public class MinException extends Exception {

  public MinException(String message) {
    super(message);
  }

  public MinException(Throwable cause) {
    super(cause);
  }
  
  public MinException(String message, Throwable cause) {
    super(message, cause);
  }

}