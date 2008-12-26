module Min
  # Indentation sensitive-Python-like tokenizer.
  # Based on http://www.secnetix.de/~olli/Python/block_indentation.hawk,
  #          "How does the compiler parse the indentation?"
  class Tokenizer
    class Matcher < Struct.new(:pattern, :block); end
    
    class Token < Struct.new(:name, :value)
      def inspect
        "<#{name}:#{value.inspect}>"
      end
    end
    
    def initialize
      @matchers = []
      
      # Rules declaration
      
      operator :eq,  "=="
      operator :add, "+"
      operator :rem, "-"
      operator :lt,  "<"
      operator :gt,  ">"
      operator :let, "<="
      operator :get, ">="
      
      token(/\A\n([ \t]+)/m) do |v, level|
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
      
      token(/\A\n+/m) do |value|
        tokens = @indents.map do |indent|
          Token.new(:DEDENT, indent)
        end
        @indent = 0
        @indents.clear
        tokens << Token.new(:SEP, "\n")
        tokens
      end
      
      token(/\A;+/) do |value|
        Token.new(:SEP, value)
      end
      
      token(/\A\d+(?:\.\d+)?/) do |value|
        Token.new(:NUMBER, eval(value))
      end
      
      token(/\A\"(.*?)\"/) do |_, value|
        Token.new(:STRING, value)
      end
      
      token(/\A\w+/) do |value|
        Token.new(:ID, value)
      end
      
      token(/\A\s+/) # Ignore spaces
      
      token(/\A./) do |value|
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
      def token(pattern, &block)
        @matchers << Matcher.new(pattern, block || proc { nil })
      end

      def operator(name, pattern=name)
        token(/\A#{Regexp.escape(pattern.to_s)}/) do |value|
          Token.new(name.to_s.upcase.to_sym, value)
        end
      end
      
  end
end
