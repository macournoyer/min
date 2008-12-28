module Min
  class Object
    attr_accessor :vtable
    
    def initialize(vtable)
      @vtable = vtable
    end
    
    def min_send(message, *args)
      if method = @vtable.lookup(message)
        method.call(self, *args)
      else
        raise "Method not found #{message.inspect} on #{inspect}"
      end
    end
  end
end