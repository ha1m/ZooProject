package factory;

import animals.Animal;
/**
 * 
 * @author Haim Nahmani & Kfir Mazliah
 * ID: 203141197 , 305371866 
 * Campus: Beer Sheva
 */
public interface AbstractZooFactory {
	public Animal produceAnimal(String animal, int sz, int hor, int ver, String c);


}
