require File.dirname(__FILE__) + '/spec_helper'

describe Runtime do
  before do
    @runtime = TestRuntime.new
  end
  
  it "should load file in load path" do
    @runtime.load("empty")
  end
  
  it "should raise when cant file file in load path" do
    proc { @runtime.load("poop") }.should raise_error
  end
  
  it "should eval 1" do
    @runtime.eval("1").should == Number.new(@runtime.context, 1)
  end

  it "should eval self" do
    @runtime.eval("self").should == @runtime.context.min_self
  end

  it "should eval Object" do
    @runtime.eval("Object").should be_a(Min::Object)
  end

  it "should eval Object.new" do
    @runtime.eval("Object.new").should be_a(Min::Object)
  end

  it "should eval VTable" do
    @runtime.eval("VTable").should be_a(VTable)
  end

  it "should eval eval" do
    @runtime.eval('eval("1")').should == Number.new(@runtime.context, 1)
  end

  it "should eval load" do
    @runtime.eval('load("empty")')
  end

  it "should add method" do
    @runtime.eval("Object.vtable.add_method :return_itself { it }")
    @runtime.context.constants[:Object].vtable.lookup(:return_itself).should be_a(Closure)
    @runtime.eval('return_itself "test"').should == Min::String.new("test")
  end
end
