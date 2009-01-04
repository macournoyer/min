module Min
  class Literal < Min::Object
    attr_reader :value
    
    def initialize(value)
      @value = value
      super Min[self.class.min_constant_name]
    end
    
    def ==(other)
      self.class === other && value == other.value
    end
    alias :eql? :==
    
    def hash
      value.hash
    end
    
    def self.min_constant_name
      self.name[/::(\w+)$/, 1].to_sym
    end
    
    def self.bootstrap(runtime)
      klass = runtime[:Object].min_class.subclass
      
      @exposed_methods.each do |op|
        klass.add_method(op, RubyMethod.new(op, :delegate_to => :value))
      end
      
      runtime[min_constant_name] = klass
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

  class Array < Literal
    exposed_methods :[], :[]=, :size, :<<
  end

  class Hash < Literal
    exposed_methods :[], :[]=, :size
  end
end