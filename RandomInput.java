import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class RandomInput {

	public void runTest() throws IOException {
		
		int[][] result = new int[7][7];

		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++) {
				result[i][j] = 0;
			}
		
		int correct = 0;
		int incorrect = 0;
		NumberFormat nf = NumberFormat.getNumberInstance();
		
		nf.setMaximumFractionDigits(2);
		String text;
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader in1 = new BufferedReader(new FileReader("training_data.txt"));
		while ((text = in1.readLine()) != null) {

			list.add(text);

		}
		
		int maxVal= list.size()-1;
		for (int k = 0; k < 10; k++) {
			System.out.println("for Round" + k);		

			ArrayList<String> trainingData = new ArrayList<String>();

			ArrayList<Integer> testCusines = new ArrayList<Integer>();
			ArrayList<String> testingData = new ArrayList<String>();
			Random myRandomizer = new Random();
			

			testCusines = new ArrayList<Integer>();

			testingData = new ArrayList<String>();
			
			int number = (int) Math.floor(list.size()/10);
			//trainingData.addAll(list.subList(maxVal,list.size()-1));
			for (int i = 0; i <= number; i++) {
				int rindex=myRandomizer.nextInt(maxVal);
				String random = list.get(rindex);
				testCusines.add(Character.getNumericValue(random.charAt(0)));
				testingData.add(random.substring(2));
				Collections.swap(list,rindex,maxVal);
				maxVal--;
				}
			trainingData.addAll(list);
			for(int a=maxVal+number+1; a >maxVal; a--){
				trainingData.remove(a);
			}
			//trainingData.addAll(list.subList(0,maxVal));
			Main m= new Main();
			//HashMap<String, Integer> ingredients = m.findUncommon(trainingData);
			ArrayList<RecP> TrainingRecipes = m.readTrainingRecipes(trainingData);
			System.out.println("Training : "+TrainingRecipes.size());
			ArrayList<String> TestingRecipes = m.removeUncommonTest(testingData);
			HashMap<String, Integer> ingredients = m.ingredients;
			System.out.println("Size=" + TestingRecipes.size());
			Recipe objRec = new Recipe();
			int counterr = 0;
			for (String t : TestingRecipes) {
				ArrayList<Neighbour> myNeighbours = objRec
						.getNearestNeighbours(t, TrainingRecipes, 10, ingredients);
				int predicted = objRec.getCusine(myNeighbours, 10);
				Integer actual = (Integer) testCusines.get(counterr);
				if (actual == predicted) {
					correct++;
				} else {
					if (predicted == 0) {
						System.out.println("Actual: " + actual + "Predicted: "
								+ predicted);
					}
					incorrect++;

				}
				result = updateMat(actual, predicted, result);
				counterr++;
				
			}
			System.out.println(((double) (100*correct/(double) (correct+incorrect))));
			in1.close();
			for (int[] i : result) {
				cleanPrint(i);
			}
		}
		System.out.println("Confusion matrix from the cross validation.Rows show actual values and columns predicted values");
		for (int[] i : result) {
			cleanPrint(i);
		}
		System.out.println(((double) (100.00 * correct / (double)(correct + incorrect))));
		System.out.println("Program completee");
		
	}

	public static int[][] updateMat(int actual, int pred, int[][] result) {

		result[actual - 1][pred - 1]++;
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

// }

