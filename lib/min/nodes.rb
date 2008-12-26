module Min
  module Nodes
    def self.NodeClass(*args)
      Struct.new(*args)
    end
    
    # Constructs
    class Block < NodeClass(:nodes); end
    class Tree < Block; end
    class Call < NodeClass(:receiver, :message, :arguments); end
    class Assign < NodeClass(:var, :value); end
    
    # Literals
    class String < NodeClass(:value)
      def to_ruby
        value
      end
    end
    
    class Number < NodeClass(:value)
      def to_ruby
        value
      end
    end
    
    class False < NodeClass(:value)
      def to_ruby
        false
      end
    end
    
    class True < NodeClass(:value)
      def to_ruby
        true
      end
    end
    
    class Nil < NodeClass(:value)
      def to_ruby
        nil
      end
    end
  end
end