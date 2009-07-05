// line 1 "src/min/lang/Scanner.rl"
package min.lang;

import java.util.Stack;

public class Scanner {
  final String input;
  Message root = null;
  Message message = null;
  Stack<Message> argStack = new Stack<Message>();
  Stack<Integer> indentStack = new Stack<Integer>();
  int currentIndent = 0;
  boolean inBlock = false;
  boolean debug = false;

  public Scanner(String input) {
    this.input = input;
  }

  
// line 22 "src/min/lang/Scanner.java"
private static byte[] init__Scanner_actions_0()
{
	return new byte [] {
	    0,    1,    4,    1,   11,    1,   12,    1,   13,    1,   14,    1,
	   15,    1,   16,    1,   18,    1,   20,    1,   21,    1,   22,    1,
	   23,    1,   24,    1,   25,    1,   26,    2,    1,    0,    2,    1,
	   17,    2,    1,   19,    2,    2,    3,    2,    5,    6,    2,    5,
	    7,    2,    5,    8,    2,    5,    9,    2,    5,   10,    4,    5,
	    1,    0,    6,    4,    5,    1,    0,    8
	};
}

private static final byte _Scanner_actions[] = init__Scanner_actions_0();


private static byte[] init__Scanner_key_offsets_0()
{
	return new byte [] {
	    0,    0,    1,    2,    4,    5,    6,    7,    9,   10,   11,   14,
	   43,   45,   47,   49,   49,   50,   51,   53,   54,   56,   65,   74,
	   77,   79,   81
	};
}

private static final byte _Scanner_key_offsets[] = init__Scanner_key_offsets_0();


private static char[] init__Scanner_trans_keys_0()
{
	return new char [] {
	   32,   10,   32,   35,   10,   34,   39,    9,   32,   32,   10,   10,
	   13,   32,   10,   13,   32,   33,   34,   35,   38,   39,   40,   41,
	   42,   44,   58,   59,   60,   64,   94,   95,  124,   36,   47,   48,
	   57,   61,   62,   65,   90,   97,  122,    9,   32,    9,   32,   32,
	   35,   61,   38,   46,   59,   42,   46,   59,   33,   63,   95,   48,
	   57,   65,   90,   97,  122,   33,   63,   95,   48,   57,   65,   90,
	   97,  122,   10,   13,   32,    9,   32,   60,   61,  124,    0
	};
}

private static final char _Scanner_trans_keys[] = init__Scanner_trans_keys_0();


private static byte[] init__Scanner_single_lengths_0()
{
	return new byte [] {
	    0,    1,    1,    2,    1,    1,    1,    2,    1,    1,    3,   19,
	    2,    2,    2,    0,    1,    1,    2,    1,    2,    3,    3,    3,
	    2,    0,    1
	};
}

private static final byte _Scanner_single_lengths[] = init__Scanner_single_lengths_0();


private static byte[] init__Scanner_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    5,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,    3,    0,
	    0,    1,    0
	};
}

private static final byte _Scanner_range_lengths[] = init__Scanner_range_lengths_0();


private static byte[] init__Scanner_index_offsets_0()
{
	return new byte [] {
	    0,    0,    2,    4,    7,    9,   11,   13,   16,   18,   20,   24,
	   49,   52,   55,   58,   59,   61,   63,   66,   68,   71,   78,   85,
	   89,   92,   94
	};
}

private static final byte _Scanner_index_offsets[] = init__Scanner_index_offsets_0();


private static byte[] init__Scanner_indicies_0()
{
	return new byte [] {
	    1,    0,    2,    3,    5,    6,    4,    7,    6,    9,    8,    9,
	   10,   12,   13,   11,   14,    0,   15,   11,   15,   16,   17,   11,
	    2,   18,   19,   20,    8,    6,   22,   10,   23,   24,   25,   26,
	   28,   21,   29,   21,   21,   30,   31,   21,   27,   20,   30,   30,
	    3,   33,   34,   32,    1,   36,   35,    5,    6,   37,   38,   21,
	   39,   21,   39,   23,   23,   40,   21,   39,   26,   26,   41,   21,
	   21,   30,   27,   30,   30,   42,   21,   21,   30,   30,   30,   30,
	   39,   15,   16,   17,   43,   14,   45,   44,   21,   39,   21,   39,
	    0
	};
}

private static final byte _Scanner_indicies[] = init__Scanner_indicies_0();


private static byte[] init__Scanner_trans_targs_0()
{
	return new byte [] {
	   11,   13,   12,    0,   11,    3,    4,   15,    5,   11,    6,   11,
	   24,    8,   24,    7,    9,   10,    2,   14,   16,   11,   17,   18,
	   11,   19,   20,   21,   23,   25,   22,   26,   11,   13,    1,   11,
	    1,   11,   11,   11,   11,   11,   11,   11,   11,    8
	};
}

private static final byte _Scanner_trans_targs[] = init__Scanner_trans_targs_0();


private static byte[] init__Scanner_trans_actions_0()
{
	return new byte [] {
	   29,   49,   52,    0,   27,    0,    0,    0,    0,    3,    0,   25,
	   58,   31,   43,    0,    0,    0,    0,   55,    0,    5,    0,    0,
	    7,    0,    0,    0,   46,    0,    0,    0,   34,   63,   31,   13,
	    0,   15,   37,   19,   21,   23,   17,   11,    9,    0
	};
}

private static final byte _Scanner_trans_actions[] = init__Scanner_trans_actions_0();


private static byte[] init__Scanner_to_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,   40,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0
	};
}

private static final byte _Scanner_to_state_actions[] = init__Scanner_to_state_actions_0();


private static byte[] init__Scanner_from_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0
	};
}

private static final byte _Scanner_from_state_actions[] = init__Scanner_from_state_actions_0();


private static byte[] init__Scanner_eof_trans_0()
{
	return new byte [] {
	    0,    1,    0,    5,    1,    0,    0,   12,    1,   12,   12,    0,
	   33,   36,   38,   39,   40,   40,   41,   40,   42,   43,   40,   44,
	   45,   40,   40
	};
}

private static final byte _Scanner_eof_trans[] = init__Scanner_eof_trans_0();


static final int Scanner_start = 11;
static final int Scanner_error = 0;

static final int Scanner_en_main = 11;

// line 98 "src/min/lang/Scanner.rl"

  
  @SuppressWarnings("fallthrough")
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs, top;
    int eof = data.length;
    int p = 0, pe = eof, ts = 0, te = 0, act = 0, mark = 0;
    int[] stack = new int[32];
    int lineno = 1;
    
    
// line 200 "src/min/lang/Scanner.java"
	{
	cs = Scanner_start;
	ts = -1;
	te = -1;
	act = 0;
	}
// line 110 "src/min/lang/Scanner.rl"
    
// line 209 "src/min/lang/Scanner.java"
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
	case 4:
// line 1 "src/min/lang/Scanner.rl"
	{ts = p;}
	break;
// line 238 "src/min/lang/Scanner.java"
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

	_trans = _Scanner_indicies[_trans];
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
// line 22 "src/min/lang/Scanner.rl"
	{ mark = p; }
	break;
	case 1:
// line 24 "src/min/lang/Scanner.rl"
	{ lineno++; }
	break;
	case 5:
// line 1 "src/min/lang/Scanner.rl"
	{te = p+1;}
	break;
	case 6:
// line 45 "src/min/lang/Scanner.rl"
	{act = 1;}
	break;
	case 7:
// line 53 "src/min/lang/Scanner.rl"
	{act = 2;}
	break;
	case 8:
// line 57 "src/min/lang/Scanner.rl"
	{act = 3;}
	break;
	case 9:
// line 75 "src/min/lang/Scanner.rl"
	{act = 4;}
	break;
	case 10:
// line 81 "src/min/lang/Scanner.rl"
	{act = 5;}
	break;
	case 11:
// line 85 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), MinObject.newString(getSlice(ts + 1, te - 1)))); }}
	break;
	case 12:
// line 87 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te))); }}
	break;
	case 13:
// line 90 "src/min/lang/Scanner.rl"
	{te = p+1;{
        if (argStack.empty())
          throw new ParsingException("Unmatched closing parenthesis at line " + lineno);
        message = argStack.pop();
      }}
	break;
	case 14:
// line 45 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        int indent = (te - mark) / 2;
        // creating new block
        startBlock();
        // add indent level
        debugIndent(lineno, "+", indent);
        pushIndent(indent);
      }}
	break;
	case 15:
// line 53 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        startBlock();
        pushIndent(0);
      }}
	break;
	case 16:
// line 57 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        int indent = (te - mark) / 2;
        if (indent > currentIndent) { // indent in same block
          debugIndent(lineno, "/", indent);
        } else if (indent == currentIndent) { // same block
          debugIndent(lineno, "=", indent);
          pushTerminator();
        } else if (inBlock && indent < currentIndent) { // dedent
          debugIndent(lineno, "-", indent);
          indentStack.pop();
          message = argStack.pop();
          if (argStack.empty()) inBlock = false;
          pushTerminator();
        } else {
          pushTerminator();
        }
        currentIndent = indent;
      }}
	break;
	case 17:
// line 75 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        emptyIndentStack();
        pushTerminator();
      }}
	break;
	case 18:
// line 81 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 19:
// line 82 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 20:
// line 86 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); }}
	break;
	case 21:
// line 87 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te))); }}
	break;
	case 22:
// line 88 "src/min/lang/Scanner.rl"
	{te = p;p--;{ argStack.push(message); message = null; }}
	break;
	case 23:
// line 89 "src/min/lang/Scanner.rl"
	{te = p;p--;{ message = null; }}
	break;
	case 24:
// line 53 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{
        startBlock();
        pushIndent(0);
      }}
	break;
	case 25:
// line 81 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}}
	break;
	case 26:
// line 1 "src/min/lang/Scanner.rl"
	{	switch( act ) {
	case 0:
	{{cs = 0; _goto_targ = 2; if (true) continue _goto;}}
	break;
	case 1:
	{{p = ((te))-1;}
        int indent = (te - mark) / 2;
        // creating new block
        startBlock();
        // add indent level
        debugIndent(lineno, "+", indent);
        pushIndent(indent);
      }
	break;
	case 2:
	{{p = ((te))-1;}
        startBlock();
        pushIndent(0);
      }
	break;
	case 3:
	{{p = ((te))-1;}
        int indent = (te - mark) / 2;
        if (indent > currentIndent) { // indent in same block
          debugIndent(lineno, "/", indent);
        } else if (indent == currentIndent) { // same block
          debugIndent(lineno, "=", indent);
          pushTerminator();
        } else if (inBlock && indent < currentIndent) { // dedent
          debugIndent(lineno, "-", indent);
          indentStack.pop();
          message = argStack.pop();
          if (argStack.empty()) inBlock = false;
          pushTerminator();
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
	default:
	{{p = ((te))-1;}}
	break;
	}
	}
	break;
// line 485 "src/min/lang/Scanner.java"
			}
		}
	}

case 2:
	_acts = _Scanner_to_state_actions[cs];
	_nacts = (int) _Scanner_actions[_acts++];
	while ( _nacts-- > 0 ) {
		switch ( _Scanner_actions[_acts++] ) {
	case 2:
// line 1 "src/min/lang/Scanner.rl"
	{ts = -1;}
	break;
	case 3:
// line 1 "src/min/lang/Scanner.rl"
	{act = 0;}
	break;
// line 503 "src/min/lang/Scanner.java"
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
// line 111 "src/min/lang/Scanner.rl"
    
    if (cs == Scanner_error || p != pe)
      throw new ParsingException(String.format("Syntax error at line %d around '%s...'", lineno, input.substring(p, Math.min(p+5, pe))));
    
    if (root == null) return new Message("\n");
    
    emptyIndentStack();
    
    if (!argStack.empty())
      throw new ParsingException(argStack.size() + " unclosed parenthesis at line " + lineno);
    
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
    return pushUniqueMessage(new Message("\n"));
  }
  
  private void startBlock() {
    inBlock = true;
    argStack.push(message);
    message = null;
  }
  
  private void pushIndent(int indent) {
    indentStack.push(indent);
    currentIndent = indent;
  }
  
  private void debugIndent(int lineno, String action, int indent) {
    if (debug)
      System.out.println(String.format("[%2d] %s to %d was %d (indentStack: %d)", lineno-1, action, indent, currentIndent, indentStack.size()));
  }
}