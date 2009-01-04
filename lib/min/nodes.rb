# Nodes that are strictly used to build an AST when parsing and do not represent
# an object in Min's world.
module Min
  class Block < Struct.new(:nodes)
    def eval(context)
      nodes.map { |node| node.eval(context) }.last
    end
  end
  
  class Call < Struct.new(:receiver, :message, :arguments)
    def eval(context)
      if arguments.is_a?(::Array)
        arg_array = arguments
      else
        arg_array = arguments.eval(context).value
      end
      
      if receiver.nil? && value = context[message]
        return value
      end
      
      (receiver || context.min_self).eval(context).min_send(context, message, *arg_array)
    end
  end
  
  class Param < Struct.new(:name, :default, :splat)
  end
  
  class Assign < Struct.new(:name, :value)
    def eval(context)
      context[name] = value.eval(context)
    end
  end
end