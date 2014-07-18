# THE MIN LANGUAGE

Min is a Prototype-based language with syntax inspired by Io and Ruby, topped
with Python's indentation (but only when you want to). It's aimed at being the
most readable and powerful language ever, really!
Oh, and not too slow (running on the JVM).


```

  Min = Io clone do:
    you_can indent to("create blocks")
    you_can indent
            to("split a message chain in several lines")
    
    initialize = method:
      @it = "rly looks like Ruby"
      @but = "with lots of Io flavor"
      
    if(indentation == confusing?("sometimes"),
      use () like in(Io)
    )
```

## Philosophies
* Everything is an object.
* Everything is a message, no special magic statements (if, def, while, +, &&).
* As little parenthesis as possible.
* Most of the core is open.
* As close to English as possible.

## Features
* Running on the JVM
* Indentation is an option
* Ruby-like syntax
* More to come...

## License
MIT License, (c) macournoyer

## Status on travis-ci.org
[![Build Status](https://travis-ci.org/ker2x/min.svg?branch=master)](https://travis-ci.org/ker2x/min)
