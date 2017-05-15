package Practice;

public class Strings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "Let's take LeetCode contest";
		Strings obj = new Strings();
		String result = obj.reverseString(str);
		System.out.println(result);
		
	}
	public String reverseString(String str){
		String[] str1 = str.split(" ");
		String[] res = new String[str1.length];
		StringBuilder result = new StringBuilder();
		//String result=" ";
		String temp;
		for(int i=0; i<str1.length; i++){
			
			//System.out.println(str1[i]);
			temp=new StringBuffer(str1[i]).reverse().toString();
			//System.out.println(temp);
			res[i]=temp;
		}
		for (int i=0; i<res.length; i++){
			if(i+1==res.length){
				result.append(res[i]);
			}
			else{
				result.append(res[i]);
				result.append(" ");
			}
			
		}
		//System.out.println(result);
		return result.toString();
		
		
	}

}
