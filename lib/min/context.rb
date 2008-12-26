module Min
  class Context
    attr_reader :min_self, :min_class
    
    def initialize(runtime, min_self, min_class)
      @runtime   = runtime
      @min_self  = min_self
      @min_klass = min_class
    end
    
    def eval(block)
      block.eval(self)
    end
  end
end