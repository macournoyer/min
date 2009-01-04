module Min
  # Based on VTable documented in "Open, extensible object models" by Ian Piumarta <ian@vpri.org>
  class Class < Min::Object
    attr_accessor :min_superclass
    
    def initialize(superclass=nil)
      @methods        = {}
      @min_superclass = superclass
      @min_class      = superclass && superclass.min_class
    end
    
    # creates a new object within the class's family (by copying
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
      @methods[message] || @min_superclass && @min_superclass.min_send(context, :lookup, message.to_min)
    end
    
    # creates a new class that will delegate unhandled
    # messages to the receiver
    def subclass
      self.class.new(self)
    end
    
    def name
      Min.runtime.context.slots.detect { |k,v| v == self }.first.to_s
    end
    
    def inspect
      "Min::#{name}"
    end
    
    def self.bootstrap(runtime)
      klass = runtime[:Class]
      klass.add_method(:name, RubyMethod.new(:name))
      klass.add_method(:superclass, RubyMethod.new(:min_superclass))
      klass.add_method(:lookup, RubyMethod.new(:lookup, :pass_context => true))
      klass.add_method(:add_method, RubyMethod.new(:add_method))
      klass.add_method(:allocate, RubyMethod.new(:allocate))
      klass.add_method(:subclass, RubyMethod.new(:subclass))
    end
  end
end