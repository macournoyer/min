require File.dirname(__FILE__) + '/spec_helper'

describe Min::Class do
  before do
    @klass = Min::Class.new
    @context = mock("context")
    
    @klass.add_method(:who, proc { |*a| :parent })
    @klass.add_method(:parent, proc { |*a| true })
  end
  
  it "should lookup method" do
    @klass.lookup(@context, :who).should be_a(Proc)
  end
  
  it "should return callabale methods" do
    @klass.lookup(@context, :who).call.should == :parent
  end
  
  describe "allocated" do
    before do
      @allocated = @klass.allocate.min_class
      
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