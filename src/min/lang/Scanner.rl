package min.lang;

public class Scanner {
  private final String input;
  private Message root = null;
  private Message message = null;

  public Scanner(String input) {
    this.input = input;
  }

  %%{
    machine Scanner;
    
    action mark { mark = p; }
    action message { addMessage(new Message(getSlice(mark, p))); }
    action string { addMessage(new Message(getSlice(mark, p), MinObject.newString(getSlice(mark+1, p-1)))); }
    action number { addMessage(new Message(getSlice(mark, p), MinObject.newNumber(Integer.parseInt(getSlice(mark, p))))); }
    
    comma       = ",";
    newline     = "\r"? "\n" %{ lineno++; };
    whitespace  = " " | "\f" | "\t" | "\v";
    string      = ("'" (any - "'")* "'" ) >mark %string;
    dstring     = ('"' (any - '"')* '"' ) >mark %string;
    number      = [0-9]+ >mark %number;
    term        = ( "." | newline ) >mark %message;
    id          = ( [a-z] [a-z0-9]* ) >mark %message;
    literal     = id | string | dstring | number;
    message     = ( literal term? | term );
    
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
    
    %% write init;
    %% write exec;
    
    if (cs == Scanner_error || p != pe) {
      // TODO Better error reporting
      throw new ParsingException(String.format("Syntax error at line %d", lineno));
    }
    
    // System.out.println(this.root.toString());
    return this.root;
  }
  
  private String getSlice(int start, int end) {
    return input.substring(start, end);
  }
  
  private Message addMessage(Message m) {
    if (this.message != null) message.setNext(m);
    this.message = m;
    if (this.root == null) this.root = this.message;
    return m;
  }
}