module Min
  class Literal < Min::Object
    attr_reader :value
    
    def initialize(value)
      @value = value
      super Min[self.class.min_constant_name].vtable
    end
    
    def ==(other)
      self.class === other && value == other.value
    end
    
    def self.min_constant_name
      self.name[/::(\w+)$/, 1].to_sym
    end
    
    def self.bootstrap(runtime)
      vtable = runtime[:Object].vtable.delegated
      
      @exposed_methods.each do |op|
        vtable.add_method(op, RubyMethod.new(op, :value))
      end
      
      runtime[min_constant_name] = vtable.allocate
    end
    
    def self.exposed_methods(*methods)
      @exposed_methods = methods
    end
  end
  
  class Number < Literal
    exposed_methods :+, :-, :*, :/, :to_s
  end
  
  class String < Literal
    exposed_methods :+, :to_sym, :to_s
  end
  
  class Symbol < Literal
    exposed_methods :to_s
  end
end