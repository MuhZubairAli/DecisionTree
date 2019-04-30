import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DecisionTree {		
	public static void main(String[] args){
		final String data_src_path = "data_sources/vehicle_selection_data.csv";
		final String test_data = "data_sources/vehicle_selection_test_data.csv";
		TrainingDS.readData(data_src_path);
		/*
		for(String[] arr : Data.Table){
			for(String datum : arr){
				System.out.print(datum+"\t");
			}
			System.out.println();
		}
		//*/
		
		DTGenerator dt = new DTGenerator(TrainingDS.ds);
		
		Tree DecisionTreeStructure = dt.GenerateTree();
		DecisionTreeStructure.showTree();
		System.out.println("\n\n--------------------------------------------------\n");
		
		int[][] queries = buildQueries(readToArray(test_data));
		//Debug.printArr(queries);
		//*
		if(queries == null)
			return;
		for(int[] query : queries){
			printQuery(query);
			
			Node leaf = DecisionTreeStructure.getLeaf(query);
			
			System.out.print("\nResult: ");
			System.out.print(leaf.child[0].title); 
			System.out.println("\n--------");
		}
		//*/
		
		Scanner input = new Scanner(System.in);
		int quit = input.nextInt();
		quit++;
	}
	
	public static int[][] buildQueries(ArrayList<String[]> data){
		if(data == null)
			return null;
		int nCols = data.get(0).length;
		int[][] queries = new int[data.size()][nCols];
		for(int i=0; i<data.size(); i++){
			for(int j=0; j<nCols; j++){
				queries[i][j] = Data.getIndex(j, data.get(i)[j]);
			}
		}
		return queries;
	}
	
	public static ArrayList<String[]> readToArray(String src){
		ArrayList<String[]> data = new ArrayList<String[]>();
		File file = null;
		Scanner scanner = null;
		try{
			file = new File(src);
			scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				data.add(scanner.nextLine().split(TrainingDS.SP));
			}
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
			
		if(file!=null)
			file = null;
		if(scanner!=null)
			scanner = null;
		if(data.size()>0)
			return data;
		else
			return null;
	}
	
	public static void printQuery(int[] query){
		System.out.print("Current Query : ");
		Debug.printArr(query);
		System.out.print("\nQuery Values : ");
		for(int i=0; i<query.length; i++){
			if(query[i]==-999){
				System.out.print("No Val\t");
				continue;
			}
				System.out.print(Data.Table[i][query[i]] + "\t");
		}
	}
}
