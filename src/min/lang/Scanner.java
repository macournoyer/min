
// line 1 "src/min/lang/Scanner.rl"
package min.lang;

import java.util.Stack;

public class Scanner {

    String input;
    String filename;

    int line;
    int currentIndent = 0;

    Message root = null;
    Message message = null;

    Stack<Message> argStack = new Stack<Message>();
    Stack<Integer> indentStack = new Stack<Integer>();


    boolean inBlock = false;
    boolean singleBlock = false;
    boolean debug = false;

    public Scanner(String input, String filename) {
        this.input = input;
        this.filename = filename;
    }

// RAGEL STUFF BELOW

    
// line 35 "src/min/lang/Scanner.java"
private static byte[] init__Scanner_actions_0()
{
	return new byte [] {
	    0,    1,    1,    1,    2,    1,    3,    1,    4,    1,    9,    1,
	   10,    1,   11,    1,   12,    1,   13,    1,   14,    1,   15,    1,
	   17,    1,   18,    1,   19,    1,   20,    1,   21,    1,   22,    1,
	   23,    1,   24,    1,   25,    1,   26,    1,   27,    1,   28,    2,
	    1,    0,    2,    1,   16,    2,    1,   18,    2,    1,   21,    2,
	    1,   22,    2,    4,    1,    2,    4,    5,    2,    4,    6,    2,
	    4,    7,    2,    4,    8,    4,    4,    1,    0,    5,    4,    4,
	    1,    0,    7
	};
}

private static final byte _Scanner_actions[] = init__Scanner_actions_0();


private static byte[] init__Scanner_key_offsets_0()
{
	return new byte [] {
	    0,    0,    1,    5,    6,    7,    9,   10,   11,   12,   13,   17,
	   18,   19,   22,   52,   56,   58,   60,   61,   61,   62,   63,   66,
	   69,   70,   73,   76,   85,   94,   97,   99,  101
	};
}

private static final byte _Scanner_key_offsets[] = init__Scanner_key_offsets_0();


private static char[] init__Scanner_trans_keys_0()
{
	return new char [] {
	   32,    9,   10,   13,   32,   10,   10,   32,   35,   34,   39,   10,
	   10,    9,   10,   13,   32,   32,   10,   10,   13,   32,   10,   13,
	   32,   33,   34,   35,   38,   39,   40,   41,   42,   44,   58,   59,
	   60,   94,   95,  124,   36,   47,   48,   57,   61,   62,   63,   64,
	   65,   90,   97,  122,    9,   10,   13,   32,    9,   32,   32,   35,
	   10,   61,   38,   10,   13,   59,   10,   13,   59,   42,   10,   13,
	   59,   10,   13,   59,   33,   63,   95,   48,   57,   65,   90,   97,
	  122,   33,   63,   95,   48,   57,   65,   90,   97,  122,   10,   13,
	   32,    9,   32,   60,   61,  124,    0
	};
}

private static final char _Scanner_trans_keys[] = init__Scanner_trans_keys_0();


private static byte[] init__Scanner_single_lengths_0()
{
	return new byte [] {
	    0,    1,    4,    1,    1,    2,    1,    1,    1,    1,    4,    1,
	    1,    3,   18,    4,    2,    2,    1,    0,    1,    1,    3,    3,
	    1,    3,    3,    3,    3,    3,    2,    0,    1
	};
}

private static final byte _Scanner_single_lengths[] = init__Scanner_single_lengths_0();


private static byte[] init__Scanner_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    3,    3,    0,    0,    1,    0
	};
}

private static final byte _Scanner_range_lengths[] = init__Scanner_range_lengths_0();


private static short[] init__Scanner_index_offsets_0()
{
	return new short [] {
	    0,    0,    2,    7,    9,   11,   14,   16,   18,   20,   22,   27,
	   29,   31,   35,   60,   65,   68,   71,   73,   74,   76,   78,   82,
	   86,   88,   92,   96,  103,  110,  114,  117,  119
	};
}

private static final short _Scanner_index_offsets[] = init__Scanner_index_offsets_0();


private static byte[] init__Scanner_trans_targs_0()
{
	return new byte [] {
	   16,   14,   16,    2,    3,    1,   14,    2,   14,   15,    0,    5,
	   18,   14,   14,    6,   14,    7,   23,   14,   26,   14,   30,   10,
	   12,   11,   14,   30,   14,   10,   14,   10,   12,   13,   14,   15,
	    4,   17,   20,    6,   18,   21,    7,   22,   14,   24,   25,   29,
	   14,   31,   14,   28,   32,   14,   27,   20,   14,   28,   28,    0,
	   16,    2,    3,    1,   14,   16,    1,   14,    5,   18,   14,   19,
	   18,   14,   14,   14,   14,   14,   23,    8,   22,   14,   23,    8,
	   22,   14,   14,   14,   26,    9,   25,   14,   26,    9,   25,   14,
	   14,   14,   28,   27,   28,   28,   14,   14,   14,   28,   28,   28,
	   28,   14,   10,   12,   13,   14,   30,   11,   14,   14,   14,   14,
	   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
	   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
	   14,   14,   14,   14,   14,    0
	};
}

private static final byte _Scanner_trans_targs[] = init__Scanner_trans_targs_0();


private static byte[] init__Scanner_trans_actions_0()
{
	return new byte [] {
	   71,   45,   82,    1,    1,   47,   37,    0,   37,   74,    0,    0,
	    0,   39,   11,    0,   11,    0,    7,   41,    7,   43,   77,    1,
	    1,   47,   35,   65,   45,    0,   35,    0,    0,    0,   35,   74,
	    0,    7,    0,    0,    0,    0,    0,    7,   15,    0,    7,   68,
	    9,    0,   13,    0,    0,   13,    0,    0,   13,    0,    0,    0,
	   82,    1,    1,   47,   50,   71,    0,   21,    0,    0,   23,    0,
	    0,   53,   13,   29,   13,   29,    7,    0,    7,   31,   62,    1,
	   62,   56,   13,   29,    7,    0,    7,   33,   62,    1,   62,   59,
	   13,   13,    0,    0,    0,    0,   27,   13,   13,    0,    0,    0,
	    0,   29,    0,    0,    0,   19,   65,    0,   17,   13,   29,   13,
	   29,   45,   37,   37,   39,   41,   43,   35,   45,   35,   35,   50,
	   21,   23,   25,   53,   29,   29,   31,   56,   29,   33,   59,   27,
	   29,   19,   17,   29,   29,    0
	};
}

private static final byte _Scanner_trans_actions[] = init__Scanner_trans_actions_0();


private static byte[] init__Scanner_to_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    3,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0
	};
}

private static final byte _Scanner_to_state_actions[] = init__Scanner_to_state_actions_0();


private static byte[] init__Scanner_from_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    5,    0,    0,    0,    0,    0,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0
	};
}

private static final byte _Scanner_from_state_actions[] = init__Scanner_from_state_actions_0();


private static short[] init__Scanner_eof_trans_0()
{
	return new short [] {
	    0,  129,  124,  124,    0,  125,    0,    0,  126,  127,  131,  129,
	  131,  131,    0,  132,  133,  134,  135,  136,  149,  149,  139,  140,
	  149,  142,  143,  144,  149,  146,  147,  149,  149
	};
}

private static final short _Scanner_eof_trans[] = init__Scanner_eof_trans_0();


static final int Scanner_start = 14;
static final int Scanner_error = 0;

static final int Scanner_en_main = 14;


// line 121 "src/min/lang/Scanner.rl"


    // END OF RAGEL STUFF
  
    @SuppressWarnings("fallthrough")
    public Message scan() throws ParsingException {
        char[] data = input.toCharArray();
        int cs, top;
        int eof = data.length;
        int p = 0, pe = eof, ts = 0, te = 0, act = 0, mark = 0;
        int[] stack = new int[32];
        line = 1;

        if (debug) {
            System.out.println("=== Parsing " + filename + " ===");
            System.out.println(input);
            System.out.println("===============");
        }
    
        
// line 226 "src/min/lang/Scanner.java"
	{
	cs = Scanner_start;
	ts = -1;
	te = -1;
	act = 0;
	}

// line 141 "src/min/lang/Scanner.rl"
        
// line 236 "src/min/lang/Scanner.java"
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
// line 265 "src/min/lang/Scanner.java"
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
// line 34 "src/min/lang/Scanner.rl"
	{ mark = p; }
	break;
	case 1:
// line 36 "src/min/lang/Scanner.rl"
	{ line++; }
	break;
	case 4:
// line 1 "NONE"
	{te = p+1;}
	break;
	case 5:
// line 60 "src/min/lang/Scanner.rl"
	{act = 1;}
	break;
	case 6:
// line 68 "src/min/lang/Scanner.rl"
	{act = 2;}
	break;
	case 7:
// line 73 "src/min/lang/Scanner.rl"
	{act = 3;}
	break;
	case 8:
// line 94 "src/min/lang/Scanner.rl"
	{act = 4;}
	break;
	case 9:
// line 94 "src/min/lang/Scanner.rl"
	{te = p+1;{
            emptyIndentStack();
            pushTerminator(getSlice(ts, te));
        }}
	break;
	case 10:
// line 104 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), filename, line, MinObject.newString(getSlice(ts + 1, te - 1)))); }}
	break;
	case 11:
// line 106 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), filename, line)); }}
	break;
	case 12:
// line 113 "src/min/lang/Scanner.rl"
	{te = p+1;{
            if (argStack.empty())
                throw new ParsingException("Unmatched closing parenthesis at line " + line);
            message = argStack.pop();
        }}
	break;
	case 13:
// line 60 "src/min/lang/Scanner.rl"
	{te = p;p--;{
            int indent = (te - mark) / 2;
            // creating new block
            startBlock();
            // add indent level
            pushIndent(indent);
            debugIndent("+", indent);
        }}
	break;
	case 14:
// line 68 "src/min/lang/Scanner.rl"
	{te = p;p--;{
            startSingleBlock();
            pushIndent(0);
            debugIndent("+", 0);
        }}
	break;
	case 15:
// line 73 "src/min/lang/Scanner.rl"
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
	case 16:
// line 94 "src/min/lang/Scanner.rl"
	{te = p;p--;{
            emptyIndentStack();
            pushTerminator(getSlice(ts, te));
        }}
	break;
	case 17:
// line 100 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 18:
// line 101 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 19:
// line 105 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), filename, line, MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); }}
	break;
	case 20:
// line 106 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), filename, line)); }}
	break;
	case 21:
// line 107 "src/min/lang/Scanner.rl"
	{te = p;p--;{
            if (message == null) pushMessage(new Message("", filename, line));
            argStack.push(message);
            message = null;
        }}
	break;
	case 22:
// line 112 "src/min/lang/Scanner.rl"
	{te = p;p--;{ message = null; }}
	break;
	case 23:
// line 68 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{
            startSingleBlock();
            pushIndent(0);
            debugIndent("+", 0);
        }}
	break;
	case 24:
// line 94 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{
            emptyIndentStack();
            pushTerminator(getSlice(ts, te));
        }}
	break;
	case 25:
// line 100 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}}
	break;
	case 26:
// line 107 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{
            if (message == null) pushMessage(new Message("", filename, line));
            argStack.push(message);
            message = null;
        }}
	break;
	case 27:
// line 112 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{ message = null; }}
	break;
	case 28:
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
            pushTerminator(getSlice(ts, te));
        }
	break;
	}
	}
	break;
// line 540 "src/min/lang/Scanner.java"
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
// line 554 "src/min/lang/Scanner.java"
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

// line 142 "src/min/lang/Scanner.rl"

        if (cs == Scanner_error || p != pe)
            throw new ParsingException(String.format("Syntax error at line %d around '%s...'", line, input.substring(p, Math.min(p+5, pe))));

        if (root == null) return new Message("\n", filename, line);
    
        emptyIndentStack();
    
        if (!argStack.empty())
            throw new ParsingException(argStack.size() + " unclosed parenthesis at line " + line);

        if (debug) {
            System.out.println("=== Done parsing ===");
            System.out.println(root.fullName());
            System.out.println("====================");
        }
    
        return root;
    }
  
    private String getSlice(int start, int end) {
        return input.substring(start, end);
    }
  
    private void emptyIndentStack() {
        if (debug && !indentStack.empty()) System.out.println("Emptying indent stack");
        while (!indentStack.empty()) {
            int indent = indentStack.pop();
            message = argStack.pop();
            debugIndent("-", indent);
        }
        currentIndent = 0;
        inBlock = false;
    }
  
    private Message pushMessage(Message m) {
        if (debug) System.out.println("pushMessage: '" + m.name + "'");
    
        if (message != null) {
            message.setNext(m);
        } else if (!argStack.empty()) {
            argStack.peek().args.add(m);
        }
    
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
            System.out.println(String.format("[%s:%02d] %s to %d    indentStack: %-20s  singleBlock? %b", filename, line, action, indent, indentStack.toString(), singleBlock));
    }
}