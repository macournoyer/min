class Symbol
  def to_min
    Min::String.new(to_s)
  end
end

class String
  def to_min
    Min::String.new(self)
  end
end

class Integer
  def to_min
    Min::Number.new(self)
  end
end

class Array
  def to_min
    Min::Array.new(map { |item| item.to_min })
  end
end