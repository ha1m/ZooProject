package graphics;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import graphics.DecorateDialog;
import animals.Animal;
import factory.AbstractZooFactory;
import factory.CarnivoreFactory;
import factory.HerbivoreFactory;
import factory.OmnivoreFactory;
import food.EFoodType;
import plants.Cabbage;
import plants.Lettuce;
import plants.Plant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * class represent the zoo
 * @author Haim Nahmani 
 */
public class ZooPanel extends JPanel implements ActionListener
{
   private static final long serialVersionUID = 1L;
   private static final int MAX_ANIMAL_NUMBER  = 10;
   private final String BACKGROUND_PATH = Animal.PICTURE_PATH+"savanna.jpg";
   private final String MEAT_PATH = Animal.PICTURE_PATH+"meat.gif";
   private EFoodType Food;
   private JPanel p1,p2,p3;
   private JButton[] b_num;
   private JButton[] b_num2;
   private String[] names = {"Add Animal","Sleep","Wake up","Clear","Food","Info","Exit"};
   private String[] names2 = {"Decorate","Duplicate","Save state","Restore state"};
   private ArrayList<Animal> animals;
   private Plant forFood = null;
   private JScrollPane scrollPane;
   private boolean isTableVisible;
   private int totalCount;
   private BufferedImage img, img_m;
   private boolean bgr;
   private Executor threadPool;
   private int factory=-1;
   private ZooObserver controller2;
   protected boolean duplicateFlag=false;
   private static ZooPanel instance = null;
   private ArrayList<ZooMemento> mementos;
   /**
    * create the zoopanel
    */
   public ZooPanel()
   {
	    threadPool = Executors.newFixedThreadPool (5);
	    mementos=new ArrayList<ZooMemento>();
	    Food = EFoodType.NOTFOOD;
	    totalCount = 0;
	    isTableVisible = false;
	    controller2=new ZooObserver();
	    animals = new ArrayList<Animal>();
	    
	    setBackground(new Color(255,255,255));
	    p3=new JPanel(new BorderLayout());
	    p3.setBackground(new Color(240,240,240));
	    
	    p1=new JPanel();
		p1.setLayout(new GridLayout(1,7,0,0));
		p1.setBackground(new Color(240,240,240));

		b_num=new JButton[names.length];
		for(int i=0;i<names.length;i++)
		{
		    b_num[i]=new JButton(names[i]);
		    b_num[i].addActionListener(this);
		    b_num[i].setBackground(Color.lightGray);
		    p1.add(b_num[i]);		
		}

		setLayout(new BorderLayout());
		
	    p2=new JPanel();
		p2.setLayout(new GridLayout(1,7,0,0));
		p2.setBackground(new Color(240,240,240));

		b_num2=new JButton[names2.length];
		for(int i=0;i<names2.length;i++)
		{
		    b_num2[i]=new JButton(names2[i]);
		    b_num2[i].addActionListener(this);
		    b_num2[i].setBackground(Color.lightGray);
		    p2.add(b_num2[i]);		
		}
		add("South", p3);
		p3.add("North",p1);
		p3.add("South", p2);


		img = img_m = null;
		bgr = false;
		try { img = ImageIO.read(new File(BACKGROUND_PATH)); } 
		catch (IOException e) { System.out.println("Cannot load background"); }
		try { img_m = ImageIO.read(new File(MEAT_PATH)); } 
		catch (IOException e) { System.out.println("Cannot load meat"); }
		
		controller2.start();
   }		
   /**
    * singelton related
    * @return instance
    */
	public static ZooPanel getInstance()
	{
		if(instance == null)
			synchronized(ZooPanel.class)
			{
				instance  = new ZooPanel();
			}
	
		
		return instance;
	}
   
	/**
	 * function to draw the right image. 
	 */
   public void paintComponent(Graphics g)
   {
	   	super.paintComponent(g);	
	   	
	   	if(bgr && (img!=null))
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

	   	if(Food == EFoodType.MEAT)
	   		g.drawImage(img_m, getWidth()/2-20, getHeight()/2-20, 40, 40, this);
	    
	   	if((Food == EFoodType.VEGETABLE) && (forFood != null))
	   		forFood.drawObject(g);

	   	synchronized(this) {
		   	for(Animal an : animals)
		    	an.drawObject(g);
	   	}
   }
   /**
    * set background 
    * @param num
    */
   public void setBackgr(int num) {
	   switch(num) {
	   case 0:
		   setBackground(new Color(255,255,255));
		   bgr = false; 
		   break;
	   case 1:
		   setBackground(new Color(0,155,0));
		   bgr = false; 
		   break;
	   default:
			bgr = true;   
	   }
	   repaint();
   }
   
   synchronized public EFoodType checkFood()
   {
	   return Food;
   }

   /**
    * CallBack function 
    * @param f
    */
   synchronized public void eatFood(Animal an)
   {
	   if(Food != EFoodType.NOTFOOD)
	   {
		    if(Food == EFoodType.VEGETABLE)
		    	forFood = null;
		   	Food = EFoodType.NOTFOOD;
	   		an.eatInc();
	   		totalCount++;
	   		System.out.println("The "+an.getName()+" with "+an.getColor()+" color and size "+an.getSize()+" ate food.");
	   }
	   else
	   {
		   System.out.println("The "+an.getName()+" with "+an.getColor()+" color and size "+an.getSize()+" missed food.");
	   }
   }

   public void addDialog()
   {
	   if(animals.size()==MAX_ANIMAL_NUMBER) {
		   JOptionPane.showMessageDialog(this, "You cannot add more than "+MAX_ANIMAL_NUMBER+" animals");
	   }
	   else {
		   this.factory=selectFactory();
		   AddAnimalDialog dial = new AddAnimalDialog("Add an animal to Zoo",factory);
		   dial.setVisible(true);
	   }
   }
   /**
    * add animal to the zoo 
    * @param animal
    * @param sz
    * @param hor
    * @param ver
    * @param c
    * @param factor
    */
   public void addAnimal(String animal, int sz, int hor, int ver, String c,int factor)
   {
	   Animal an = null;
	   AbstractZooFactory zooFactory = null;
	   if(factor==0)
		   zooFactory = this.createAnimalFactory("HerbivoreFactory");
	   else if(factor==1)
		   zooFactory = this.createAnimalFactory("OmnivoreFactory");
	   else if(factor==2)
		   zooFactory = this.createAnimalFactory("CarnivoreFactory");
	   
		if(zooFactory != null || duplicateFlag)
		{
			   if(animal.equals("Elephant"))
			   {
				   if(duplicateFlag)
					   zooFactory = this.createAnimalFactory("HerbivoreFactory");
				   factory=0;
				   an = zooFactory.produceAnimal("Elephant",sz,hor,ver,c);
			   }
			   else if (animal.equals("Lion"))
			   {
				   if(duplicateFlag)
					   zooFactory = this.createAnimalFactory("CarnivoreFactory");
				   factory=2;
				   an = zooFactory.produceAnimal("Lion",sz,hor,ver,c);
			   }
			   else if (animal.equals("Turtle"))
			   {
				   if(duplicateFlag)
					   zooFactory = this.createAnimalFactory("HerbivoreFactory");
				   factory=0;
				   an = zooFactory.produceAnimal("Turtle",sz,hor,ver,c);

			   }
			   else if (animal.equals("Bear"))
			   {
				   if(duplicateFlag)
					   zooFactory = this.createAnimalFactory("OmnivoreFactory");
				   factory=1;
				   an = zooFactory.produceAnimal("Bear",sz,hor,ver,c);

			   }
			   else 
			   {
				   if(duplicateFlag)
					   zooFactory = this.createAnimalFactory("HerbivoreFactory");
				   factory=0;
				   an = zooFactory.produceAnimal("Giraffe",sz,hor,ver,c);
			   }
			   if(an!=null)
			   {
			   		an.setFactor(factory);
					   animals.add(an);
					   threadPool.execute(an);
					   an.addObserver(controller2);
			   }

			   duplicateFlag=false;
		}
		factory=-1;
   }
   /**
    * select the factory
    * @return the result
    */
	private int selectFactory()
	{
		String FactoryType[] = {"Herbivore", "Omnivore", "Carnivore"};
		int dialogRes = JOptionPane.showOptionDialog (null, "Please Choose Animal Factory", "Animal Factory", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, FactoryType, FactoryType[0]);
		return dialogRes;	
	}
	/**
	 * create the animal by the factory type
	 * @param type
	 * @return
	 */
	private AbstractZooFactory createAnimalFactory (String type)
	{
		if (type.equals("HerbivoreFactory"))
			return new HerbivoreFactory();
		else if (type.equals("OmnivoreFactory"))
			return new OmnivoreFactory();
		else if (type.equals("CarnivoreFactory"))
			return new CarnivoreFactory();
		
		return null;
	}

	public void start() {
	    for(Animal an : animals)
	    	an.setResume();
   }

 	public void stop() {
	    for(Animal an : animals)
	    	an.setSuspend();
   }
/**
 * clear method to clear all animals and food
 */
   synchronized public void clear()
   {
	   Iterator<Animal> iterator  = animals.iterator();
		while(iterator.hasNext())
		{
			Animal an = iterator.next();
			if(an.IsRunning())
			{
				an.interrupt();					
				iterator.remove();
			}
		}
	   Food = EFoodType.NOTFOOD;
	   forFood = null;
	   totalCount = 0;
	   repaint();
   }

   synchronized public void preyEating(Animal predator, Animal prey)
   {
	   predator.eatInc();
	   totalCount -= (prey.getEatCount()-1);
   }
/**
 * add food by choice
 */
   synchronized public void addFood()
   {
	   if(Food == EFoodType.NOTFOOD){
		   Object[] options = {"Meat", "Cabbage", "Lettuce"}; 
		   int n = JOptionPane.showOptionDialog(null, 
		   		"Please choose food", "Food for animals", 
		   		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
		   		null, options, options[2]);
		   switch(n) {
		   case 0: // Meat
			   Food = EFoodType.MEAT;
			   break;
		   case 1: // Cabbage
			   Food = EFoodType.VEGETABLE;
			   forFood = Cabbage.getInstance();
			   //forFood
			   break;
		   default: // Lettuce
			   Food = EFoodType.VEGETABLE;
			   forFood = Lettuce.getInstance();

			   break;
		   }
	   }
	   else {
		   Food = EFoodType.NOTFOOD;
		   forFood = null;
	   }
	   repaint();
  }
   /**
    * info table show info about animals on the zoo 
    */
   public void info()
   {  	 
	   if(isTableVisible == false)
	   {
		  int i=0;
		  int sz = animals.size();

		  String[] columnNames = {"Animal","Color","Weight","Hor. speed","Ver. speed","Eat counter"};
	      String [][] data = new String[sz+1][columnNames.length];
		  for(Animal an : animals)
	      {
	    	  data[i][0] = an.getName();
	    	  data[i][1] = an.getColor();
	    	  data[i][2] = new Integer((int)(an.getWeight())).toString();
		      data[i][3] = new Integer(an.getHorSpeed()).toString();
		      data[i][4] = new Integer(an.getVerSpeed()).toString();
	    	  data[i][5] = new Integer(an.getEatCount()).toString();
	    	  i++;
	      }
	      data[i][0] = "Total";
	      data[i][5] = new Integer(totalCount).toString();
	      
	      JTable table = new JTable(data, columnNames);
	      scrollPane = new JScrollPane(table);
	      scrollPane.setSize(450,table.getRowHeight()*(sz+1)+24);
	      add( scrollPane, BorderLayout.CENTER );
	      isTableVisible = true;
	   }
	   else
	   {
		   isTableVisible = false;
	   }
	   scrollPane.setVisible(isTableVisible);
       repaint();
   }

   public void destroy()
   { 
	  for(Animal an : animals)
		  an.interrupt();
	  controller2.interrupt();
      System.exit(0);
   }
   /**
    * secorate function to set a new color to exsit animal who color is natural
    */
   public void decorate()
   {
	   boolean natural=false;
	   for(int i=0; i<animals.size(); i++)
	   {
		   if(animals.get(i).getColor()=="Natural")
			   natural=true;
	   }
	   if(natural)
	   {
		   DecorateDialog d = new DecorateDialog();
		   d.setVisible(true);
	   }
	   else
		   JOptionPane.showMessageDialog(this, "You do not have animals for decoration"); 
   }
   /**
    * open duplicate dialog
    */
   public void duplicate()
   {
	   DuplicateDialog dd = new DuplicateDialog();
	   dd.setVisible(true);
   }
   /**
    * actionPerformed
    */
   public void actionPerformed(ActionEvent e)
   {
	if(e.getSource() == b_num[0]) // "Add Animal"
		addDialog();
	else if(e.getSource() == b_num[1]) // "Sleep"
		stop();
	else if(e.getSource() == b_num[2]) // "Wake up"
		start();
	else if(e.getSource() == b_num[3]) // "Clear"
		clear();
	else if(e.getSource() == b_num[4]) // "Food"
		addFood();
	else if(e.getSource() == b_num[5]) // "Info"
		info();
	else if(e.getSource() == b_num[6]) // "Exit"
		destroy();
	else if(e.getSource() == b_num2[0]) // "decorate"
		decorate();
	else if(e.getSource() == b_num2[1]) // "Duplicate"
		duplicate();
	else if(e.getSource() == b_num2[2]) // "Save state"
		saveState();
	else if(e.getSource() == b_num2[3]) // "Restore State"
		restoreState();
   }


//	public boolean isChange() {
//		boolean rc = false;
//		for(Animal an : animals) {
//		    if(an.getChanges()){
//		    	rc = true;
//		    	an.setChanges(false);
//			}
//	    }
//		return rc;
//	}
	/**
	 * check if animal has changed
	 * @return true if changed
	 */
	public boolean isChange2() {
		boolean rc = false;
		for(Animal an : animals) {
		    if(an.hasChanged()){
		    	rc = true;
		    	an.setTheChanged();
			}
	    }
		return rc;
	}



	public ArrayList<Animal> getAnimals() {
		return animals;
	}
	/**
	 * checks if one animal can eat another
	 * @param prey_eaten
	 */
	public void eatAnotherAnimal(boolean prey_eaten)
	{
		for(Animal predator : animals) {
			for(Animal prey : animals) {
				if(predator != prey && predator.getDiet().canEat(prey) && predator.getWeight()/prey.getWeight() >= 2 &&
						(Math.abs(predator.getLocation().getX() - prey.getLocation().getX()) < prey.getSize()) &&
						(Math.abs(predator.getLocation().getY() - prey.getLocation().getY()) < prey.getSize())) {
					preyEating(predator,prey);
					System.out.print("The "+predator+" cought up the "+prey+" ==> ");
					prey.interrupt();
					animals.remove(prey);
					repaint();
					prey_eaten = true;
					break;
				}
			}
			if(prey_eaten)
				break;					
		}
	}
	/**
	 * save state by memento pattern
	 */
	public void saveState()
	{
		ZooMemento zm;
			try
			{
				zm = new ZooMemento(animals,Food);
				if(mementos.size()<3){
					mementos.add(zm);
					JOptionPane.showMessageDialog(null,"The state has been saved!!");
				}
				else
					JOptionPane.showMessageDialog(null,"You cant save more then 3 states");
			}
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
	}
	/**
	 * restore the saved state if there is any
	 */
	public void restoreState()
	{
		if(mementos.size()<1)
		{
			   JOptionPane.showMessageDialog(null,"You have not saved state");
			   return;
		}
		ArrayList<Animal> rAnimlas = new ArrayList<Animal>();
		String States[] = {"State 1", "State 2", "State 3", "Cancle"};
		int s = JOptionPane.showOptionDialog (null, "Please Choose state for restore", "Saved states", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, States, States[0]);
		   switch(s) {
		   case 0:
			   if(mementos.size()>0)
			   {
				   clear();
				   Food=mementos.get(0).getFood();
				   repaint();
				   rAnimlas = mementos.get(0).getList();
					System.out.println(rAnimlas.size());
				   for(int i=0; i<rAnimlas.size(); i++)
				   {
					   addAnimal(rAnimlas.get(i).getName(),rAnimlas.get(i).getSize(),rAnimlas.get(i).getHorSpeed(),rAnimlas.get(i).getVerSpeed(),rAnimlas.get(i).getColor(),rAnimlas.get(i).getFactor());
				   }
				   mementos.remove(0);
			   }
			   else
				   JOptionPane.showMessageDialog(null,"There is no saved state here");

			   break;
		   case 1:
			   if(mementos.size()>1)
			   {
				   clear();
				   Food=mementos.get(1).getFood();
				   repaint();
				   rAnimlas = mementos.get(1).getList();
				   for(int i=0; i<rAnimlas.size(); i++)
				   {
					   addAnimal(rAnimlas.get(i).getName(),rAnimlas.get(i).getSize(),rAnimlas.get(i).getHorSpeed(),rAnimlas.get(i).getVerSpeed(),rAnimlas.get(i).getColor(),rAnimlas.get(i).getFactor());
				   }
				   mementos.remove(1);
			   }
			   else
				   JOptionPane.showMessageDialog(null,"There is no saved state here");
			   break;
		   case 2:
			   if(mementos.size()>2)
			   {
				   clear();
				   Food=mementos.get(2).getFood();
				   repaint();
				   rAnimlas = mementos.get(2).getList();
				   for(int i=0; i<rAnimlas.size(); i++)
				   {
					   addAnimal(rAnimlas.get(i).getName(),rAnimlas.get(i).getSize(),rAnimlas.get(i).getHorSpeed(),rAnimlas.get(i).getVerSpeed(),rAnimlas.get(i).getColor(),rAnimlas.get(i).getFactor());
				   }
				   mementos.remove(2);
			   }
			   else
				   JOptionPane.showMessageDialog(null,"There is no saved state here");

			   break;
		   }
	}

}
