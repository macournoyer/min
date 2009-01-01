require File.dirname(__FILE__) + '/../spec_helper'

describe "Symbol" do
  it "should create new" do
    Min.eval(%{:ohaie}).should == Min::Symbol.new(:ohaie)
  end
  
  it "should convert to string" do
    Min.eval(%{:ohaie.to_s}).should == Min::String.new("ohaie")
  end
end