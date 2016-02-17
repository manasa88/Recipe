import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class GetInput {

	public void runTest() throws IOException {
		FileReader f = new FileReader("training_data.txt");
		BufferedReader br = new BufferedReader(f);
		String line;
		ArrayList<String> list= new ArrayList<String>();
		while((line = br.readLine())!= null){
				list.add(line);
		}
		/*for(int k=9;k<=14;k++)
		{
			System.out.println("for k value"+k);*/
		String text;
		int[][] result=new int[7][7];
		
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++)
			{
				result[i][j]=0;
			}
		int foldSize = (int) (list.size()/10);
		int correct=0;
		int incorrect=0;
		for (int k = 0; k < 10; k++) {
			
			System.out.println("for Round" + k);		

			ArrayList<String> trainingData = new ArrayList<String>();

			ArrayList<Integer> testCusines = new ArrayList<Integer>();
			ArrayList<String> testingData = new ArrayList<String>();
			int foldMin = k*foldSize;
			int foldMax = (k+1)*foldSize;

			//trainingData.addAll(list.subList(maxVal,list.size()-1));
			for (int i = 0; i < list.size(); i++) {
				if(i>=foldMin && i<=foldMax){
					testCusines.add(Character.getNumericValue(list.get(i).charAt(0)));
					testingData.add(list.get(i).substring(2));
				}
				else
					trainingData.add(list.get(i));
			}
		

			Main m=new Main();
			ArrayList<RecP> TrainingRecipes = m.readTrainingRecipes(trainingData);
			ArrayList<String> TestingRecipes = m.removeUncommonTest(testingData);
			HashMap<String, Integer> ingredients = m.ingredients;
			System.out.println("Size=" +TestingRecipes.size());
			Recipe objRec = new Recipe();
			int counterr=0;
			for (String t : TestingRecipes) {
				
				ArrayList<Neighbour> myNeighbours = objRec
						.getNearestNeighbours(t, TrainingRecipes, 10, ingredients);
				int predicted = objRec.getCusine(myNeighbours, 10);
				Integer actual = (Integer) testCusines.get(counterr);
				if(actual==predicted){
					correct++;
				}
				else{
					if(predicted==0){
						System.out.println("Actual: " +actual +"Predicted: " +predicted);
					}
					incorrect++;
					
				}
				result=updateMat(actual,predicted,result);
				counterr++;
			}
			System.out.println(((double)(100.00*correct/(correct+incorrect))));
			System.out.println("Matrix after this round");
			for (int[] i :result)
			{
				cleanPrint(i);
			}
			
		}
		for (int[] i :result)
		{
			cleanPrint(i);
		}
		System.out.println(((double)(100.00*correct/(correct+incorrect))));
		System.out.println("round completee");
	}
	 public static int[][] updateMat(int actual,int pred,int[][] result){
			
			result[actual-1][pred-1]++;	
			return result;
		}
		public static void cleanPrint(int[] row) {
			for (int i : row) {
				System.out.print(i);
				System.out.print("\t");
			}
			System.out.println();
		}
	}

//}
