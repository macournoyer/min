require File.dirname(__FILE__) + '/../spec_helper'

describe "Boolean" do
  it "should have nil" do
    Min.eval(%{nil}).should == Min[:nil]
  end

  it "should have false" do
    Min.eval(%{false}).should == Min[:false]
  end

  it "should have true" do
    Min.eval(%{true}).should == Min[:true]
  end
end