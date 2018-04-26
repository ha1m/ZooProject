package animals;

import diet.Herbivore;
import mobility.Point;
/**
 * A class represents a specific animal. This class represents Giraffe.
 *  extends  the class Animal
 * @author Haim Nahmani & Kfir Mazliah
 * ID: 203141197 , 305371866 
 * Campus: Beer Sheva
 */
public class Giraffe extends Animal {

	 public Giraffe(int s,int x, int y, int h, int v, String c) {
		 super("Giraffe",s,(int)(s*2.2),h,v,c);
		 setLocation(new Point(0,0));
		 setDiet(new Herbivore());
		 loadImages("grf");
		 cor_x1 = size/4;
		 cor_x2 = (-size/4);
		 cor_x3 = (int) (- size*0.25);
		 cor_x4 = (int) (size*0.25);
		 cor_y1 = (int) (-30 - size*9/10);
		 cor_y3 = size/10;
		 cor_x5 = -size/2;
		 cor_y5 = cor_y6 = -size/10;
		 cor_w = (int)(size*0.7);
	 }
}
