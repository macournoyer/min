module Min
  class Runtime
    attr_reader :context
    
    def initialize
      @parser  = Parser.new
      @context = Context.new(Min::Object.new)
    end
    
    def eval(string)
      @parser.parse(string).eval(@context)
    end
    
    def eval_file(filename)
      eval File.read(filename)
    end
  end
end
