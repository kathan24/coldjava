package linkedlist;

public class LinkedList 
{
	Integer data;
	LinkedList next;
	
	LinkedList(Integer a)
	{
		this.data = a;
		this.next = null;
	}
		
	public static LinkedList insert(LinkedList head, Integer a)			// insert element
	{
		if(head == null)
		{
			head = new LinkedList(a);
			return head;
		}
		else
		{
			LinkedList temp = new LinkedList(a);
			temp.next = head;
			head = temp;
			return head;
		}
		
	}
	
	public static LinkedList delete(LinkedList head, Integer a)					// delete the first occurance of the element
	{
		if(head.data == a)
		{
			head = head.next;
			return head;
		}
		while(head.next!=null)
		{
			if(head.next.data == a )
			{
				head.next = head.next.next;
				return head;
			}
			head = head.next;
		}
		return head;
	}
	public static void printLinkedList(LinkedList head)
	{
		if(head == null)
		{
			System.out.println("List is empty");
		}
		else
		{
			while(head.next!=null)
			{
				System.out.println("Data = "+head.data);
				head = head.next;
			}
			System.out.println("Data = "+head.data);
		}
	}
}
