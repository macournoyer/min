$:.unshift File.dirname(__FILE__) + "/../lib"
require "min"
include Min

module Min
  class TestRuntime < Runtime
    def initialize(*args)
      super
      load_path << File.dirname(__FILE__) + "/fixtures"
    end
  end
end

Spec::Runner.configure do |config|
  config.predicate_matchers[:be_a] = :is_a?
end