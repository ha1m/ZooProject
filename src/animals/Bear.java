package animals;

import diet.Omnivore;
import mobility.Point;
/**
 * A class represents a specific animal. This class represents Bear.
 *  extends  the class Animal
 * @author Haim Nahmani & Kfir Mazliah
 * ID: 203141197 , 305371866 
 * Campus: Beer Sheva
 */
public class Bear extends Animal {

	public Bear(int s,int x, int y, int h, int v, String c) {
		 super("Bear",s,(int)(s*1.5),h,v,c);
		 setLocation(new Point(x,y));
		 setDiet(new Omnivore());
		 loadImages("bea");
		 cor_x3 = -size/2;
		 cor_x4 = 0;
		 cor_y1 = (int) (-30-size/5);
		 cor_y3 = (int) (size*0.3);
		 cor_x5 = -size*6/7;
		 cor_y5 = cor_y6 = -size/3;
		 cor_h = (int)(size*2/3);
	 }
}
