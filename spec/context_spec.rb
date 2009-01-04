require File.dirname(__FILE__) + '/spec_helper'

describe Min::Context do
  before do
    @root = mock("root")
    @context = Context.new(@root)
  end
  
  it "should create a child context" do
    @context.create(mock("child root")).parent.should == @context
  end
  
  it "should inherit slots from parent" do
    context = @context.create(mock("child root"))
    @context["im_in_ur_locals"] = :indeed
    context["im_in_ur_locals"].should == :indeed
  end
end