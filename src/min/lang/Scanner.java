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
	    0,    1,    4,    1,   10,    1,   11,    1,   12,    1,   13,    1,
	   14,    1,   16,    1,   18,    1,   19,    1,   20,    1,   21,    1,
	   22,    1,   23,    2,    1,    0,    2,    1,   15,    2,    1,   17,
	    2,    2,    3,    2,    5,    6,    2,    5,    7,    2,    5,    8,
	    2,    5,    9,    4,    5,    1,    0,    6,    4,    5,    1,    0,
	    7
	};
}

private static final byte _Scanner_actions[] = init__Scanner_actions_0();


private static byte[] init__Scanner_key_offsets_0()
{
	return new byte [] {
	    0,    0,    1,    2,    4,    5,    6,    7,   10,   12,   13,   14,
	   43,   45,   47,   49,   49,   50,   58,   59,   61,   62,   64,   72,
	   74,   76
	};
}

private static final byte _Scanner_key_offsets[] = init__Scanner_key_offsets_0();


private static char[] init__Scanner_trans_keys_0()
{
	return new char [] {
	   32,   10,   32,   35,   10,   34,   39,   10,   13,   32,    9,   32,
	   32,   10,   10,   13,   32,   33,   34,   35,   36,   38,   39,   40,
	   41,   42,   44,   58,   59,   60,   94,   95,  124,   37,   47,   48,
	   57,   61,   62,   64,   90,   97,  122,    9,   32,    9,   32,   32,
	   35,   61,   36,   95,   48,   57,   64,   90,   97,  122,   38,   46,
	   59,   42,   46,   59,   36,   95,   48,   57,   64,   90,   97,  122,
	    9,   32,   60,   61,  124,    0
	};
}

private static final char _Scanner_trans_keys[] = init__Scanner_trans_keys_0();


private static byte[] init__Scanner_single_lengths_0()
{
	return new byte [] {
	    0,    1,    1,    2,    1,    1,    1,    3,    2,    1,    1,   19,
	    2,    2,    2,    0,    1,    2,    1,    2,    1,    2,    2,    2,
	    0,    1
	};
}

private static final byte _Scanner_single_lengths[] = init__Scanner_single_lengths_0();


private static byte[] init__Scanner_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    5,
	    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,    3,    0,
	    1,    0
	};
}

private static final byte _Scanner_range_lengths[] = init__Scanner_range_lengths_0();


private static byte[] init__Scanner_index_offsets_0()
{
	return new byte [] {
	    0,    0,    2,    4,    7,    9,   11,   13,   17,   20,   22,   24,
	   49,   52,   55,   58,   59,   61,   67,   69,   72,   74,   77,   83,
	   86,   88
	};
}

private static final byte _Scanner_index_offsets[] = init__Scanner_index_offsets_0();


private static byte[] init__Scanner_indicies_0()
{
	return new byte [] {
	    1,    0,    2,    3,    5,    6,    4,    7,    6,    9,    8,    9,
	   10,   11,   12,   13,    3,   14,   15,    3,   16,    0,   11,    3,
	    2,   17,   18,   19,    8,    6,   20,   22,   10,   23,   24,   25,
	   26,   13,   21,   28,   21,   20,   29,   21,   27,   19,   20,   20,
	    3,   31,   32,   30,    1,   34,   33,    5,    6,   35,   36,   21,
	   37,   20,   20,   20,   20,   20,   37,   21,   37,   23,   23,   38,
	   21,   37,   26,   26,   39,   20,   20,   27,   20,   20,   40,   16,
	   42,   41,   21,   37,   21,   37,    0
	};
}

private static final byte _Scanner_indicies[] = init__Scanner_indicies_0();


private static byte[] init__Scanner_trans_targs_0()
{
	return new byte [] {
	   11,   13,   12,    0,   11,    3,    4,   15,    5,   11,    6,    8,
	   10,    7,   23,    9,   23,    2,   14,   16,   17,   11,   18,   19,
	   11,   20,   21,   22,   24,   25,   11,   13,    1,   11,    1,   11,
	   11,   11,   11,   11,   11,   11,    9
	};
}

private static final byte _Scanner_trans_targs[] = init__Scanner_trans_targs_0();


private static byte[] init__Scanner_trans_actions_0()
{
	return new byte [] {
	   25,   42,   45,    0,   23,    0,    0,    0,    0,    3,    0,    0,
	    0,    0,   51,   27,   39,    0,   48,    0,    0,    5,    0,    0,
	    7,    0,    0,    0,    0,    0,   30,   56,   27,   11,    0,   13,
	   33,   17,   19,   21,   15,    9,    0
	};
}

private static final byte _Scanner_trans_actions[] = init__Scanner_trans_actions_0();


private static byte[] init__Scanner_to_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,   36,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0
	};
}

private static final byte _Scanner_to_state_actions[] = init__Scanner_to_state_actions_0();


private static byte[] init__Scanner_from_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0
	};
}

private static final byte _Scanner_from_state_actions[] = init__Scanner_from_state_actions_0();


private static byte[] init__Scanner_eof_trans_0()
{
	return new byte [] {
	    0,    1,    0,    5,    1,    0,    0,    0,    0,    1,    0,    0,
	   31,   34,   36,   37,   38,   38,   38,   39,   38,   40,   41,   42,
	   38,   38
	};
}

private static final byte _Scanner_eof_trans[] = init__Scanner_eof_trans_0();


static final int Scanner_start = 11;
static final int Scanner_error = 0;

static final int Scanner_en_main = 11;

// line 96 "src/min/lang/Scanner.rl"

  
  @SuppressWarnings("fallthrough")
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs, top;
    int eof = data.length;
    int p = 0, pe = eof, ts = 0, te = 0, act = 0, mark = 0;
    int[] stack = new int[32];
    int lineno = 1;
    
    
// line 199 "src/min/lang/Scanner.java"
	{
	cs = Scanner_start;
	ts = -1;
	te = -1;
	act = 0;
	}
// line 108 "src/min/lang/Scanner.rl"
    
// line 208 "src/min/lang/Scanner.java"
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
// line 237 "src/min/lang/Scanner.java"
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
// line 44 "src/min/lang/Scanner.rl"
	{act = 1;}
	break;
	case 7:
// line 55 "src/min/lang/Scanner.rl"
	{act = 2;}
	break;
	case 8:
// line 73 "src/min/lang/Scanner.rl"
	{act = 3;}
	break;
	case 9:
// line 79 "src/min/lang/Scanner.rl"
	{act = 4;}
	break;
	case 10:
// line 83 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), MinObject.newString(getSlice(ts + 1, te - 1)))); }}
	break;
	case 11:
// line 85 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te))); }}
	break;
	case 12:
// line 88 "src/min/lang/Scanner.rl"
	{te = p+1;{
        if (this.argStack.empty())
          throw new ParsingException("Unmatched closing parenthesis at line " + lineno);
        this.message = this.argStack.pop();
      }}
	break;
	case 13:
// line 44 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        int indent = (te - mark) / 2;
        // creating new block
        inBlock = true;
        argStack.push(message);
        message = null;
        // add indent level
        debugIndent(lineno, "+", indent);
        indentStack.push(indent);
        currentIndent = indent;;
      }}
	break;
	case 14:
// line 55 "src/min/lang/Scanner.rl"
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
	case 15:
// line 73 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        emptyIndentStack();
        pushTerminator();
      }}
	break;
	case 16:
// line 79 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 17:
// line 80 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 18:
// line 84 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); }}
	break;
	case 19:
// line 85 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te))); }}
	break;
	case 20:
// line 86 "src/min/lang/Scanner.rl"
	{te = p;p--;{ this.argStack.push(this.message); this.message = null; }}
	break;
	case 21:
// line 87 "src/min/lang/Scanner.rl"
	{te = p;p--;{ this.message = null; }}
	break;
	case 22:
// line 79 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}}
	break;
	case 23:
// line 1 "src/min/lang/Scanner.rl"
	{	switch( act ) {
	case 0:
	{{cs = 0; _goto_targ = 2; if (true) continue _goto;}}
	break;
	case 1:
	{{p = ((te))-1;}
        int indent = (te - mark) / 2;
        // creating new block
        inBlock = true;
        argStack.push(message);
        message = null;
        // add indent level
        debugIndent(lineno, "+", indent);
        indentStack.push(indent);
        currentIndent = indent;;
      }
	break;
	case 2:
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
	case 3:
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
// line 466 "src/min/lang/Scanner.java"
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
// line 484 "src/min/lang/Scanner.java"
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
// line 109 "src/min/lang/Scanner.rl"
    
    if (cs == Scanner_error || p != pe) {
      // TODO Better error reporting
      throw new ParsingException(String.format("Syntax error at line %d around '%s...'", lineno, input.substring(p, Math.min(p+5, pe))));
    }
    
    emptyIndentStack();
    
    if (!this.argStack.empty())
      throw new ParsingException(this.argStack.size() + " unclosed parenthesis at line " + lineno);
    
    return this.root;
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
    if (this.message != null)
      message.setNext(m);
    else if (!this.argStack.empty())
      this.argStack.peek().args.add(m);
      
    this.message = m;
    
    if (this.root == null) this.root = this.message;
    return m;
  }
  
  private Message pushUniqueMessage(Message m) {
    if (message != null && message.name.equals(m.name)) return message;
    return pushMessage(m);
  }
  
  private Message pushTerminator() {
    return pushUniqueMessage(new Message("\n"));
  }
  
  private void debugIndent(int lineno, String action, int indent) {
    if (debug)
      System.out.println(String.format("[%2d] %s to %d was %d (indentStack: %d)", lineno-1, action, indent, currentIndent, indentStack.size()));
  }
}