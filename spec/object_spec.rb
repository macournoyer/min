require File.dirname(__FILE__) + '/spec_helper'

describe Min::Object do
  class UberAwesomeObject < Min::Object
    attributes :ubersome, :awesomeness
    
    min_def :test do |context, *args|
      Number.new(1)
    end
  end
  
  class ExtraAwesomeObject < Min::Object
    attributes :indeed
  end
  
  before do
    @object = UberAwesomeObject.new(true, 0.1)
  end
  
  it "should have attributes" do
    @object.ubersome.should == true
    @object.awesomeness.should == 0.1
  end
  
  it "should == other" do
    other = UberAwesomeObject.new(true, 0.1)
    @object.should == other
  end

  it "should not == other" do
    other = UberAwesomeObject.new(true, 0.2)
    @object.should_not == other
  end
  
  it "should not share attributes w/ other subclasses" do
    UberAwesomeObject.attributes.should == [:ubersome, :awesomeness]
    ExtraAwesomeObject.attributes.should == [:indeed]
    Min::Object.attributes.should == []
  end
  
  it "should send message" do
    @object.min_send(mock("context"), :test).should == Number.new(1)
  end
end