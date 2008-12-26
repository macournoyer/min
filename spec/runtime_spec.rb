require File.dirname(__FILE__) + '/spec_helper'

describe Runtime do
  before do
    @runtime = Runtime.new
  end
  
  it "should eval 1" do
    @runtime.eval("1").should == Number.new(1)
  end
  
  it "should eval def" do
    code = <<-EOS
def "two_leggit_to_quit":
  2
two_leggit_to_quit
EOS
    
    @runtime.eval(code).should == Number.new(2)
  end
end
