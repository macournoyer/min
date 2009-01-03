module Min
  class RubyMethod
    def initialize(message, options={})
      @message      = message
      @delegate_to  = options[:delegate_to]
      @pass_context = options[:pass_context]
    end
    
    def call(context, receiver, *args)
      # Set receiver
      receiver = receiver.send(@delegate_to) if @delegate_to
      
      # Construct args
      call_args = args.map { |arg| arg.eval(context).value }
      call_args.unshift(context) if @pass_context
      
      receiver.send(@message, *call_args).to_min
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
