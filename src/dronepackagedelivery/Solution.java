package dronepackagedelivery;
import java.util.*;
/*
Amazon OA
Minimum Replacements for Drone Delivery

In Amazon’s distribution network, there are several drones with varying capacities, ranging from 1 to 10⁹.
Each j-th drone has a carrying capacity of j. The company needs to dispatch n packages, where the weight of
the i-th package is given by pack[i].

Delivery Constraint:

During peak delivery times, only two drones are available to transport the packages,
and they must alternate in their duties.
This means:
	•	If Drone 1 handles the 1st package,
	•	Drone 2 must handle the 2nd package,
	•	Then Drone 1 handles the 3rd package,
	•	And so on…

Challenge:

However, some package weights may be too heavy for either of the two drones. To address this,
Amazon is allowed to replace certain packages with others of a different weight to ensure all packages
are deliverable by the two chosen drones.

Your Task:

Determine the minimum number of package replacements needed so that the two drones (with any two chosen capacities)
can successfully deliver all the packages by alternating turns.

Function:
int findMinReplacements(List<Integer> pack)
Input:
	•	pack: A list of integers representing the weights of the packages.

Output:
	•	Return an integer — the minimum number of replacements needed.

Constraints:
	•	2 ≤ n ≤ 2 × 10⁵
	•	n is even
	•	1 ≤ pack[i] ≤ 10⁹

Example 1:
n = 4
pack = [1, 1, 1, 1]
Explanation:
Drones must alternate. But all packages are weight 1, which means the two drones would need the same
capacity — not allowed.
We can change package 1 and 3 to weight 2, so the sequence becomes [2, 1, 2, 1].
Now, drones with capacities 1 and 2 can alternate.
Answer: 2 replacements needed.

Example 2:
n = 4
pack = [3, 1, 3, 2]
Explanation:
Replace the 4th package (weight 2) with weight 1, so the sequence becomes [3, 1, 3, 1].
Now, drones with capacities 3 and 1 can handle the alternating pattern.
Answer: 1 replacement needed.

*/
public class Solution {
    public static int findMinReplacements(List<Integer> pack) {
        int n = pack.size();
        Map<Integer, Integer> freqOdd = new HashMap<>();
        Map<Integer, Integer> freqEven = new HashMap<>();

        // Count frequency at even and odd indices
        for (int i = 0; i < n; i++) {
            int weight = pack.get(i);
            if (i % 2 == 0) {
                freqEven.put(weight, freqEven.getOrDefault(weight, 0) + 1);
            } else {
                freqOdd.put(weight, freqOdd.getOrDefault(weight, 0) + 1);
            }
        }

        // Get the most frequent (and second most frequent) weights
        List<Map.Entry<Integer, Integer>> evenList = new ArrayList<>(freqEven.entrySet());
        List<Map.Entry<Integer, Integer>> oddList = new ArrayList<>(freqOdd.entrySet());

        evenList.sort((a, b) -> b.getValue() - a.getValue());
        oddList.sort((a, b) -> b.getValue() - a.getValue());

        int evenMaxFreq = evenList.get(0).getValue();
        int evenMaxWeight = evenList.get(0).getKey();
        int evenSecondFreq = evenList.size() > 1 ? evenList.get(1).getValue() : 0;

        int oddMaxFreq = oddList.get(0).getValue();
        int oddMaxWeight = oddList.get(0).getKey();
        int oddSecondFreq = oddList.size() > 1 ? oddList.get(1).getValue() : 0;

        // If most frequent weights differ, use them directly
        if (evenMaxWeight != oddMaxWeight) {
            return n - evenMaxFreq - oddMaxFreq;
        } else {
            // Otherwise, try second-best alternative
            int replaceWithEvenSecond = n - evenSecondFreq - oddMaxFreq;
            int replaceWithOddSecond = n - evenMaxFreq - oddSecondFreq;
            return Math.min(replaceWithEvenSecond, replaceWithOddSecond);
        }
    }

    public static void main(String[] args) {
        List<Integer> pack1 = List.of(1,1,1,1);
        List<Integer> pack2 = List.of(1,1,1,1);

        System.out.println(findMinReplacements(pack1));
        System.out.println(findMinReplacements(pack2));
    }
}