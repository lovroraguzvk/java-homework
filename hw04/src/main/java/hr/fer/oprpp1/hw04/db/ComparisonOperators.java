package hr.fer.oprpp1.hw04.db;

import java.util.Arrays;

/**
 * Class containing defined comparison operators using the interface IComparisonOperator
 */
public class ComparisonOperators {

    public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;

    public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;

    public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;

    public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;

    public static final IComparisonOperator EQUALS = String::equals;

    public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> !v1.equals(v2);

    public static final IComparisonOperator LIKE = (v1, v2) -> {
        String[] splitString = v2.split("\\*");

        if(v2.equals("*")) return true;

        if(v2.indexOf('*') != v2.lastIndexOf('*')) {
            throw new IllegalArgumentException("Associative keyword LIKE requires only one special char '*'");
        }

        if(v2.charAt(0) == '*' && v1.equals(splitString[1])) return true;
        if(v2.charAt(v2.length() - 1) == '*' && v1.equals(splitString[0])) return true;
        if(splitString.length == 2 && v1.equals(splitString[0].concat(splitString[1]))) return true;

        boolean specialUsed = false;
        int offset = 0;
        for(int i = 0; i < v1.length(); i++) {
            if(v2.charAt(i - offset) == '*') {
                if(v2.length() == i + 1) {
                    return true;
                }

                if(i == 0) {
                    i++;
                    while(v1.charAt(i) != v2.charAt(i - offset)) {
                        i++;
                        offset++;
                    }
                }

                if(specialUsed) throw new RuntimeException("Two or more special characters '*' in LIKE statement.");
                specialUsed = true;
                continue;
            }

            if(v1.charAt(i) != v2.charAt(i - offset)) return false;
        }

        return true;
    };


}
