require File.dirname(__FILE__) + '/spec_helper'

describe RubyMethod do
  before do
    @receiver = mock("receiver")
    @context = mock("context")
  end
  
  it "should call method" do
    @receiver.should_receive(:method).with()
    RubyMethod.new(:method).call(@context, @receiver)
  end

  it "should call method with args" do
    arg = mock("arg", :null_object => true)
    @receiver.should_receive(:method).with(arg, arg)
    RubyMethod.new(:method).call(@context, @receiver, arg, arg)
  end
  
  it "should convert method return value to min object" do
    @receiver.should_receive(:method).with().and_return(nil)
    RubyMethod.new(:method).call(@context, @receiver).should == Min[:nil]
  end

  it "should call delegate" do
    @receiver.should_receive(:value).
      and_return(mock("return", :method => nil))
    
    RubyMethod.new(:method, :delegate_to => :value).call(@context, @receiver)
  end

  it "should pass context" do
    @receiver.should_receive(:method).with(@context)
    
    RubyMethod.new(:method, :pass_context => true).call(@context, @receiver)
  end

  it "should call proc" do
    @receiver.should_receive(:method).with(@context)
    
    RubyMethod.new { |context, receiver, *args| @receiver.method(context) }.call(@context, @receiver)
  end

  it "should convert proc return value to min object" do
    RubyMethod.new { |context, receiver, *args| nil }.call(@context, @receiver).should == Min[:nil]
  end
end