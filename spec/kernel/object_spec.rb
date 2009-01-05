require File.dirname(__FILE__) + '/../spec_helper'

describe "Object" do
  it "should create new" do
    Min.eval(%{Object.clone}).should be_a(Min::Object)
  end

  it "should send message" do
    Min.eval(%{Object.send(:clone)}).should be_a(Min::Object)
  end
  
  it "should respond_to clone" do
    Min.eval(%{Object.respond_to?(:clone)}).should == Min[:true]
  end
  
  it "should send message w/ args" do
    Min.eval(%{send(:eval, "1")}).should == Number.new(1)
  end
  
  it "should have method" do
    Min.eval(%{Object.method(:clone)}).class.should == Min::Closure
  end
  
  it "should raise exception" do
    proc { Min.eval(%{raise "test"}) }.should raise_error(MinExceptionRaised)
  end

  it "should return exception when wrapped in try" do
    Min.eval(%{try { raise "test" }}).should == Min::String.new("test")
  end

  it "should return nil if try has no exception" do
    Min.eval(%{try { 1 }}).should == Min[:nil]
  end
end