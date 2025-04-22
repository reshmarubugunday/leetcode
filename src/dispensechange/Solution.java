package dispensechange;
// Company: Bloomberg (On-site)
// Your old code in python3 has been preserved below.
// """
// Problem Description

// Write a function that prints out the minimum number of coins required to add up to the number of cents passed in. You may dispense any number of 1c, 5c, 10c, 25c coins to make up the minimum.

// For example:

// to dispense 17 cents with the least coins would take 1x 5c coin, 1x 10c coin, and 2x 1c coins.
// to dispense 128 cents with the least coins would take 5x 25c coins and 3x 1c coins.

// Sample inputs - Expected outputs

// 12  -> "1x 10c, 2x 1c"
// 68  -> "2x 25c, 1x 10c, 1x 5c, 3x 1c"
// 132 -> "5x 25c, 1x 5c, 2x 1c"
// 132 > 25 -> 132 / 25 - 5
// v= 132 % 25
// v / 10
// """

public class Solution {
    public static void main(String[] args){
        Solution s = new Solution();
        System.out.println(s.getMinCoins(12)); // 1x 10c, 2x 1c
        System.out.println(s.getMinCoins(68)); // 2x 25c, 1x 10c, 1x 5c, 3x 1c
        System.out.println(s.getMinCoins(132)); // 5x 25c, 1x 5c, 2x 1c
    }
    public String getMinCoins(int value){
        StringBuilder result = new StringBuilder();
        while(value != 0){
            if (value > 25){
                int temp = value/ 25;
                value = value % 25;
                if (temp > 0){
                    result.append(temp);
                    result.append("x 25c");
                }
            } else if (value > 10){
                int temp = value / 10;
                value = value % 10;
                if (temp > 0){
                    result.append(temp);
                    result.append("x 10c");
                }
            } else if (value > 5){
                int temp = value/5;
                value = value % 5;
                if (temp > 0){
                    result.append(temp);
                    result.append("x 5c");
                }
            } else {
                int temp = value/1;
                value = value % 1;
                result.append(temp);
                result.append("x 1c");
            }
            result.append(", ");
        }
        return result.toString();
    }
}