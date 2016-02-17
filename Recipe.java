import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Recipe {

	public double getJaccardDistance(int cuisine, HashMap<String, Integer> instingredients,
			ArrayList<String> s2, HashMap<String, Integer> ingredients) {
		double dist = 0;
		double common = 0, total = 0, totalUC=0;
		int count=0;
		for (String s : s2) {
	
    		double IDF=0;
			if(ingredients.containsKey(s)){
				IDF=ingredients.get(s);
			}
			else
				IDF=0.0;
    		totalUC = totalUC + IDF;
			if (instingredients.containsKey(s)) {
				
				common= common + instingredients.get(s);
				
			}
			}
		
		  for (int d : instingredients.values()) { total += (d); }
		  //System.out.println(common);
		  //System.out.println(total);
		  //System.out.println(totalUC);
		  total+=(totalUC)-common;
		 // total+=s2.size()-count;
		//total = (s1.size() + s2.size()) - common;
		dist = 1 - (common / (total));
		return dist;
	}


	// Getting the cusine from the neighbours

	public int getCusine(ArrayList<Neighbour> neighbours, int kValue) {
		int c = 0;

		Collections.sort(neighbours, new Comparator<Neighbour>() {
			public int compare(Neighbour n1, Neighbour n2) {
				return n1.dist < n2.dist ? -1 : n1.dist > n2.dist ? 1 : 0;
			}
		});

		int stats[] = new int[8];

		int arrayLength = kValue < neighbours.size() ? kValue : neighbours.size();
		double vote[] = new double[arrayLength];
		double farthest = neighbours.get(neighbours.size() - 1).dist;
		double nearest = neighbours.get(0).dist;
		double diff = farthest - nearest;
		if(diff==0){
			diff=1;
		}
		for (int i = 0; i < arrayLength; i++) {
			int thisLabel = neighbours.get(i).cuisine;
			// System.out.println(thisLabel);
			vote[i] = 1+((farthest - neighbours.get(i).dist) / diff);
			//vote[i] = 1;

			stats[thisLabel]++;
		}
		double max = 0;
		for (int i = 0; i < arrayLength; i++) {
			int thisLabel = neighbours.get(i).cuisine;
			int f = stats[thisLabel];
			if ((f * vote[i]) > max) {
				max = f * vote[i];
				c = thisLabel;
			}
		}
		// return neighbours.get(c).cuisine;
		return c;

	}

	// getting k nearest neighbors
	public ArrayList<Neighbour> getNearestNeighbours(String newinstance,
			ArrayList<RecP> trainingset, int k, HashMap<String, Integer> ingredients) {
		ArrayList<Neighbour> listOfNeighbours = new ArrayList<Neighbour>();
		ArrayList<Double> distances = new ArrayList<Double>();
		int counter = 0;
		for (RecP instance : trainingset) {

			ArrayList<String> s2 = new ArrayList<String>(Arrays.asList(newinstance.split(" ")));
			double distance = getJaccardDistance(instance.cuisine, instance.Ingredients, s2, ingredients);

			if (listOfNeighbours.size() < 10) {
				Neighbour n = new Neighbour((instance.cuisine), distance);
				listOfNeighbours.add(n);
				distances.add(counter, distance);
				counter++;
			} else {
				for (int i = 0; i < listOfNeighbours.size(); i++) {
					if (distances.get(i) > distance) {

						Neighbour n = new Neighbour((instance.cuisine),distance);
						listOfNeighbours.set(i, n);
						distances.set(i, distance);
						break;
					}
				}
			}
		}

		return listOfNeighbours;

	}

	// Taking 1st file as test set removing already existing cuisine and 2nd to
	// 10th as training sets.
	// For each line in testset predicting the cuisine and comparing with the
	// actual value and storing the results in matrix
	
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

class Neighbour {
	int cuisine;
	double dist;

	public Neighbour(int c, double d) {
		cuisine = c;
		dist = d;

	}

}
