require File.dirname(__FILE__) + '/spec_helper'

describe Min::Context do
  before do
    @root = mock("root")
    @context = Context.new(@root)
  end
  
  it "should create a child context" do
    @context.create(mock("child root")).parent.should == @context
  end
  
  it "should inherit locals from parent" do
    context = @context.create(mock("child root"))
    @context.locals["im_in_ur_locals"] = :indeed
    context.locals["im_in_ur_locals"].should == :indeed
  end

  it "should inherit constants from parent" do
    context = @context.create(mock("child root"))
    @context.constants["Class"] = :awesome
    context.constants["Class"].should == :awesome
  end
end