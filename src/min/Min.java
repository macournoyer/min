package min;

import min.lang.*;

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

    private static void repl() throws MinException {
        Boolean LoopAgain = true;
        Scanner scanner = new Scanner(System.in);
        String input;
        Message message;

        new Bootstrap().run();

        System.out.println("REPL not implemented yet");
        System.out.println("------------------------");
        System.out.println("Type 'bye' to exit\n");

        while(LoopAgain) {
            System.out.print("min> ");
            input = scanner.nextLine();

            if(input.equals("bye"))  break;

            try {
                message = Message.parse(input, "<eval>");
                System.out.print(input + " > ");
                message.evalOn(MinObject.lobby);
                System.out.println("");
            } catch (Exception e) {

                System.out.println(e);
            }
        }

        System.out.println("Bye!\n");
        System.exit(1);
    }

}