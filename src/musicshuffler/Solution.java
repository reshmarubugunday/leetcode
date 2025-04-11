package musicshuffler;

import java.util.*;
// """
// Company: StackAdapt
// Implement a music shuffling data structure with a Shuffle() method

// Inputs:
// 1. List of N integers representing songs
// 2. Integer K < N, representing the cooldown for a song after each shuffle

// The Shuffle method should randomly pick a song from the songs list.
// After a song is picked, it should not be picked again for the next K shuffles.


// Example:
// [1, 2, 3, 4, 5]
// 2
//
// > s = Shuffler([1,2,3,4,5], 2)
// > s.shuffle() -> 1 - 0
// > s.shuffle() -> 3 - 1
// > s.shuffle() -> 2 - 2
// > s.shuffle() -> 1 - 0
// """
public class Solution {
    int coolDown = 0;
    List<Integer> songs;
    Queue<Integer> queue;
    Solution(List<Integer> list, int coolDown){
        this.queue = new LinkedList<>();
        this.songs = new LinkedList<>();
        this.songs.addAll(list);
        this.coolDown = coolDown;
    }
    public static void main (String[] args) {
        System.out.println("Hello Java");
        Solution s = new Solution(List.of(1,2,3,4,5), 2);
        System.out.println(s.shuffle());
        System.out.println(s.shuffle());
        System.out.println(s.shuffle());
        System.out.println(s.shuffle());
        System.out.println(s.shuffle());
        System.out.println(s.shuffle());
    }
    public int shuffle(){
        int index = (int)(Math.random() * (songs.size()));
        queue.add(index);
        int result = songs.get(index);
        songs.remove(index);
        if(queue.size() > coolDown){
            songs.add(queue.poll());
        }
        return result;
    }
}

// Discussion during the interview
// Sol 1
// songs[1, 2..]
// Map(0 , [song,shuffle])
// Random(0, N)
// globalShuffle - map.get(rand).[1] > k
// globalShuffle++
// 1, shuffle
// 3. shuffle
// Sol 2
// random(0 , length)
// 2 , 5, 4, 3 1
// 2, 3, 4, 5, 1
// arr[k]
// """
// Solution given during the interview
//    public static int shuffle(List<Integer> songs, int coolDown){
//        int n = songs.size();
//        int index = 0;
//        index = (int)(Math.random() * (n - coolDown));
//        int result = songs.get(index);
//        songs.remove(index);
//        songs.add(result);
//        return result;
//    }