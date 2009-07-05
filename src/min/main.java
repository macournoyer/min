package min;

import min.lang.MinObject;
import min.lang.Message;
import min.lang.Method;
import min.lang.File;
import min.lang.Bootstrap;

public class main {
  public static void main(String[] args) throws Exception {
    String code = null;
    boolean debug = false;
    
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-e")) code = args[++i];
      else if (args[i].equals("-d")) debug = true;
      else code = File.read(args[i]);
    }
    
    if (code == null) {
      System.out.println("usage: min [-d] < -e code | file.min >");
      System.exit(1);
    }

    new Bootstrap().run();
    Message message = Message.parse(code);
    if (debug) System.out.println(message.toString());
    message.evalOn(MinObject.lobby);
  }
}