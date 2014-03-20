import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class towerScreen extends Canvas {
	private towerWorld world;
	private int width,height; 
	final private int T = 40;
	private Label label;
	Image turret = new ImageIcon("images/turret.png").getImage();
	Image dirtroad = new ImageIcon("images/road.png").getImage();
	Image rock = new ImageIcon("images/rock.png").getImage();
	Image cannon = new ImageIcon("images/cannon.png").getImage();
	Image tankup = new ImageIcon("images/tankup.png").getImage();
	Image tankdown = new ImageIcon("images/tankdown.png").getImage();
	Image tankleft = new ImageIcon("images/tankleft.png").getImage();
	Image tankright = new ImageIcon("images/tankright.png").getImage();
	Image metal = new ImageIcon("images/metal.png").getImage();
	Image plane = new ImageIcon("images/plane.png").getImage();	
	
public towerScreen(towerWorld w, Label l){

	label = l;
	world =  w;
	height = world.height();
	width = world.width();
	
	setSize(T*width,T*height);
	
}

public void update(Graphics g)
{
	BufferedImage image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
	Graphics g2 = image.getGraphics();
	
	paint(g2);
	g.drawImage(image, 0,0,null);
	
}

public void paint(Graphics g)
{
/*Set score in label*/
	label.setText("$" +world.money+ "\t\tLives: " +world.lives+ "\t\tLevel: " + world.level + "\t\t\t\t\t\tScore: "+ world.score);

/*Paint the board*/
	for(int y = 0; y<height;y++)
	{
		for(int x = 0; x<width;x++)
		{
			Character tile = world.get(x,y);
			switch(tile)
			{
			case '#':
				g.drawImage(rock, x*T, y*T,null);
				break;
			case '.':
				g.drawImage(dirtroad, x*T, y*T,null);
				break;
			case 'o':
				g.drawOval(x*T, y*T, T, T);
				break;
			case '0':
				g.drawImage(metal, x*T, y*T,null);
				g.drawImage(turret, x*T, y*T, null);
				break;
			case 'D':
				g.drawImage(dirtroad, x*T, y*T,null);
				break;
			case ' ':
				g.drawImage(metal, x*T, y*T,null);
				break;

			default: System.out.println("What is this? "+ tile);
			}
		}
	
		
	}
	
/*Paint the weapons*/
	for(int j=0;j<world.weapon.size();j++)
	{
		float x =world.weapon.get(j).x;
		float y = world.weapon.get(j).y;

		g.drawImage(turret, (int)x-20, (int) y-20, null);
		
	}
	

	g.setColor(Color.BLACK);
/*Paint the enemy's*/
	for(int n = 0;n<world.enemy.size();n++)
	{
		float enemyX = world.enemy.get(n).x;
		float enemyY = world.enemy.get(n).y;
		int enemyDir = world.enemy.get(n).dir;
		
		if(world.level%3==0)
			g.drawImage(plane, (int)(enemyX*T), (int) (enemyY*T-20), null);
		else if(enemyDir == towerWorld.LEFT)
			g.drawImage(tankleft, (int)(enemyX*T), (int) (enemyY*T), null);
		else if(enemyDir == towerWorld.RIGHT)
			g.drawImage(tankright, (int)(enemyX*T), (int) (enemyY*T+3), null);
		else if(enemyDir == towerWorld.UP)
			g.drawImage(tankup, (int)(enemyX*T), (int) (enemyY*T), null);
		else if(enemyDir == towerWorld.DOWN)
			g.drawImage(tankdown, (int)(enemyX*T+4), (int) (enemyY*T), null);
		
	}
/*Paint the bullets*/
	for(int i = 0; i<world.bullet.size();i++)
	{
		g.setColor(Color.YELLOW);
		float bulletX = world.bullet.get(i).x;
		float bulletY = world.bullet.get(i).y;
		g.drawImage(cannon, (int)(bulletX)-10, (int)(bulletY)-10,null);
		
	}
/*If game over print highscore*/
	if(world.gameOver())
	{
		g.setFont(new Font("Serif",Font.BOLD,40));
		g.drawString("High Scores", 750, 40 );
		int i =1;
		for (String line : world.scores)
		{
			g.drawString(line,810, height*T/4*i/5+50);
			i+=1;
		}	
		g.drawString("Game Over",width*T/2-100, height*T/2);
	}
}

}





