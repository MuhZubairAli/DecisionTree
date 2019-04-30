public class Data {
	public static String[][] Table = null;
	public static String[] Column = null;
	
	public static int getIndex(int col,String val){
		if(Table==null)
			return -999;
		for(int i=0; i<Table[col].length; i++){
			if(Table[col][i].equals(val))
				return i;
		}
		return -999;
	}
}
