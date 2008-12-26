module Min
  class MethodNotFound < RuntimeError; end
  
  class Object
    class << self
      def attributes(*attributes)
        @attributes ||= begin
          attr_reader *attributes
          attributes
        end
      end
      
      def min_methods
        @min_methods ||= {}
      end
      
      def min_def(name, &block)
        min_methods[name] = block
      end
    end
    
    # Define Min methods
    
    min_def :puts do |context, *args|
      puts *args
    end
    
    min_def :def do |context, (name, block)|
      min_methods[name.value.to_sym] = proc { context.eval(block) }
    end
    
    def initialize(*attributes)
      self.class.attributes.zip(attributes).each do |attr, value|
        instance_variable_set "@#{attr}", value
      end
    end
    
    def min_send(context, message, *arguments)
      method = self.class.min_methods[message.to_sym]
      raise MethodNotFound, "Method not found: #{message}" unless method
      method.call(context, arguments)
    end
    
    def ==(other)
      other.class == self.class &&
      self.class.attributes.all? { |attr| send(attr) == other.send(attr) }
    end
  end
end