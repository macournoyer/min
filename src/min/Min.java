package min;

import min.lang.MinObject;
import min.lang.Message;
import min.lang.File;
import min.lang.Bootstrap;

import java.util.Scanner;

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
            else if (args[i].equals("--help")) usage();
            else if (args[i].equals("-x")) repl();
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
        System.out.println("       -x REPL (not implemented yet)");
        System.exit(1);
    }

    private static void repl() {
        Boolean LoopAgain = true;
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("REPL not implemented yet");
        System.out.println("------------------------");
        System.out.println("Type 'bye' to exit\n");

        while(LoopAgain) {
            System.out.print("min> ");
            input = scanner.nextLine();
            System.out.println(input);
            if(input.equals("bye")) LoopAgain = false;
        }
        System.out.println("Bye!\n");
        System.exit(1);
    }

}