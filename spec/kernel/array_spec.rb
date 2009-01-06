require File.dirname(__FILE__) + '/../spec_helper'

describe "Array" do
  it "should have size" do
    Min.eval("[1, 2].size").should == 2.to_min
  end

  xit "should call each" do
    Min.eval("c = []; [1, 2].each { c << it }; c").should == [1.to_min, 2.to_min].to_min
  end
end