
public class Debug {
	//For debugging only
	////////////////////
	public static void printArr(int[] arr){
		for(int i = 0; i<arr.length; i++)
			System.out.print(arr[i] + "\t");
	}
	
	public static void printArr(int[][] arr){
		for(int i = 0; i<arr.length; i++){
			for(int j=0; j<arr[i].length; j++){
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}
	}
	public static void printArr(float[][] arr){
		for(int i = 0; i<arr.length; i++){
			for(int j=0; j<arr[i].length; j++){
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}
	}
	public static void printArr(float[] arr){
		for(int i = 0; i<arr.length; i++)
			System.out.print(arr[i] + "\t");
	}	
}
