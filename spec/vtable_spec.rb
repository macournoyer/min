require File.dirname(__FILE__) + '/spec_helper'

describe VTable do
  before do
    @vtable = VTable.new
    @context = mock("context")
    
    @vtable.add_method(:who, proc { |*a| :parent })
    @vtable.add_method(:parent, proc { |*a| true })
  end
  
  it "should lookup method" do
    @vtable.lookup(@context, :who).should be_a(Proc)
  end
  
  it "should return callabale methods" do
    @vtable.lookup(@context, :who).call.should == :parent
  end
  
  describe "allocated" do
    before do
      @allocated = @vtable.allocate.vtable
      
      @allocated.add_method(:who, proc { |*a| :allocated })
    end
    
    it "should lookup parent method" do
      @allocated.lookup(@context, :parent).should be_a(Proc)
    end

    it "should override parent method" do
      @allocated.lookup(@context, :who).call.should == :allocated
    end
  end
end