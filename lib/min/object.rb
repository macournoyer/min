module Min
  class MinExceptionRaised < RuntimeError
    attr_reader :min_class
    
    def initialize(min_class)
      @min_class = min_class
      super min_class.inspect
    end
  end
  
  class Object
    attr_accessor :min_class
    
    def initialize(min_class)
      @min_class = min_class
    end
    
    def min_send(context, message, *args)
      min_method(context, message).call(context, self, *args)
    end
    
    def min_method(context, message)
      # short-circuit to break recursion in send.
      if message == :lookup && is_a?(Min::Class)
        @min_class.lookup(context, message)
      else
        @min_class.min_send(context, :lookup, message.to_min)
      end
    end
    
    def min_raise(klass)
      raise MinExceptionRaised.new(klass)
    end
    
    def min_try(context, closure)
      closure.min_send(context, :call)
      nil
    rescue MinExceptionRaised => e
      e.min_class
    end
    
    def eval(context)
      self
    end
    
    def value
      self
    end
    
    def self.bootstrap(runtime)
      object = runtime[:Object]
      object.add_method(:class, RubyMethod.new(:min_class))
      
      # Reflection
      object.add_method(:send, RubyMethod.new(:min_send, :pass_context => true, :eval_args => false))
      object.add_method(:method, RubyMethod.new(:min_method, :pass_context => true))
      
      # Exception
      object.add_method(:raise, RubyMethod.new(:min_raise))
      object.add_method(:try, RubyMethod.new(:min_try, :pass_context => true))
      
      # Kernel
      object.add_method(:puts, RubyMethod.new { |context, obj, str| puts str.eval(context).value })
      object.add_method(:eval, RubyMethod.new { |context, obj, code| runtime.eval(code.eval(context).value, context) })
      object.add_method(:load, RubyMethod.new { |context, obj, file| runtime.load(file.eval(context).value) })
    end
  end
end
