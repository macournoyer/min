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
      
      def bootstrap
        vtable_vt = @context.constants[:VTable] = VTable.new
        vtable_vt.vtable = vtable_vt
        
        object_vt = VTable.new
        object_vt.vtable = vtable_vt
        vtable_vt.parent = object_vt
        object = @context.constants[:Object] = object_vt.allocate
        
        @context.min_self = object_vt.allocate
        
        vtable_vt.add_method(:lookup, RubyMethod.new(:lookup))
        vtable_vt.add_method(:add_method, proc { |context, vtable, message, block| vtable.add_method(message.value, block) })
        vtable_vt.add_method(:allocate, RubyMethod.new(:allocate))
        
        vtable_vt.add_method(:delegated, RubyMethod.new(:delegated))
        
        # Crap
        object_vt.add_method(:vtable, RubyMethod.new(:vtable))
        object_vt.add_method(:puts, proc { |context, object, str| puts str.eval(context).value })
        load "class"
      end
  end
end
