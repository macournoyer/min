
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
	    0,    0,    1,    2,    4,    5,    6,    8,    9,   10,   13,   42,
	   44,   46,   48,   49,   49,   50,   51,   53,   54,   56,   65,   74,
	   77,   79,   81
	};
}

private static final byte _Scanner_key_offsets[] = init__Scanner_key_offsets_0();


private static char[] init__Scanner_trans_keys_0()
{
	return new char [] {
	   32,   10,   32,   35,   34,   39,    9,   32,   32,   10,   10,   13,
	   32,   10,   13,   32,   33,   34,   35,   38,   39,   40,   41,   42,
	   44,   58,   59,   60,   64,   94,   95,  124,   36,   47,   48,   57,
	   61,   62,   65,   90,   97,  122,    9,   32,    9,   32,   32,   35,
	   10,   61,   38,   46,   59,   42,   46,   59,   33,   63,   95,   48,
	   57,   65,   90,   97,  122,   33,   63,   95,   48,   57,   65,   90,
	   97,  122,   10,   13,   32,    9,   32,   60,   61,  124,    0
	};
}

private static final char _Scanner_trans_keys[] = init__Scanner_trans_keys_0();


private static byte[] init__Scanner_single_lengths_0()
{
	return new byte [] {
	    0,    1,    1,    2,    1,    1,    2,    1,    1,    3,   19,    2,
	    2,    2,    1,    0,    1,    1,    2,    1,    2,    3,    3,    3,
	    2,    0,    1
	};
}

private static final byte _Scanner_single_lengths[] = init__Scanner_single_lengths_0();


private static byte[] init__Scanner_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    5,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,    3,    0,
	    0,    1,    0
	};
}

private static final byte _Scanner_range_lengths[] = init__Scanner_range_lengths_0();


private static byte[] init__Scanner_index_offsets_0()
{
	return new byte [] {
	    0,    0,    2,    4,    7,    9,   11,   14,   16,   18,   22,   47,
	   50,   53,   56,   58,   59,   61,   63,   66,   68,   71,   78,   85,
	   89,   92,   94
	};
}

private static final byte _Scanner_index_offsets[] = init__Scanner_index_offsets_0();


private static byte[] init__Scanner_indicies_0()
{
	return new byte [] {
	    1,    0,    2,    3,    5,    6,    4,    8,    7,    8,    9,   11,
	   12,   10,   13,    0,   14,   10,   14,   15,   16,   10,    2,   17,
	   18,   19,    7,    6,   21,    9,   22,   23,   24,   25,   27,   20,
	   28,   20,   20,   29,   30,   20,   26,   19,   29,   29,    3,   32,
	   33,   31,    1,   35,   34,    5,    6,   36,   38,    6,   39,   20,
	   40,   20,   40,   22,   22,   41,   20,   40,   25,   25,   42,   20,
	   20,   29,   26,   29,   29,   43,   20,   20,   29,   29,   29,   29,
	   40,   14,   15,   16,   44,   13,   46,   45,   20,   40,   20,   40,
	    0
	};
}

private static final byte _Scanner_indicies[] = init__Scanner_indicies_0();


private static byte[] init__Scanner_trans_targs_0()
{
	return new byte [] {
	   10,   12,   11,    0,   10,    3,   14,    4,   10,    5,   10,   24,
	    7,   24,    6,    8,    9,    2,   13,   16,   10,   17,   18,   10,
	   19,   20,   21,   23,   25,   22,   26,   10,   12,    1,   10,    1,
	   10,   10,   15,   10,   10,   10,   10,   10,   10,   10,    7
	};
}

private static final byte _Scanner_trans_targs[] = init__Scanner_trans_targs_0();


private static byte[] init__Scanner_trans_actions_0()
{
	return new byte [] {
	   35,   52,   55,    0,   33,    0,    0,    0,    7,    0,   31,   58,
	   37,   46,    0,    0,    0,    0,    5,    0,    9,    0,    0,   11,
	    0,    0,    0,   49,    0,    0,    0,   40,   63,   37,   17,    0,
	   19,   21,    0,   43,   25,   27,   29,   23,   15,   13,    0
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
	    0,    1,    0,    5,    0,    0,   11,    1,   11,   11,    0,   32,
	   35,   37,   38,   40,   41,   41,   42,   41,   43,   44,   41,   45,
	   46,   41,   41
	};
}

private static final byte _Scanner_eof_trans[] = init__Scanner_eof_trans_0();


static final int Scanner_start = 10;
static final int Scanner_error = 0;

static final int Scanner_en_main = 10;


// line 106 "src/min/lang/Scanner.rl"

  
  @SuppressWarnings("fallthrough")
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs, top;
    int eof = data.length;
    int p = 0, pe = eof, ts = 0, te = 0, act = 0, mark = 0;
    int[] stack = new int[32];
    line = 1;
    
    
// line 206 "src/min/lang/Scanner.java"
	{
	cs = Scanner_start;
	ts = -1;
	te = -1;
	act = 0;
	}

// line 118 "src/min/lang/Scanner.rl"
    
// line 216 "src/min/lang/Scanner.java"
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
// line 1 "src/min/lang/Scanner.rl"
	{ts = p;}
	break;
// line 245 "src/min/lang/Scanner.java"
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
// line 26 "src/min/lang/Scanner.rl"
	{ mark = p; }
	break;
	case 1:
// line 28 "src/min/lang/Scanner.rl"
	{ line++; }
	break;
	case 4:
// line 1 "src/min/lang/Scanner.rl"
	{te = p+1;}
	break;
	case 5:
// line 49 "src/min/lang/Scanner.rl"
	{act = 1;}
	break;
	case 6:
// line 57 "src/min/lang/Scanner.rl"
	{act = 2;}
	break;
	case 7:
// line 62 "src/min/lang/Scanner.rl"
	{act = 3;}
	break;
	case 8:
// line 83 "src/min/lang/Scanner.rl"
	{act = 4;}
	break;
	case 9:
// line 93 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), filename, line, MinObject.newString(getSlice(ts + 1, te - 1)))); }}
	break;
	case 10:
// line 95 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), filename, line)); }}
	break;
	case 11:
// line 98 "src/min/lang/Scanner.rl"
	{te = p+1;{
        if (argStack.empty())
          throw new ParsingException("Unmatched closing parenthesis at line " + line);
        message = argStack.pop();
      }}
	break;
	case 12:
// line 49 "src/min/lang/Scanner.rl"
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
// line 57 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        startSingleBlock();
        pushIndent(0);
        debugIndent("+", 0);
      }}
	break;
	case 14:
// line 62 "src/min/lang/Scanner.rl"
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
// line 83 "src/min/lang/Scanner.rl"
	{te = p;p--;{
        emptyIndentStack();
        pushTerminator();
      }}
	break;
	case 16:
// line 89 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 17:
// line 90 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 18:
// line 94 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), filename, line, MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); }}
	break;
	case 19:
// line 95 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), filename, line)); }}
	break;
	case 20:
// line 96 "src/min/lang/Scanner.rl"
	{te = p;p--;{ argStack.push(message); message = null; }}
	break;
	case 21:
// line 97 "src/min/lang/Scanner.rl"
	{te = p;p--;{ message = null; }}
	break;
	case 22:
// line 57 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{
        startSingleBlock();
        pushIndent(0);
        debugIndent("+", 0);
      }}
	break;
	case 23:
// line 89 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}}
	break;
	case 24:
// line 1 "src/min/lang/Scanner.rl"
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
// line 491 "src/min/lang/Scanner.java"
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
// line 505 "src/min/lang/Scanner.java"
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

// line 119 "src/min/lang/Scanner.rl"
    
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