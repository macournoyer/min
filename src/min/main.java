package min;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;

import min.lang.MinObject;
import min.lang.Message;
import min.lang.Method;
import min.lang.Bootstrap;

public class main {
  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.println("usage: min < -e code | file.min >");
      System.exit(1);
    }
    
    String code = null;
    if (args[0] == "-e")
      code = args[1];
    else
      code = readFile(args[0]);

    Bootstrap.run();
    Message.parse(code).evalOn(MinObject.lobby);
  }
  
  // Taken from http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
  private static String readFile(String path) throws IOException {
    FileInputStream stream = new FileInputStream(new File(path));
    try {
      FileChannel fc = stream.getChannel();
      MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
      /* Instead of using default, pass in a decoder. */
      return java.nio.charset.Charset.defaultCharset().decode(bb).toString();
    }
    finally {
      stream.close();
    }
  }
}