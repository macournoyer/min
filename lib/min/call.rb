module Min
  class Call < Min::Object
    attr_reader :receiver, :message, :arguments
    
    def initialize(receiver, message, arguments=[])
      @receiver  = receiver
      @message   = message
      @arguments = arguments
    end
    
    def eval(context)
      if receiver.nil? && value = context.locals[message]
        # local var
        return value
      end
      (receiver || context.min_self).min_send(context, message, *arguments)
    end
  end
end