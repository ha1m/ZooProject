package graphics;

import java.util.ArrayList;

import food.EFoodType;
import animals.Animal;
/**
 * class represent memento pattern
 * @author Haim Nahmani 
 */
public class ZooMemento {
	private ArrayList<Animal> animals;
	private EFoodType food;
	/**
	 * save list of animals and food object so we can restore later
	 * @param an
	 * @param food
	 * @throws CloneNotSupportedException
	 */
	public ZooMemento(ArrayList<Animal> an, EFoodType food) throws CloneNotSupportedException {
		animals = new ArrayList<Animal>();
		for(Animal animal : an)
		{
			Animal new_animal = (Animal)animal.clone();
			animals.add(new_animal);
			
		}
		this.food = food;
	}
	/**
	 * 
	 * @return the list of animals
	 */
	public ArrayList<Animal> getList()
	{
		return animals;
	}
	/**
	 * 
	 * @return food
	 */
	public EFoodType getFood()
	{
		return food;
	}
	

}
