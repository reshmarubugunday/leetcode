package Practice;

public class AddTwoNumbers {
	
	public class ListNode{
		int val;
		ListNode next;
		ListNode(int x){
			this.val = x;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddTwoNumbers obj1 = new AddTwoNumbers();
		ListNode n1 = obj1.new ListNode(2);
		ListNode n2 = obj1.new ListNode(4);
		ListNode n3 = obj1.new ListNode(3);
		n1.next=n2;
		n2.next=n3;
		n3.next=null;
		
		ListNode m1 = obj1.new ListNode(5);
		ListNode m2 = obj1.new ListNode(6);
		ListNode m3 = obj1.new ListNode(4);
		m1.next=m2;
		m2.next=m3;
		m3.next=null;
		
//		while(n1!=null){
//			System.out.print(n1.val+" ");
//			n1=n1.next;
//		}
//		
//		while(m1!=null){
//			System.out.print(m1.val+" ");
//			m1=m1.next;
//		}
		ListNode result= obj1.solution(n1,m1);
		//System.out.println(result.val);
		while(result!=null){
			System.out.print(result.val+" ");
			result=result.next;
		}

	}
	public ListNode solution(ListNode l1, ListNode l2){
		//System.out.println("l1"+l1);
		//System.out.println("l2"+l2);
		ListNode c1 = l1;
		ListNode c2 = l2;
		ListNode temp = new ListNode(0);
		ListNode res = temp;
		int sum=0;
		//System.out.println("Before while");
		while(c1!=null || c2!=null){
		//System.out.println("Inside while");
			sum = sum/10;
			//System.out.println("sum"+sum);
			if(c1!=null){
				sum+= c1.val;
				c1=c1.next;
			}
			if(c2!=null){
				sum+=c2.val;
				c2=c2.next;
			}
			res.next = new ListNode(sum%10);	
			//System.out.println(res.next.val);
			res=res.next;
		}
		if (sum / 10 == 1)
            res.next = new ListNode(1);
		return temp.next;
	}
	
}
