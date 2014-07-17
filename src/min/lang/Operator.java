package min.lang;

import java.util.HashMap;

public class Operator {

    final int precedence;
    private final int arity;
    private final boolean rightToLeft;

    static final HashMap<String, Operator> table = new HashMap<String, Operator>();

    static final Operator nullOperator = new Operator(null, 0, 0, false);

    // Declare operators
    static {
        int precedence = 1;

        //
        //               == Operator precedence table ==
        //
        //                            arity, rightToLeft?, names
        defineOperators(precedence++, 0, false, ";", "\n", "\r\n");
        defineOperators(precedence++, 3, true, "=",
                "+=", "-=", "*=", "/=",
                "&=", "|=",
                "&&=", "||=");
        defineOperators(precedence++, 2, false, "||", "or");
        defineOperators(precedence++, 2, false, "&&", "and");
        defineOperators(precedence++, 2, false, "|");
        defineOperators(precedence++, 2, false, "^");
        defineOperators(precedence++, 2, false, "&");
        defineOperators(precedence++, 2, false, "==", "!=", "is",
                "=~", "!~");
        defineOperators(precedence++, 2, false, "<", "<=", ">", ">=");
        defineOperators(precedence++, 2, false, "<<", ">>");
        defineOperators(precedence++, 2, false, "+", "-");
        defineOperators(precedence++, 2, false, "*", "/", "%");
        defineOperators(precedence++, 1, true, "!", "not",
                "~", "?",
                "-@", "+@", "*@", "&@", "-");
        defineOperators(precedence, 0, false, ".");

    }


    private static void defineOperators(int precedence, int arity, boolean rightToLeft, String... names) {
        for (String name : names) {
            table.put(name, new Operator(name, precedence, arity, rightToLeft));
        }
    }

    public Operator(String name, int precedence, int arity, boolean rightToLeft) {
        //String name1 = name;
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
