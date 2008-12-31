module Min
  class Parser < GeneratedParser
    def initialize
      @tokenizer = Tokenizer.new
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
