The Min Language
==================

What?
-----
Min is almost Ruby + Python's indentation.

Why?
----
Ruby is a very nice language but has many dark
corners that make it hard to implement. I believe
most of those dark corners can be replaced with
approach that don't impact the language
implementation complexity.
Min will have a fully open object model allowing
anyone to fix, extend and hack any part of the
language.

How?
----
1. Create simple and basic interpreter written in
   Ruby with all core libs written in Min.
2. Write faster interpreter in Min using
   LLVM-ruby.
3. Rule the world and everything around.

Since (almost) all of the core will be written in Min
it will be reusable even if the interpreter is
rewritten in another language.

Philosophies
------------
* Clear and simple code is better.
* Less code is better.
* Language core should be implemented in itself.
* Non-Min code should be kept to a minimum.
* No special magic statements (class, def, if).

License
-------
MIT License
(c) Marc-Andre Cournoyer <macournoyer@gmail.com>
