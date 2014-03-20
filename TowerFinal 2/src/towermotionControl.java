import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class towermotionControl implements MouseMotionListener {
	private towerWorld world;
	public boolean moved;
	

	public towermotionControl(towerWorld w) {
		world = w;
	}


	public void mouseDragged(MouseEvent mouse) {
/*Check if mouse is over weapon*/	
		if(mouse.getX()>520&&mouse.getX()<560&&mouse.getY()>680&&mouse.getY()<720 && !world.released &&!world.start)
		{	
			
			world.weapon.add(new Weapon(mouse.getX(),mouse.getY(),1));
			world.start=true;
		}
/*Keep updating the weapon location*/
		try{
			if(!world.released)
			{
				world.weapon.set(world.index, new Weapon(mouse.getX(),mouse.getY(),1));
			}
		}
		catch(IndexOutOfBoundsException ioobe)
		{
			System.out.println("oops");
		}
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
	
}
	
	

