import java.util.ArrayList;
import java.util.HashMap;


public class RecP {
	HashMap<String,Integer> Ingredients=new HashMap<String,Integer>();
	Integer cuisine;
	RecP(int cuisine){
		this.cuisine=cuisine;
	}
	public void addIngredient(String ingr, int val){
		//HashMap<String, Double> ingredient= new HashMap<String, Double>();
		Ingredients.put(ingr, val);
	}
	
}

class TestRecP{
	int cuisine;
	String recipe;
	TestRecP(int cuisine, String recipe){
		this.cuisine=cuisine;
		this.recipe=recipe;
	}
}
