module Min
  class Block
    def eval(context)
      nodes.map { |node| node.eval(context) }.last
    end
  end
  
  class Closure
    def eval(context)
      self
    end
    
    def call(context, receiver, *args)
      # TODO pass args
      block.eval(context)
    end
  end
  
  class Call
    def eval(context)
      if receiver.nil? && value = context.locals[message]
        # local var
        return value
      end
      (receiver || context.min_self).eval(context).min_send(context, message, *arguments)
    end
  end
  
  class Assign
    def eval(context)
      context.locals[name] = value.eval(context)
    end
  end
  
  class Constant
    def eval(context)
      context.constants[name]
    end
  end
  
  class AssignConstant
    def eval(context)
      context.constants[name] = value.eval(context)
    end
  end
  
  class Object
    def eval(context)
      self
    end
  end
  
  class Number
    def eval(context)
      self
    end
    
    def to_ruby
      value
    end
  end

  class String
    def eval(context)
      self
    end
    
    def to_ruby
      value
    end
  end

  class Symbol
    def eval(context)
      self
    end
    
    def to_ruby
      value
    end
  end
end