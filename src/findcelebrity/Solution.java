package findcelebrity;
/*
Given a list of people attending the party list = Brian, Amy, Sam, Lady Gaga, Justin

You'll also be given who knows who
Brian - Sam, Amy, Lady Gaga
Amy - Justin, Brian, Lady Gaga
Sam - Brian, Justin, Lady Gaga
Justin - Sam, Brian, Amy, Lady Gaga

Given these details, find the celebrity if any. You'll have at most 1 celebrity in the group
If no celebrity was found, return "None"
*/
import java.util.*;

public class Solution {

    Map<String, Set<String>> knownMap;

    Solution(String[] people, String[][] knownPeople){
        this.knownMap = new HashMap<>();
        for(int i=0; i<people.length; i++){
            knownMap.putIfAbsent(people[i], new HashSet<>());
            knownMap.get(people[i]).addAll(List.of(knownPeople[i]));
        }
    }
    // a knows b
    public boolean knows(String a, String b){
        return knownMap.containsKey(a) && knownMap.get(a).contains(b);
    }

    public String FindCelebrity(String[] people){
        String candidate = people[0];
        for(int i=1; i<people.length; i++){
            if(knows(candidate, people[i])){
                candidate = people[i];
            }
        }

        for(String p: people){
            if(candidate.equals(p)){
                continue;
            }
            if(knows(candidate, p) || !knows(p, candidate)){
                return "None";
            }
        }
        return candidate;
    }



    public static void main(String[] args) {
        String[] people = new String[]{"Brian", "Sam","Alex" , "Amy", "Justin", "Lady Gaga"};
        String[][] knownPeople = new String[][]{{"Sam", "Amy", "Lady Gaga"}, {"Justin", "Brian", "Lady Gaga"},{"Lady Gaga"}, {"Brian", "Justin", "Lady Gaga"}, {"Sam", "Brian", "Amy", "Lady Gaga"}, {}};
        Solution s = new Solution(people, knownPeople);
        System.out.println("Celebrity in the group: " + s.FindCelebrity(people));

    }
}
