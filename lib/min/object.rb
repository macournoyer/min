module Min
  class MethodNotFound < RuntimeError; end
  
  class Object
    attr_accessor :min_class, :min_methods
    
    def initialize
      @min_class   = nil
      @min_methods = {}
      
      ruby_method :puts, Kernel, :puts
      
      min_def :def do |context, (name, block)|
        @min_methods[name.value.to_sym] = proc { context.eval(block) }
      end
    end
    
    def min_send(context, message, *arguments)
      method = @min_methods[message.to_sym]
      raise MethodNotFound, "Method not found: #{message}" unless method
      method.call(context, arguments)
    end
    
    def min_def(name, &block)
      @min_methods[name] = block
    end
    
    def ruby_method(name, object, method)
      @min_methods[name] = proc { |context, args| object.send(method, *args.map { |arg| arg.to_ruby }) }
    end
  end
end