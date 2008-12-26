require File.dirname(__FILE__) + "/spec_helper"

describe Tokenizer do
  before do
    @tokenizer = Tokenizer.new
  end
  
  it "should tokenize indents" do
    tokens = @tokenizer.tokenize(<<-EOS)
if foo:
  if bar:
    x = 42
    y = x
else:
  print foo
EOS
    tokens.should == [
      [:ID, "if"], [:ID, "foo"], [":", ":"],
      [:INDENT, 2], [:ID, "if"], [:ID, "bar"], [":", ":"],
      [:INDENT, 4], [:ID, "x"], ["=", "="], [:NUMBER, 42], [:SEP, "\n"],
                    [:ID, "y"], ["=", "="], [:ID, "x"],
      [:DEDENT, 2], [:DEDENT, 4], [:SEP, "\n"], [:ID, "else"], [":", ":"],
      [:INDENT, 2], [:ID, "print"], [:ID, "foo"],
      [:DEDENT, 2]
    ]
  end
  
  it "should tokenize id" do
    tokens = @tokenizer.tokenize("something_cool_with_123")
    
    tokens.should == [[:ID, "something_cool_with_123"]]
  end

  it "should tokenize const" do
    tokens = @tokenizer.tokenize("Class")
    
    tokens.should == [[:CONST, "Class"]]
  end

  it "should remove leading sep" do
    tokens = @tokenizer.tokenize("\n\n\nx")
    
    tokens.should == [[:ID, "x"]]
  end

  it "should remove trailing sep" do
    tokens = @tokenizer.tokenize("x\n\n\n")
    
    tokens.should == [[:ID, "x"]]
  end
end