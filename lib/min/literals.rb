module Min
  class String < Min::Object
    attributes :value
    
    def eval(context)
      self
    end
  end

  class Number < Min::Object
    attributes :value
    
    def eval(context)
      self
    end
  end
end