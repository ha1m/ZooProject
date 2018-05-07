package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import animals.Animal;
/**
 * DecorateDialog
 * @author Haim Nahmani 
 */
public class DecorateDialog extends JDialog implements ItemListener, ActionListener{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JPanel p1,p2,p4,p5;
	private JComboBox<String> cb;
    private String[] colors = {"Red","Blue"};
    private ButtonGroup rbg;
    private JRadioButton[] rb;
	private ArrayList<Animal> animals;
	private ArrayList<Animal> naturalAnimals=new ArrayList<Animal>();
	private Animal an=null;
	private String c;
/**
 * create the dialog
 */
	public DecorateDialog()
	{
    	super(new JFrame(),"Change_color",true);
    	setSize(550,300);
		setBackground(new Color(100,230,255));

		this.animals = ZooPanel.getInstance().getAnimals();
		this.setTitle("Decorate An Animal");
		p1 = new JPanel();
		p2 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		
		p1.setLayout(new GridLayout(2,3,10,0));
		cb=new JComboBox<String>();

		for(int i=0; i<animals.size(); i++)
		{
			if(animals.get(i).getColor()=="Natural")
			{
				naturalAnimals.add(animals.get(i));
				String s=animals.get(i).getName()+": running=" +animals.get(i).IsRunning()+", weight="+animals.get(i).getWeight()+", color="+animals.get(i).getColor();
				cb.addItem(s);
			}
		}
		
		p1.add(cb);
		p2.setLayout(new GridLayout(3,1,0,0));
		rbg = new ButtonGroup();
		rb=new JRadioButton[colors.length];
		for(int i=0;i<colors.length;i++)
		{
		    rb[i] = new JRadioButton(colors[i],false);
		    rb[i].addItemListener(this);
		    rbg.add(rb[i]);
		    p2.add(rb[i]);
		}
		p4.setLayout(new GridLayout(1,2,5,5));
		ok=new JButton("OK");
		ok.addActionListener(this);
		ok.setBackground(Color.lightGray);
		p4.add(ok);
		p5.setLayout(new GridLayout(1,2,10,10));
		p5.add(p1);
		p5.add(p2);
		p1.setBorder(BorderFactory.createTitledBorder("Choose animal to decorate"));
		p2.setBorder(BorderFactory.createTitledBorder("Choose decoration color"));
		setLayout(new BorderLayout());
		add("Center", p5);
		add("South" , p4);
	}
	/**
	 * actionPerformed
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(an!=null)
			an.PaintAnimal(c);
		this.setVisible(false);
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		int index=cb.getSelectedIndex();
		an=naturalAnimals.get(index);
		for(int i=0;i<rb.length;i++)
			if(rb[i].isSelected())
		    {
		    	c = colors[i];
		    	break;
	        }		
	}

}
