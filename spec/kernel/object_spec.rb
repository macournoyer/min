require File.dirname(__FILE__) + '/../spec_helper'

describe "Object" do
  it "should create new" do
    Min.eval(%{Object.new}).should be_a(Min::Object)
  end

  it "should send message" do
    Min.eval(%{Object.send(:new)}).should be_a(Min::Object)
  end
  
  it "should have method" do
    Min.eval(%{Object.method(:new)}).should be_a(Min::Closure)
  end
  
  it "should have class" do
    Min.eval(%{Object.new.class}).should == Min[:Object]
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