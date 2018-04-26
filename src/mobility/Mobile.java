package mobility;

/**
 * @author Haim Nahmani & Kfir Mazliah
 * ID: 203141197 , 305371866 
 * Campus: Beer Sheva
 */
public abstract class Mobile implements ILocatable {
	protected Point location;

	public Mobile(Point location) {
		this.setLocation(location);
	}
	public Point getLocation()
	{
		return location;
	}

	public boolean setLocation(Point newLocation)
	{
		this.location = newLocation;
		return true;

	}
}
