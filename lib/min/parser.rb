module Min
  class Parser < GeneratedParser
    def initialize
      @tokens = []
    end

    def parse(string)
      @tokens = Tokenizer.new.tokenize(string)
      do_parse
    end
    
    def next_token
      @tokens.shift || [false, false]
    end
  end
end
