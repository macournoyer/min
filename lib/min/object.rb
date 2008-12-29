module Min
  class Object
    attr_accessor :vtable
    
    def initialize(vtable)
      @vtable = vtable
    end
    
    def min_send(context, message, *args)
      if method = @vtable.lookup(message)
        method.call(context, self, *args)
      else
        raise "Method not found #{message} on #{inspect}"
      end
    end
    
    def value
      inspect # for debug
    end
  end
end