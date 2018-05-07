package factory;

import animals.Animal;
/**
 * 
 * @author Haim Nahmani 
 */
public interface AbstractZooFactory {
	public Animal produceAnimal(String animal, int sz, int hor, int ver, String c);


}
