package animals;

import diet.Herbivore;
import mobility.Point;
/**
 * A class represents a specific animal. This class represents Elephant.
 *  extends  the class Animal
 * @author Haim Nahmani & Kfir Mazliah
 * ID: 203141197 , 305371866 
 * Campus: Beer Sheva
 */
public class Elephant extends Animal {

	 public Elephant(int s,int x, int y, int h, int v, String c) {
		 super("Elephant",s,s*10,h,v,c);
		 setLocation(new Point(x,y));
		 setDiet(new Herbivore());
		 loadImages("elf");
		 cor_x3 = (int) (-size*0.3);
		 cor_y1 = (int) (-30-size*0.45);
		 cor_y3 = (int) (size*0.25);
		 cor_x5 = -size*3/4;
		 cor_x6 = -size*1/5;
		 cor_y5 = cor_y6 = -size/4;
		 cor_h = (int)(size*0.7);
	 }
	 
}
