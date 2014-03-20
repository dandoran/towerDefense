import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;

import javax.swing.BoxLayout;



public class Main {

	public static void main(String[] args) {
		
		towerWorld w = new towerWorld();//model
/*Frame for screen*/
		Frame f = new Frame("Tower Defense");
/*Label for score*/
		Label l = new Label("");
		l.setFont(new Font("Serif",Font.BOLD,25));

		BoxLayout vertical = new BoxLayout(f,BoxLayout.Y_AXIS);
		f.setLayout(vertical);
		towerScreen s = new towerScreen(w,l);//View
		f.addWindowListener(new Closer());
		f.add(l);
		f.add(s);

		f.pack();
		f.setVisible(true);
		
		s.addMouseMotionListener(new towermotionControl(w));
		s.addMouseListener(new towerControl(w));
/*Loop until game over*/
		while(!w.gameOver())	
		{
			try{
				w.advanceTime();
				}
			catch(IndexOutOfBoundsException ioobe){
				System.out.println("Main out of bounds");
			}
			
			s.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
    				e.printStackTrace();
			}
		}
		s.repaint();
		
	}

}
