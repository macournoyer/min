module Min
  class Number < Min::Object
    attr_reader :value
    
    def initialize(context, value)
      @value = value
      const = context.constants[:Number]
      raise BootstrapError, "Number can't be found in context" unless const
      super const.vtable
    end
    
    def ==(other)
      Number === other && value == other.value
    end
    
    def self.bootstrap(runtime)
      number_vt = runtime.constants[:Object].vtable.delegated
      
      [:+, :-, :*, :/].each do |op|
        number_vt.add_method(op, RubyMethod.new(op, :value))
      end
      
      runtime.constants[:Number] = number_vt.allocate
    end
  end
end