package min.lang;

import java.io.IOException;
import java.io.File;

public class Dir extends MinObject {
  static public String current() throws MinException {
    try {
      return new File(".").getCanonicalPath();
    } catch (IOException e) {
      throw new MinException(e);
    }
  }
}