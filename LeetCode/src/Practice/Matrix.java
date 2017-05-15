package Practice;

import java.util.*;

public class Matrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] nums = {{1,2,3},{3,4,5},{1,2,3},{2,3,4}};
		Matrix obj = new Matrix();
		int r=6;
		int c=2;
		int[][] result = new int[r][c];
		result = obj.matrixReshape1(nums,r,c);
		for(int i=0; i<r; i++){
			for(int j=0;j<c;j++){
				System.out.print(result[i][j]);
			}
			System.out.println();
		}

	}
	public int[][] matrixReshape(int[][] nums, int r, int c){
		int row=nums.length;
		int col=nums[0].length;
		if(r*c==row*col){
			int[][] nums1=new int[r][c];
			Stack<Integer> s = new Stack<Integer>();
			for(int i=0;i<row;i++){
				for(int j=0; j<col;j++){
					s.push(nums[i][j]);
				}
			}
			for(int i=r-1;i>=0;i--){
				for(int j=c-1; j>=0;j--){
					nums1[i][j]=s.pop();
				}
			}
			return nums1;
						
		}
		else{
			System.out.println("Cannot be reshaped");
			return nums;
		}
	}
	public int[][] matrixReshape1(int[][] nums, int r, int c){
		int n = nums.length, m = nums[0].length;
	    if (r*c != n*m) return nums;
	    int[][] res = new int[r][c];
	    for (int i=0;i<r*c;i++){
	        res[i/c][i%c] = nums[i/m][i%m];
	    	System.out.print(i/c+" "+i%c);
	    	System.out.println();
	    	System.out.print(i/m+" "+i%m);
	    	System.out.println();
	    }
	    return res;
	}

}
