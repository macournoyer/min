require File.dirname(__FILE__) + '/../spec_helper'

describe "String" do
  it "should create new" do
    Min.eval(%{"ohaie"}).should == Min::String.new("ohaie")
  end
  
  it "should concat" do
    Min.eval(%{"oh" + "aie"}).should == Min::String.new("ohaie")
  end

  it "should convert to symbol" do
    Min.eval(%{"ohaie".to_sym}).should == Min::Symbol.new(:ohaie)
  end
end