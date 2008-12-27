module Min
  class Object
    attr_accessor :vtable
    
    def initialize(vtable)
      @vtable = vtable
    end
    
    def send(message, *args)
      if method = @vtable.lookup(message)
        method.call(*args)
      else
        raise "Method not found: #{message}"
      end
    end
  end
end