require File.dirname(__FILE__) + '/spec_helper'

describe Runtime do
  before do
    @runtime = Runtime.new
  end
  
  xit "should eval 1" do
    @runtime.eval("1").should == Number.new(1)
  end
end
