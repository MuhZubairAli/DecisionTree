import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class TrainingDS {
	public static int ds[][] = null;
	public static ArrayList<Integer[]> rawData = new ArrayList<Integer[]>();
	public static ArrayList<ArrayList<String>> meta = new ArrayList<ArrayList<String>>();
	public static String SP = ",";

	public static void readData(String data_src_path){
		File data_src = new File(data_src_path);
		Scanner reader = null;
		try{
			reader = new Scanner(data_src);
			if(reader != null){
				Data.Column = reader.nextLine().split(SP);
				for(int i=0; i<Data.Column.length; i++){
					TrainingDS.meta.add(new ArrayList<String>());
				}
				String[] dt;
				int metaIndex = 0;
				int dataRow = 0;
				while(reader.hasNextLine()){
					TrainingDS.rawData.add(new Integer[Data.Column.length]);
					dataRow = TrainingDS.rawData.size()-1;
					dt = reader.nextLine().split(SP);
					for(int i=0; i<dt.length; i++){
						metaIndex = TrainingDS.meta.get(i).indexOf(dt[i]);
						if(metaIndex!=-1){
							//Already have this meta data
							TrainingDS.rawData.get(dataRow)[i] = metaIndex;
						}else {
							//New meta datum
							TrainingDS.meta.get(i).add(dt[i]);
							metaIndex = TrainingDS.meta.get(i).size() -1;
							TrainingDS.rawData.get(dataRow)[i] = metaIndex;
						}
					}
				}
				
				// Initializing Arrays
				int dsRows = TrainingDS.rawData.size();
				TrainingDS.ds = new int[dsRows][];
				for(int i=0; i<TrainingDS.rawData.size(); i++){
					int[] row = new int[TrainingDS.rawData.get(i).length];
					for(int j=0; j<TrainingDS.rawData.get(i).length; j++)
						row[j] = TrainingDS.rawData.get(i)[j];
					TrainingDS.ds[i] = row;
				}
				int metaRows = TrainingDS.meta.size();
				Data.Table = new String[metaRows][];
				for(int i=0; i<metaRows; i++){
					String[] row = new String[TrainingDS.meta.get(i).size()];
					for(int j=0; j<TrainingDS.meta.get(i).size(); j++){
						row[j] = TrainingDS.meta.get(i).get(j);
					}
					Data.Table[i] = row;
				}
			
				/*
			
				System.out.println(TrainingDS.meta.toString());
				for(int i=0; i<TrainingDS.rawData.size(); i++){
					for(int j=0; j<TrainingDS.rawData.get(i).length; j++)
						System.out.print(TrainingDS.rawData.get(i)[j].toString()+"\t");
					System.out.println();
				}
				
				*/
				
				//Destroying Objects
				TrainingDS.rawData = null;
				TrainingDS.meta = null;
			}
			
		}catch (IOException ioe){
			ioe.printStackTrace();
		}finally{
			if(data_src != null)
				data_src = null;
			if(reader != null)
				reader = null;
		}
	}
}
