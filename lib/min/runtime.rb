module Min
  class ConstantNotFound < StandardError; end
  class BootstrapError < StandardError; end
  
  class Runtime
    attr_reader :context, :load_path, :parser
    
    def initialize
      @parser    = Parser.new
      @context   = Context.new(nil)
      @load_path = [File.dirname(__FILE__) + "/../../kernel"]
    end
    
    def [](name)
      @context.constants[name] ||
      raise(ConstantNotFound, "#{name} class can't be found in context.")
    end
    
    def []=(name, value)
      @context.constants[name] = value
    end
    
    def eval(string, context=@context)
      @parser.parse(string).eval(context)
    end
    
    def load(file)
      path = find_file_in_load_path(file) || raise("File not found: #{file}")
      eval File.read(path)
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
      Min::Number.bootstrap(self)
      Min::String.bootstrap(self)
      Min::Symbol.bootstrap(self)
      
      # Root context init
      @context.min_self = object.vtable.allocate
      @context.locals[:self] = @context.min_self
      
      # Load kernel
      load "object"
      load "class"
      load "string"
    end
    
    private
      def find_file_in_load_path(file)
        if path = @load_path.detect { |path| File.file?("#{path}/#{file}.min") }
          "#{path}/#{file}.min"
        end
      end
  end
  
  class << self
    def bootstrap(runtime_class = Runtime)
      @runtime = runtime_class.new
      @runtime.bootstrap
    end
    
    def runtime
      raise BootstrapError, "Runtime not bootstrapped" unless @runtime
      @runtime
    end
    
    # Delegates
    [:[], :eval, :load].each { |method| define_method(method) { |*a| runtime.send(method, *a) } }
  end
end
