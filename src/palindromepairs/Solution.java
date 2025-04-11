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

import java.util.*;

public class Solution {

    public static void main(String[] args) {

        List<String> input1 = new ArrayList<>(List.of("gab", "cat", "bag", "alpha", "nurses", "race", "car", "run", "nu", "aba"));
        List<List<String>> output1 = palindromePairs(input1);
        List<List<String>> expected1 = List.of(List.of("gab", "bag"), List.of("bag", "gab"), List.of("race", "car"), List.of("nurses", "run"));
        System.out.println("output1: " + output1 + "expected1: " + expected1);
        System.out.println(output1.equals(expected1));

        List<String> input2 = new ArrayList<>(List.of("aaba", "a", "baa", "aa", "ba"));
        List<List<String>> output2 = palindromePairs(input2);
        List<List<String>> expected2 =  List.of(List.of("aaba", "a"), List.of("aaba", "baa"), List.of("aa", "baa"), List.of("a", "ba"));
        System.out.println("output2: " + output1 + "expected2: " + expected1);
        System.out.println(output2.equals(expected2));

        List<String> input3 = new ArrayList<>(List.of("abcd","dcba","lls","s","sssll"));
        List<List<String>> output3 = palindromePairs(input3);
        System.out.println(output3.equals(List.of(List.of("abcd", "dcba"), List.of("dcba", "abcd"), List.of("s", "lls"), List.of("lls", "sssll"))));

        List<String> input4 = new ArrayList<>(List.of("bat","tab","cat"));
        List<List<String>> output4 = palindromePairs(input4);
        System.out.println(output4.equals(List.of(List.of("bat", "tab"), List.of("tab", "bat"))));

        List<String> input5 = new ArrayList<>(List.of("a",""));
        List<List<String>> output5 = palindromePairs(input5);
        System.out.println(output5.equals(List.of(List.of("a", ""), List.of("", "a"))));

    }

    public static List<List<String>> palindromePairs(List<String> words){
        HashMap<String, Integer> reversedMap = new HashMap<>();
        List<List<String>> result = new ArrayList<>();

        // this part will add the exact pairs that can be concatenated together to make a palindrome
        for(int i=0; i<words.size(); i++) {
            String reverse = new StringBuilder(words.get(i)).reverse().toString();
            reversedMap.put(reverse, i);
        }

        for(int i=0; i<words.size(); i++){
            String word = words.get(i);
            for(int cut = 0; cut < word.length(); cut++){
                String prefix = word.substring(0,cut);
                String suffix = word.substring(cut);

                if(isPalindrome(prefix)){
                    Integer j = reversedMap.get(suffix);

                    if(j != null && j != i){
                        result.add(List.of(words.get(j), words.get(i)));
                    }
                }

                if(isPalindrome(suffix)){
                    Integer j = reversedMap.get(prefix);

                    if(j != null && j != i){
                        result.add(List.of(words.get(i), words.get(j)));
                    }
                }
            }
        }

        return result;
    }

    public static boolean isPalindrome(String s){
        int i = 0, j = s.length() - 1 ;
        while(i < j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

}
