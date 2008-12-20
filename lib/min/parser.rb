require "generated_parser"
require "tokenizer"

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
      @tokens.pop
    end
  end
end

if __FILE__ == $PROGRAM_NAME
  puts Min::Parser.new.parse("4").inspect
end