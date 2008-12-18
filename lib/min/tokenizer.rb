# See http://www.secnetix.de/~olli/Python/block_indentation.hawk "How does the compiler parse the indentation?"
module Min
  class Tokenizer
    class Matcher < Struct.new(:name, :pattern, :block); end
    
    def initialize
      @matchers = []
      
      # Rules declaration
    
      keyword :if
      keyword :else
      keyword :true
      keyword :false
      keyword :nil
      
      keyword :block, ":"
      keyword :cmp, "=="
      keyword :eq,  "="
    
      token :INDENT, /\A\n(\s+)/m do |_, level|
        indent = level.size
        if indent > @indent
          [:INDENT, @indent = level.size]
        elsif indent < @indent
          [:DEDENT, @indent = @indent - level.size]
        end
      end

      token :DEDENT, /\A\n/m do |value|
        unless @indent == 0
          indent  = @indent
          @indent = 0
          [:DEDENT, indent]
        end
      end
    
      token :NUMBER, /\A\d+/ do |value|
        [:NUMBER, value.to_i]
      end
    
      token :ID, /\A\w+/ do |value|
        [:ID, value]
      end

      token :SPACE, /\A\s+/
    end
    
    def tokenize(string)
      @tokens  = []
      @nparsed = 0
      @indent  = 0
      
      while @nparsed < string.size
        @matchers.each do |matcher|
          if matches = matcher.pattern.match(string[@nparsed..-1])
            if token = matcher.block.call(*matches)
              @tokens << token
            end
            @nparsed += matches[0].size
            break
          end
        end
      end
      
      @tokens
    end
    
    private
      def token(name, pattern, &block)
        @matchers << Matcher.new(name, pattern, block || proc { nil })
      end

      def keyword(name, pattern=name)
        token_name = name.to_s.upcase.to_sym
        token token_name, /\A#{pattern}/ do |value|
          [token_name, value]
        end
      end
      
  end
end

if __FILE__ == $PROGRAM_NAME
  tokens = Min::Tokenizer.new.tokenize(<<-EOS)
if foo:
  if bar:
    x = 42
else:
  print foo
EOS
  
  tokens.each do |token|
    puts if [:INDENT, :DEDENT].include?(token.first)
    print token.inspect, " "
  end
end