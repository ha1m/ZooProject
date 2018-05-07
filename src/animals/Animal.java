package animals;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import javax.imageio.ImageIO;

import diet.IDiet;
import food.EFoodType;
import food.IEdible;
import graphics.ColoredAnimal;
import graphics.ColoredAnimalDecorator;
import graphics.IAnimalBehavior;
import graphics.IDrawable;
import graphics.ZooPanel;
import mobility.Point;
/**
 * A class represents an animal. the class extends the Mobile calss and implements IEdible IDrawable,IAnimalBehavior,ColoredAnimal,Cloneable,Runnable
 * @author Haim Nahmani
 *  
 * 
 */
public abstract class Animal extends Observable implements IEdible,IDrawable,IAnimalBehavior,ColoredAnimal,Cloneable,Runnable {

	protected final int EAT_DISTANCE = 5;
	private IDiet diet;
	protected String name;
	private double weight;
	protected int size;
	protected String col;
	protected int horSpeed;
	protected int verSpeed;
	protected boolean coordChanged;
	protected Thread thread;
	protected int x_dir;
	protected int y_dir;
	protected int eatCount;
	protected boolean threadSuspended;	 
	protected BufferedImage img1, img2;
	protected int cor_x1, cor_x2, cor_x3, cor_x4, cor_x5, cor_x6;
	protected int cor_y1, cor_y2, cor_y3, cor_y4, cor_y5, cor_y6;
	protected int cor_w, cor_h;
	private boolean isRun = false;
	protected Point location;
	private int factor;

	
	/**
	 * Default Constructor initializes all the fields that belongs to animal and the super class
	 * @param nm
	 * @param sz
	 * @param w
	 * @param hor
	 * @param ver
	 * @param c
	 */
	public Animal(String nm, int sz, int w, int hor, int ver, String c) {
		this.setLocation(new Point(0,0));
		name = new String(nm);
		size = sz;
		weight = w;
		horSpeed = hor;
		verSpeed = ver;
		col = c;
		x_dir = 1;
		y_dir = 1;
		cor_x1=cor_x3=cor_x5=cor_x6=0;
		cor_y1=cor_y3=cor_y5=cor_y6=0;
		cor_x2=cor_y2=cor_x4=cor_y4=-1;
		cor_w = cor_h = size;
		coordChanged = false;
		thread = new Thread(this);
	}	
	/**
	 * Clone method 
	 */
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
		}  
	
	public EFoodType getFoodtype() { return EFoodType.MEAT;	}	
	public IDiet getDiet() { return diet; }
	public String getName() { return this.name;	}
	public double getWeight() {	return this.weight;	}
	public void setWeight(double w) { weight = w; }
	protected void setDiet(IDiet diet) { this.diet = diet;}
	public int getSize() { return size; }
	public int getHorSpeed() { return horSpeed; }
	public void setHorSpeed(int hor) { horSpeed  = hor; }
	public int getVerSpeed() { return verSpeed; }
	public void setVerSpeed(int ver) { verSpeed  = ver; }
	public void eatInc() { eatCount++; }
	public int getEatCount() { return eatCount; }
	synchronized public void setSuspend() { threadSuspended = true; }
	synchronized public void setResume() { threadSuspended = false; notify(); }
	synchronized public boolean getChanges(){ return coordChanged; }
	synchronized public void setChanges(boolean state){ coordChanged = state; }	 
	public String getColor() { return col; }
	public void setColor(String color){this.col=color;}
	public void start() { thread.start(); }
	public void interrupt()
	{
		isRun = false;
		thread.interrupt();
		//notify();     
	}
	/**
	 * load the images of the animal by his color
	 */
	public void loadImages(String nm){
		 switch(getColor()){
			 case "Red":
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_1.png"));
				 	   img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }
				 break;
			 case "Blue":
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_1.png"));
				 	   img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }
				 break;
			 default:
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_1.png"));
			 	       img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }			 
		 }
	}
	/**
	 * run method for the animal, checks if there any food that can be eaten, and set new direction
	 * if there is no food, apply movement as normal
	 */
    public void run() 
    {
    	isRun=true;
       while (isRun) 
       {
           try 
           {
               Thread.sleep(50);
               
               synchronized(this) {
                   while (threadSuspended)
   						wait();
   				}  
          } 
           catch (InterruptedException e) 
           {
           	System.out.println(getName()+ " dead...");
           	return;
           }
                      
           if(this.getDiet().canEat(ZooPanel.getInstance().checkFood()))
           {
           		double oldSpead = Math.sqrt(horSpeed*horSpeed+verSpeed*verSpeed);
           		double newHorSpeed = oldSpead*(location.getX() - ZooPanel.getInstance().getWidth()/2)/
           				   (Math.sqrt(Math.pow(location.getX() - ZooPanel.getInstance().getWidth()/2,2)+
           						      Math.pow(location.getY() - ZooPanel.getInstance().getHeight()/2,2)));
           		double newVerSpeed = oldSpead*(location.getY() - ZooPanel.getInstance().getHeight()/2)/
           				   (Math.sqrt(Math.pow(location.getX() - ZooPanel.getInstance().getWidth()/2,2)+
           						      Math.pow(location.getY() - ZooPanel.getInstance().getHeight()/2,2)));
              	int v = 1;
                if(newVerSpeed<0) { v=-1; newVerSpeed = -newVerSpeed; }
              	if(newVerSpeed > 10)
              		newVerSpeed = 10;
              	else if(newVerSpeed < 1) {
              	   if(location.getY() != ZooPanel.getInstance().getHeight()/2)
              		newVerSpeed = 1;   
              	   else
              		newVerSpeed = 0;  
              	}
              	int h = 1;
                if(newHorSpeed<0) { h=-1; newHorSpeed = -newHorSpeed; }
              	if(newHorSpeed > 10)
              		newHorSpeed = 10;
              	else if(newHorSpeed < 1) {
              	   if(location.getX() != ZooPanel.getInstance().getWidth()/2)
              		newHorSpeed = 1;   
              	   else
              		newHorSpeed = 0;  
              	}
               	location.setX((int)(location.getX() - newHorSpeed*h));
               	location.setY((int)(location.getY() - newVerSpeed*v));
              	if(location.getX()<ZooPanel.getInstance().getWidth()/2)
              		x_dir = 1;
              	else
              		x_dir = -1;
              	if((Math.abs(location.getX()-ZooPanel.getInstance().getWidth()/2)<EAT_DISTANCE) && 
              	   (Math.abs(location.getY()-ZooPanel.getInstance().getHeight()/2)<EAT_DISTANCE))
              	{
              		ZooPanel.getInstance().eatFood(this);
              	}
           }
           else
           {
			    location.setX(location.getX() + horSpeed*x_dir);
			    location.setY(location.getY() + verSpeed*y_dir);
           }

		    if(location.getX() > ZooPanel.getInstance().getWidth()+cor_x1)
		    {
		    	x_dir = -1;
		    	if(cor_x2!=-1)
		    		location.setX(location.getX()+cor_x2);
		    }
		    else if(location.getX() < cor_x3)
		    {
		    	x_dir = 1;
		    	if(cor_x4!=-1)
		    		location.setX(cor_x4);
		    }
	
		    if(location.getY() > (ZooPanel.getInstance().getHeight() + cor_y1))
		    {
		    	y_dir = -1;
		    	if(cor_y2!=-1)
		    		location.setY(location.getY()+cor_y2);
		    }
		    else if(location.getY() < cor_y3)
		    {
		    	y_dir = 1;
		    	if(cor_y4!=-1)
		    		location.setY(cor_y4);
		    }		    
 		    setChanged();
 		    notifyObservers();
 		    ZooPanel.getInstance().repaint();
      }
   }
	/**
	 * draw the animal
	 */
    public void drawObject(Graphics g)
    {

 		if(x_dir==1) // an animal goes to right side
 		{
 			g.drawImage(img1, location.getX()+cor_x5, location.getY()+cor_y5, cor_w, cor_h, ZooPanel.getInstance());
 		}
 		else // an animal goes to left side
 		{
 			g.drawImage(img2, location.getX()+cor_x6, location.getY()+cor_y6, cor_w, cor_h, ZooPanel.getInstance());
 		}
 		
    }

    public String toString(){
    	return "["+getName() + ": weight=" + weight + ", color="+col+"]";
    }
    /**
     * check if thread is running
     * @return true if running
     */
    public boolean IsRunning(){return this.isRun;}

    /**
     * create new decorate that gets this animal and paint him
     */
	@Override
	public void PaintAnimal(String col)
	{
		
		ColoredAnimalDecorator dec = new ColoredAnimalDecorator(this);
		dec.PaintAnimal(col);		
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
	/**
	 * set change related to observer\listner pattern
	 */
	public void setTheChanged()
	{
		setChanged();
	}
    /**
     * @return factor by int
     */
	public int getFactor() {
		return factor;
	}
/**
 * set the factory type of this animal by number(0,1,2)
 * @param factor
 */
	public void setFactor(int factor) {
		this.factor = factor;
	}
}
