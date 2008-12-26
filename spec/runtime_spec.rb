require File.dirname(__FILE__) + '/spec_helper'

describe Runtime do
  before do
    @runtime = Runtime.new
  end
  
  it "should eval 1" do
    @runtime.eval("1").should == 1
  end
  
  it "should eval def" do
    code = <<-EOS
def "two_leggit_to_quit":
  2
two_leggit_to_quit
EOS
    
    @runtime.eval(code).should == 2
  end
end
