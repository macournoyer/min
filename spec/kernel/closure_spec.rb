require File.dirname(__FILE__) + '/../spec_helper'

describe "Closure" do
  it "should create new" do
    Min.eval("{1}").should == Closure.new(Block.new([Number.new(1)]), [])
  end

  it "should be callable" do
    Min.eval("{1}.call").should == Number.new(1)
  end

  it "should accept arguments" do
    Min.eval("{ x | x }.call(1)").should == Number.new(1)
  end

  it "should accept arguments with default" do
    Min.eval("{ x=1 | x }.call").should == Number.new(1)
  end

  it "should accept arguments with splat" do
    Min.eval("{ *x | x }.call(1, 2)").should == [Number.new(1), Number.new(2)].to_min
  end

  it "should pass arguments with splat" do
    Min.eval("{ x, y | x }.call(*[1, 2])").should == Number.new(1)
  end

  it "should pass arguments with splat on var" do
    Min.eval("a = [1, 2]; { x, y | x }.call(*a)").should == Number.new(1)
  end

  it "should pass & accept arguments with splat" do
    Min.eval("{ *x | x }.call(*[1, 2])").should == [Number.new(1), Number.new(2)].to_min
  end

  it "should set self" do
    Min.eval("{ self }.call").should == Min.runtime.context.min_self
  end

  it "should send methods to self" do
    Min.eval('{ eval("1") }.call').should == Number.new(1)
  end

  it "should bind to object" do
    Min.eval("{ self }.bind(1).call").should == Number.new(1)
  end

  it "should see parent context slots" do
    Min.eval("x = 1; { x }.call").should == Number.new(1)
  end

  it "should keep slots in context" do
    Min.eval("x = 1; { x = 2 }.call; x").should == Number.new(1)
  end
end