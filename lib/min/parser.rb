require "min/generated_parser"

module Min
  class Parser < GeneratedParser
    def initialize
      @tokens = []
      @logger = nil
      @terminator = false
      @prev_token = nil
      @comments = []
    end

    def parse(string)
      @tokens = TOKENIZER.tokenize(string)
      @position = 0
      do_parse
    end
  end
end