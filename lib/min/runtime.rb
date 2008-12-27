module Min
  class Runtime
    attr_reader :context
    
    def initialize
      @parser  = Parser.new
      @context = Context.new
    end
    
    def eval(string)
      @parser.parse(string).eval(@context)
    end
    
    def load(filename)
      eval File.read(filename)
    end
    
    def bootstrap
      @context.constants["VTable"] = vtable_vt = VTable.new

      @context.constants["Object"] = object_vt = VTable.new
      object_vt.vtable = vtable_vt
      vtable_vt.parent = object_vt
      
      @context.constants["Symbol"] = symbol_vt = object_vt.delegated
      
      vtable_vt.add_method(:lookup, VTable.method(:lookup))
      
      vtable_vt.add_method(:add_method, VTable.method(:add_method))

      vtable_vt.send(:add_method, :allocate, VTable.method(:allocate))

      symbol_vt.send(:add_method, :intern, proc { |str| str.to_sym })

      vtable_vt.send(:add_method, :delegated, VTable.method(:delegated));
    end
  end
end
