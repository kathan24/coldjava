package tree;

public class BSTNode 
{
	int value;
	BSTNode left;
	BSTNode right;
	
	BSTNode(int value)
	{
		this.value = value;
		this.left = null;
		this.right = null;
	}
	
	public boolean add(int value)
	{
		if(this.value == value)
		{
			return false;
		}
		else if(value < this.value)
		{
			if(this.left == null)
			{
				this.left = new BSTNode(value);
				return true;
			}
			else
			{
				return this.left.add(value);
			}
		}
		else if(value > this.value)
		{
			if(this.right == null)
			{
				this.right = new BSTNode(value);
				return true;
			}
			else
			{
				return this.right.add(value);
			}
		}
		return false;
	}
	
	public boolean search(int value)
	{
		if(this.value == value)
		{
			return true;
		}
		else if(value < this.value)
		{
			if(this.left == null)
			{
				return false;
			}
			else
			{
				return this.left.search(value);
			}
		}
		else if(value > this.value)
		{
			if(this.right == null)
			{
				return false;
			}
			else 
			{
				return this.right.search(value);
			}
		}
		return false;
	}
	
	public boolean remove(int value, BSTNode parent)
	{
		if(value < this.value)
		{
			if(this.left != null)
			{
				return this.left.remove(value, this);
			}
			else
			{
				return false;
			}
		}
		else if(value > this.value)
		{
			if(this.right != null)
			{
				return this.right.remove(value, this);
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(this.left!=null && this.right!=null)				// node has two child
			{
				this.value = this.right.minValue();
				this.right.remove(this.value, this);
			}
			else if(this.left != null)							// node has only left child
			{
				if(parent.left == this)
				{
					parent.left = this.left;
				}
				else
				{
					parent.right = this.left;
				}
			}
			else if(this.right != null)							// node has only right child
			{
				if(parent.left == this)
				{
					parent.left = this.right;
					this.right = null;
				}
				else
				{
					parent.right = this.right;
					this.right = null;
				}
			}
			/*else if(parent.left == this)
			{
				parent.left = (this.left!=null) ? this.left : this.right;
			}
			else if(parent.right == this)
			{
				parent.right = (this.left!=null) ? this.left : this.right;
			}*/
			return true;
		}
	}
	
	public int minValue()
	{
		if(this.left == null)
		{
			return this.value;
		}
		else
		{
			return this.left.minValue();
		}
	}
}
