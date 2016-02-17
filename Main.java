import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
public class Main {
	static HashSet<String> uncommon;
	static Cuisine french=new Cuisine(1);
	static Cuisine italian=new Cuisine(2);
	static Cuisine indian=new Cuisine(3);
	static Cuisine chinese=new Cuisine(4);
	static Cuisine thai=new Cuisine(5);
	static Cuisine greek=new Cuisine(6);
	static Cuisine mexican=new Cuisine(7);
	static Cuisine[] cuisineMaps=new Cuisine[]{french,italian,indian,chinese,thai,greek,mexican};
	static int[] cuisineCount=new int[]{0,0,0,0,0,0,0};
	static double[] cuisineFreq=new double[]{0,0,0,0,0,0,0};
	static double count_ingr=0;
	static HashMap<String, Integer> ingredients = null;
	public static ArrayList<RecP> readTrainingRecipes(ArrayList<String> input){
		findUncommon(input);
		removeUncommon();
			ArrayList<RecP> TrainingRecipes = null;
			TrainingRecipes=new ArrayList<RecP>();
			double max=0;
			 for(String line: input){
				    String[] words = line.split(" ");
				    int cuisineKey=Integer.parseInt(words[0]);
				    RecP trial= new RecP(cuisineKey);
				    for(int i=1; i<words.length; i++){
				    	if(!(uncommon.contains(words[i]) &&  words[i].length()>2 && ingredients.get(words[i])>=0)){
		
				    		trial.addIngredient(words[i], ingredients.get(words[i]));
				    	}
				    		else{
				    			int IDF=0;
				    			trial.addIngredient(words[i], IDF);
				    		}
				    	}
				  //  }
				    TrainingRecipes.add(trial);
			 }
		return TrainingRecipes;
	}
	
	public static HashMap<String, Integer> findUncommon( ArrayList<String> input){
		
		ingredients=new HashMap<String, Integer>();
		for(String line: input){
		    String[] words = line.split(" ");
		    int cuisineKey=Integer.parseInt(words[0]);
		    cuisineCount[cuisineKey-1]++;
		    for(int i=1; i<words.length; i++){
		       		cuisineMaps[cuisineKey-1].addIngredient(words[i]);
		       			if (ingredients.containsKey(words[i])) 
		       			{
		       				int count=ingredients.get(words[i]);
		       				ingredients.put(words[i], ++count);
		       				count_ingr++;
		       			}
		       			else
		       			{
		       				ingredients.put(words[i], 1);
		       				count_ingr++;
		       			}
		    }
		 }	 
		return ingredients;
	}
	private static void removeUncommon()
	{
	
		uncommon=new HashSet<String>();
		for(String key:ingredients.keySet()){
			if(ingredients.get(key)<10){
				count_ingr=count_ingr-ingredients.get(key);
				ingredients.put(key, -1);
				uncommon.add(key);
			}
			else{
				double wt[]=new double[7];
				int i=0;
				double sum=0;
				for(Cuisine c: cuisineMaps){
					if(c.ingredients.containsKey(key)){
					wt[i]=c.ingredients.get(key);
					sum+=wt[i];
					}
					else
						wt[i]=0;
					i++;
				}
				sum=sum/6;
				int count0=0;
				for(int j=0; j<7;j++){
					if((wt[j]-sum)<0){
						count0++;
					}
				}
				ingredients.put(key, count0);
			}
				
		}
	}
	public static ArrayList<String> removeUncommonTest(ArrayList<String> recipes)
	{
		ArrayList<String> processedTest=new ArrayList<String>();
		for(String line: recipes){
		    String[] words = line.split(" ");
		    StringBuilder recipe=new StringBuilder();
		    for(int i=0; i<words.length; i++){
		    	if(!uncommon.contains(words[i]) && recipe.indexOf(words[i])==-1){
		    		recipe.append(words[i] +" ");
		    	}
		    }
		    processedTest.add(recipe.toString());
		}
		return processedTest;
		
	}

}
