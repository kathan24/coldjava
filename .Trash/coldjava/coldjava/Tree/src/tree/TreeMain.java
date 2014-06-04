package tree;

public class TreeMain 
{
	public static void main(String[] args) 
	{
		BinarySearchTree bst = new BinarySearchTree(); 
		bst.add(5);
		bst.add(2);
		bst.add(12);
		bst.add(-4);
		bst.add(3);
		bst.add(9);
		bst.add(21);
		bst.add(19);
		bst.add(25);
		System.out.println(bst.search(5));
		bst.remove(5);
		System.out.println(bst.search(5));
	}
}
/*
 
  			5
  	   2		12
  	-4  3	  9     21
  	 		      19 25
*/