require "fileutils"

class SmokeTest
  include FileUtils
  
  def initialize(pattern, dir)
    @total    = 0
    @failures = []
    @pattern  = pattern
    @dir      = dir
  end
  
  def run
    cd @dir do
      Dir[@pattern].each do |file|
        expected = File.read(file).split("\n").map { |l| l[/^# (.*)$/, 1] }.compact.join("\n")
        actual   = min(file)
        if expected == actual
          pass(file)
        else
          fail(file, expected, actual)
        end
      end
      
      puts
      
      @failures.each do |file, expected, actual|
        puts "[%s]" % file
        puts "    expected #{expected.inspect}"
        puts "         got #{actual.inspect}"
        puts
      end
      
      puts "#{@total} tests, #{@failures.size} failures"
    end
  end
  
  protected
    def min(*args)
      cmd = "./min #{args * ' '}"
      `#{cmd}`.chomp
    end

    def pass(file)
      @total += 1
      print "."
    end

    def fail(file, expected, actual)
      @total += 1
      @failures << [file, expected, actual]
      print "F"
    end
end

SmokeTest.new("test/**.min", File.dirname(__FILE__) + "/..").run