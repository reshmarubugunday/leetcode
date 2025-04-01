package minimumimbalance;
/*
Amazon OA
Code Question 2

A customer ordered n items from Amazon’s online shopping app. Each item has a fragility value which denotes
how easily it can be damaged. Only n-k items can be shipped in the first transit and the remaining k items
will be shipped in the second transit. The imbalance in n-k items is defined as the maximum difference between
the fragility values of any two consecutive items after sorting them in the ascending order of their fragility
values.

Given the fragility values of n items and the value k, find the n-k items with the smallest imbalance possible.
Return the imbalance in those items.

Example:

fragilityValues = [1, 6, 5, 4, 3]
k = 2

Some possible scenarios are:
	•	Ship 2nd, 3rd, and 5th items in the first transit.
Their fragility values in increasing order will be [3, 5, 6], hence the imbalance will be 5 - 3 = 2.
	•	Ship 1st, 4th and 5th items in the first transit.
Their fragility values in increasing order will be [1, 3, 4], hence the imbalance will be 3 - 1 = 2.
	•	Ship 3rd, 4th and 5th items in the first transit.
Their fragility values in increasing order will be [3, 4, 5], hence the imbalance will be 4 - 3 or 5 - 4 = 1.

There are some more cases, however, it can be proven that the minimum imbalance you can get is 1.

Complete the function findMinimumImbalance in the editor below.
int findMinimumImbalance(int k, int[] fragilityValues)
Parameters:
	•	int k: the number of items to be shipped in the second transit
	•	int[] fragilityValues: fragility values of each item

Returns:
	•	int: the minimum imbalance in n-k items

Constraints:
	•	3 ≤ n ≤ 3 * 10^5
	•	1 ≤ k ≤ n - 2
	•	1 ≤ fragilityValues[i] ≤ 10^9

Sample test case 0
input
k = 1
fragilityValues = [1, 6, 5, 4]

output
1

Explanation:
	•	The imbalance in items having fragility values [4, 5, 6] is 1.
	•	The imbalance in items having fragility values [1, 4, 5] is 3.
	•	The imbalance in items having fragility values [1, 4, 6] is 3.
	•	The imbalance in items having fragility values [1, 5, 6] is 4.
First ship items having fragility values [4, 5, 6].

Sample test case 1
input
k = 2
fragilityValues = [1, 4, 11, 8]

output
3

Explanation:

One optimal way is to ship items having fragility values [1, 4].

*/
import java.util.*;

public class Solution {
    public static int findMinimumImbalance(int k, List<Integer> fragilityValues) {
        // Create a copy to avoid modifying the original list
        List<Integer> sortedList = new ArrayList<>(fragilityValues);
        Collections.sort(sortedList);

        int n = sortedList.size();
        int m = n - k;         // Number of items in the first transit
        int windowSize = m - 1;  // There will be m - 1 differences in a block of m items

        // Compute the differences between consecutive elements in the sorted list.
        List<Integer> diffs = new ArrayList<>(n - 1);
        for (int i = 0; i < n - 1; i++) {
            diffs.add(sortedList.get(i + 1) - sortedList.get(i));
        }

        // Use a deque to efficiently compute the maximum of each sliding window in diffs.
        Deque<Integer> deque = new LinkedList<>();

        // Process the first window of size windowSize.
        for (int i = 0; i < windowSize; i++) {
            while (!deque.isEmpty() && diffs.get(deque.peekLast()) <= diffs.get(i)) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }

        int best = Integer.MAX_VALUE;
        // Slide the window across the diffs list.
        // There are (diffs.size() - windowSize + 1) windows to consider.
        for (int i = 0; i <= diffs.size() - windowSize; i++) {
            // The maximum difference in the current window is at the front of the deque.
            int currentMax = diffs.get(deque.peekFirst());
            best = Math.min(best, currentMax);

            // Remove indices that are out of the current window.
            if (!deque.isEmpty() && deque.peekFirst() == i) {
                deque.pollFirst();
            }

            // Add the next element into the window, if it exists.
            int nextIndex = i + windowSize;
            if (nextIndex < diffs.size()) {
                while (!deque.isEmpty() && diffs.get(deque.peekLast()) <= diffs.get(nextIndex)) {
                    deque.pollLast();
                }
                deque.offerLast(nextIndex);
            }
        }

        return best;
    }

    // Example usage:
    public static void main(String[] args) {
        // Example 1:
        List<Integer> fragilityValues1 = Arrays.asList(1, 6, 5, 4, 3);
        int k1 = 2;
        // Expected imbalance is 1 (choosing items with fragility [3, 4, 5]).
        System.out.println("Minimum Imbalance: " + findMinimumImbalance(k1, fragilityValues1));

        // Example 2:
        List<Integer> fragilityValues2 = Arrays.asList(1, 6, 5, 4);
        int k2 = 1;
        // For sorted values [1, 4, 5, 6] and m = 3, the best block is [4, 5, 6] with imbalance 1.
        System.out.println("Minimum Imbalance: " + findMinimumImbalance(k2, fragilityValues2));

        // Example 3:
        List<Integer> fragilityValues3 = Arrays.asList(1, 4, 11, 8);
        int k3 = 2;
        System.out.println("Minimum Imbalance: " + findMinimumImbalance(k3, fragilityValues3));
    }

}
