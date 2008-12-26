module Min
  class Assign < Min::Object
    attributes :name, :value
    
    def eval(context)
      context.locals[name] = value.eval(context)
    end
  end
end