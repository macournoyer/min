module Min
  class Number < Min::Object
    attr_reader :value
    
    def initialize(value)
      @value = value
      super Min[:Number].vtable
    end
    
    def ==(other)
      Number === other && value == other.value
    end
    
    def self.bootstrap(runtime)
      number_vt = runtime[:Object].vtable.delegated
      
      [:+, :-, :*, :/].each do |op|
        number_vt.add_method(op, RubyMethod.new(op, :value))
      end
      
      runtime[:Number] = number_vt.allocate
    end
  end
end