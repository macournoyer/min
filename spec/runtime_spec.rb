require File.dirname(__FILE__) + '/spec_helper'

describe Runtime do
  before do
    @runtime = Runtime.new
  end
  
  it "should eval 1" do
    @runtime.eval("1").should == Number.new(1)
  end

  it "should eval Object" do
    @runtime.eval("Object").should be_instance_of(Min::Object)
  end

  it "should eval VTable" do
    @runtime.eval("VTable").should be_instance_of(VTable)
  end
end
