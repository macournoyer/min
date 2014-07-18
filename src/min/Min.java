package min;

import jline.ConsoleReader;
import min.lang.*;

import java.io.IOException;

/*  Simplest of the simple stuff
    Entry point for main :
        Parsing args and setting flags
        Running the bootstrap
        Parse & eval code
 */
public class Min {

    /* main ! duh ! */

    public static boolean debug = false;

    public static void main(String[] args) throws Exception {

        String code = null;
        String file = "<eval>";
        Boolean launchREPL = false;

        /* parsing args */
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-e")) code = args[++i];
            else if (args[i].equals("-d")) debug = true;
            else if (args[i].equals("-h")) usage();
            else if (args[i].equals("--help")) usage();
            else if (args[i].equals("-x")) launchREPL = true;
            else code = File.read(file = args[i]);
        }

        /* Run bootstrap */
        new Bootstrap().run();

        if(launchREPL) repl();
        if (code == null) usage();


        /* run the scanner & eval code */
        Message message = Message.parse(code, file);
        if (debug) System.out.println(message.fullName());
        message.evalOn(MinObject.lobby);

    }

    /* Print help and exit */
    private static void usage() {
        System.out.println("usage: min [-d] < -e code | file.min >");
        System.out.println("       -x REPL (experimental)");
        System.exit(1);
    }

    private static void repl() throws MinException, IOException {
        //Boolean LoopAgain = true;
        ConsoleReader console = new ConsoleReader();
        console.setDefaultPrompt("min> ");

        String input;
        Message message;

        System.out.println("REPL (experimental)");
        System.out.println("-------------------");
        System.out.println("Type 'bye' to exit\n");

        while(true) {
            input = console.readLine();
            if(input.equals("bye"))  break;

            try {
                message = Message.parse(input, "<eval>");
                if(debug) System.out.println("debug >> " + message.fullName());
                System.out.print(">> ");
                message.evalOn(MinObject.lobby);
                System.out.println("");
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        System.out.println("Bye!\n");
        System.exit(1);
    }

}