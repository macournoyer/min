module Min
  class BootstrapError < StandardError; end
  
  class Runtime
    attr_reader :context, :load_path
    
    def initialize
      @parser    = Parser.new
      @context   = Context.new(nil)
      @load_path = [File.dirname(__FILE__) + "/../../kernel"]
      
      bootstrap
    end
    
    def constants
      @context.constants
    end
    
    def eval(string, context=@context)
      @parser.parse(string).eval(context)
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
      
      def bootstrap
        vtable = @context.constants[:VTable] = VTable.new
        vtable.vtable = vtable
        
        object_vt = VTable.new
        object_vt.vtable = vtable
        vtable.parent = object_vt
        object = @context.constants[:Object] = object_vt.allocate
        
        # Base classes bootstrap
        VTable.bootstrap(self)
        Min::Object.bootstrap(self)
        
        # Root context init
        @context.min_self = object.vtable.allocate
        @context.locals[:self] = @context.min_self
        
        # Load kernel
        load "object"
        load "class"
        load "string"
      end
  end
end
