module Min
  class String < Min::Object
    attributes :value
    
    def eval(context)
      value
    end
  end

  class Number < Min::Object
    attributes :value
    
    def eval(context)
      value
    end
  end
end