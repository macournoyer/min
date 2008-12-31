require File.dirname(__FILE__) + '/../spec_helper'

describe "Number" do
  before do
    @runtime = TestRuntime.new
  end
  
  it "should create new" do
    @runtime.eval(%{1}).should == Number.new(@runtime.context, 1)
  end
  
  it "should add" do
    @runtime.eval(%{1 + 2}).should == Number.new(@runtime.context, 3)
  end
end