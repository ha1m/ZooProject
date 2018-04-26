package factory;

import javax.swing.JOptionPane;

import animals.Animal;
import animals.Elephant;
import animals.Giraffe;
import animals.Turtle;
/**
 * 
 * @author Haim Nahmani & Kfir Mazliah
 * ID: 203141197 , 305371866 
 * Campus: Beer Sheva
 */
public class HerbivoreFactory implements AbstractZooFactory{
	/**
	 * produceAnimal by his Factory(HerbivoreFactory)
	 */
	@Override
	public Animal produceAnimal(String animal, int sz, int hor, int ver,String c) {
		if(animal.equals("Turtle"))
			return new Turtle(sz,0,0,hor,ver,c);
		else if(animal.equals("Elephant"))
			return new Elephant(sz,0,0,hor,ver,c);
		else if(animal.equals("Giraffe"))
			return new Giraffe(sz,0,0,hor,ver,c);
		else
		{
			JOptionPane.showMessageDialog(null, "Herbivore Factory Can Create Only : Turtle, Elephant, Giraffe");
			return null;	
		}
	}

}
