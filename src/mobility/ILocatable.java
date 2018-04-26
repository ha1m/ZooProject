package mobility;

/**
 * @author Haim Nahmani & Kfir Mazliah
 * ID: 203141197 , 305371866 
 * Campus: Beer Sheva
 */
public interface ILocatable {
	/**
	 * @return the current location
	 */
	public Point getLocation();

	/**
	 * 
	 * @param location
	 *            the new location
	 * @return true if location is valid, false if not
	 */
	public boolean setLocation(Point location);
}
