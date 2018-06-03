package btimer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import resizer.*;

public class BorderlessTimer extends JFrame{
	/**
	 * A basic timer with GUI
	 */
	private static final long serialVersionUID = 5346883054396468954L;
	
	static BorderlessTimer timeGUI = null;
	static Counter ct = new Counter();
	
	JPanel panel = new JPanel();
	JLabel Button_Close = new JLabel("");
	JLabel about = new JLabel("");
	JTextPane TInput = new JTextPane();
	JButton Button_Start = new JButton("GO");
	JLabel timeDisplay = new JLabel("00:00", SwingConstants.CENTER);
	int posX=0;
	int posY=0;
	public BorderlessTimer() {
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200, 100);
		this.setAlwaysOnTop (true);
		this.setBackground(Color.white);
		panel.setLayout(null);
		
		//Resizer
		ComponentResizer cr = new ComponentResizer();
		cr.registerComponent(this);
		cr.setMinimumSize(new Dimension(200, 100));
		
		//Input Text
		StyledDocument doc = TInput.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		TInput.setText("HH:MM:SS");
		timeDisplay.setForeground(Color.black);
		
		Button_Close.setBackground(Color.gray);
		Button_Close.setOpaque(true);
		
		//Component Size and Positions
		about.setBounds(0,90,10,10);
		timeDisplay.setBounds(5, 5, 190, 90);
		Button_Start.setBounds(60, 50, 80, 20);
		TInput.setBounds(40,25,120,20);
		Button_Close.setBounds(190,90,10,10);
		
		//Add everything to panel
		panel.add(TInput);
		panel.add(Button_Close);
		panel.add(Button_Start);
		panel.add(timeDisplay);
		panel.add(about);
		
		
		timeDisplay.setVisible(false);
		
		//Add panel to frame
		this.getContentPane().add(panel);
		
		
		//Mouse listener for dragging frame
	    this.addMouseListener(new MouseAdapter()
	    {
	       public void mousePressed(MouseEvent e)
	       {
	          posX=e.getX();
	          posY=e.getY();
	       }
	    });
	    this.addMouseMotionListener(new MouseAdapter()
	    {
	         public void mouseDragged(MouseEvent evt)
	         {
	    		//sets frame position when mouse dragged
	        	 Component source = evt.getComponent();
	        	 if(source.getCursor().getType() == 0 && SwingUtilities.isLeftMouseButton(evt)) {
	        		 setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
	        	 }else {
	        		 	about.setBounds(0,getHeight()-10,10,10);
	        		 	timeDisplay.setFont(new Font("sans-serif", Font.PLAIN, getFontSizeToUse(timeDisplay)));
	        			timeDisplay.setBounds(5, 5, getWidth()-10, getHeight()-10);
	        			Button_Start.setBounds((getWidth()-80)/2, getHeight()/2, 80, 20);
	        			TInput.setBounds((getWidth()-120)/2,getHeight()/4,120,20);
	        			Button_Close.setBounds(getWidth()-10,getHeight()-10,10,10);
	        	 }
	        }
	    });
		
		timeDisplay.addMouseListener(new MouseAdapter(){
		    public void mousePressed(MouseEvent  e)
		    {
		    	if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
		    		if(ct.isRunning()) {
		    			ct.counterStop();
		    			timeDisplay.setForeground(Color.red);
		    		}else {
		    			ct.counterStart();
		    			timeDisplay.setForeground(Color.black);
		    		}
		    	}else{
		    		posX=e.getX();
		    		posY=e.getY();
		    	}
		    }
		});
		
		timeDisplay.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e)
		    {
		    	if (e.getClickCount() == 2 && timeDisplay.getForeground() == Color.red) {
		    		TInput.setText(ct.getRemaining());
		    		timeDisplay.setForeground(Color.black);
		    		restartTimer();
		    	}
		    }
		});
		
		timeDisplay.addMouseMotionListener(new MouseAdapter()
	    {
	         public void mouseDragged(MouseEvent evt)
	         {
	    		//sets frame position when mouse dragged
	        	 Component source = evt.getComponent();
	        	 if(source.getCursor().getType() == 0 && SwingUtilities.isLeftMouseButton(evt)) {
	        		 setLocation (evt.getXOnScreen()-posX-5,evt.getYOnScreen()-posY-5);
	        	 }else {
					 about.setBounds(0,getHeight()-10,10,10);
					 timeDisplay.setFont(new Font("sans-serif", Font.PLAIN, getFontSizeToUse(timeDisplay)));
					 timeDisplay.setBounds(5, 5, getWidth()-10, getHeight()-10);
					 Button_Start.setBounds((getWidth()-80)/2, getHeight()/2, 80, 20);
					 TInput.setBounds((getWidth()-40)/2,getHeight()/4,40,20);
					 Button_Close.setBounds(getWidth()-10,getHeight()-10,10,10);
	        	 }
	        }
	    });
		
		TInput.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent  e)
		    {
		    	if(TInput.getText().equals("HH:MM:SS")) {
		    		TInput.setText("");
		    	}
		    }
		});
		TInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(TInput.getText().equals("HH:MM:SS")) {
		    		TInput.setText("");
		    	}
				if(!Character.isDigit(e.getKeyChar())) {
					if(e.getKeyChar() != ':') {
						e.consume();
						getToolkit().beep();
					}
					
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
				}
			}
		});
		about.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent  e)
		    {
		    	JOptionPane.showMessageDialog(null, "@2018\nBeofroll", "Borderless Timer", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		
		
		Button_Start.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  ct = new Counter(TInput.getText().trim());
			  timeDisplay.setText(ct.getRemaining());
			  timeDisplay.setFont(new Font("sans-serif", Font.PLAIN, getFontSizeToUse(timeDisplay)));
  			  timeDisplay.setBounds(5, 5, getWidth()-10, getHeight()-10);
			  timeDisplay.setVisible(true);
			  Button_Start.setVisible(false);
			  TInput.setVisible(false);
		  }
		});
		
		
		Button_Close.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent  e)
		    {
		    	System.exit(0);
		    }
		});
	    this.setVisible(true);
	}
	
	public void updateTimer(Counter cc) {
		timeDisplay.setText(cc.getRemaining());
		timeDisplay.setFont(new Font("sans-serif", Font.PLAIN, getFontSizeToUse(timeDisplay)));
	}
	
	public void restartTimer() {
		ct = new Counter();
		timeDisplay.setVisible(false);
		Button_Start.setVisible(true);
		TInput.setVisible(true);
	}
	public int getFontSizeToUse(JLabel label) { //Gets font size according to frame size
		//source: https://stackoverflow.com/a/48041474
		String labelText = label.getText();
		Font labelFont = label.getFont();
		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = label.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = ((double)componentWidth-40) / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = label.getHeight();
		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);
		return fontSizeToUse;
	}
	
	public static void main(String[] args) {
		timeGUI = new BorderlessTimer();
	}

}
