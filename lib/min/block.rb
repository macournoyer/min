module Min
  class Block < Min::Object
    attributes :objects
    
    def initialize(objects=[])
      super
    end
    
    def eval(context)
      objects.map { |object| object.eval(context) }.last
    end
  end
end