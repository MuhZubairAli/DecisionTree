import java.util.ArrayList;
import java.util.Stack;
public class Tree {
	Node root;
	
	public Tree(int col) {
		root = new Node(false,col,Data.Column[col],-999);
		root.level = 0;
	}

	Node currentParent(){
		Node parent = root;
		while(parent.child != null){
			for(int i=0; i< parent.child.length; i++){
				if(!parent.child[i].foundLeaf){
					parent  = parent.child[i];
					break;
				}
			}
		}
		
		///System.out.println(" - Current Parent: " + parent.toString());
		
		return parent;
	}
	
	public Node getLeaf(int[] query){
		//System.out.println("\n->Query values");
		//Debug.printArr(query);
		
		Node current = root;
		current = current.child[query[current.col]];
		int qps = query.length;		//query processing status
		try{
			while(!current.child[0].isLeaf && qps >=0){
				qps--;
				if(query[current.child[0].col] == -999)
					continue;
				current = current.child[query[current.child[0].col]];
			}
		}catch(ArrayIndexOutOfBoundsException e){
			current.child[0] = new Node(true,0,"Could not resolve the query",-999);
		}catch(Exception e){
			current.child[0] = new Node(true,0,"Could not resolve the query",-999);
		}
		return current;
	}
	
	public void addLeaf(Attribute leaf){
		Node parent = currentParent();
		Node[] kid = {new Node(true,true,Data.Column.length-1,leaf.val,Data.Table[Data.Column.length-1][leaf.val],parent)};
		parent.child = kid;
		
		///System.out.println("From addLeaf - kid: " + parent.child[0].toString());
		
		foundLeaf(parent.child[0]);
	}
	
	public void addChilds(int col,int[] childs){
		Node parent = currentParent();
		
			Node[] children = new Node[childs.length];
			for(int i=0; i <childs.length; i++){
				children[i] = new Node(false,col,childs[i],Data.Table[col][childs[i]],parent);
				//System.out.println(children[i].toString());
			}
			parent.child = children;
	}
	
	private void printNode(Node node){
		if(node.level==0){
			System.out.print(node.title+"\n");
			return;
		}
		String icon = node.isLeaf ? "->" : "=";
		for(int i=0; i<node.level; i++){
			if(i+1==node.level)
				System.out.print("\t"+"|["+Data.Column[node.col]+"] "+icon+node.title);
			else
				System.out.print("\t|");
		}
		System.out.println();
	}

	public void showTree(){
		Stack<Node> openSet = new Stack<Node>();
		openSet.add(root);
		while(openSet.size() > 0){
			Node current = openSet.pop();
			printNode(current);
			if(current.child != null){
				for(int i=0; i < current.child.length; i++){
					current.child[i].level = current.child[i].parent.level + 1;
					openSet.push(current.child[i]);
				}
			}
		}
		
	}
	
	public void printTree(){
		ArrayList<Node> openSet = new ArrayList<Node>();
		openSet.add(root);
		while(openSet.size() > 0){
			Node current = openSet.remove(0);
			System.out.println(current.toString() + "\n");
			if(current.child != null){
				for(int i=0; i < current.child.length; i++){
					openSet.add(current.child[i]);
				}
			}
		}
		
	}
	
	void foundLeaf(Node leafNode){
		Node parent = leafNode.parent;
		while(checkChildFLS(parent)){
			parent.foundLeaf = true;
			if(parent.parent != null)
				parent = parent.parent;
			else
				break;
		}		
	}
	
	//checks if all children got foundLeaf = true status
	boolean checkChildFLS(Node parent){
		boolean flag = true;
		
		//System.out.println("checkChildFLS: " + parent.toString());
		
		for(int i=0; i < parent.child.length; i++){
			if(!parent.child[i].foundLeaf){
				flag = false;
				break;
			}
		}
		return flag;
	}
}
