module Min
  def self.NodeClass(*attributes)
    Struct.new(*attributes)
  end
  
  class Block < NodeClass(:nodes); end
  class Call < NodeClass(:receiver, :message, :arguments); end
  class Assign < NodeClass(:name, :value); end
  
  class Constant < NodeClass(:name); end
  class AssignConstant < NodeClass(:name, :value); end
  
  class String < NodeClass(:value); end
  class Number < NodeClass(:value); end
end