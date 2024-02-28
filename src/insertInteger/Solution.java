package insertInteger;

// You are given an array A of integers and a target integer S. Assume that you generate some other array B containing all pairwise sums of two numbers from A, i.e. A[i] + A[j] with i <= j.

// Find the index of the number S in the sorted array B.  If number S is not present in sorted array B, return the index at which S would be inserted.

// Example:
// A: [5, 6, 1, 10, 20]
// S: 10
// A: [0]
// S: 1

// The sorted array B would look like:
// B[0] = 1 + 1 = 2
// B[1] = 1 + 5 = 6
// B[2] = 1 + 6 = 7
// B[3] = 5 + 5 = 10
// B[4] = 5 + 6 = 11
// B[5] = 1 + 10 = 11
// B[6] = 6 + 6 = 12
// and so on…

// S = 10 is found at index 3, so the answer for this example is 3.

// input array - positive integers
// S is also +ve integer
// output - integer


import java.io.*;
import java.util.*;

class Solution {
    public static void main(String[] args) {

        int[] nums = {5, 6, 1, 10, 20}; // 5 + 4 + 3 + 2 + 1 = 15
        // 1, 5, 6, 10, 20
        // 1 5 6 10 20 10, 11, 15, 25

        int target = 10;
        int[] nums1 = {0}; // 0
        int target1 = 10;

        int[] nums2 = {0,3,1,5,10};
        // 0,3,1,5,10,6,4,8,13,1,6,11,10,15
        // 0,1,1,3,4,5,6,6,8,10,10,11,13,15
        // 4
        int target2 = 4;

        // array = { 100,200,300,400.... 900, 1, 2}
        // target = 3

        System.out.println(insertInteger(nums, target)); // 3
        System.out.println(insertInteger(nums1, target1)); // 0
        System.out.println(insertInteger(nums2, target2)); // 4

        //System.out.println(insertInteger2(nums, target)); // 3
        //System.out.println(insertInteger2(nums1, target1)); // 0
        //System.out.println(insertInteger2(nums2, target2)); // 4

    }

    // n is size(a)
    // time complexity in terms of n
    public static int insertInteger(int[] nums, int target){
        int n = nums.length;
        List<Integer> b = new ArrayList<>();
        int i = 0;
        while(i < n) { // n^2
            int j = i;
            while (j < n && i <= j) {
                b.add(nums[i] + nums[j]);
                //System.out.println(b[k] + " nums[i] "+nums[i] + "nums[j]" + nums[j]);
                j++;
            }
            i++;
        }
        // size of collection b - m
        Collections.sort(b); // m log m

        // m = n(n-2) = n^2 log (n^2)

        for(int x = 0; x < b.size(); x++){ // n^2
            if(b.get(x)>=target){
                return x;
            }
        }
        return b.size() - 1;
    }

    // optimization - didnt work
    public static int insertInteger2(int[] nums, int target){
        int n = nums.length;
        List<Integer> b = new ArrayList<>();
        Arrays.sort(nums);
        int i = 0;


        while(i < n){ // n^2
            int j = i;
            while(j < n && i<=j){
                int y = nums[i] + nums[j];
                if(y >= target){
                    return b.size() - 1;
                }
                b.add(y);
                j++;
            }
            i++;
        }
        //Collections.sort(b); // m log m

        // m = n(n-2) = n^2 log (n^2)

        for(int x = 0; x < b.size(); x++){ // n^2
            if(b.get(x)>=target){
                return x;
            }
        }
        return b.size() - 1;
    }
}





