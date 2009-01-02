require File.dirname(__FILE__) + '/../spec_helper'

describe "Object" do
  it "should create new" do
    Min.eval(%{Object.new}).should be_a(Min::Object)
  end

  it "should have class" do
    Min.eval(%{Object.new.class}).should == Min[:Object]
  end

  it "should set constant" do
    Min.eval(%{Object.constant_set(:Test, 1)}).should == Number.new(1)
  end

  it "should get constant" do
    Min.eval(%{Object.constant_get(:Object)}).should == Min[:Object]
  end
end