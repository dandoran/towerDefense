import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class towerControl implements MouseListener {
	private towerWorld world;
	
	public towerControl(towerWorld w) {
		world = w;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		world.released = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
		if(world.start)
		{
			int offsetX,offsetY;
			
			offsetX = world.weapon.get(world.index).x%40;
			offsetY = world.weapon.get(world.index).y%40;
			
			offsetX=20-offsetX;
			offsetY=20-offsetY;
/*Dont allow weapons on the road or if not enough money*/
			if(world.get( (world.weapon.get(world.index).x+offsetX)/40 , (world.weapon.get(world.index).y+offsetY)/40) =='.'||world.money<5)
			{
				world.weapon.remove(world.index);
			}
/*Snap the weapon to the grid*/
			else
			{
				world.weapon.set(world.index,new Weapon(world.weapon.get(world.index).x+offsetX, world.weapon.get(world.index).y+offsetY, world.weapon.get(world.index).damage));
				world.index+=1;
				world.money-=5;
			}	
			world.released = true;
			world.start = false;
			
		}
			
		
		
		
	}

}
