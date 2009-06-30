// line 1 "src/min/lang/Scanner.rl"
package min.lang;

public class Scanner {
  private final String input;

  public Scanner(String input) {
    this.input = input;
  }

  
// line 13 "src/min/lang/Scanner.java"
private static byte[] init__Scanner_actions_0()
{
	return new byte [] {
	    0,    1,    0,    1,    1,    2,    1,    0,    2,    2,    1
	};
}

private static final byte _Scanner_actions[] = init__Scanner_actions_0();


private static byte[] init__Scanner_key_offsets_0()
{
	return new byte [] {
	    0,    0,    5,   13,   14,   18,   22
	};
}

private static final byte _Scanner_key_offsets[] = init__Scanner_key_offsets_0();


private static char[] init__Scanner_trans_keys_0()
{
	return new char [] {
	   10,   13,   46,   97,  122,   10,   13,   32,   46,    9,   12,   97,
	  122,   10,    9,   32,   11,   12,    9,   32,   11,   12,   10,   13,
	   32,   46,    9,   12,   48,   57,   97,  122,    0
	};
}

private static final char _Scanner_trans_keys[] = init__Scanner_trans_keys_0();


private static byte[] init__Scanner_single_lengths_0()
{
	return new byte [] {
	    0,    3,    4,    1,    2,    2,    4
	};
}

private static final byte _Scanner_single_lengths[] = init__Scanner_single_lengths_0();


private static byte[] init__Scanner_range_lengths_0()
{
	return new byte [] {
	    0,    1,    2,    0,    1,    1,    3
	};
}

private static final byte _Scanner_range_lengths[] = init__Scanner_range_lengths_0();


private static byte[] init__Scanner_index_offsets_0()
{
	return new byte [] {
	    0,    0,    5,   12,   14,   18,   22
	};
}

private static final byte _Scanner_index_offsets[] = init__Scanner_index_offsets_0();


private static byte[] init__Scanner_indicies_0()
{
	return new byte [] {
	    0,    2,    3,    4,    1,    0,    2,    5,    3,    5,    4,    1,
	    6,    1,    7,    7,    7,    1,    8,    8,    8,    1,    9,   10,
	    8,   11,    8,   12,   12,    1,    0
	};
}

private static final byte _Scanner_indicies[] = init__Scanner_indicies_0();


private static byte[] init__Scanner_trans_targs_0()
{
	return new byte [] {
	    4,    0,    3,    5,    6,    2,    4,    2,    2,    4,    3,    5,
	    6
	};
}

private static final byte _Scanner_trans_targs[] = init__Scanner_trans_targs_0();


private static byte[] init__Scanner_trans_actions_0()
{
	return new byte [] {
	    1,    0,    1,    1,    1,    0,    0,    8,    3,    5,    5,    5,
	    0
	};
}

private static final byte _Scanner_trans_actions[] = init__Scanner_trans_actions_0();


private static byte[] init__Scanner_eof_actions_0()
{
	return new byte [] {
	    0,    0,    0,    0,    8,    3,    3
	};
}

private static final byte _Scanner_eof_actions[] = init__Scanner_eof_actions_0();


static final int Scanner_start = 1;
static final int Scanner_first_final = 4;
static final int Scanner_error = 0;

static final int Scanner_en_main = 1;

// line 31 "src/min/lang/Scanner.rl"

  
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
    Message root = null;
    Message message = null;
    Message next = null;
    
    
// line 144 "src/min/lang/Scanner.java"
	{
	cs = Scanner_start;
	}
// line 48 "src/min/lang/Scanner.rl"
    
// line 150 "src/min/lang/Scanner.java"
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
	cs = _Scanner_trans_targs[_trans];

	if ( _Scanner_trans_actions[_trans] != 0 ) {
		_acts = _Scanner_trans_actions[_trans];
		_nacts = (int) _Scanner_actions[_acts++];
		while ( _nacts-- > 0 )
	{
			switch ( _Scanner_actions[_acts++] )
			{
	case 0:
// line 13 "src/min/lang/Scanner.rl"
	{ mark = p; }
	break;
	case 1:
// line 14 "src/min/lang/Scanner.rl"
	{
      next = new Message(getSlice(mark, p));
      if (message != null) message.setNext(next);
      message = next;
      if (root == null) root = message;
    }
	break;
	case 2:
// line 22 "src/min/lang/Scanner.rl"
	{ lineno++; }
	break;
// line 247 "src/min/lang/Scanner.java"
			}
		}
	}

case 2:
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
	int __acts = _Scanner_eof_actions[cs];
	int __nacts = (int) _Scanner_actions[__acts++];
	while ( __nacts-- > 0 ) {
		switch ( _Scanner_actions[__acts++] ) {
	case 1:
// line 14 "src/min/lang/Scanner.rl"
	{
      next = new Message(getSlice(mark, p));
      if (message != null) message.setNext(next);
      message = next;
      if (root == null) root = message;
    }
	break;
	case 2:
// line 22 "src/min/lang/Scanner.rl"
	{ lineno++; }
	break;
// line 281 "src/min/lang/Scanner.java"
		}
	}
	}

case 5:
	}
	break; }
	}
// line 49 "src/min/lang/Scanner.rl"
    
    if (cs == Scanner_error || p != pe) {
      // TODO Better error reporting
      throw new ParsingException(String.format("Syntax error at line %d", lineno));
    }
    
    System.out.println(root.toString());
    return root;
  }
  
  private String getSlice(int start, int end) {
    return input.substring(start, end);
  }
}