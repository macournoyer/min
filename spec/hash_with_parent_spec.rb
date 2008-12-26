require File.dirname(__FILE__) + '/spec_helper'

describe HashWithParent do
  before do
    @parent = HashWithParent.new(nil)
    @hash   = HashWithParent.new(@parent)
    
    @parent[:in_parent] = true
    @hash[:in_child] = true
  end
  
  it "should get in current hash" do
    @hash[:in_child].should be_true
  end

  it "should get in parent hash" do
    @hash[:in_parent].should be_true
  end

  it "should get nonexistent" do
    @hash[:muffin].should be_nil
  end
end