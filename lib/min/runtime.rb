require "pathname"
require "forwardable"

module Min
  class Runtime
    extend Forwardable
    
    attr_reader :context, :load_path
    
    def_delegators :@context, :constants
    
    def initialize
      @parser    = Parser.new
      @context   = Context.new(nil)
      @load_path = [File.dirname(__FILE__) + "/../../kernel"]
      
      bootstrap
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
        vtable_vt = @context.constants[:VTable] = VTable.new
        vtable_vt.vtable = vtable_vt
        
        object_vt = VTable.new
        object_vt.vtable = vtable_vt
        vtable_vt.parent = object_vt
        
        # VTable init
        vtable_vt.add_method(:lookup, RubyMethod.new(:lookup))
        vtable_vt.add_method(:add_method, RubyMethod.new(:add_method))
        vtable_vt.add_method(:allocate, RubyMethod.new(:allocate))
        vtable_vt.add_method(:delegated, RubyMethod.new(:delegated))
        
        # Object init
        object = @context.constants[:Object] = object_vt.allocate
        object.vtable.add_method(:vtable, RubyMethod.new(:vtable))
        object.vtable.add_method(:puts, proc { |context, obj, str| puts str.eval(context).value })
        object.vtable.add_method(:eval, proc { |context, obj, code| eval(code.eval(context).value, context) })
        object.vtable.add_method(:load, proc { |context, obj, file| load(file.eval(context).value) })
        
        # Runtime init
        @context.min_self = object.vtable.allocate
        @context.locals[:self] = @context.min_self
        
        load "object"
        load "class"
        load "string"
      end
  end
end
