require File.dirname(__FILE__) + "/spec_helper"

describe Parser do
  before do
    @parser = Parser.new
  end
  
  def self.it_should_parse(code, &expected)
    it "should parse #{code}" do
      @parser.parse(code).nodes.should == instance_eval(&expected)
    end
  end
  
  # Literals
  it_should_parse(%{1}) { [Number.new(1)] }
  it_should_parse(%{"ohaie"}) { [Min::String.new("ohaie")] }
  
  # Assign
  it_should_parse(%{x = 1}) { [Assign.new(:x, Number.new(1))] }
  
  # Consts
  it_should_parse(%{Const = 1}) { [AssignConstant.new(:Const, Number.new(1))] }
  it_should_parse(%{Const}) { [Constant.new(:Const)] }
  
  # Call
  it_should_parse(%{x}) { [Call.new(nil, :x, [])] }
  it_should_parse(%{x()}) { [Call.new(nil, :x, [])] }
  it_should_parse(%{x 1}) { [Call.new(nil, :x, [Number.new(1)])] }
  it_should_parse(%{x(1)}) { [Call.new(nil, :x, [Number.new(1)])] }
  it_should_parse(%{x(1, "1")}) { [Call.new(nil, :x, [Number.new(1), Min::String.new("1")])] }
  it_should_parse(%{x 1, "1"}) { [Call.new(nil, :x, [Number.new(1), Min::String.new("1")])] }
  it_should_parse(%{1[2]}) { [Call.new(Number.new(1), :[], [Number.new(2)])] }
  it_should_parse(%{1[2] = 3}) { [Call.new(Number.new(1), :[]=, [Number.new(2), Number.new(3)])] }
  
  # Call w/ closure
  it_should_parse(%{x:\n  1\n}) { [Call.new(nil, :x, [Closure.new(
                                                            Block.new([Number.new(1)]), [])
                                                          ])] }
  it_should_parse(%{x: a |\n  1\n}) { [Call.new(nil, :x, [Closure.new(
                                                            Block.new([Number.new(1)]), [Param.new(:a, nil, false)])
                                                          ])] }
  it_should_parse(%{x(1):\n  1\n  2\n}) { [Call.new(nil, :x, [Number.new(1),
                                                                    Closure.new(
                                                                      Block.new([Number.new(1), Number.new(2)]), [])
                                                                   ])] }
  it_should_parse(%{x:\n  1\n2\n}) { [Call.new(nil, :x, [Closure.new(Block.new([Number.new(1)]), [])]),
                                           Number.new(2)] }
  it_should_parse(%{x {1}\n}) { [Call.new(nil, :x, [Closure.new(Block.new([Number.new(1)]), [])])] }
  it_should_parse(%{x(2, {1})\n}) { [Call.new(nil, :x, [Number.new(2), Closure.new(Block.new([Number.new(1)]), [])])] }
  it_should_parse(%{x { a | 1}\n}) { [Call.new(nil, :x, [Closure.new(Block.new([Number.new(1)]), [Param.new(:a, nil, false)])])] }
  it_should_parse(%{x: 1\n}) { [Call.new(nil, :x, [Closure.new(Block.new([Number.new(1)]), [])])] }
  it_should_parse(%{x: a | 1\n}) { [Call.new(nil, :x, [Closure.new(Block.new([Number.new(1)]), [Param.new(:a, nil, false)])])] }
  it_should_parse(%{x(1): a | 1\n}) { [Call.new(nil, :x, [Number.new(1), Closure.new(Block.new([Number.new(1)]), [Param.new(:a, nil, false)])])] }
  
  # Closure
  it_should_parse(%{{ a | 1 }}) { [Closure.new(Block.new([Number.new(1)]), [Param.new(:a, nil, false)])] }
  it_should_parse(%{{ *a | 1 }}) { [Closure.new(Block.new([Number.new(1)]), [Param.new(:a, nil, true)])] }
  it_should_parse(%{{ a=2 | 1 }}) { [Closure.new(Block.new([Number.new(1)]), [Param.new(:a, Number.new(2), false)])] }
  
  # Object calls
  it_should_parse(%{1.x}) { [Call.new(Number.new(1), :x, [])] }
  it_should_parse(%{x.y}) { [Call.new(Call.new(nil, :x, []), :y, [])] }
  it_should_parse(%{1.x.y}) { [Call.new(Call.new(Number.new(1), :x, []),
                                                :y, [])] }
  
  # Special object calls
  it_should_parse(%{a.x = 1}) { [Call.new(Call.new(nil, :a, []), :x=, [Number.new(1)])] }
  it_should_parse(%{x == 1}) { [Call.new(Call.new(nil, :x, []), :==, [Number.new(1)])] }
  it_should_parse(%{x + 1}) { [Call.new(Call.new(nil, :x, []), :+, [Number.new(1)])] }
  it_should_parse(%{x && 1}) { [Call.new(Call.new(nil, :x, []), :"&&", [Number.new(1)])] }
  it_should_parse(%{!1}) { [Call.new(Number.new(1), :"!", [])] }
  it_should_parse(%{-1}) { [Call.new(Number.new(1), :"-", [])] }
  
  # Comments
  it_should_parse(%{# I IZ COMMENTZIN}) { [] }
  
  # Symbol
  it_should_parse(%{:ohaie}) { [Min::Symbol.new(:ohaie)] }
  
  # Array
  it_should_parse(%{[1]}) { [Min::Array.new([Number.new(1)])] }
  it_should_parse(%{[1, 2]}) { [Min::Array.new([Number.new(1), Number.new(2)])] }

  # Hash
  it_should_parse(%{[:]}) { [Min::Hash.new({})] }
  it_should_parse(%{[1: 2]}) { [Min::Hash.new(Number.new(1) => Number.new(2))] }
  it_should_parse(%{[a: 2]}) { [Min::Hash.new(Min::Symbol.new(:a) => Number.new(2))] }
  it_should_parse(%{["a" : 2]}) { [Min::Hash.new(Min::String.new("a") => Number.new(2))] }
end