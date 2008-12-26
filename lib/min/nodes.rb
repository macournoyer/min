module Min
  module Nodes
    def self.NodeClass(*args)
      Struct.new(*args)
    end
    
    # Constructs
    class Block < NodeClass(:nodes); end
    class Tree < Block; end
    class Call < NodeClass(:receiver, :message, :arguments); end
    class Assign < NodeClass(:name, :value); end
    class ConstSet < NodeClass(:name, :value); end
    class ConstGet < NodeClass(:name); end
    
    # Literals
    class String < NodeClass(:value); end
    class Number < NodeClass(:value); end
  end
end