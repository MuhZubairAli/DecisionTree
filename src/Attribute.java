
public class Attribute {
	public int col;
	public int val;
	
	public Attribute(int _col,int _val){
		col = _col;
		val = _val;
	}
	
	public String getName(){
		return Data.Table[col][val];
	}
}
