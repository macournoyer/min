module Min
  class Constant < Min::Object
    attributes :name
    
    def eval(context)
      context.constants[name]
    end
  end
  
  class AssignConstant < Min::Object
    attributes :name, :value
    
    def eval(context)
      context.constants[name] = value.eval(context)
    end
  end
end