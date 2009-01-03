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
      Min::Object.new(self)
    end
    
    # creates an association from a message name to 
    # a method implementation, and the method. 
    def add_method(message, closure)
      @methods[message] = closure
    end
    
    # queries the associations to ﬁnd an implementation
    # corresponding to a message name
    def lookup(context, message)
      @methods[message] || parent && parent.min_send(context, :lookup, message.to_min)
    end
    
    # creates a new vtable that will delegate unhandled
    # messages to the receiver
    def delegated
      self.class.new(self)
    end
    
    def self.bootstrap(runtime)
      vtable = runtime[:VTable]
      vtable.add_method(:lookup, RubyMethod.new(:lookup, :pass_context => true))
      vtable.add_method(:add_method, RubyMethod.new(:add_method))
      vtable.add_method(:allocate, RubyMethod.new(:allocate))
      vtable.add_method(:delegated, RubyMethod.new(:delegated))
    end
  end
end