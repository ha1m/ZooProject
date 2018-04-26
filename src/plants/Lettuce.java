package plants;

public class Lettuce extends Plant {
	private static Lettuce instance = null;

	public Lettuce() {
		super();
		loadImages("lettuce");
	}
	/**
	 * get instance related to singelton
	 * @return instance
	 */
	public static Lettuce getInstance()
	{
		if (instance  == null)
			instance = new Lettuce();
		
		return instance;
	}
}
