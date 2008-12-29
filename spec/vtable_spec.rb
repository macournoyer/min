require File.dirname(__FILE__) + '/spec_helper'

describe VTable do
  before do
    @vtable = VTable.new
    
    @vtable.add_method(:who, proc { |*a| :parent })
    @vtable.add_method(:parent, proc { |*a| true })
  end
  
  it "should lookup method" do
    @vtable.lookup(:who).should be_a(Proc)
  end
  
  it "should return callabale methods" do
    @vtable.lookup(:who).call.should == :parent
  end
  
  describe "allocated" do
    before do
      @allocated = @vtable.allocate.vtable
      
      @allocated.add_method(:who, proc { |*a| :allocated })
    end
    
    it "should lookup parent method" do
      @allocated.lookup(:parent).should be_a(Proc)
    end

    it "should override parent method" do
      @allocated.lookup(:who).call.should == :allocated
    end
  end
end