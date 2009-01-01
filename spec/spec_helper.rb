$:.unshift File.dirname(__FILE__)
$:.unshift File.dirname(__FILE__) + "/../lib"
require "min"
include Min
require "test_runtime"

Spec::Runner.configure do |config|
  config.predicate_matchers[:be_a] = :is_a?
  config.prepend_before do
    Min.bootstrap TestRuntime
  end
end