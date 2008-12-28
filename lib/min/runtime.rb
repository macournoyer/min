require "pathname"

module Min
  class Runtime
    attr_reader :context
    
    def initialize
      @parser    = Parser.new
      @context   = Context.new(nil)
      @load_path = [File.dirname(__FILE__) + "/../../kernel"]
      
      bootstrap
    end
    
    def eval(string)
      @parser.parse(string).eval(@context)
    end
    
    def load(file)
      path = find_file_in_load_path(file) || raise("File not found: #{file}")
      eval File.read(path)
    end
    
    private
      def find_file_in_load_path(file)
        if path = @load_path.detect { |path| File.file?("#{path}/#{file}.min") }
          "#{path}/#{file}.min"
        end
      end
      
      def call_method(method)
        proc { |obj, *args| obj.send(method, *args) }
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
        
        # Crap
        object_vt.add_method(:vtable, proc { |o| o.vtable })
        object_vt.add_method(:debug, proc { |o| puts "holy crap!" })
        # load "class"
      end
  end
end
