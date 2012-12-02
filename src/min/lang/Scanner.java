
// line 1 "src/min/lang/Scanner.rl"
package min.lang;

import java.util.Stack;

public class Scanner {
  final String input;
  final String filename;
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

  
// line 27 "src/min/lang/Scanner.java"
private static byte[] init__Scanner_actions_0()
{
	return new byte [] {
	    0,    1,    2,    1,    3,    1,    4,    1,    9,    1,   10,    1,
	   11,    1,   12,    1,   13,    1,   14,    1,   16,    1,   17,    1,
	   18,    1,   19,    1,   20,    1,   21,    1,   22,    1,   23,    1,
	   24,    2,    1,    0,    2,    1,   15,    2,    1,   17,    2,    4,
	    5,    2,    4,    6,    2,    4,    7,    2,    4,    8,    4,    4,
	    1,    0,    5,    4,    4,    1,    0,    7
	};
}

private static final byte _Scanner_actions[] = init__Scanner_actions_0();


private static byte[] init__Scanner_key_offsets_0()
{
	return new byte [] {
	    0,    0,    1,    2,    4,    5,    6,    8,    9,   10,   13,   43,
	   45,   47,   49,   50,   50,   51,   52,   53,   54,   55,   64,   73,
	   76,   78,   80
	};
}

private static final byte _Scanner_key_offsets[] = init__Scanner_key_offsets_0();


private static char[] init__Scanner_trans_keys_0()
{
	return new char [] {
	   32,   10,   32,   35,   34,   39,    9,   32,   32,   10,   10,   13,
	   32,   10,   13,   32,   33,   34,   35,   38,   39,   40,   41,   42,
	   44,   58,   59,   60,   94,   95,  124,   36,   47,   48,   57,   61,
	   62,   63,   64,   65,   90,   97,  122,    9,   32,    9,   32,   32,
	   35,   10,   61,   38,   59,   42,   59,   33,   63,   95,   48,   57,
	   65,   90,   97,  122,   33,   63,   95,   48,   57,   65,   90,   97,
	  122,   10,   13,   32,    9,   32,   60,   61,  124,    0
	};
}

private static final char _Scanner_trans_keys[] = init__Scanner_trans_keys_0();


private static byte[] init__Scanner_single_lengths_0()
{
	return new byte [] {
	    0,    1,    1,    2,    1,    1,    2,    1,    1,    3,   18,    2,
	    2,    2,    1,    0,    1,    1,    1,    1,    1,    3,    3,    3,
	    2,    0,    1
	};
}

private static final byte _Scanner_single_lengths[] = init__Scanner_single_lengths_0();


private static byte[] init__Scanner_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    6,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,    3,    0,
	    0,    1,    0
	};
}

private static final byte _Scanner_range_lengths[] = init__Scanner_range_lengths_0();


private static byte[] init__Scanner_index_offsets_0()
{
	return new byte [] {
	    0,    0,    2,    4,    7,    9,   11,   14,   16,   18,   22,   47,
	   50,   53,   56,   58,   59,   61,   63,   65,   67,   69,   76,   83,
	   87,   90,   92
	};
}

private static final byte _Scanner_index_offsets[] = init__Scanner_index_offsets_0();


private static byte[] init__Scanner_trans_targs_0()
{
	return new byte [] {
	   12,   10,   11,    0,    3,   14,   10,   10,    4,   10,    5,   24,
	    7,   10,   24,   10,    6,   10,    6,    8,    9,   10,   11,    2,
	   13,   16,    4,   14,   17,    5,   18,   10,   19,   20,   23,   10,
	   25,   10,   22,   26,   10,   21,   16,   10,   22,   22,    0,   12,
	    1,   10,   12,    1,   10,    3,   14,   10,   15,   14,   10,   10,
	   10,   10,   10,   18,   10,   10,   10,   20,   10,   10,   10,   22,
	   21,   22,   22,   10,   10,   10,   22,   22,   22,   22,   10,    6,
	    8,    9,   10,   24,    7,   10,   10,   10,   10,   10,   10,   10,
	   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
	   10,   10,   10,   10,   10,   10,   10,   10,    0
	};
}

private static final byte _Scanner_trans_targs[] = init__Scanner_trans_targs_0();


private static byte[] init__Scanner_trans_actions_0()
{
	return new byte [] {
	   52,   35,   55,    0,    0,    0,   33,    7,    0,    7,    0,   58,
	   37,   31,   46,   35,    0,   31,    0,    0,    0,   31,   55,    0,
	    5,    0,    0,    0,    0,    0,    0,   11,    0,    0,   49,    9,
	    0,    9,    0,    0,    9,    0,    0,    9,    0,    0,    0,   63,
	   37,   40,   52,    0,   17,    0,    0,   19,    0,    0,   43,    9,
	   25,    9,   25,    0,   27,    9,   25,    0,   29,    9,    9,    0,
	    0,    0,    0,   23,    9,    9,    0,    0,    0,    0,   25,    0,
	    0,    0,   15,   46,    0,   13,    9,   25,    9,   25,   35,   33,
	   31,   35,   31,   31,   40,   17,   19,   21,   43,   25,   25,   27,
	   25,   29,   23,   25,   15,   13,   25,   25,    0
	};
}

private static final byte _Scanner_trans_actions[] = init__Scanner_trans_actions_0();


private static byte[] init__Scanner_to_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0
	};
}

private static final byte _Scanner_to_state_actions[] = init__Scanner_to_state_actions_0();


private static byte[] init__Scanner_from_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0
	};
}

private static final byte _Scanner_from_state_actions[] = init__Scanner_from_state_actions_0();


private static byte[] init__Scanner_eof_trans_0()
{
	return new byte [] {
	    0,   98,    0,   96,    0,    0,  100,   98,  100,  100,    0,  101,
	  102,  103,  104,  105,  116,  116,  108,  116,  110,  111,  116,  113,
	  114,  116,  116
	};
}

private static final byte _Scanner_eof_trans[] = init__Scanner_eof_trans_0();


static final int Scanner_start = 10;
static final int Scanner_error = 0;

static final int Scanner_en_main = 10;


// line 112 "src/min/lang/Scanner.rl"

  
  @SuppressWarnings("fallthrough")
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs, top;
    int eof = data.length;
    int p = 0, pe = eof, ts = 0, te = 0, act = 0, mark = 0;
    int[] stack = new int[32];
    line = 1;
    
    
// line 200 "src/min/lang/Scanner.java"
	{
	cs = Scanner_start;
	ts = -1;
	te = -1;
	act = 0;
	}

// line 124 "src/min/lang/Scanner.rl"
    
// line 210 "src/min/lang/Scanner.java"
	{
	int _klen;
	int _trans = 0;
	int _acts;
	int _nacts;
	int _keys;
	int _goto_targ = 0;

	_goto: while (true) {
	switch ( _goto_targ ) {
	case 0:
	if ( p == pe ) {
		_goto_targ = 4;
		continue _goto;
	}
	if ( cs == 0 ) {
		_goto_targ = 5;
		continue _goto;
	}
case 1:
	_acts = _Scanner_from_state_actions[cs];
	_nacts = (int) _Scanner_actions[_acts++];
	while ( _nacts-- > 0 ) {
		switch ( _Scanner_actions[_acts++] ) {
	case 3:
// line 1 "NONE"
	{ts = p;}
	break;
// line 239 "src/min/lang/Scanner.java"
		}
	}

	_match: do {
	_keys = _Scanner_key_offsets[cs];
	_trans = _Scanner_index_offsets[cs];
	_klen = _Scanner_single_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + _klen - 1;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + ((_upper-_lower) >> 1);
			if ( data[p] < _Scanner_trans_keys[_mid] )
				_upper = _mid - 1;
			else if ( data[p] > _Scanner_trans_keys[_mid] )
				_lower = _mid + 1;
			else {
				_trans += (_mid - _keys);
				break _match;
			}
		}
		_keys += _klen;
		_trans += _klen;
	}

	_klen = _Scanner_range_lengths[cs];
	if ( _klen > 0 ) {
		int _lower = _keys;
		int _mid;
		int _upper = _keys + (_klen<<1) - 2;
		while (true) {
			if ( _upper < _lower )
				break;

			_mid = _lower + (((_upper-_lower) >> 1) & ~1);
			if ( data[p] < _Scanner_trans_keys[_mid] )
				_upper = _mid - 2;
			else if ( data[p] > _Scanner_trans_keys[_mid+1] )
				_lower = _mid + 2;
			else {
				_trans += ((_mid - _keys)>>1);
				break _match;
			}
		}
		_trans += _klen;
	}
	} while (false);

case 3:
	cs = _Scanner_trans_targs[_trans];

	if ( _Scanner_trans_actions[_trans] != 0 ) {
		_acts = _Scanner_trans_actions[_trans];
		_nacts = (int) _Scanner_actions[_acts++];
		while ( _nacts-- > 0 )
	{
			switch ( _Scanner_actions[_acts++] )
			{
	case 0:
// line 26 "src/min/lang/Scanner.rl"
	{ mark = p; }
	break;
	case 1:
// line 28 "src/min/lang/Scanner.rl"
	{ line++; }
	break;
	case 4:
// line 1 "NONE"
	{te = p+1;}
	break;
	case 5:
// line 51 "src/min/lang/Scanner.rl"
	{act = 1;}
	break;
	case 6:
// line 59 "src/min/lang/Scanner.rl"
	{act = 2;}
	break;
	case 7:
// line 64 "src/min/lang/Scanner.rl"
	{act = 3;}
	break;
	case 8:
// line 85 "src/min/lang/Scanner.rl"
	{act = 4;}
	break;
	case 9:
// line 95 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), filename, line, MinObject.newString(getSlice(ts + 1, te - 1)))); }}
	break;
	case 10:
// line 97 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), filename, line)); }}
	break;
	case 11:
// line 104 "src/min/lang/Scanner.rl"
	{te = p+1;{
        if (argStack.empty())
          throw new ParsingException("Unmatched closing parenthesis at line " + line);
        message = argStack.pop();
      }}
	break;
	case 12:
// line 51 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        int indent = (te - mark) / 2;
        // creating new block
        startBlock();
        // add indent level
        pushIndent(indent);
        debugIndent("+", indent);
      }}
	break;
	case 13:
// line 59 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        startSingleBlock();
        pushIndent(0);
        debugIndent("+", 0);
      }}
	break;
	case 14:
// line 64 "src/min/lang/Scanner.rl"
	{te = p;p--;{
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
      }}
	break;
	case 15:
// line 85 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        emptyIndentStack();
        pushTerminator();
      }}
	break;
	case 16:
// line 91 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 17:
// line 92 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 18:
// line 96 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), filename, line, MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); }}
	break;
	case 19:
// line 97 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), filename, line)); }}
	break;
	case 20:
// line 98 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        if (message == null) pushMessage(new Message("", filename, line));
        argStack.push(message);
        message = null;
      }}
	break;
	case 21:
// line 103 "src/min/lang/Scanner.rl"
	{te = p;p--;{ message = null; }}
	break;
	case 22:
// line 59 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{
        startSingleBlock();
        pushIndent(0);
        debugIndent("+", 0);
      }}
	break;
	case 23:
// line 91 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}}
	break;
	case 24:
// line 1 "NONE"
	{	switch( act ) {
	case 1:
	{{p = ((te))-1;}
        int indent = (te - mark) / 2;
        // creating new block
        startBlock();
        // add indent level
        pushIndent(indent);
        debugIndent("+", indent);
      }
	break;
	case 2:
	{{p = ((te))-1;}
        startSingleBlock();
        pushIndent(0);
        debugIndent("+", 0);
      }
	break;
	case 3:
	{{p = ((te))-1;}
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
      }
	break;
	case 4:
	{{p = ((te))-1;}
        emptyIndentStack();
        pushTerminator();
      }
	break;
	}
	}
	break;
// line 488 "src/min/lang/Scanner.java"
			}
		}
	}

case 2:
	_acts = _Scanner_to_state_actions[cs];
	_nacts = (int) _Scanner_actions[_acts++];
	while ( _nacts-- > 0 ) {
		switch ( _Scanner_actions[_acts++] ) {
	case 2:
// line 1 "NONE"
	{ts = -1;}
	break;
// line 502 "src/min/lang/Scanner.java"
		}
	}

	if ( cs == 0 ) {
		_goto_targ = 5;
		continue _goto;
	}
	if ( ++p != pe ) {
		_goto_targ = 1;
		continue _goto;
	}
case 4:
	if ( p == eof )
	{
	if ( _Scanner_eof_trans[cs] > 0 ) {
		_trans = _Scanner_eof_trans[cs] - 1;
		_goto_targ = 3;
		continue _goto;
	}
	}

case 5:
	}
	break; }
	}

// line 125 "src/min/lang/Scanner.rl"
    
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
    return pushUniqueMessage(new Message("\n", filename, line));
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