package animals;

import diet.Carnivore;
import mobility.Point;
/**
 * A class represents a specific animal. This class represents Lion.
 *  extends  the class Animal
 * @author Haim Nahmani
 */
public class Lion extends Animal {

	 public Lion(int s,int x, int y, int h, int v, String c) {
		 super("Lion",s,(int)(s*0.8),h,v,c);
		 setLocation(new Point(x,y));
		 setDiet(new Carnivore());
		 loadImages("lio");
		 cor_x4 = 0;
		 cor_y1 = (int) (-30-size/3);
		 cor_y3 = (int) (size*0.25);
		 cor_x5 = cor_x6 = -size/2;
		 cor_y5 = cor_y6 = -size/3;
		 cor_h = (int)(size*0.73);
	 }
}
