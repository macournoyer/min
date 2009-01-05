module Min
  # Based on VTable documented in "Open, extensible object models" by Ian Piumarta <ian@vpri.org>
  class VTable < Min::Object
    attr_accessor :parent
    
    def initialize(parent=nil)
      @methods = {}
      @parent  = parent
      @vtable  = parent && parent.vtable
    end
    
    # creates a new object within the vtable's family (by copying
    # the receiver into the new object's class slot).
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
      @methods[message] || @parent && @parent.min_send(context, :lookup, message.to_min)
    end
    
    # creates a new class that will delegate unhandled
    # messages to the receiver
    def delegated
      self.class.new(self)
    end
    
    def min_methods
      @methods.keys
    end
    
    def self.bootstrap(runtime)
      klass = runtime[:VTable]
      klass.add_method(:parent, RubyMethod.new(:parent))
      klass.add_method(:lookup, RubyMethod.new(:lookup, :pass_context => true))
      klass.add_method(:add_method, RubyMethod.new(:add_method))
      klass.add_method(:allocate, RubyMethod.new(:allocate))
      klass.add_method(:delegated, RubyMethod.new(:delegated))
      klass.add_method(:methods, RubyMethod.new(:min_methods))
    end
  end
end