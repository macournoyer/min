module Min
  class Closure < Min::Object
    attr_reader   :block, :arguments
    attr_accessor :receiver
    
    def initialize(block, arguments=[])
      @block     = block
      @arguments = arguments
      @receiver  = nil
      
      super Min[:Closure]
    end
    
    def call(context, receiver, *args)
      closure_context = context.create(@receiver)
      
      # inherit receiver from parent context
      bind(closure_context.min_self)
      
      # Special local vars
      closure_context[:it]       = args.first
      closure_context[:self]     = @receiver
      closure_context[:receiver] = receiver
      
      # Pass args as local vars
      bind_params(args).each do |name, value|
        closure_context[name] = value
      end
      
      block.eval(closure_context)
    end
    
    def bind_params(args)
      mapped = {}
      arguments.each_with_index do |argument, i|
        if argument.splat
          # TODO Splat only allowed as last arg for now
          mapped[argument.name] = args[i..-1].to_min
          break
        else
          mapped[argument.name] = args[i] || argument.default
        end
      end
      mapped
    end
    
    def bind(object)
      @receiver = object
      self
    end
    
    def ==(other)
      Closure === other && block == other.block && arguments == other.arguments
    end
    
    def self.bootstrap(runtime)
      klass = runtime[:Object].min_class.subclass
      
      klass.add_method(:bind, RubyMethod.new(:bind))
      klass.add_method(:call, proc { |context, receiver, *args| receiver.call(context, receiver, *args).to_min })
      
      runtime[:Closure] = klass
    end
  end
end