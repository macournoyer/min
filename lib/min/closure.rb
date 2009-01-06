module Min
  class Closure < Min::Object
    attr_reader   :block, :arguments
    attr_accessor :receiver, :data
    
    def initialize(block, arguments=[])
      @block     = block
      @arguments = arguments
      @receiver  = nil
      @data      = nil
      @bind_to_caller = false
      
      super Min[:Closure].vtable
    end
    
    def call(context, receiver, *args)
      closure_context = context.create(@receiver)
      
      if @bind_to_caller
        bind(receiver)
      else
        # inherit receiver from parent context
        bind(closure_context.min_self)
      end
      
      # Special local vars
      closure_context.min_self =
      closure_context[:self]   = @receiver
      closure_context[:caller] = receiver
      closure_context[:it]     = args.first.eval(context) if args.first
      
      # Pass args as local vars
      bind_params(args).each do |name, value|
        closure_context[name] = value.eval(context) if value
      end
      
      block.eval(closure_context)
    end
    
    def bind_to_caller!
      @bind_to_caller = true
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
      vtable = runtime[:Object].vtable.delegated
      runtime[:Closure] = vtable.allocate
      
      vtable.add_method(:bind, RubyMethod.new(:bind))
      vtable.add_method(:bind_to_caller!, RubyMethod.new(:bind_to_caller!))
      vtable.add_method(:data, RubyMethod.new(:data))
      vtable.add_method(:data=, RubyMethod.new(:data=))
      vtable.add_method(:call, RubyMethod.new { |context, receiver, *args| receiver.call(context, receiver, *args).to_min })
    end
  end
end