module Min
  # Based on "Open, extensible object models" by Ian Piumarta <ian@vpri.org>
  class VTable < Min::Object
    attr_accessor :parent
    
    def initialize(parent=nil)
      @methods = {}
      @parent  = parent
      @vtable  = parent && parent.vtable
    end
    
    # creates a new object within the vtable's family (by copying
    # the receiver into the new object's vtable slot).
    def allocate
      Min::Object.new(delegated)
    end
    
    # creates an association from a message name to 
    # a method implementation, and the method. 
    def add_method(message, block)
      @methods[message] = block
    end
    
    # queries the associations to ﬁnd an implementation
    # corresponding to a message name
    def lookup(message)
      @methods[message] || parent && parent.lookup(message)
    end
    
    # creates a new vtable that will delegate unhandled
    # messages to the receiver
    def delegated
      self.class.new(self)
    end
  end
end