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
    
    min_def :def do |context, name, block|
      self.class.min_methods[name.value.to_sym] = proc { context.eval(block) }
    end
    
    min_def :methods do |context|
      Min::Array.new(min_methods.to_min)
    end
    
    def initialize(*attributes)
      self.class.attributes.zip(attributes).each do |attr, value|
        instance_variable_set "@#{attr}", value
      end
    end
    
    def min_methods
      self.class.min_methods.keys
    end
    
    def min_send(context, message, *arguments)
      method = self.class.min_methods[message.to_sym]
      raise MethodNotFound, "Method not found: #{self.class}\##{message}" unless method
      method.bind(self).call(context, *arguments)
    end
    
    def eval(context)
      self
    end
    
    def ==(other)
      other.class == self.class &&
      self.class.attributes.all? { |attr| send(attr) == other.send(attr) }
    end
  end
end