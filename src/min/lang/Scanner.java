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
	    0,    1,    1,    1,    2,    1,    3,    1,    4,    1,    5,    1,
	    6,    1,    7,    1,    8,    1,    9,    1,   10,    2,    0,   10
	};
}

private static final byte _Scanner_actions[] = init__Scanner_actions_0();


private static byte[] init__Scanner_key_offsets_0()
{
	return new byte [] {
	    0,    0,    1,    2,    3,    4,   31,   31,   32,   40,   41,   42,
	   50
	};
}

private static final byte _Scanner_key_offsets[] = init__Scanner_key_offsets_0();


private static char[] init__Scanner_trans_keys_0()
{
	return new char [] {
	   10,   34,   39,   61,   10,   13,   32,   33,   34,   36,   38,   39,
	   40,   41,   42,   44,   59,   61,   94,   95,  124,   37,   47,   48,
	   57,   60,   62,   64,   90,   97,  122,   61,   36,   95,   48,   57,
	   64,   90,   97,  122,   38,   42,   36,   95,   48,   57,   64,   90,
	   97,  122,  124,    0
	};
}

private static final char _Scanner_trans_keys[] = init__Scanner_trans_keys_0();


private static byte[] init__Scanner_single_lengths_0()
{
	return new byte [] {
	    0,    1,    1,    1,    1,   17,    0,    1,    2,    1,    1,    2,
	    1
	};
}

private static final byte _Scanner_single_lengths[] = init__Scanner_single_lengths_0();


private static byte[] init__Scanner_range_lengths_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    5,    0,    0,    3,    0,    0,    3,
	    0
	};
}

private static final byte _Scanner_range_lengths[] = init__Scanner_range_lengths_0();


private static byte[] init__Scanner_index_offsets_0()
{
	return new byte [] {
	    0,    0,    2,    4,    6,    8,   31,   32,   34,   40,   42,   44,
	   50
	};
}

private static final byte _Scanner_index_offsets[] = init__Scanner_index_offsets_0();


private static byte[] init__Scanner_indicies_0()
{
	return new byte [] {
	    0,    1,    3,    2,    3,    4,    5,    1,    0,    6,    7,    8,
	    2,    9,   10,    4,   11,   12,   13,   14,    5,   16,    5,    9,
	   17,    5,   15,    8,    9,    9,    1,   18,    5,   19,    9,    9,
	    9,    9,    9,   19,    5,   19,    5,   19,    9,    9,   15,    9,
	    9,   20,    5,   19,    0
	};
}

private static final byte _Scanner_indicies[] = init__Scanner_indicies_0();


private static byte[] init__Scanner_trans_targs_0()
{
	return new byte [] {
	    6,    0,    2,    5,    3,    5,    1,    5,    7,    8,    9,    5,
	    5,   10,    5,   11,    4,   12,    5,    5,    5
	};
}

private static final byte _Scanner_trans_targs[] = init__Scanner_trans_targs_0();


private static byte[] init__Scanner_trans_actions_0()
{
	return new byte [] {
	    0,    0,    0,    7,    0,    9,    0,    5,    0,    0,    0,   11,
	   15,    0,   13,    0,    0,    0,   21,   19,   17
	};
}

private static final byte _Scanner_trans_actions[] = init__Scanner_trans_actions_0();


private static byte[] init__Scanner_to_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
	    0
	};
}

private static final byte _Scanner_to_state_actions[] = init__Scanner_to_state_actions_0();


private static byte[] init__Scanner_from_state_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,    0,
	    0
	};
}

private static final byte _Scanner_from_state_actions[] = init__Scanner_from_state_actions_0();


private static byte[] init__Scanner_eof_trans_0()
{
	return new byte [] {
	    0,    0,    0,    0,    0,    0,   19,   20,   20,   20,   20,   21,
	   20
	};
}

private static final byte _Scanner_eof_trans[] = init__Scanner_eof_trans_0();


static final int Scanner_start = 5;
static final int Scanner_error = 0;

static final int Scanner_en_main = 5;

// line 46 "src/min/lang/Scanner.rl"

  
  public Message scan() throws ParsingException {
    char[] data = input.toCharArray();
    int cs, top;
    int eof = data.length;
    int p = 0, pe = eof, ts = 0, te = 0, act = 0;
    int[] stack = new int[32];
    int lineno = 1;
    
    
// line 174 "src/min/lang/Scanner.java"
	{
	cs = Scanner_start;
	ts = -1;
	te = -1;
	act = 0;
	}
// line 57 "src/min/lang/Scanner.rl"
    
// line 183 "src/min/lang/Scanner.java"
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
// line 212 "src/min/lang/Scanner.java"
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
// line 18 "src/min/lang/Scanner.rl"
	{ lineno++; }
	break;
	case 3:
// line 32 "src/min/lang/Scanner.rl"
	{te = p+1;}
	break;
	case 4:
// line 33 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te), MinObject.newString(getSlice(ts + 1, te - 1)))); }}
	break;
	case 5:
// line 35 "src/min/lang/Scanner.rl"
	{te = p+1;{ pushMessage(new Message(getSlice(ts, te))); }}
	break;
	case 6:
// line 36 "src/min/lang/Scanner.rl"
	{te = p+1;{ this.argStack.push(this.message); this.message = null; }}
	break;
	case 7:
// line 37 "src/min/lang/Scanner.rl"
	{te = p+1;{ this.message = null; }}
	break;
	case 8:
// line 38 "src/min/lang/Scanner.rl"
	{te = p+1;{
        if (this.argStack.empty())
          throw new ParsingException("Unmatched closing parenthesis at line " + lineno);
        this.message = this.argStack.pop();
      }}
	break;
	case 9:
// line 34 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te), MinObject.newNumber(Integer.parseInt(getSlice(ts, te))))); }}
	break;
	case 10:
// line 35 "src/min/lang/Scanner.rl"
	{te = p;p--;{ pushMessage(new Message(getSlice(ts, te))); }}
	break;
// line 316 "src/min/lang/Scanner.java"
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
// line 330 "src/min/lang/Scanner.java"
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
// line 58 "src/min/lang/Scanner.rl"
    
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