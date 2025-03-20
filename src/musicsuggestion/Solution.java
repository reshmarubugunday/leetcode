package musicsuggestion;

import java.util.*;

/*
Given list of users that like a list of songs
1 : [A, B, C, D]
2 : [B, C, D]
3 : [C, D, E]

input: [B, C] - list of songs
output: [D, A] - suggestion for folks liking similar songs

Given - List<List<Integers>> - list of songs that people in the family plan like listening to
input - assume list of integers
output - list of integers

* */
public class Solution {
    List<Set<Integer>> songList;

    public Solution(List<Set<Integer>> songList){
        this.songList = songList;
    }

    public List<Integer> suggestSongs(Set<Integer> songs){
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> songFreq = new HashMap<>();
        if(songs == null || songs.isEmpty()){
            return result;
        }
        for(Set<Integer> set : songList){
            boolean hasAllSongs = true;
            for(int song:songs){
                if(!set.contains(song)){
                    hasAllSongs = false;
                    break;
                }
            }
            if(hasAllSongs){
                for(int song: set){
                    if(!songs.contains(song)){
                        songFreq.put(song, songFreq.getOrDefault(song, 0) + 1);
                    }
                }
            }
        }

        return sortByValueDescending(songFreq);
    }

    public static void main(String[] args) {
        List<Set<Integer>> songList = new ArrayList<>();
        Set<Integer> temp = new HashSet<>();
        temp.add(1); temp.add(2); temp.add(3); temp.add(4);
        songList.add(temp);
        temp = new HashSet<>();
        temp.add(2); temp.add(3); temp.add(4);
        songList.add(temp);
        temp = new HashSet<>();
        temp.add(5); temp.add(3); temp.add(4);
        songList.add(temp);
        Solution s = new Solution(songList);
        // valid test case - input 2,3 output 1,4
        Set<Integer> input = new HashSet<>();
        input.add(2); input.add(3);

        System.out.println(s.suggestSongs(input));

    }

    public static List<Integer> sortByValueDescending(Map<Integer, Integer> map) {
        // Convert Map to List of Map entries
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());

        // Sort the list based on values in descending order
        list.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Maintaining insertion order using LinkedHashMap
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            result.add(entry.getKey());
        }

        return result;
    }
}
