package palindromepairs;
/*
1. Given a string, write a function to return whether it is a palindrome or not

Palindrome examples: "bob", "gig", "abba" -> True
Not palindrome examples: "firetruck" -> False


2. Given a list of unique words, find the pairs of words that, when concatenated, will form a palindrome. For example:

["gab", "cat", "bag", "alpha"]
   => [["gab", "bag"], ["bag", "gab"]]

["gab", "cat", "bag", "alpha", "nurses", "race", "car", "run", "nu", "aba"]
   => [["gab", "bag"], ["bag", "gab"], ["race", "car"], ["nurses", "run"], ["nu", "run"]]

["cat", "dog", "elephant", "tac", "aire", "gigeria"]
   => [["cat", "tac"], ["tac", "cat"], ["aire", "gigeria"]]

["cat", "dog", "elephant", "tac", "cath", "aire", "gigeria"]
   => [["cat", "tac"], ["tac", "cat"], ["cath", "tac"], ["aire", "gigeria"]]

["aaba", "a", "baa", "aa", "ba"]
   => [['aaba', 'a'], ['aaba', 'baa'], ['aa', 'baa'], ['aa', 'a'], ['a', 'aa'], ['a', 'ba']]


Set - gab, cat, bag , nurses
result - [[gab, bag], [bag, gab]]
*/

import java.io.*;
        import java.util.*;
        import java.text.*;
        import java.math.*;
        import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println(isPalindrome("bob"));
        System.out.println(isPalindrome("gig"));
        System.out.println(isPalindrome("abba"));
        System.out.println(isPalindrome("firetruck"));

        List<String> input = new LinkedList<>();
        input.add("gab");
        input.add("cat") ;
        input.add("bag");
        input.add("alpha");
        input.add("nurses");
        input.add("race") ;
        input.add("car") ;
        input.add("run") ;
        input.add("nu");
        input.add("aba");

        List<String> input1 = new LinkedList<>();
        input.add("aaba");
        input.add("a") ;
        input.add("baa");
        input.add("aa");
        input.add("ba");


        List<List<String>> output = palindromPairs(input);

        for(List<String> list:output){
            System.out.println(list);
        }

        List<List<String>> output1 = palindromPairs(input);
        for(List<String> list:output1){
            System.out.println(list);
        }
    }

    public static boolean isPalindrome(String str){
        char[] chars = str.toCharArray();
        int i=0;
        int j=chars.length-1;
        while(i<chars.length && j>0 && i<j){
            if(chars[i] == chars[j]){
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    public static List<List<String>> palindromPairs(List<String> words){
        HashMap<String, String> map = new HashMap<>();
        List<List<String>> result = new LinkedList<>();

        // this part will add the exact pairs that can be concatenated together to make a palindrom
        for(String str:words){
            StringBuilder input1 = new StringBuilder();
            input1.append(str);
            input1.reverse();
            if(map.containsKey(input1.toString())){
                List<String> temp = new LinkedList<>();
                temp.add(str);
                temp.add(input1.toString());
                // complaining with error here.
                result.add(temp);
                List<String> temp1 = new LinkedList<>();
                temp1.add(input1.toString());
                temp1.add(str);
                result.add(temp1);
                map.put(str, "");
            } else {
                map.put(str, "");
            }
        }

        // this part will leave out those pairs and cover the other words and check if it is a palindrome when concatenated with each word in the list
        for(String str:words){
            for(String s:map.keySet()){
                StringBuilder input1 = new StringBuilder();
                input1.append(s);
                input1.reverse();
                if(str.equals(input1.toString())){
                    continue;
                } else{
                    if (isPalindrome(str+s)){
                        List<String> temp = new LinkedList<>();
                        temp.add(str);
                        temp.add(s);
                        result.add(temp);
                    }
                }
            }
        }
        return result;
    }

}
