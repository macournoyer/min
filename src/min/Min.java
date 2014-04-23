package min;

import min.lang.MinObject;
import min.lang.Message;
import min.lang.File;
import min.lang.Bootstrap;

/*  Simplest of the simple stuff
    Entry point for main :
        Parsing args and setting flags
        Running the bootstrap
        Parse & eval code
 */
public class Min {

    /* main ! duh ! */
    public static void main(String[] args) throws Exception {

        String code = null;
        String file = "<eval>";
        boolean debug = false;

        /* parsing args */
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-e")) code = args[++i];
            else if (args[i].equals("-d")) debug = true;
            else if (args[i].equals("-h")) usage();
            else code = File.read(file = args[i]);
        }

        if (code == null) {
            usage();
        }

        /* Run bootstrap */
        new Bootstrap().run();

        /* run the scanner & eval code */
        Message message = Message.parse(code, file);
        if (debug) System.out.println(message.fullName());
        message.evalOn(MinObject.lobby);

    }

    /* Print help and exit */
    private static void usage() {
        System.out.println("usage: min [-d] < -e code | file.min >");
        System.exit(1);
    }

}