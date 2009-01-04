module Min
  class MethodNotFound < RuntimeError; end
  
  class Object
    attr_accessor :min_class
    
    def initialize(min_class)
      @min_class = min_class
    end
    
    def min_send(context, message, *args)
      if method = min_method(context, message)
        method.call(context, self, *args)
      else
        raise MethodNotFound, "Method not found #{message} on #{inspect}"
      end
    end
    
    def min_method(context, message)
      # short-circuit to break recursion in send.
      if message == :lookup && is_a?(Min::Class)
        @min_class.lookup(context, message)
      else
        @min_class.min_send(context, :lookup, message.to_min)
      end
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
      object.add_method(:send, RubyMethod.new(:min_send, :pass_context => true))
      object.add_method(:method, RubyMethod.new(:min_method, :pass_context => true))
      object.add_method(:puts, proc { |context, obj, str| puts str.eval(context).value })
      object.add_method(:eval, proc { |context, obj, code| runtime.eval(code.eval(context).value, context) })
      object.add_method(:load, proc { |context, obj, file| runtime.load(file.eval(context).value) })
    end
  end
end
