package min;

import min.lang.MinObject;
import min.lang.Message;
import min.lang.Method;
import min.lang.Bootstrap;

public class main {
  public static void main(String[] args) throws Exception {
    Bootstrap.run();
    Message.parse(args[0]).evalOn(MinObject.lobby);
  }
}