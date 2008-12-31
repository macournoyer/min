module Min
  class Parser < GeneratedParser
    attr_reader :runtime
    
    def initialize(runtime)
      @runtime   = runtime
      @tokenizer = Tokenizer.new
    end
    
    def context
      @runtime.context
    end

    def parse(string)
      @tokens = @tokenizer.tokenize(string)
      do_parse
    end
    
    def next_token
      @tokens.shift || [false, false]
    end
  end
end
