# Source: http://en.wikipedia.org/wiki/Shunting-yard_algorithm
class Message
  class Operator
    attr_reader :name, :precedence, :type
    
    def initialize(name, precedence, type, associativity)
      @name = name
      @precedence = precedence
      @type = type
      @associativity = associativity
    end
    
    def right_to_left?
      @associativity == :right
    end
    
    def left_to_right?
      @associativity == :left
    end
    
    def nullary?
      @type == :nullary
    end
    
    def unary?
      @type == :unary
    end
    
    def binary?
      @type == :binary
    end
    
    def ternary?
      @type == :ternary
    end
  end
  
  Operators = {}
  def self.add_operator(names, *args)
    @precedence ||= 0
    @precedence += 1
    [names].flatten.each do |name|
      Operators[name] = Operator.new(name, @precedence, *args)      
    end
  end

  #
  #            == Operator precedence table ==
  #               (from lowest to highest)
  #
  #                                      type       assoc
  add_operator ["\n", "\r\n", ";"],      :nullary,  :left
  add_operator ["=", "+=", "-=", "*=",
                "/=", "&=", "|=",
                "&&=", "||="],           :ternary,  :right
  add_operator ["||", "or"],             :binary,   :left
  add_operator ["&&", "and"],            :binary,   :left
  add_operator ["|"],                    :binary,   :left
  add_operator ["^"],                    :binary,   :left
  add_operator ["&"],                    :binary,   :left
  add_operator ["==", "!=", "is",
                "=~", "!~"],             :binary,   :left
  add_operator ["<", "<=", ">", ">="],   :binary,   :left
  add_operator ["<<", ">>"],             :binary,   :left
  add_operator ["+", "-"],               :binary,   :left
  add_operator ["*", "/", "%"],          :binary,   :left
  add_operator ["!", "not", "~", "@",
                "-@", "+@", "*@", "&@"], :unary,    :right
  add_operator ["."],                    :nullary,  :left
  
  attr_reader :name, :operator
  attr_accessor :next, :prev, :args
  
  def initialize(name, _next=nil)
    @name = name
    self.next = _next
    @prev = nil
    @operator = Operators[name]
    @args = []
  end
  
  def fullname
    args_fullnames = @args.map(&:fullname).join(', ')
    [@name + (@args.any? ? "(#{args_fullnames})" : ""), (@next.fullname if @next)].compact.join
  end
  
  def to_s
    @name
  end

  def terminator?
    %w( . \n \r\n ).include? @name
  end

  def next=(n)
    @next = n
    n.prev = self if n
    n
  end

  # Get the tail of this message chain
  def tail
    if last?
      self
    else
      @next.tail
    end
  end

  def last?
    @next.nil? #|| terminator?
  end

  # Detach the last message from the chain
  def pop
    if last?
      nil
    elsif @next.last?
      detatch
    else
      @next.pop
    end
  end

  # Insert `m` right after the current message.
  # Before: <prev> <self> <next>
  # After:  <prev> <self> <m> <next>
  def insert(m)
    @next.prev = m if @next
    self.next = m
    m
  end

  # Append the message at the tail of the chain
  # Before: <self> <next> ... <last> <terminator?> ...
  # After:  <self> <next> ... <last> <m> <terminator?> ...
  def append(m)
    tail.insert m
    self
  end

  # Detatch the next message chain (up to a terminator or end) and returns it.
  # Before: <self> <next> ... <tail> <terminator?>
  # After:  <self> <terminator?>
  def detatch
    m = @next
    # self.next = tail.next ????
    self.next = nil
    m
  end

  def shuffle
    #### Shunting yard algo ####
    output_queue = []
    stack = []
    m = self

    while m
      if m.operator
        m2 = stack.last
        while m2 && ((m.operator.left_to_right? && m.operator.precedence <= m2.operator.precedence) ||
                     (m.operator.right_to_left? && m.operator.precedence < m2.operator.precedence))
          output_queue.push stack.pop
          m2 = stack.last
        end
        stack.push m
      else
        output_queue.push m
        # Advance to the next operator
        # TODO perhaps could refactor to combine w/ parent while?
        m = m.next until m.next.nil? || m.next.operator
      end

      # Cut it from the message chain
      m = m.detatch
    end

    while operator = stack.pop
      output_queue.push operator
    end

    # Convert from RPN
    stack = []
    while m = output_queue.shift
      if m.operator
        case true
        when m.operator.nullary?
          after = stack.pop
          before = stack.pop
          before.append m
          m.append after
          stack.push before
        when m.operator.unary?
          arg = stack.pop
          m.args = [arg]
          stack.push m
        when m.operator.binary?
          arg = stack.pop
          m.args = [arg]
          receiver = stack.pop
          receiver.append m
          stack.push receiver
        when m.operator.ternary?
          val = stack.pop
          receiver = stack.pop
          if name = receiver.pop
            m.args = [name, val]
            receiver.append m
            stack.push receiver
          else
            m.args = [receiver, val]
            stack.push m
          end
        end
      else
        stack.push m
      end
    end

    # Empty the stack
    m = nil
    until stack.empty?
      n = m
      m = stack.pop
      m.append n if n
    end

    m
  end
end

def M(names)
  m = nil
  while name = names.pop
    m = Message.new(name, m)
  end
  m
end

if __FILE__ == $PROGRAM_NAME
  require "test/unit"
  
  class ShufflingTest < Test::Unit::TestCase
    def xtest_nullary
      assert_equal "a.b.c", M(%w( a . b . c )).shuffle.fullname
      assert_equal "a.b;c", M(%w( a . b ; c )).shuffle.fullname
    end

    def xtest_ternary
      assert_equal "=(x, 2+(3))", M(%w( x = 2 + 3 )).shuffle.fullname
      assert_equal "object.=(prop, x.y)", M(%w( object . prop = x . y )).shuffle.fullname
      assert_equal "=(object, x.y.x)", M(%w( object = x . y . x )).shuffle.fullname
      assert_equal "x.=(a, e.d)", M(%w( x . a = e . d )).shuffle.fullname
      assert_equal "=(a, =(b, c))", M(%w( a = b = c )).shuffle.fullname
      assert_equal "||=(a, &&=(b, c))", M(%w( a ||= b &&= c )).shuffle.fullname
    end

    def test_binary
      assert_equal "1+(2)", M(%w( 1 + 2 )).shuffle.fullname
      assert_equal "1+(2*(3))", M(%w( 1 + 2 * 3 )).shuffle.fullname
      assert_equal "1*(2)+(3)", M(%w( 1 * 2 + 3 )).shuffle.fullname
      assert_equal "1+(2.to_i)", M(%w( 1 + 2 . to_i )).shuffle.fullname
      assert_equal "a.b+(c.d)", M(%w( a . b + c . d )).shuffle.fullname      
    end

    def test_unary
      assert_equal "-@(a)", M(%w( -@ a )).shuffle.fullname
      assert_equal "!(a)", M(%w( ! a )).shuffle.fullname
      assert_equal "not(a)", M(%w( not a )).shuffle.fullname
      assert_equal "not(a.b)", M(%w( not a . b )).shuffle.fullname
      assert_equal "!(@(a))", M(%w( ! @ a )).shuffle.fullname
      assert_equal "!(!(a))", M(%w( ! ! a )).shuffle.fullname
      assert_equal "a.b@(c)", M(%w( a . b @ c )).shuffle.fullname
      assert_equal "a+(!(b.c))", M(%w( a + ! b . c )).shuffle.fullname
      assert_equal "=(x, !(2)+(3*(1)))", M(%w( x = ! 2 + 3 * 1 )).shuffle.fullname
    end

    def test_with_terminator
      assert_equal "1\n2", M(%W( 1 \n 2 )).shuffle.fullname
      assert_equal "1+(2)\n1", M(%W( 1 + 2 \n 1 )).shuffle.fullname
      assert_equal "=(x, 2)\n=(y, 3+(4))", M(%W( x = 2 \n y = 3 + 4 )).shuffle.fullname
      assert_equal "a.=(x, 2+(3))\n1", M(%W( a . x = 2 + 3 \n 1 )).shuffle.fullname
    end
  end
end
