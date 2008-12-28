require File.dirname(__FILE__) + "/spec_helper"

describe Parser do
  before do
    @parser = Parser.new
  end
  
  def self.it_should_parse(code, options)
    it "should parse #{code}" do
      @parser.parse(code).nodes.should == options[:as]
    end
  end
  
  # Literals
  it_should_parse %{1}, :as => [Number.new(1)]
  it_should_parse %{"ohaie"}, :as => [Min::String.new("ohaie")]
  
  # Assign
  it_should_parse %{x = 1}, :as => [Assign.new("x", Number.new(1))]
  
  # Consts
  it_should_parse %{Const = 1}, :as => [AssignConstant.new("Const", Number.new(1))]
  it_should_parse %{Const}, :as => [Constant.new("Const")]
  
  # Call
  it_should_parse %{x}, :as => [Call.new(nil, "x", [])]
  it_should_parse %{x()}, :as => [Call.new(nil, "x", [])]
  it_should_parse %{x 1}, :as => [Call.new(nil, "x", [Number.new(1)])]
  it_should_parse %{x(1)}, :as => [Call.new(nil, "x", [Number.new(1)])]
  it_should_parse %{x(1, "1")}, :as => [Call.new(nil, "x", [Number.new(1), Min::String.new("1")])]
  it_should_parse %{x 1, "1"}, :as => [Call.new(nil, "x", [Number.new(1), Min::String.new("1")])]
  it_should_parse %{x:\n  1\n}, :as => [Call.new(nil, "x", [Block.new(
                                                                [Number.new(1)])
                                                              ])]
  it_should_parse %{x(1):\n  1\n  2\n}, :as => [Call.new(nil, "x", [Number.new(1),
                                                                       Block.new(
                                                                         [Number.new(1), Number.new(2)])
                                                                      ])]
  it_should_parse %{x:\n  1\n2\n}, :as => [Call.new(nil, "x", [Block.new([Number.new(1)])]),
                                           Number.new(2)]
  
  it_should_parse %{1[2]}, :as => [Call.new(Number.new(1), "[]", [Number.new(2)])]
  it_should_parse %{1[2] = 3}, :as => [Call.new(Number.new(1), "[]=", [Number.new(2), Number.new(3)])]
  
  # Object calls
  it_should_parse %{1.x}, :as => [Call.new(Number.new(1), "x", [])]
  it_should_parse %{x.y}, :as => [Call.new(Call.new(nil, "x", []), "y", [])]
  it_should_parse %{1.x.y}, :as => [Call.new(Call.new(Number.new(1), "x", []),
                                                "y", [])]
  
  # Special object calls
  it_should_parse %{a.x = 1}, :as => [Call.new(Call.new(nil, "a", []), "x=", [Number.new(1)])]
  it_should_parse %{x == 1}, :as => [Call.new(Call.new(nil, "x", []), "==", [Number.new(1)])]
  it_should_parse %{x + 1}, :as => [Call.new(Call.new(nil, "x", []), "+", [Number.new(1)])]
  
  # Comments
  it_should_parse %{# I IZ COMMENTZIN}, :as => []
  
  # Symbol
  it_should_parse %{:ohaie}, :as => [Min::Symbol.new("ohaie")]
end