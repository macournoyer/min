module Min
  class RubyMethod
    def initialize(message, attribute=nil)
      @message   = message
      @attribute = attribute
    end
    
    def call(context, receiver, *args)
      if @attribute
        receiver = receiver.send(@attribute)
      end
      receiver.send(@message, *args.map { |arg| arg.eval(context).value }).to_min
    end
  end
end

# Convertions from Ruby object to Min object
class Object
  def to_min
    self
  end
end

class Fixnum
  def to_min
    Min::Number.new(self)
  end
end

class Float
  def to_min
    Min::Number.new(self)
  end
end

class String
  def to_min
    Min::String.new(self)
  end
end

class Symbol
  def to_min
    Min::Symbol.new(self)
  end
end

class Array
  def to_min
    Min::Array.new(self)
  end
end

class Hash
  def to_min
    Min::Hash.new(self)
  end
end
