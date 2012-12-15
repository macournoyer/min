package min.lang;

import java.util.Stack;

public class Scanner {
  String input;
  String filename;
  int line;
  Message root = null;
  Message message = null;
  Stack<Message> argStack = new Stack<Message>();
  Stack<Integer> indentStack = new Stack<Integer>();
  int currentIndent = 0;
  boolean inBlock = false;
  boolean singleBlock = false;
  boolean debug = false;

  public Scanner(String input, String filename) {
    this.input = input;
    this.filename = filename;
  }

  %%{
    machine Scanner;
    
    action mark { mark = p; }
    
    newline     = "\r"? "\n" %{ line++; };
    indent      = "  " | "\t";
    block       = newline+ %mark indent+;
    whitespace  = " ";
    comment     = whitespace* "#" (any - newline)* newline?;
    string      = ("'" (any - "'")* "'" )
                | ('"' (any - '"')* '"' );
    number      = [0-9]+;
    single      = "$" | "@";
    identifier  = ( alnum | "_" )+ ( "?" | "!" )?;
    operator    = "+" | "-" | "*" | "/" | "**" | "^" | "%"
                | "||" | "|" | "&&" | "&"
                | "<<"
                | "<=" | "<" | ">=" | ">"
                | "==" | "!=" | "!"
                | "="
                | "?"
                | "."
                | ";";
    terminator  = newline | ";";
    symbol      = single | identifier | operator;
    
    main := |*
      # Indentation magic
      ":" whitespace* block => {
        int indent = (te - mark) / 2;
        // creating new block
        startBlock();
        // add indent level
        pushIndent(indent);
        debugIndent("+", indent);
      };
      ":" => {
        startSingleBlock();
        pushIndent(0);
        debugIndent("+", 0);
      };
      block => {
        int indent = (te - mark) / 2;
        if (!singleBlock && indent > currentIndent) { // indent in same block
          debugIndent("/", indent);
        } else if (!singleBlock && indent == currentIndent) { // same block
          pushTerminator();
          debugIndent("=", indent);
        } else if (singleBlock || inBlock && indent < currentIndent) { // dedent
          while (!indentStack.isEmpty() && indentStack.peek() != indent) {
            indentStack.pop();
            message = argStack.pop();
            if (argStack.empty()) inBlock = false;
            singleBlock = false;
            pushTerminator();
            debugIndent("-", indent);
          }
        } else {
          pushTerminator();
        }
        currentIndent = indent;
      };
      terminator  => {
        emptyIndentStack();
        pushTerminator(getSlice(ts, te));
      };
      
      # Ignored tokens
      whitespace;
      comment;
      
      # Symbols and literals
      string           => { pushMessage(new Message(getSlice(ts, te), filename, line, MinObject.newString(getSlice(ts + 1, te - 1)))); };
      number           => { pushMessage(new Message(getSlice(ts, te), filename, line, MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); };
      symbol           => { pushMessage(new Message(getSlice(ts, te), filename, line)); };
      "(" terminator*  => {
        if (message == null) pushMessage(new Message("", filename, line));
        argStack.push(message);
        message = null;
      };
      "," terminator*  => { message = null; };
      ")"              => {
        if (argStack.empty())
          throw new ParsingException("Unmatched closing parenthesis at line " + line);
        message = argStack.pop();
      };
    *|;
    
    write data nofinal;
  }%%
  
  @SuppressWarnings("fallthrough")
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs, top;
    int eof = data.length;
    int p = 0, pe = eof, ts = 0, te = 0, act = 0, mark = 0;
    int[] stack = new int[32];
    line = 1;
    
    %% write init;
    %% write exec;
    
    if (cs == Scanner_error || p != pe)
      throw new ParsingException(String.format("Syntax error at line %d around '%s...'", line, input.substring(p, Math.min(p+5, pe))));
    
    if (root == null) return new Message("\n", filename, line);
    
    emptyIndentStack();
    
    if (!argStack.empty())
      throw new ParsingException(argStack.size() + " unclosed parenthesis at line " + line);
    
    return root;
  }
  
  private String getSlice(int start, int end) {
    return input.substring(start, end);
  }
  
  private void emptyIndentStack() {
    while (!indentStack.empty()) {
      indentStack.pop();
      message = argStack.pop();
    }
    currentIndent = 0;
    inBlock = false;
  }
  
  private Message pushMessage(Message m) {
    if (message != null)
      message.setNext(m);
    else if (!argStack.empty())
      argStack.peek().args.add(m);
      
    message = m;
    
    if (root == null) root = message;
    return m;
  }
  
  private Message pushUniqueMessage(Message m) {
    if (message != null && message.name.equals(m.name)) return message;
    return pushMessage(m);
  }
  
  private Message pushTerminator() {
    return pushTerminator("\n");
  }
  
  private Message pushTerminator(String name) {
    return pushUniqueMessage(new Message(name, filename, line));
  }
  
  private void startBlock() {
    inBlock = true;
    argStack.push(message);
    message = null;
  }
  
  private void startSingleBlock() {
    inBlock = true;
    singleBlock = true;
    argStack.push(message);
    message = null;
  }
  
  private void pushIndent(int indent) {
    indentStack.push(indent);
    currentIndent = indent;
  }
  
  private void debugIndent(String action, int indent) {
    if (debug)
      System.out.println(String.format("[%s:%02d] %s to %d was %d    indentStack: %-20s  singleBlock? %b", filename, line, action, indent, currentIndent, indentStack.toString(), singleBlock));
  }
}