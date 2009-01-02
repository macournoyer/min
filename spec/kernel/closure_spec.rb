require File.dirname(__FILE__) + '/../spec_helper'

describe "Closure" do
  it "should create new" do
    Min.eval("{1}").should == Closure.new(Block.new([Number.new(1)]), [])
  end

  it "should be callable" do
    Min.eval("{1}.call").should == Number.new(1)
  end

  it "should be accept arguments" do
    Min.eval("{ x | x }.call(1)").should == Number.new(1)
  end

  it "should set self to closure" do
    Min.eval("{ self }.call").should be_a(Closure)
  end

  it "should bind to object" do
    Min.eval("{ self }.bind(1).call").should == Number.new(1)
  end
end