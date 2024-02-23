package nextgreaterelement;

public class Solution {
    // Doordash question about rounding to the next greater element
    public static String nextGreaterElement(String n){
        char[] ch = n.toCharArray();
        int i = ch.length - 2;
        // Traverse from the right to left to find a position where ch[i-1] < ch[i]
        while(i>=0 && ch[i] >= ch[i+1]){
            i--;
        }
        // if i = -1 return empty string
        if(i == -1){
            return "";
        }

        int j = ch.length - 1;
        // Traverse from the right to left to find a position where ch[i] < ch[j]
        while(ch[j] <= ch[i]){
                j--;
        }
        // swapping i and j
        swap(ch, i ,j);

        StringBuilder st = new StringBuilder();
        // append numbers from index 0 to i into st
        for(int k=0; k<=i; k++){
            st.append(ch[k]);
        }
        // append numbers from i+1 to ch.length - 1 in reverse order
        for(int k=ch.length-1; k>i; k--){
            st.append(ch[k]);
        }

        return st.toString();
    }

    public static void swap(char[] ch, int i, int j){
        char temp = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
    }
    public static void main(String[] args) {
        System.out.println("Input: 12, Expected: 21, Actual: "+ nextGreaterElement("12"));
        System.out.println("Input: 185, Expected: 518, Actual: "+ nextGreaterElement("185"));
        System.out.println("Input: 6537421, Expected: 6541237, Actual: "+ nextGreaterElement("6537421"));
        System.out.println("Input: 54321, Expected: , Actual: "+ nextGreaterElement("54321"));
    }
}
