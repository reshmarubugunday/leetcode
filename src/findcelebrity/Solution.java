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
    Map<String, Person> whoIKnow;
    Map<String, Set<String>> whoKnowMe;
    Set<String> potentialCelebrities;
    int numberOfPeople;

    class Person{
        String name;
        Set<String> peopleIKnow;

        Person(String name, String[] peopleIKnow){
            this.name = name;
            this.peopleIKnow = new HashSet<>();
            this.peopleIKnow.addAll(Arrays.asList(peopleIKnow));
        }
    }

    Solution(String[] people, String[][] knownPeople){
        this.whoIKnow = new HashMap<>();
        this.whoKnowMe = new HashMap<>();
        this.potentialCelebrities = new HashSet<>();
        this.numberOfPeople = people.length;

        for(int i=0; i<people.length; i++){
            whoIKnow.put(people[i], new Person(people[i], knownPeople[i]));
            if(knownPeople[i].length == 0){
                potentialCelebrities.add(people[i]);
            }
            for(String p: knownPeople[i]){
                whoKnowMe.putIfAbsent(p, new HashSet<>());
                whoKnowMe.get(p).add(people[i]);
            }
            whoKnowMe.putIfAbsent(people[i], new HashSet<>());
        }
    }

    public String FindCelebrity(){
        for(String p : potentialCelebrities){
            if(whoKnowMe.get(p).size() == numberOfPeople - 1){
                return p;
            }
        }
        return "None";
    }

    public static void main(String[] args) {
        String[] people = new String[]{"Brian", "Sam", "Lady Gaga", "Amy", "Justin", "Alex"};
        String[][] knownPeople = new String[][]{{"Sam", "Amy", "Lady Gaga"}, {"Justin", "Brian", "Lady Gaga"},{}, {"Brian", "Justin", "Lady Gaga"}, {"Sam", "Brian", "Amy", "Lady Gaga"}, {"Lady Gaga"}};
        Solution s = new Solution(people, knownPeople);
        System.out.println("Celebrity in the group: " + s.FindCelebrity());

    }
}
