require File.dirname(__FILE__) + '/../spec_helper'

describe "Number" do
  it "should create new" do
    Min.eval(%{1}).should == Number.new(1)
  end
  
  it "should add" do
    Min.eval(%{1 + 2}).should == Number.new(3)
  end
end