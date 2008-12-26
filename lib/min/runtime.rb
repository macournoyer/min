$: << File.dirname(__FILE__) + "/.."
require "min"

module Min
  class Runtime
    attr_reader :context
    
    def initialize
      @parser  = Parser.new
      root     = Min::Object.new
      @context = Context.new(self, root, root.min_class)
    end
    
    def eval(string)
      @parser.parse(string).eval(@context)
    end
  end
end

if __FILE__ == $PROGRAM_NAME
  Min::Runtime.new.eval(%{def "ohaie":\n  puts "ohaie"\nohaie;ohaie\n})
end