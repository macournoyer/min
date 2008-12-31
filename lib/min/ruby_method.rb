module Min
  class RubyMethod
    def initialize(message, attribute=nil)
      @message   = message
      @attribute = attribute
    end
    
    def call(context, receiver, *args)
      if @attribute
        receiver = receiver.send(@attribute)
      end
      receiver.send(@message, *args.map { |arg| arg.eval(context).value }).to_min(context)
    end
  end
end

# Convertions from Ruby object to Min object
class Object
  def to_min(context)
    self
  end
end

class Fixnum
  def to_min(context)
    Min::Number.new(context, self)
  end
end