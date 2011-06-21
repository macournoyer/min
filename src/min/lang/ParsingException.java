package min.lang;

@SuppressWarnings("serial")
public class ParsingException extends MinException {
  public ParsingException(String message) {
    super(message);
  }
  
  public ParsingException(String message, String file, int line) {
    super(String.format("%s in %s:%d", message, file, line));
  }
}