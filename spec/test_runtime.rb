module Min
  class TestRuntime < Runtime
    def initialize(*args)
      super
      load_path << File.dirname(__FILE__) + "/fixtures"
    end
  end
  
  def self.runtime
    @runtime ||= TestRuntime.new
  end
end
