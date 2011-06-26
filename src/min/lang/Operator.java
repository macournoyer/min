package min.lang;

import java.util.HashMap;

public class Operator {
  String name;
  int precedence;
  int arity;
  boolean rightToLeft;
  
  static HashMap<String, Operator> table = new HashMap<String, Operator>();
  
  static Operator nullOperator = new Operator(null, 0, 0, false);
  
  // Declare operators
  static {
    int precedence = 1;
    
    //                       arity, rightToLeft, names
    operators(precedence++,  0,     true,        ".", "\n", "\r\n");
    
    operators(precedence++,  3,     true,        "=");
    
    operators(precedence++,  2,     false,       "||");
    operators(precedence++,  2,     false,       "&&");
    
    operators(precedence++,  2,     false,       "|");
    operators(precedence++,  2,     false,       "^");
    operators(precedence++,  2,     false,       "&");
    
    operators(precedence++,  2,     false,       "==", "!=");
    
    operators(precedence++,  2,     false,       "<", "<=", ">", ">=");
    
    operators(precedence++,  2,     false,       "<<", ">>");
    
    operators(precedence++,  2,     false,       "+", "-");
    
    operators(precedence++,  2,     false,       "*", "/", "%");
    
    operators(precedence++,  1,     true,        "!", "~");
  };
  
  private static void operators(int precedence, int arity, boolean rightToLeft, String... names) {
    for (String name : names) {
      table.put(name, new Operator(name, precedence,  arity, rightToLeft));
    }
  }
  
  public Operator(String name, int precedence, int arity, boolean rightToLeft) {
    this.name = name;
    this.precedence = precedence;
    this.arity = arity;
    this.rightToLeft = rightToLeft;
  }
  
  public boolean isRightToLeft() {
    return rightToLeft;
  }
  
  public boolean isLeftToRight() {
    return !rightToLeft;
  }
  
  public boolean isNullary() {
    return arity == 0;
  }
  
  public boolean isUnary() {
    return arity == 1;
  }
  
  public boolean isBinary() {
    return arity == 2;
  }
  
  public boolean isTernary() {
    return arity == 3;
  }
}
