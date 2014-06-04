package tree;

public class BinarySearchTree 
{
	BSTNode root;
	BinarySearchTree()
	{
		root = null;
	}
	
	public boolean add(int value)
	{
		if(root == null)
		{
			root = new BSTNode(value);
			return true;
		}
		else
		{
			return root.add(value);
		}
	}
	
	public boolean search(int value)
	{
		if( root == null)
		{
			return false;
		}
		else
		{
			return root.search(value);
		}
	}
	
	public boolean remove(int value)
	{
		if(root == null)
		{
			return false;
		}
		else if(root.value == value)
		{
			BSTNode auxRoot = new BSTNode(0);
			auxRoot.left = root;
			boolean result = root.remove(value,auxRoot);
			root = auxRoot.left;
			return result;
		}
		else
		{
			return root.remove(value,null);
		}
	}
}
