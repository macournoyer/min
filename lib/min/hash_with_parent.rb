module Min
  class HashWithParent < Hash
    def initialize(parent)
      @parent = parent
      super()
    end
    
    def [](key)
      super || @parent && @parent[key]
    end
  end
end
