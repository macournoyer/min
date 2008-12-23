$: << File.dirname(__FILE__) + "/.."
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
      @tokens.shift || [false, false]
    end
  end
end

if __FILE__ == $PROGRAM_NAME
  puts Min::Parser.new.parse(<<-EOS).inspect
if x == 1:
  print "ohaie"
EOS
end