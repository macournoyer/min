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

  it "should answer true to nil.nil?" do
    Min.eval(%{nil.nil?}).should == Min[:true]
  end

  it "should answer false to anything.nil?" do
    Min.eval(%{1.nil?}).should == Min[:false]
  end

  it "should answer true to 1 && true" do
    Min.eval(%{1 && true}).should == Min[:true]
  end

  it "should answer 1 to true && 1" do
    Min.eval(%{true && 1}).should == Number.new(1)
  end

  it "should answer false to false && true" do
    Min.eval(%{false && true}).should == Min[:false]
  end

  it "should answer 1 to 1 || true" do
    Min.eval(%{1 || true}).should == Number.new(1)
  end

  it "should answer 1 to true && 1" do
    Min.eval(%{true && 1}).should == Number.new(1)
  end

  it "should answer false to false && true" do
    Min.eval(%{false && true}).should == Min[:false]
  end

  it "should answer false to !true" do
    Min.eval(%{!true}).should == Min[:false]
  end

  it "should answer false to !object" do
    Min.eval(%{!1}).should == Min[:false]
  end

  it "should answer true to !false" do
    Min.eval(%{!false}).should == Min[:true]
  end

  it "should answer true to !nil" do
    Min.eval(%{!nil}).should == Min[:true]
  end
  
  it "should run if condition" do
    Min.eval(%(if true: 1)).should == Number.new(1)
  end

  it "should not run if not condition" do
    Min.eval(%(if false: 1)).should == Min[:false]
  end
end