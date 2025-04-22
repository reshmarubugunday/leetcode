package pairsinarray;
// Company: Bloomberg (On-site)
// Problem Description

// Given an array of positive integers, find the closest pair. The closest pair has the smallest absolute value of the difference.

// Sample inputs - Expected outputs

// Input: {32,71,12,45,26,80,34,43}
// Output: {32,34}

import java.util.*;

public class Solution {
    public static void main(String[] args){
        Solution s = new Solution();
        int[] nums = new int[]{32,71,12,45,26,80,34,43};
        int[] res = s.getClosestPairs(nums); // {32, 34}
        for(int n : res){
            System.out.println(n);
        }
    }
    public int[] getClosestPairs(int[] nums){
        if(nums.length < 3){
            return nums;
        }

        Arrays.sort(nums);

        int minDiff = Integer.MAX_VALUE;
        int n1 = 0, n2 = 0;

        for(int i=0; i<nums.length - 1; i++){
            int diff = Math.abs(nums[i] - nums[i+1]);
            if(diff < minDiff){
                minDiff = diff;
                n1 = nums[i];
                n2 = nums[i+1];
            }
        }

        return new int[]{n1, n2};
    }
}
