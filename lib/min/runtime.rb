module Min
  class Runtime
    attr_reader :context
    
    def initialize
      @parser  = Parser.new
      @context = Context.new(nil)
      
      bootstrap
    end
    
    def eval(string)
      @parser.parse(string).eval(@context)
    end
    
    def load(filename)
      eval File.read(filename)
    end
    
    private
      def call_method(method)
        proc { |obj, *args| puts "calling: #{method}"; obj.send(method, *args) }
      end
      
      def bootstrap
        vtable_vt = @context.constants["VTable"] = VTable.new
        vtable_vt.vtable = vtable_vt
        
        object_vt = VTable.new
        object_vt.vtable = vtable_vt
        vtable_vt.parent = object_vt
        object = @context.constants["Object"] = object_vt.allocate
        
        symbol_vt = object_vt.delegated
        @context.constants["Symbol"] = symbol_vt.allocate
        
        @context.min_self = object_vt.allocate
        
        vtable_vt.add_method(:lookup, call_method(:lookup))
        vtable_vt.add_method(:add_method, call_method(:add_method))
        vtable_vt.add_method(:allocate, call_method(:allocate))
        
        symbol_vt.add_method(:intern, proc { |str| str.to_sym })
        
        vtable_vt.add_method(:delegated, call_method(:delegated))
      end
  end
end
