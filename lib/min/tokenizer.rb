module Min
  # Indentation sensitive-Python-like tokenizer.
  # Based on http://www.secnetix.de/~olli/Python/block_indentation.hawk,
  #          "How does the compiler parse the indentation?"
  class Tokenizer
    class Matcher < Struct.new(:name, :pattern, :block); end
    
    class Token < Struct.new(:name, :value)
      def inspect
        "<#{name}:#{value.inspect}>"
      end
    end
    
    def initialize
      @matchers = []
      
      # Rules declaration
      
      keyword :true
      keyword :false
      keyword :nil
      
      keyword :eq,  "=="
      keyword :lt,  "<"
      keyword :gt,  ">"
      keyword :let,  "<="
      keyword :get,  ">="
      
      token :INDENT, /\A\n([ \t]+)/m do |v, level|
        indent = level.size
        if indent > @indent
          @indents.push(indent)
          @indent = indent
          Token.new(:INDENT, indent)
        elsif indent < @indent
          @indent = indent
          Token.new(:DEDENT, @indents.pop)
        else
          Token.new(:SEP, "\n")
        end
      end
      
      token :DEDENT, /\A\n+/m do |value|
        tokens = @indents.map do |indent|
          Token.new(:DEDENT, indent)
        end
        @indent = 0
        @indents.clear
        tokens << Token.new(:SEP, "\n")
        tokens
      end
      
      token :SEP, /\A;+/ do |value|
        Token.new(:SEP, value)
      end
      
      token :NUMBER, /\A\d+(?:\.\d+)?/ do |value|
        Token.new(:NUMBER, eval(value))
      end
      
      token :STRING, /\A\"(.*?)\"/ do |_, value|
        Token.new(:STRING, value)
      end
      
      token :ID, /\A\w+/ do |value|
        Token.new(:ID, value)
      end
      
      # Ignored tokens
      token :SPACE, /\A\s+/
      
      token :CHAR, /\A./ do |value|
        Token.new(value, value)
      end
    end
    
    def tokenize(string)
      @tokens  = []
      @nparsed = 0
      @indent  = 0
      @indents = []
      
      while @nparsed < string.size
        @matchers.each do |matcher|
          if matches = matcher.pattern.match(string[@nparsed..-1])
            if token = matcher.block.call(*matches)
              @tokens += [token].flatten
            end
            @nparsed += matches[0].size
            break
          end
        end
      end
      
      # cleanup
      @tokens.shift while @tokens.first.name == :SEP
      @tokens.pop while @tokens.last.name == :SEP
      
      @tokens.map { |t| t.to_a }
    end
    
    private
      def token(name, pattern, &block)
        @matchers << Matcher.new(name, pattern, block || proc { nil })
      end

      def keyword(name, pattern=name)
        token_name = name.to_s.upcase.to_sym
        token token_name, /\A#{pattern}/ do |value|
          Token.new(token_name, value)
        end
      end
      
  end
end
