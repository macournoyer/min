require File.dirname(__FILE__) + '/spec_helper'

describe Runtime do
  before do
    @runtime = Runtime.new
  end
  
  it "should load file in load path" do
    @runtime.load("class")
  end
  
  it "should raise when cant file file in load path" do
    proc { @runtime.load("poop") }.should raise_error
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

  it "should add method" do
    @runtime.eval(<<-EOS).should == Min::String.new("test")
Object.vtable.add_method(:test):
  "test"
test
    EOS
    @runtime.context.constants[:Object].vtable.methods.should include(:test)
  end
end
