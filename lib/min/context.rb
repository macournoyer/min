module Min
  class Context
    attr_reader :min_self, :locals, :constants
    attr_accessor :parent
    
    def initialize(min_self, parent=nil)
      @parent    = parent
      @min_self  = min_self
      
      @locals    = HashWithParent.new(parent && parent.locals)
      @constants = HashWithParent.new(parent && parent.constants)
    end
    
    def eval(block)
      block.eval(self)
    end
    
    def create(min_self)
      self.class.new(min_self, self)
    end
  end
end