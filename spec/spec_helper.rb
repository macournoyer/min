$:.unshift File.dirname(__FILE__) + "/../lib"
require "min"
include Min

Spec::Runner.configure do |config|
  config.predicate_matchers[:be_a] = :is_a?
end