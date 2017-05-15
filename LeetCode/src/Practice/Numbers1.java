package Practice;

public class Numbers1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num=19;
		Numbers1 obj = new Numbers1();
		System.out.println(obj.sumOfDigits(num));

	}
	public int sumOfDigits(int num){
		 
//		int sum=0;
//		int temp=0;
//		while(n!=0){
//			sum+=n%10;
//			n=n/10;
//		}
//		if(sum>9){
//		    
//		}
//		return sum;

		return num==0?0:(num%9==0?9:(num%9));
	}

}
