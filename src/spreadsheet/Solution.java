package spreadsheet;
/*
 * Click `Run` to execute the snippet below!


// Your task is to create a representation of a spreadsheet:
// - a spreadsheet should have a fixed number of columns (specified as string-names at creation)
// - a spreadsheet should have infinitely many rows (numbered, starting at 0)
// - each cell contains a number, defaulting to 0
// - you should be able to:
//   - set the value of a cell
//   - get the value of a cell
//   - print out the first N rows of the spreadsheet (don’t worry too much about making this pretty)

// Please add the capability to set a cell to a formula referencing other cells. An example formula might be stated as "column A, row 1 plus column B, row 2". You can assume that a simple "plus" is the only operation we need to support and that the row and column of the two referenced cells are each passed as separate arguments.
// +------+--------+--------+--------+
// |      | "fips" | "pop"  | "area" |
// +======+========+========+========+
// | 0    | 1001   | 200000 | 5000   |
// +------+--------+--------+--------+
// | 1    | 0      | 0      | 0      |
// +------+--------+--------+--------+
// | 2    | 0      | 0      | 0      |
// +------+--------+--------+--------+
// | 3    | 0      | 0      | 0      |
// +------+--------+--------+--------+
// | 4    | 0      | 0      | 0      |
// +------+--------+--------+--------+
// | 5    | 1002   | 0      | 0      |
// +------+--------+--------+--------+
// | ...  | ...    | ...    | ...    |
*/

import java.util.*;

public class Solution {
    List<int[]> spreadsheet;
    int noOfColumns;
    String[] columns;
    Map<int[], int[][]> equation;
    Map<String, Integer> colMap;
    Solution(String[] columns) {
        this.columns = columns;
        equation = new HashMap<>();
        spreadsheet = new ArrayList<>();
        colMap = new HashMap<>();
        noOfColumns = columns.length;

        for(int i=0; i<noOfColumns; i++){
            colMap.put(columns[i], i);
        }
    }
    void setEquation(int row, String column, int[][] coordinates){
        set(row, column, 0);
        equation.put(new int[]{row, colMap.get(column)}, coordinates);
    }

    void set(int row, String column, int value) {
        if(spreadsheet.size() < row){
            int currRow = spreadsheet.size();
            for(int i = currRow; i<=row; i++){
                int[] newRow = new int[noOfColumns];
                spreadsheet.add(newRow);
            }
        } else {
            int[] newRow = new int[noOfColumns];
            spreadsheet.add(newRow);
        }
        spreadsheet.get(row)[colMap.get(column)] = value;
    }

    int get(int row, String column) {
        return spreadsheet.get(row)[colMap.get(column)];
    }

    void printFirstN(int n) {
        System.out.println("print " + n + " rows");
        for(String s:columns){
            System.out.print(" " + s);
        }
        System.out.print('\n');
        // for every cell, check the equation map to see if there is a valid equation, calculate that value, print it
        int rowsToPrint = Math.min(n, spreadsheet.size());
        for(int i=0; i<rowsToPrint; i++){
            int[] row = spreadsheet.get(i);
            for(int rowVal : row){
                System.out.print(rowVal + " ");
            }
            System.out.print('\n');
        }
    }

    public static void main(String[] args) {
        Solution my_spreadsheet = new Solution(new String[] {"fips", "pop", "area"});
        my_spreadsheet.set(0, "fips", 1001);
        my_spreadsheet.set(0, "pop", 200000);
        my_spreadsheet.set(0, "area", 5000);
        my_spreadsheet.set(5, "fips", 1002);
        System.out.println("get value " + my_spreadsheet.get(0, "fips"));
        System.out.println("get value " + my_spreadsheet.get(0, "area"));
        System.out.println("get value " + my_spreadsheet.get(1, "fips"));
        System.out.println("get value " + my_spreadsheet.get(1, "area"));
        System.out.println("get value " + my_spreadsheet.get(5, "fips"));
        System.out.println("get value " + my_spreadsheet.get(5, "area"));
        my_spreadsheet.printFirstN(10);
    }
}

// +------+--------+--------+--------+
// |      | "fips" | "pop"  | "area" |
// +======+========+========+========+
// | 0    | 1001   | 200000 | 5000   |
// +------+--------+--------+--------+
// | 1    | 0      | 0      | 0      |
// +------+--------+--------+--------+
// | 2    | 0      | 0      | 0      |
// +------+--------+--------+--------+
// | 3    | 0      | 0      | 0      |
// +------+--------+--------+--------+
// | 4    | 0      | 0      | 0      |
// +------+--------+--------+--------+
// | 5    | 1002   | 0      | 0      |
// +------+--------+--------+--------+
// | ...  | ...    | ...    | ...    |