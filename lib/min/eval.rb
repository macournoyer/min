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
    
    class Call
      def eval(context)
        (receiver || context.min_self).min_send(context, message, *arguments)
      end
    end
  end
end