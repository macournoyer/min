require File.dirname(__FILE__) + '/spec_helper'

describe Min::Class do
  before do
    @klass = Min::Class.new
    @context = mock("context")
    @receiver = mock("receiver")
    
    @klass.add_method(:who, RubyMethod.new { |*a| :parent })
    @klass.add_method(:parent, RubyMethod.new { |*a| true })
  end
  
  it "should lookup method" do
    @klass.lookup(@context, :who).should be_a(RubyMethod)
  end
  
  it "should return callabale methods" do
    @klass.lookup(@context, :who).call(@context, @receiver).should == :parent.to_min
  end
  
  describe "allocated" do
    before do
      @allocated = @klass.allocate.min_class
      
      @allocated.add_method(:who, RubyMethod.new { |*a| :allocated })
    end
    
    it "should lookup parent method" do
      @allocated.lookup(@context, :parent).should be_a(RubyMethod)
    end

    it "should override parent method" do
      @allocated.lookup(@context, :who).call(@context, @receiver).should == :allocated.to_min
    end
  end
end