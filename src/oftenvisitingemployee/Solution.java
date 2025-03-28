package oftenvisitingemployee;
/*
We are working on a security system for a badged-access room in our company's building.

We want to find employees who badged into our secured room unusually often. We have an unordered list of names and entry times over a single day. Access times are given as numbers up to four digits in length using 24-hour time, such as "800" or "2250".

Write a function that finds anyone who badged into the room three or more times in a one-hour period. Your function should return each of the employees who fit that criteria, plus the times that they badged in during the one-hour period. If there are multiple one-hour periods where this was true for an employee, just return the earliest one for that employee.

badge_times = [
  ["Paul",      "1355"], ["Jennifer",  "1910"], ["Jose",    "835"],
  ["Jose",       "830"], ["Paul",      "1315"], ["Chloe",     "0"],
  ["Chloe",     "1910"], ["Jose",      "1615"], ["Jose",   "1640"],
  ["Paul",      "1405"], ["Jose",       "855"], ["Jose",    "930"],
  ["Jose",       "915"], ["Jose",       "730"], ["Jose",    "940"],
  ["Jennifer",  "1335"], ["Jennifer",   "730"], ["Jose",   "1630"],
  ["Jennifer",     "5"], ["Chloe",     "1909"], ["Zhang",     "1"],
  ["Zhang",       "10"], ["Zhang",      "109"], ["Zhang",   "110"],
  ["Amos",         "1"], ["Amos",         "2"], ["Amos",    "400"],
  ["Amos",       "500"], ["Amos",       "503"], ["Amos",    "504"],
  ["Amos",       "601"], ["Amos",       "602"], ["Paul",   "1416"],
];

Expected output (in any order)
   Paul: 1315 1355 1405
   Jose: 830 835 855 915 930
   Zhang: 10 109 110
   Amos: 500 503 504

n: length of the badge records array
*/

import java.io.*;
import java.util.*;

public class Solution {
    Map<String, List<Integer>> timeMap = new HashMap<>();

    public Map<String, List<Integer>> oftenVisitingEmployees(String[][] badgeTimes){
        Map<String, List<Integer>> result = new HashMap<>();
        for (String[] badgeTime : badgeTimes) {
            timeMap.putIfAbsent(badgeTime[0], new ArrayList<>());
            timeMap.get(badgeTime[0]).add(Integer.parseInt(badgeTime[1]));
        }
        for(Map.Entry<String,List<Integer>> entry: timeMap.entrySet()){
            String person = entry.getKey();
            List<Integer> times = entry.getValue();
            Collections.sort(times);
            List<Integer> t = checkTimes(times);
            if(!t.isEmpty()){
                result.put(person, t);
            }
        }
        return result;
    }

    public List<Integer> checkTimes(List<Integer> times){
        int i = 0;
        List<Integer> timeList = new ArrayList<>();
        while(i < times.size()){
            int startTime = times.get(i);
            int endTime = startTime + 100;
            int j = i;
            while(j < times.size() && times.get(j) >= startTime && times.get(j) <= endTime){
                timeList.add(times.get(j));
                j++;
            }
            if(j - i >= 3){
                return timeList;
            } else {
                timeList = new ArrayList<>();
            }
            i++;
        }
        return timeList;
    }
    public static void main(String[] argv) {
        String[][] badgeTimes = new String[][] {
                {"Paul", "1355"},
                {"Jennifer", "1910"},
                {"Jose", "835"},
                {"Jose", "830"},
                {"Paul", "1315"},
                {"Chloe", "0"},
                {"Chloe", "1910"},
                {"Jose", "1615"},
                {"Jose", "1640"},
                {"Paul", "1405"},
                {"Jose", "855"},
                {"Jose", "930"},
                {"Jose", "915"},
                {"Jose", "730"},
                {"Jose", "940"},
                {"Jennifer", "1335"},
                {"Jennifer", "730"},
                {"Jose", "1630"},
                {"Jennifer", "5"},
                {"Chloe", "1909"},
                {"Zhang", "1"},
                {"Zhang", "10"},
                {"Zhang", "109"},
                {"Zhang", "110"},
                {"Amos", "1"},
                {"Amos", "2"},
                {"Amos", "400"},
                {"Amos", "500"},
                {"Amos", "503"},
                {"Amos", "504"},
                {"Amos", "601"},
                {"Amos", "602"},
                {"Paul", "1416"},
        };

        Solution s = new Solution();
        Map<String, List<Integer>> result = s.oftenVisitingEmployees(badgeTimes);
        for(Map.Entry<String, List<Integer>> entry : result.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
