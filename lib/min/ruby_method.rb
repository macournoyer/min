module Min
  class RubyMethod < Min::Object
    def initialize(message=nil, options={}, &proc)
      @message      = message
      @proc         = proc
      @delegate_to  = options[:delegate_to]
      @pass_context = options[:pass_context]
      @eval_args    = !options[:eval_args].is_a?(FalseClass)
      
      super Min[:RubyMethod]
    end
    
    def call(context, receiver, *args)
      if @message
        send_message(context, receiver, *args)
      else
        @proc.call(context, receiver, *args)
      end.to_min
    end
    
    def self.bootstrap(runtime)
      klass = runtime[:Object].min_class.subclass
      
      klass.add_method(:call, RubyMethod.new(:call, :pass_context => true, :eval_args => false))
      
      runtime[:RubyMethod] = klass
    end
    
    private
      def send_message(context, receiver, *args)
        # Set receiver
        receiver = receiver.send(@delegate_to) if @delegate_to
        
        # Construct args
        if @eval_args
          call_args = args.map { |arg| arg.eval(context).value }
        else
          call_args = args
        end
        call_args.unshift context if @pass_context
        
        receiver.send(@message, *call_args)
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

class NilClass
  def to_min
    Min[:nil]
  end
end

class TrueClass
  def to_min
    Min[:true]
  end
end

class FalseClass
  def to_min
    Min[:false]
  end
end
