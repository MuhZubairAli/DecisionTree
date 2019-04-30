import java.util.Stack;

public class DTGenerator {
	Dataset data;
	
	public DTGenerator(int[][] _ds){
		data = new Dataset(_ds);
	}
	
	public void changeDS(int[][] _ds){
		this.data = new Dataset(_ds);
	}
	
	public Tree GenerateTree(){
		/*
		data.printDS();
		System.out.println("----------------------------------------------------------------------------------");
		*/
		
		Stack<Dataset> toProcess = new Stack<Dataset>();
		int[][] ds = data.dataset;
		float[] gain = new float[ds[0].length-1];
		gain = CalculateInfoGain(ds);
		int withHighestGain = getHighestGain(gain);
		Tree DTree = new Tree(withHighestGain);		
		int[][] uAtr = countAttribs(withHighestGain,ds);
		int[] childNodes = new int[uAtr[uAtr.length -1][0]];
		
		for(int i=0; i < uAtr[uAtr.length -1][0]; i++){
			childNodes[i] = uAtr[i][0];
		}
		
		DTree.addChilds(withHighestGain, childNodes);
		for(int i=uAtr[uAtr.length -1][0]-1; i>-1 ; i--){	
			Attribute atr = new Attribute(withHighestGain,uAtr[i][0]);
			float entropy = attributeEntropy(ds,atr.col,atr.val);
			int[][] newDs = trimDataset(withHighestGain,uAtr[i][0],ds);
			Dataset tempDS = new Dataset(newDs,atr,entropy);
			toProcess.push(tempDS);
			
			///System.out.println("Pushing to toProcess: " + tempDS.toString());
		}
		
		while(toProcess.size() >0){

			///for(int i=0; i<toProcess.size(); i++){
			///	System.out.println(i + " -> toProcess: " + toProcess.get(i).attrib.getName());
			///	Debug.printArr(toProcess.get(i).dataset);
			///}System.out.println("---------");
			
			Dataset currentDS = toProcess.pop();
			///System.out.println("Poped from toProcess: " + currentDS.toString());
			
			if(currentDS.entropy == 0.0f){
				Attribute leafNode = getLeaf(currentDS.dataset,currentDS.attrib.col,currentDS.attrib.val);
				DTree.addLeaf(leafNode);
			}
			else {
				int[][] aDS = currentDS.dataset;
				float[] infoGain = new float[aDS[0].length -1];				
				infoGain = CalculateInfoGain(aDS);
				withHighestGain = getHighestGain(infoGain);				
				uAtr = countAttribs(withHighestGain,aDS);
				int[] children = new int[uAtr[uAtr.length -1][0]];
				
				for(int i=0; i < uAtr[uAtr.length -1][0]; i++){
					children[i] = uAtr[i][0];
				}
				
				//if(currentDS.attrib.getName() == "Female"){
					//System.out.println("----------Dataset----------");
					//Debug.printArr(aDS);
					///System.out.println("----------InfoGain----------");
					///Debug.printArr(infoGain);
				//}
				
				for(int i=uAtr[uAtr.length -1][0]-1; i>-1 ; i--){
					Attribute atr = new Attribute(withHighestGain,uAtr[i][0]);
					float entropy = attributeEntropy(aDS,atr.col,atr.val);
					int[][] newDs = trimDataset(withHighestGain,uAtr[i][0],aDS);
					Dataset tempDS = new Dataset(newDs,atr,entropy);
					toProcess.push(tempDS);
					
					///System.out.println("Pushing to toProcess: " + tempDS.toString());
				}
				DTree.addChilds(withHighestGain,children);
			}
		}		
		///DTree.printTree();
		return DTree;
	}

	private Attribute getLeaf(int[][] ds,int col,int aV){
		int indx = 0;
		for(int i=0; i<ds.length; i++){
			if(ds[i][col] == aV){
				indx = i;
				break;
			}
		}
		Attribute leaf =  new Attribute(ds[0].length-1,ds[indx][ds[0].length -1]);
		return leaf;
	}
	
	private int getHighestGain(float[] gain){
		float tmp = gain[0];
		int indx = 0;
		for(int i=1; i<gain.length; i++){
			if(gain[i] > tmp){
				tmp = gain[i];
				indx = i;
			}
		}
		return indx;
	}
	
	private int[][] trimDataset(int col,int aV,int[][] sds){
		int newRows = countOccurences(aV,col,sds);
		//System.out.println("Number of new rows " + newRows);
		int[][] newDS = new int[newRows][sds[0].length]; //new data set
		
		for(int i=0,x=0; i < sds.length; i++){
			if(aV == sds[i][col]){
				//copy columns
				for(int j=0; j < sds[0].length; j++){
					if(j==col)
						newDS[x][j] = -999;	//empty placeholder
					else
						newDS[x][j] = sds[i][j];
				}
				x++;
			}
		}
		
		return newDS;
	}
	
	public float[] CalculateInfoGain(int[][] dataSet){
		float classAntropy = calcClassEntropy(dataSet);	
		//System.out.println("Class Entropy = " + classAntropy + "\n------------------------------------");
		//Calculating expected (new) entropies and information gain
		float[] gain = new float[dataSet[0].length-1];
		for(int i=0; i<gain.length; i++){
			gain[i] = classAntropy - CalculateExpectedEntropy(i,dataSet);
		}		
		return gain;
	}
			
	//it will count unique attributes of given columns,it returns a 2d array which contains that attribute values and number of it's occurrences.
	private int[][] countAttribs(int col,int[][] sds){
		int b = 0;
		int[][] arr = new int[sds.length + 1][2];
		FillArray(arr); //fill with empty placeholder
		int pathIndex;
		for(int i=0; i<sds.length; i++){
			pathIndex = contains(arr,sds[i][col]);
			if(pathIndex != -999){
				arr[pathIndex][1]++;
			}
			else{
				arr[b][0] = sds[i][col];
				arr[b][1] = 1;
				b++;
			}
		}
		arr[sds.length][0] = b; //How many distinct attributes found
		return arr;
	}
	
	//it check weather the given array contains particular value the give array would have -999 as placeholder for empty elements and returns -999 if doesn't found else it would return index
	private int contains(int[][] arr,int val){
		for(int i=0; i<arr.length; i++){
			if(arr[i][0] == val)
				return i;
			else if(arr[i][0] == -999)
				return -999;
		}
		return -999;
	}
	
	public static boolean contains(int[] arr, int val){
		boolean contains = false;
		for(int i=0; i<arr.length;i++){
			if(arr[i] == val){
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	//fill 2d array with -999 as empty placeholder
	private void FillArray(int[][] arr){
		for(int i=0; i<arr.length; i++)
			for(int j=0; j<arr[i].length; j++)
				arr[i][j] = -999; 
	}
	
	//calculates entropy of a column
	public float CalculateExpectedEntropy(int col,int[][] sds){
		if(sds[0][col] == -999){
			return 10.0f;
		}
		int[][] uniqueAttributes = countAttribs(col,sds);
		int totalUniqueAttributes = uniqueAttributes[uniqueAttributes.length-1][0];
		float totalEntropy = 0.0f;
		float[] attribsEntropy = new float[totalUniqueAttributes]; //To keep the entropy of each attribute
		
		for(int i=0; i < totalUniqueAttributes ; i++){
			attribsEntropy[i] = attributeEntropy(sds,col,uniqueAttributes[i][0]);
		}

		for (int i=0; i<totalUniqueAttributes; i++){
			totalEntropy += (float)uniqueAttributes[i][1]/(float)sds.length * (attribsEntropy[i]);
		}
		return totalEntropy;
	}
	
	private int countOccurences(int aV,int col,int[][] sds){
		int occurenceCount = 0;
		for (int i = 0; i < sds.length; i++){
			if(sds[i][col] == aV)
				occurenceCount++;
		}
		return occurenceCount;
	}
		
	//Calculates entropy for specific attribute in specific column . takes that attribute value and column number and small dataSet as parameters
	//return entropy in float
	public float attributeEntropy(int[][] sds,int col,int aV){
		int total = countOccurences(aV,col,sds);
		float entropy = 0f;
		int clsCol = sds[0].length -1;
		int[][] clsAttrb = countAttribs(clsCol,sds);
		
		for(int i=0; i < clsAttrb[clsAttrb.length-1][0]; i++)
			clsAttrb[i][1] = 0;
		
		for(int i = 0; i <sds.length;i++){
			if(sds[i][col] == aV){
				clsAttrb[contains(clsAttrb,sds[i][clsCol])][1]++;
			}
		}
		
		for(int i=0; i < clsAttrb[clsAttrb.length -1][0]; i++){
			entropy += entropy(clsAttrb[i][1],total);
		}
		//System.out.println("\n******************\n ColVar: " + col + " - " + aV + "    --- Entropy = " + entropy + "\n******************\n");
		return entropy;
	}
	
	private float calcClassEntropy(int[][] sds){
		int noRows = sds.length;
		float entropy = 0f;
		int[][] uniqueAttributes = countAttribs(sds[0].length-1,sds);

		for(int i=0; i<uniqueAttributes[uniqueAttributes.length-1][0]; i++){
			entropy += entropy(uniqueAttributes[i][1],noRows);
		}
		//System.out.println(entropy);
		return entropy;
	}
	
	//Calculates Entropy of attribute
	private float entropy(int occurences, int total){
		if(occurences == 0)
			return 0;
		float ratio = (float) occurences/total;
		float rslt = ratio * ((float)(Math.log(ratio)) / (float)(Math.log(2)));
		//System.out.println(rslt);
		return -rslt;
	}
}
