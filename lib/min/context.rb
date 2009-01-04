module Min
  class Context
    attr_reader   :slots
    attr_accessor :min_self, :parent
    
    def initialize(min_self, parent=nil)
      @parent   = parent
      @min_self = min_self
      @slots    = HashWithParent.new(parent && parent.slots)
    end
    
    def [](name)
      @slots[name]
    end
    
    def []=(name, value)
      @slots[name] = value
    end
    
    def create(min_self=nil)
      self.class.new(min_self || @min_self, self)
    end
  end
end