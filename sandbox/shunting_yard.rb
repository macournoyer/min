# Source: http://en.wikipedia.org/wiki/Shunting-yard_algorithm
class Message
  class Operator
    attr_reader :name, :precedence, :type
    
    def initialize(name, precedence, type, associativity=:right)
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
  
  Operators = [
    Operator.new("=", 1, :ternary, :right),
    Operator.new("+", 2, :binary, :left),
    Operator.new("-", 2, :binary, :left),
    Operator.new("*", 3, :binary, :left),
    Operator.new("/", 3, :binary, :left),
    Operator.new("%", 3, :binary, :left),
    Operator.new("!", 4, :unary, :right)
  ]
  
  attr_reader :name, :operator
  attr_accessor :next, :args
  
  def initialize(name, _next=nil)
    @name = name
    @next = _next
    @operator = Operators.detect { |o| o.name == @name }
    @args = []
  end
  
  def fullname
    args_fullnames = @args.map(&:fullname).join(', ')
    [@name + (@args.any? ? "(#{args_fullnames})" : ""), (@next.fullname if @next)].compact.join(" ")
  end
  
  def to_s
    @name
  end

  # Detatch the next message and returns it
  def detatch
    m = @next
    @next = nil
    m
  end

  # Get the tail of this message chain
  def tail
    if @next
      @next.tail
    else
      self
    end
  end

  def last?
    @next.nil?
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

  # Append the message as the tail of the chain
  def append(m)
    tail.next = m
    self
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
        when m.operator.unary?
          m.args = [stack.pop]
          stack.push m
        when m.operator.binary?
          m.args = [stack.pop]
          m2 = stack.pop
          m2.append m
          stack.push m2
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

    raise "Parsing error: things left on the stack" unless stack.size == 1

    stack.first
  end
end

def M(*args)
  Message.new(*args)
end

if __FILE__ == $PROGRAM_NAME
  require "test/unit"
  
  class ShufflingTest < Test::Unit::TestCase
    def test_case_name
      assert_equal "1 +(2)", M("1", M("+", M("2"))).shuffle.fullname
      assert_equal "1 +(2 *(3))", M("1", M("+", M("2", M("*", M("3"))))).shuffle.fullname
      assert_equal "1 *(2) +(3)", M("1", M("*", M("2", M("+", M("3"))))).shuffle.fullname
      assert_equal "=(x, 2 +(3))", M("x", M("=", M("2", M("+", M("3"))))).shuffle.fullname
      assert_equal "=(x, !(2) +(3 *(1)))", M("x", M("=", M("!", M("2", M("+", M("3", M("*", M("1")))))))).shuffle.fullname
      assert_equal "object =(prop, x y)", M("object", M("prop", M("=", M("x", M("y"))))).shuffle.fullname
      assert_equal "=(object, x y x)", M("object", M("=", M("x", M("y", M("x"))))).shuffle.fullname
      assert_equal "x =(a, e d)", M("x", M("a", M("=", M("e", M("d"))))).shuffle.fullname
      assert_equal "a b +(c d)", M("a", M("b", M("+", M("c", M("d"))))).shuffle.fullname
      assert_equal "=(a, =(b, c))", M("a", M("=", M("b", M("=", M("c"))))).shuffle.fullname
    end
  end
end
