package regexvalidator;
/*
Company: Tesla
Given an input string s and a pattern p, implement regular expression matching with support for '.' and '*' where:
'.' Matches any single character.
'*' Matches zero or more of the preceding element.

Example 1:
Input: s = "aa", p = "a"
Output: false

Explanation: "a" does not match the entire string "aa".

Example 2:
Input: s = "aa", p = "a*"
Output: true

Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".

Example 3:
Input: s = "ab", p = ".*"
Output: true

Explanation: ".*" means "zero or more (*) of any character (.)".
aa, a
0,0 -> match
1, 1 -> out of bounds on pattern -> no match
aa, aa
1, 1 -> match
aa, a.
at 1, 1

*/


// input - alphabets & numbers
// spl character * .
// output bool
// len(s) > 1 && len(p) == 1, return false
// package solution


class Solution {
    public static void main(String[] args) {
        System.out.println("Result: " +testValidation("aa", "a", false));
        System.out.println("Result: " +testValidation("aa", "a*", true));
        System.out.println("Result: " +testValidation("ab", ".*", true));
    }

    public static boolean validateString(String s, String p){
        int i=0, j=0;

        while(i < s.length() && j < p.length()){
            System.out.println("while");
            if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '.'){
                System.out.println("if");
                System.out.println(s.charAt(i));
                System.out.println(p.charAt(j));
                i++;
                j++;
            } else if(p.charAt(j) == '*'){
                System.out.println("else - if");
                System.out.println(p.charAt(j));
                System.out.println(s.charAt(i));
                System.out.println(p.charAt(j));
                char temp = s.charAt(j-1);
                while(i < s.length() && s.charAt(i) == temp){
                    i++;
                }
                System.out.println("i " + i);
                System.out.println("j " + j);
                i++;
                j++;
            } else {
                System.out.println("else");
                return false;
            }
        }
        return i == s.length() && j == p.length();
    }

    public static boolean validateStringRecursive(String s, String p) {
        // Base case: if pattern is empty, the string must be empty too.
        if (p.isEmpty()) {
            return s.isEmpty();
        }

        // Check if first characters match (considering '.')
        boolean firstMatch = (!s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.'));

        // If pattern contains '*' as the second character...
        if (p.length() >= 2 && p.charAt(1) == '*') {
            // Two cases:
            // 1. Treat "x*" as matching zero characters: skip it in the pattern.
            // 2. If first character matches, consume one character from s.
            return (validateStringRecursive(s, p.substring(2)) ||
                    (firstMatch && validateStringRecursive(s.substring(1), p)));
        } else {
            // No '*' means match one character and move on.
            return firstMatch && validateStringRecursive(s.substring(1), p.substring(1));
        }
    }

    public static boolean validateStringDP(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];

        // Base case: empty string and empty pattern match
        dp[m][n] = true;

        // Handle cases where s is empty but p is not.
        // For example, patterns like "a*b*c*" can match an empty string.
        for (int j = n - 1; j >= 0; j--) {
            if (j + 1 < n && p.charAt(j + 1) == '*') {
                dp[m][j] = dp[m][j + 2];
            }
        }

        // Fill the DP table from bottom up.
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                boolean firstMatch = (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');

                if (j + 1 < n && p.charAt(j + 1) == '*') {
                    // Two possibilities:
                    // 1. Skip the current char and '*' in pattern (zero occurrence).
                    // 2. If first characters match, consume one char from s.
                    dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
                } else {
                    dp[i][j] = firstMatch && dp[i + 1][j + 1];
                }
            }
        }

        return dp[0][0];
    }

    public static boolean testValidation(String s, String p, boolean result){
        return result == validateStringDP(s, p);
    }
}