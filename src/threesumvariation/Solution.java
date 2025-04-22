package threesumvariation;

/*

Find the count of triplets which has a sum < than T
Eg:
1,2,3,4,5  T = 8
o/p: 4

Explanation:
1,2,3
1,2,4
1,3,4
1,2,5

*/
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findTriplets(8, new int[]{1, 2, 3, 4, 5}));
    }

    public long findTriplets(long t,int[] d) {
        // 1, 2, 3, 4, 5
        Arrays.sort(d);
        long count = 0;
        int n = d.length;
        for(int i=0; i<n;i++){ // i = 0
            int a = d[i]; // a = 1
            int j = i+1; // j 1
            int k = n-1; // 4
            while(j < k){ // 1<4
                if(a + d[j] + d[k] > t){ // 1+3+4
                    k--;
                } else {
                    count+= (long) k-j; // count = 3
                    j++; // j 2
                }
            }
        }
        return count;
    }
}

