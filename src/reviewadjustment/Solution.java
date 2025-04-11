package reviewadjustment;
/*
Company: Amazon OA
Code Question 1

The marketing team at Amazon is experimenting with the number of reviews for each product.

You are given an array reviews of size n, where reviews[i] represents the number of reviews for the i-th product.
There are APIs available to add or remove reviews, where each API call can either add or remove one review.

You are also given an integer array counts of size q.
Your task is to calculate the number of API calls required to change the review count of each product to
match each value in the array counts.

Goal:

Return an array of size q, where the i-th element denotes the total number of API calls needed to change
the review count of all products to match counts[i].

Example:
input:

n = 5
reviews = [4, 6, 5, 2, 1]
q = 1
counts = [3]

To convert all values in reviews to 3:
	•	|4 - 3| = 1 → 1 removal
	•	|6 - 3| = 3 → 3 removals
	•	|5 - 3| = 2 → 2 removals
	•	|2 - 3| = 1 → 1 addition
	•	|1 - 3| = 2 → 2 additions

Total API calls: 1 + 3 + 2 + 1 + 2 = 9

output:
[9]

*/
import java.util.*;

public class Solution {
    public static List<Long> calculateApiCallsOptimized(List<Integer> reviews, List<Integer> counts) {
        List<Long> result = new ArrayList<>();
        int n = reviews.size();

        // Create a sorted copy of the reviews list
        List<Integer> sortedReviews = new ArrayList<>(reviews);
        Collections.sort(sortedReviews);

        // Build prefix sum array: prefix[i] is the sum of the first i sorted reviews
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + sortedReviews.get(i);
        }

        // Process each target count using binary search and prefix sums
        for (int target : counts) {
            // Find the insertion point for target using binary search
            int pos = Collections.binarySearch(sortedReviews, target);
            if (pos < 0) {
                pos = -(pos + 1);
            }

            int leftCount = pos;
            int rightCount = n - pos;
            long leftSum = prefix[pos];
            long rightSum = prefix[n] - prefix[pos];

            long apiCalls = (long) target * leftCount - leftSum
                    + rightSum - (long) target * rightCount;
            result.add(apiCalls);
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> reviews = List.of(4, 6, 5, 2, 1); // Given reviews array
        List<Integer> counts = List.of(3); // Target counts

        List<Long> apiCallsOptimized = calculateApiCallsOptimized(reviews, counts);

        // Print the optimized result
        System.out.println(apiCallsOptimized);
    }

}
