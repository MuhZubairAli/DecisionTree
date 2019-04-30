
public class Dataset {
	public int[][] dataset;
	public Attribute attrib;
	public float entropy;
	
	public Dataset(){
		dataset = null;
		attrib = null;
	}
	
	public Dataset(int[][] ds){
		dataset = ds;
		attrib = null;
	}
	
	public Dataset(int ds[][],Attribute atr,float _entropy){
		dataset = ds;
		attrib = atr;
		entropy = _entropy;
	}
	public String toString(){
		//Debug.printArr(dataset);
		String str = "";
		if(attrib != null)
			str += "Col: " + attrib.col + " Val: " + attrib.val + "  = " + attrib.getName();
		else
			str += "No ColVal";
		return str;
	}
	public void printDS(){
		for(int i=0; i<dataset.length;i++){
			for(int j=0; j<dataset[0].length; j++){
				//Print column names
				if(i==0){
					if(j==0){
						for(int x=0; x < Data.Column.length; x++)
							System.out.print(Data.Column[x] + "\t");
						System.out.println();
					}
				}
				System.out.print(Data.Table[j][dataset[i][j]] + "\t\t");						
			}
			System.out.println();
		}		
	}
}
