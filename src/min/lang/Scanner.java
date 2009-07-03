// line 1 "src/min/lang/Scanner.rl"
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

  
// line 18 "src/min/lang/Scanner.java"
private static byte[] init__Scanner_actions_0()
{
	return new byte [] {
	    0,    1,    0,    1,    1,    1,    2,    1,    3,    1,    4,    1,
	    5,    1,    6,    1,    7,    1,    9,    1,   10,    1,   11,    1,
	   12,    1,   13,    1,   14,    2,    0,    8,    2,    0,   10,    2,
	    0,   11,    2,    0,   12,    2,    3,    0
	};
}

private static final byte _Scanner_actions[] = init__Scanner_actions_0();


private static byte[] init__Scanner_key_offsets_0()
{
	return new byte [] {
	    0,    0,    1,    2,    3,    4,    5,    6,   34,   34,   35,   35,
	   43,   44,   48,   52,   53,   57,   61,   69,   71
	};
}

private static final byte _Scanner_key_offsets[] = init__Scanner_key_offsets_0();


private static char[] init__Scanner_trans_keys_0()
{
	return new char [] {
	   10,   34,   10,   39,   10,   10,   10,   13,   32,   33,   34,   35,
	   36,   38,   39,   40,   41,   42,   44,   59,   60,   94,   95,  124,
	   37,   47,   48,   57,   61,   62,   64,   90,   97,  122,   61,   36,
	   95,   48,   57,   64,   90,   97,  122,   38,   10,   13,   46,   59,
	   10,   13,   46,   59,   42,   10,   13,   46,   59,   10,   13,   46,
	   59,   36,   95,   48,   57,   64,   90,   97,  122,   60,   61,  124,
	    0
	};
}

private static final char _Scanner_trans_keys[] = init__Scanner_trans_keys_0();


private static byte[] init__Scanner_single_lengths_0()
{
	return new byte [] {
	    0,    1,    1,    1,    1,    1,    1,   18,    0,    1,    0,    2,
	    1,    4,    4,    1,    4,    4,    2,    0,    1
	};
}

private static final byte _Scanner_single_lengths[] = init__Scanner_single_lengths_0();


private static byte[] init__Scanner_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    5,    0,    0,    0,    3,
	    0,    0,    0,    0,    0,    0,    3,    1,    0
	};
}

private static final byte _Scanner_range_lengths[] = init__Scanner_range_lengths_0();


private static byte[] init__Scanner_index_offsets_0()
{
	return new byte [] {
	    0,    0,    2,    4,    6,    8,   10,   12,   36,   37,   39,   40,
	   46,   48,   53,   58,   60,   65,   70,   76,   78
	};
}

private static final byte _Scanner_index_offsets[] = init__Scanner_index_offsets_0();


private static byte[] init__Scanner_trans_targs_0()
{
	return new byte [] {
	    8,    0,    7,    2,   10,    3,    7,    4,   14,    7,   17,    7,
	    8,    1,    7,    9,    2,    3,   11,   12,    4,   13,    7,   15,
	   16,    7,   19,    7,   11,   20,    7,   18,    9,   11,   11,    0,
	    7,    7,    7,    7,   11,   11,   11,   11,   11,    7,    7,    7,
	   14,    5,   13,   13,    7,   14,    5,   13,   13,    7,    7,    7,
	   17,    6,   16,   16,    7,   17,    6,   16,   16,    7,   11,   11,
	   18,   11,   11,    7,    7,    7,    7,    7,    7,    7,    7,    7,
	    7,    7,    7,    7,    7,    7,    7,    7,    7,    7,    7,    0
	};
}

private static final byte _Scanner_trans_targs[] = init__Scanner_trans_targs_0();


private static byte[] init__Scanner_trans_actions_0()
{
	return new byte [] {
	    0,    0,   11,    0,    0,    0,   11,    0,    7,   25,    7,   27,
	    0,    0,    9,    0,    0,    0,    0,    0,    0,    7,   15,    0,
	    7,   13,    0,   13,    0,    0,   13,    0,    0,    0,    0,    0,
	   32,   13,   19,   29,    0,    0,    0,    0,    0,   19,   13,   19,
	    7,    0,    7,    7,   21,   41,    1,   41,   41,   35,   13,   19,
	    7,    0,    7,    7,   23,   41,    1,   41,   41,   38,    0,    0,
	    0,    0,    0,   17,   13,   19,   13,   19,   25,   27,   32,   19,
	   29,   19,   19,   21,   35,   19,   23,   38,   17,   19,   19,    0
	};
}

private static final byte _Scanner_trans_actions[] = init__Scanner_trans_actions_0();


private static byte[] init__Scanner_to_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0
	};
}

private static final byte _Scanner_to_state_actions[] = init__Scanner_to_state_actions_0();


private static byte[] init__Scanner_from_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,    0,    5,    0,    0,    0,    0,
	    0,    0,    0,    0,    0,    0,    0,    0,    0
	};
}

private static final byte _Scanner_from_state_actions[] = init__Scanner_from_state_actions_0();


private static byte[] init__Scanner_eof_trans_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,   81,   82,    0,   83,   95,   85,   95,
	   95,   88,   89,   95,   91,   92,   93,   95,   95
	};
}

private static final byte _Scanner_eof_trans[] = init__Scanner_eof_trans_0();


static final int Scanner_start = 7;
static final int Scanner_error = 0;

static final int Scanner_en_main = 7;

// line 50 "src/min/lang/Scanner.rl"

  
  @SuppressWarnings("fallthrough")
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs, top;
    int eof = data.length;
    int p = 0, pe = eof, ts = 0, te = 0, act = 0;
    int[] stack = new int[32];
    int lineno = 1;
    
    
// line 177 "src/min/lang/Scanner.java"
	{
	cs = Scanner_start;
	ts = -1;
	te = -1;
	act = 0;
	}
// line 62 "src/min/lang/Scanner.rl"
    
// line 186 "src/min/lang/Scanner.java"
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
	case 2:
// line 1 "src/min/lang/Scanner.rl"
	{ts = p;}
	break;
// line 215 "src/min/lang/Scanner.java"
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
// line 18 "src/min/lang/Scanner.rl"
	{ lineno++; }
	break;
	case 3:
// line 1 "src/min/lang/Scanner.rl"
	{te = p+1;}
	break;
	case 4:
// line 35 "src/min/lang/Scanner.rl"
	{te = p+1;}
	break;
	case 5:
// line 37 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), MinObject.newString(getSlice(ts + 1, te - 1)))); }}
	break;
	case 6:
// line 39 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te))); }}
	break;
	case 7:
// line 42 "src/min/lang/Scanner.rl"
	{te = p+1;{
        if (this.argStack.empty())
          throw new ParsingException("Unmatched closing parenthesis at line " + lineno);
        this.message = this.argStack.pop();
      }}
	break;
	case 8:
// line 36 "src/min/lang/Scanner.rl"
	{te = p;p--;}
	break;
	case 9:
// line 38 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); }}
	break;
	case 10:
// line 39 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te))); }}
	break;
	case 11:
// line 40 "src/min/lang/Scanner.rl"
	{te = p;p--;{ this.argStack.push(this.message); this.message = null; }}
	break;
	case 12:
// line 41 "src/min/lang/Scanner.rl"
	{te = p;p--;{ this.message = null; }}
	break;
	case 13:
// line 40 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{ this.argStack.push(this.message); this.message = null; }}
	break;
	case 14:
// line 41 "src/min/lang/Scanner.rl"
	{{p = ((te))-1;}{ this.message = null; }}
	break;
// line 334 "src/min/lang/Scanner.java"
			}
		}
	}

case 2:
	_acts = _Scanner_to_state_actions[cs];
	_nacts = (int) _Scanner_actions[_acts++];
	while ( _nacts-- > 0 ) {
		switch ( _Scanner_actions[_acts++] ) {
	case 1:
// line 1 "src/min/lang/Scanner.rl"
	{ts = -1;}
	break;
// line 348 "src/min/lang/Scanner.java"
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
// line 63 "src/min/lang/Scanner.rl"
    
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