
public class Node {
	public boolean isLeaf;
	public boolean foundLeaf;
	public Node[] child;
	public Node parent;
	public String title;
	public int col;
	public int value;
	public int level;
	
	public Node(){
		foundLeaf = false;
		child = null;
		parent = null;
	}
	public Node(boolean _foundLeaf,boolean _isLeaf,int _col,int _value,String _title,Node _parent){
		this();
		isLeaf = _isLeaf;
		foundLeaf = _foundLeaf;
		col = _col;
		value = _value;
		title = _title;
		parent = _parent;
	}
	
	public Node(boolean _isLeaf,int _col,int _value,String _title,Node _parent){
		this();
		isLeaf = _isLeaf;
		col = _col;
		value = _value;
		title = _title;
		parent = _parent;
	}
	
	public Node(boolean _isLeaf,int _col,String _title,int val){
		this();
		isLeaf = _isLeaf;
		col = _col;
		value = val;
		title = _title;
		parent = null;
	}
	
	public Node(boolean _isLeaf,int _col,int _value,String _title,Node _parent,Node[] _children){
		this();
		isLeaf = _isLeaf;
		col = _col;
		value = _value;
		title = _title;
		child = _children;
		parent = _parent;
	}
	public String toString(){
		String str = "";
		if(foundLeaf)
			str = "[FL]";
		else
			str = "[NLF]";
		if (isLeaf)
			str += "(L)= " + title;
		else
			str += "(B)= " + title;
		if(parent != null)
			str += "\t Parent: " + parent.title;
		if (child != null)
			str += "\t ChildCount: " + child.length;
		else
			str += "\t No Children";
		return str;
		
	}
}
