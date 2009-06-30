package min.lang;

public class Scanner {
  private final String input;

  public Scanner(String input) {
    this.input = input;
  }

  %%{
    machine Scanner;
    
    action mark { mark = p; }
    action message {
      next = new Message(getSlice(mark, p));
      if (message != null) message.setNext(next);
      message = next;
      if (root == null) root = message;
    }
    
    comma       = ",";
    newline     = "\r"? "\n" %{ lineno++; };
    whitespace  = " " | "\f" | "\t" | "\v";
    term        = ( "." | newline ) >mark %message;
    id          = ( [a-z] [a-z0-9]* ) >mark %message;
    message     = ( id term? | term );
    
    write data;
    
    main := message ( whitespace+ message )*;
  }%%
  
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs;
    int top;
    int[] stack = new int[32];
    int eof = data.length;
    int p = 0;
    int pe = eof;
    int mark = 0;
    int lineno = 1;
    Message root = null;
    Message message = null;
    Message next = null;
    
    %% write init;
    %% write exec;
    
    if (cs == Scanner_error || p != pe) {
      // TODO Better error reporting
      throw new ParsingException(String.format("Syntax error at line %d", lineno));
    }
    
    System.out.println(root.toString());
    return root;
  }
  
  private String getSlice(int start, int end) {
    return input.substring(start, end);
  }
}