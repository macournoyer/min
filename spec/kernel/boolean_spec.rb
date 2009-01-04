require File.dirname(__FILE__) + '/../spec_helper'

describe "Boolean" do
  it "should have nil" do
    Min.eval(%{nil}).should == Min[:Nil]
  end
end