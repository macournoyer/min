module Min
  class RubyMethod
    def initialize(message)
      @message = message
    end
    
    def call(context, receiver, *args)
      receiver.send(@message, *args.map { |arg| arg.eval(context).to_ruby })
    end
  end
end