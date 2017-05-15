package Practice;

public class Numbers {
	
	public static void main(String[] args){
		
		int x=680142203;
		int y=1111953568;
		Numbers obj=new Numbers();
		int dist = obj.hammingDistance2(x, y);
		System.out.println(dist);
		
	}
	public int hammingDistance(int x, int y){
		String strx = String.format("%32s", Integer.toBinaryString(x)).replace(' ', '0');
		String stry = String.format("%32s", Integer.toBinaryString(y)).replace(' ', '0');
		System.out.println(strx);
		System.out.println(stry);
		char[] sx = strx.toCharArray();
		char[] sy = stry.toCharArray();
		int dist=0;
		if(sx.length==sy.length){
			for(int i=0; i<sx.length;i++){
				if(sx[i]!=sy[i])
					dist++;
			}
		}
		else
			System.out.println("Cannot calculate the hamming distance");
		
		return dist;
	
	}
	public int hammingDistance1(int x, int y){
		
		int dist=0;
		for(int i=0; i<32; i++){
			int xor = 1<<i;
			//System.out.println(xor);
			//System.out.println(x^xor);
			if((x&xor)!=(y&xor)){
				dist++;
			}
		}
		return dist;
	}
	public int hammingDistance2(int x, int y){
		return Integer.bitCount(x ^ y);
	}
}
