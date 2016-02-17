import java.util.*;


public class Cuisine {
	public int key;
	public int ingredient_count=0;
	HashMap<String, Integer> ingredients=new HashMap<String, Integer>();
	
	public Cuisine(int key){
		this.key=key;
	}
	
	public void addIngredient(String ingredient){
		if (ingredients.containsKey(ingredient)) /* if CA, it will not add but shows following message*/
		{
			int count=ingredients.get(ingredient);
			ingredients.put(ingredient, ++count);
			ingredient_count++;
		}
		else
		{
			ingredients.put(ingredient, 1);
			ingredient_count++;
		}
	}
	
	Integer getIngredientCount(String ingredient){
		return (ingredients.get(ingredient));
	}
	
	Double getIngredientFreq(String ingredient){
		return (double) (ingredients.get(ingredient)/ingredient_count);
	}
	
}
