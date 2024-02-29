package printdiagonal;

/*
Print diagonals from right to left
A : [[9 3 2],
     [8 1 5],
     [2 1 3],
     [5 8 2]]

Print
9
3 8
2 1 2
5 1 5
3 8
2
 */
public class Solution {
    public static void main(String[] args) {
        int[][] arr = {{9,3,2}, {8,1,5}, {2,1,3}, {5,8,2}};
        printMatrix(arr);
    }

    public static void printMatrix(int[][] arr){
        int rows = arr.length;
        int cols = arr[0].length;

        for(int i=0; i<cols; i++){
            printHelper(arr, 0, i, cols);
            System.out.println(" ");
        }

        for(int i=1; i<rows; i++){
            printHelper(arr, i, cols-1, rows);
            System.out.println(" ");
        }
    }

    public static void printHelper(int[][] arr, int i, int j, int n){
        while(i<n && j>=0){
            System.out.print(arr[i][j] + " ");
            i++;
            j--;
        }
    }
}
