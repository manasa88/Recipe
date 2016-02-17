import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetTestInput {

	public void runTest(ArrayList<String> test) throws IOException {
		BufferedReader in1 = new BufferedReader(new FileReader("training_data.txt"));
		
		String text;

		ArrayList<String> trainingStrings = new ArrayList<String>();
		ArrayList<String> testingData = new ArrayList<String>();
		testingData.addAll(test);
		
		while ((text = in1.readLine()) != null) {

			trainingStrings.add(text);

		}
		Main m=new Main();
		ArrayList<RecP> TrainingRecipes = m.readTrainingRecipes(trainingStrings);
		ArrayList<String> TestingRecipes = m.removeUncommonTest(testingData);
		HashMap<String, Integer> ingredients = m.ingredients;
		// System.out.println("Size=" + TestingRecipes.size());
		Recipe objRec = new Recipe();
		ArrayList<Integer> CusinesPredicted = new ArrayList<Integer>();
		for (String t : TestingRecipes) {
			// System.out.println(t);
			ArrayList<Neighbour> myNeighbours = objRec
					.getNearestNeighbours(t, TrainingRecipes, 10, ingredients);
			int predicted = objRec.getCusine(myNeighbours, 10);

			if (predicted == 0) {
				System.out.println("Predicted is 0. You are in trouble!");
			}
			CusinesPredicted.add(predicted);
		}
		for (int i : CusinesPredicted) {
			System.out.println(i);
		}
		in1.close();

	}

}
