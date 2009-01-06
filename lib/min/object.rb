module Min
  class MinExceptionRaised < RuntimeError
    attr_reader :object
    
    def initialize(object)
      @object = object
      super object.respond_to?(:value) ? object.value : object.to_s
    end
  end
  
  class Object
    attr_accessor :vtable
    
    def initialize(vtable)
      @vtable = vtable
    end
    
    def min_send(context, message, *args)
      method = min_method(context, message)
      if method.nil? || method == Min[:nil]
        min_raise "Method not found #{message} on #{to_s}".to_min
      else
        method.call(context, self, *args)
      end
    end
    
    def min_method(context, message)
      # short-circuit to break recursion in send.
      if message == :lookup && is_a?(VTable)
        @vtable.lookup(context, message)
      else
        @vtable.min_send(context, :lookup, message.to_min)
      end
    end
    
    def min_raise(object)
      raise MinExceptionRaised.new(object)
    end
    
    def min_try(context, closure)
      closure.min_send(context, :call)
      nil
    rescue MinExceptionRaised => e
      e.object
    end
    
    def eval(context)
      self
    end
    
    def value
      self
    end
    
    def self.bootstrap(runtime)
      vtable = runtime[:Object].vtable
      vtable.add_method(:vtable, RubyMethod.new(:vtable))
      
      # Reflection
      vtable.add_method(:send, RubyMethod.new(:min_send, :pass_context => true, :eval_args => false))
      vtable.add_method(:method, RubyMethod.new(:min_method, :pass_context => true))
      
      # Exception
      vtable.add_method(:raise, RubyMethod.new(:min_raise))
      vtable.add_method(:try, RubyMethod.new(:min_try, :pass_context => true))
      
      # Kernel
      vtable.add_method(:puts, RubyMethod.new { |context, obj, str| puts str.eval(context).value })
      vtable.add_method(:eval, RubyMethod.new { |context, obj, code| runtime.eval(code.eval(context).value, context) })
      vtable.add_method(:load, RubyMethod.new { |context, obj, file| runtime.load(file.eval(context).value) })
    end
  end
end
