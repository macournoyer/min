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
  
  attr_reader :name, :next, :operator
  
  def initialize(name, _next=nil)
    @name = name
    @next = _next
    @operator = Operators.detect { |o| o.name == @name }
  end
  
  def fullname
    [@name, (@next.fullname if @next)] * " "
  end
  
  def to_s
    @name
  end
end

def M(*args)
  Message.new(*args)
end

# m = M("1", M("+", M("2", M("*", M("3")))))
# m = M("1", M("*", M("2", M("+", M("3")))))
# m = M("x", M("=", M("2", M("+", M("3")))))
m = M("x", M("=", M("!", M("2", M("+", M("3", M("*", M("1"))))))))
puts m.fullname

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
  end
  m = m.next
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
      stack.push "#{m}(#{stack.pop})"
    when m.operator.binary?
      arg = stack.pop
      stack.push "#{stack.pop} #{m}(#{arg})"
    when m.operator.ternary?
      val = stack.pop
      stack.push "#{m}(#{stack.pop}, #{val})"
    end
  else
    stack.push m
  end
end

# 3 *(2) +(1)
puts stack.first
