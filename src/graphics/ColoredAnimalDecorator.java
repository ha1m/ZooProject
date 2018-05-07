package graphics;

import animals.Animal;
/**
 * decorate the animal  by paint
 * @author Haim Nahmani
 */
public class ColoredAnimalDecorator implements ColoredAnimal {

	private Animal animal;
	ColoredAnimal ca;
	public ColoredAnimalDecorator(Animal animal)
	{
		super();
		this.animal = animal;
	}
	/**
	 * paint animal by color
	 */
	@Override
	public void PaintAnimal(String col)
	{
		Animal an=((Animal)animal);
		an.setColor(col);
		String nm=null;
		switch(an.getName())
		{
		case "Bear":
			nm="bea";
			break;
		case "Elephant":
			nm="elf";
			break;
		case "Giraffe":
			nm="grf";
			break;
		case "Lion":
			nm="lio";
			break;
		case "Turtle":
			nm="trt";
			break;
		}
		an.loadImages(nm);
	}

}
