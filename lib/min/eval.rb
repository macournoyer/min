module Min
  module Nodes
    class Block
      def eval(context)
        nodes.map { |node| node.eval(context) }.last
      end
    end
    
    class String
      def eval(context)
        value
      end
    end

    class Number
      def eval(context)
        value
      end
    end
    
    class Call
      def eval(context)
        if receiver.nil? && value = context.locals[message]
          # local var
          return value
        end
        (receiver || context.min_self).min_send(context, message, *arguments)
      end
    end
    
    class Assign
      def eval(context)
        context.locals[name] = value.eval(context)
      end
    end
  end
end