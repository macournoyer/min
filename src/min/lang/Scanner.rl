package min.lang;

import java.util.Stack;

public class Scanner {
  final String input;
  Message root = null;
  Message message = null;
  Stack<Message> argStack = new Stack<Message>();

  public Scanner(String input) {
    this.input = input;
  }

  %%{
    machine Scanner;
    
    newline     = "\r"? "\n" %{ lineno++; };
    whitespace  = " ";
    comment     = "#" (any - newline)* newline;
    string      = ("'" (any - "'")* "'" )
                | ('"' (any - '"')* '"' );
    number      = [0-9]+;
    identifier  = ( alnum | "_" | "$" | "@" )+;
    operator    = "+" | "-" | "*" | "/" | "**" | "^" | "%"
                | "||" | "|" | "&&" | "&"
                | "<=" | "<" | ">=" | ">"
                | "==" | "!=" | "!";
    terminator  = newline | ";" | ".";
    symbol      = identifier | operator | terminator;
    
    main := |*
      whitespace;
      comment;
      string      => { pushMessage(new Message(getSlice(ts, te), MinObject.newString(getSlice(ts + 1, te - 1)))); };
      number      => { pushMessage(new Message(getSlice(ts, te), MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); };
      symbol      => { pushMessage(new Message(getSlice(ts, te))); };
      "("         => { this.argStack.push(this.message); this.message = null; };
      ","         => { this.message = null; };
      ")"         => {
        if (this.argStack.empty())
          throw new ParsingException("Unmatched closing parenthesis at line " + lineno);
        this.message = this.argStack.pop();
      };
    *|;
    
    write data nofinal;
  }%%
  
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs, top;
    int eof = data.length;
    int p = 0, pe = eof, ts = 0, te = 0, act = 0;
    int[] stack = new int[32];
    int lineno = 1;
    
    %% write init;
    %% write exec;
    
    if (cs == Scanner_error || p != pe) {
      // TODO Better error reporting
      throw new ParsingException(String.format("Syntax error at line %d around '%s...'", lineno, input.substring(p, Math.min(p+5, pe))));
    }
    
    if (!this.argStack.empty())
      throw new ParsingException(this.argStack.size() + " unclosed parenthesis at line " + lineno);
    
    return this.root;
  }
  
  private String getSlice(int start, int end) {
    return input.substring(start, end);
  }
  
  private Message pushMessage(Message m) {
    if (this.message != null)
      message.setNext(m);
    else if (!this.argStack.empty())
      this.argStack.peek().args.add(m);
      
    this.message = m;
    
    if (this.root == null) this.root = this.message;
    return m;
  }
}