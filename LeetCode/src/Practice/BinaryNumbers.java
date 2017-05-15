package Practice;

public class BinaryNumbers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num=5;
		BinaryNumbers obj = new BinaryNumbers();
		System.out.println(obj.complement(num));
		

	}
	public int complement(int x){
		  
		System.out.println(Integer.highestOneBit(x));
		System.out.println((Integer.highestOneBit(x) << 1));
		int ones = (Integer.highestOneBit(x) << 1) - 1;
		System.out.println(ones);
		return x ^ ones;
	}

}
