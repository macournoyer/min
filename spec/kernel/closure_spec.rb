require File.dirname(__FILE__) + '/../spec_helper'

describe "Closure" do
  xit "should create new" do
    Min.eval("{1}").should == Closure.new(Block.new([Number.new(1)]), [])
  end

  xit "should be callable" do
    Min.eval("{1}.call").should == Number.new(1)
  end

  xit "should be accept arguments" do
    Min.eval("{ x | x }.call(1)").should == Number.new(1)
  end
end