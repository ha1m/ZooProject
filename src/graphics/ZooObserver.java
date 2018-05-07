package graphics;

import java.util.Observable;
import java.util.Observer;
/**
 * class represent bserver/listner pattern
 * @author Haim Nahmani 
 */
public class ZooObserver extends Thread implements Observer {

	private final int RESOLUTION = 25; 

	/**
	 * notify observe
	 */
	@Override
	public void update(Observable arg0, Object arg1)
	{
		synchronized(this)
		{
			notify();
		}

	}
/**
 * run method that do repaint if there is a need, also chekcs if animal can eat another
 */
	@Override
	public void run() {
		while(true) {
			if(ZooPanel.getInstance().isChange2())
				ZooPanel.getInstance().repaint();
			
			
			boolean prey_eaten = false;
			synchronized(this) {
				ZooPanel.getInstance().eatAnotherAnimal(prey_eaten);

			}
			try {
				Thread.sleep(1000/RESOLUTION);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

}
