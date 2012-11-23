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

  def detatch
    @next = nil
    self
  end

  def tail
    if @next
      @next.tail
    else
      self
    end
  end

  def pop
    if @next.nil?
      nil
    elsif @next.next.nil?
      n = @next
      @next = nil
      n
    else
      @next.pop
    end
  end

  def append(m)
    tail.next = m
    self
  end
end

def M(*args)
  Message.new(*args)
end

# m = M("1", M("+", M("2")))
# m = M("1", M("+", M("2", M("*", M("3")))))
# m = M("1", M("*", M("2", M("+", M("3")))))
# m = M("x", M("=", M("2", M("+", M("3")))))
# m = M("x", M("=", M("!", M("2", M("+", M("3", M("*", M("1"))))))))
m = M("object", M("prop", M("=", M("x", M("y")))))
# m = M("object", M("=", M("x", M("y", M("x")))))
# m = M("x", M("a", M("=", M("e", M("d")))))
# m = M("a", M("b", M("+", M("c", M("d")))))
puts "input: " + m.fullname


#### Shunting yard algo ####
output_queue = []
stack = []

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
  next_m = m.next
  m.next = nil
  m = next_m
end

while operator = stack.pop
  output_queue.push operator
end

print "output_queue: "
p output_queue.map(&:fullname)


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


puts "output: " + stack.first.fullname
