package mergeArrays;

/*
Suppose we have two lists of signed integers in ascending order: a and b. Write a function that takes these two ordered lists as well as an integer max_result_length argument and returns a merged list with max_result_length elements in ascending order.

Example:

a = [1, 3, 5]
b = [2, 6, 8, 9]
max_result_length = 3

=> [1, 2, 3]
*/

/*
2 sorted Array a, b
result array's length = n

 */
/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
    public static void main(String[] args) {
        int[] a = {1};
        int[] b = {2, 6, 8, 9};
        int n = 3;

        int[] result = mergeArrays(a, b, n);

        int i = 0;
        while(i<result.length){
            System.out.println(result[i]);
            i++;
        }

    }

    public static int[] mergeArrays(int[] a, int[] b, int n){
        int sizea = a.length;
        int sizeb = b.length;
        int[] result = new int[n];
        int i=0;
        int j=0;
        int k=0;

        // compare sorted arrays and add smaller element into result
        while(i<sizea && j<sizeb && k<n){
            if(a[i] < b[j]){
                result[k] = a[i];
                i++;
                k++;
            } else {
                result[k] = b[j];
                j++;
                k++;
            }
        }

        // will cover the remaining of either arrays if the result array is not full yet
        while(i<sizea && k<n){
            result[k] = a[i];
            i++;
            k++;
        }
        while(j<sizeb && k<n){
            result[k] = b[j];
            j++;
            k++;
        }

        return result;

    }
}

