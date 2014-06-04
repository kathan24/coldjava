package linkedlist;

public class Main 
{
	
	public static void main(String[] args) 
	{
		
		LinkedList head = null;
		head = LinkedList.insert(head, 20);
		head = LinkedList.insert(head,30);
		head = LinkedList.insert(head,40);
		head = LinkedList.insert(head,50);
		head = LinkedList.insert(head,60);
		head = LinkedList.insert(head,70);
		
		head = LinkedList.delete(head, 70);
		head = LinkedList.delete(head, 50);
		
		LinkedList.printLinkedList(head);
	}
}
