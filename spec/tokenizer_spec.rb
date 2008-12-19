require File.dirname(__FILE__) + "/spec_helper"

describe Min::Tokenizer do
  before do
    @tokenizer = Min::Tokenizer.new
  end
  
  it "should tokenize indents" do
    tokens = @tokenizer.tokenize(<<-EOS)
if foo:
  if bar:
    x = 42
else:
  print foo
EOS
    tokens.should == [
      [:IF, "if"], [:ID, "foo"], [:BLOCK, ":"],
      [:INDENT, 2], [:IF, "if"], [:ID, "bar"], [:BLOCK, ":"],
      [:INDENT, 4], [:ID, "x"], [:EQ, "="], [:NUMBER, 42],
      [:DEDENT, 2], [:DEDENT, 4], [:ELSE, "else"], [:BLOCK, ":"],
      [:INDENT, 2], [:ID, "print"], [:ID, "foo"],
      [:DEDENT, 2]
    ]
  end
end