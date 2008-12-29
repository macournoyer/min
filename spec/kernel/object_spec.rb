require File.dirname(__FILE__) + '/../spec_helper'

describe "Object" do
  before do
    @runtime = TestRuntime.new
  end
  
  it "should create new" do
    @runtime.eval("Object.new").should be_a(Min::Object)
  end

  it "should have class" do
    @runtime.eval("Object.new.class").should == @runtime.constants[:Object]
  end
end