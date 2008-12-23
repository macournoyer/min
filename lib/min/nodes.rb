module Min
  module Nodes
    def self.NodeClass(*args)
      Struct.new(*args)
    end

    class Block < NodeClass(:nodes); end
    class Call < NodeClass(:message, :arguments, :block); end
    class Assign < NodeClass(:var, :value); end
    class Var < NodeClass(:name); end
    
    class String < NodeClass(:value); end
    class Number < NodeClass(:value); end
    class False < NodeClass(:value); end
    class True < NodeClass(:value); end
    class Nil < NodeClass(:value); end
  end
end