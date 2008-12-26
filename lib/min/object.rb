module Min
  class MethodNotFound < RuntimeError; end
  
  class Object
    attr_accessor :min_class, :min_methods
    
    def initialize(*attributes)
      @min_class   = nil
      @min_methods = {}
      
      self.class.attributes.zip(attributes).each do |attr, value|
        instance_variable_set "@#{attr}", value
      end
      
      # Define Min methods
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
      @min_methods[name] = proc { |context, args| object.send(method, *args.map { |arg| context.eval(arg) }) }
    end
    
    def ==(other)
      other.class == self.class &&
      self.class.attributes.all? { |attr| send(attr) == other.send(attr) }
    end
    
    def self.attributes(*attributes)
      @attributes ||= begin
        attr_reader *attributes
        attributes
      end
    end
  end
end