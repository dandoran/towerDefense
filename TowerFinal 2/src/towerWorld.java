import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class towerWorld {
	
	public final static int STILL = 0, UP=1,RIGHT = 2, DOWN = 3, LEFT = 4;

	
	public float weaponY=-1;
	public float weaponX=-1;
	
	public float time = 0;
	public boolean released = false;
	public boolean start = false;
	public int index;
	public int score=0,lives=20,level=1,money=20;
	private int enemySent=0;
	private boolean allSent=false;
	private boolean levelChange = false;
	private double speed = .05;
	int gameover = 0;
	
	private String startboard =   "###############################\n"
								+ "#.....#...#.....#.....#...#...#\n"		
								+ "#..##.#.#.#...#.#..#..#.#.#.#.#\n"		
								+ "#..##.#.#.#...#.#..#..#.#.#.#.#\n"		
								+ "#..##.#.#.#...#.#..#..#.#.#.#.#\n"		
								+ "#..##.#.#.#...#.#...#.#.#...#.#\n"		
								+ "#...#.#.#.#...#.#...#.#.#####.#\n"		
								+ "###.#.#.#.#...#.###.#.#.....#.#\n"		
								+ "..#.#.#.#.#...#.###.#.#####.#DD\n"		
								+ "#.#.#.#.#.#...#.###.#.....#.###\n"		
								+ "#.#.#.#.#.#...#...#.#####.#.###\n"		
								+ "#.#.#.#.#.#...###.#.#.....#.###\n"		
								+ "#.#.#.#.#.#....##.#.#.#####.###\n"		
								+ "#.#.#.#.#.#....##.#.#.#...#.###\n"		
								+ "#.#.#.#.#.####.##.#.#.#.#.#.###\n"		
								+ "#...#...#......##...#...#...###\n"		
								+ "###############################\n"
								+ "#########    0   ##############\n";
	private ArrayList<ArrayList<Character>> board = new ArrayList<ArrayList<Character>>();
	public int nextpacdir = RIGHT;
	
	ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	ArrayList<Weapon> weapon = new ArrayList<Weapon>();
	ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	ArrayList<String> scores = new ArrayList<String>();
	
	
	public towerWorld()
	{
		String[] rows = startboard.split("\n");
		for(String row: rows)
		{
				ArrayList<Character> newrow = new ArrayList<Character>();
				for(int i = 0; i<row.length();i++)
				{
				newrow.add(row.charAt(i));	
				}
				board.add(newrow);
		}
		
	}


	
	public int width() {
		return board.get(0).size();
	}


	public int height() {
		return board.size();
	}


	public Character get(int x, int y) {
		return board.get(y).get(x);
	}

	public void set(int x,int y, Character c)	{
		board.get(y).set(x,c);
	}
	
	

	public boolean gameOver() {
/*If gameover read in scores file*/
		if(lives<=0&&gameover==0)
		{
			
	      
	        try {
	            BufferedReader scorefile = new BufferedReader(new FileReader("scores.txt"));
	            String line = scorefile.readLine();
	            while (line != null) {
	                scores.add(line);
	                line = scorefile.readLine();
	            }
	        } catch (FileNotFoundException ex) {
	            System.out.println("High scores not found.");
	        } catch (IOException ex) {
	            System.out.println("Error reading high score.");
	        }
	        
	        processScore();
	         gameover+=1;
		}
		return lives<=0;
	}

	public void processScore()
	{
		
		if(score>0)
			scores.add("" + score);
	
        try {
        
        PrintWriter out = new PrintWriter (new BufferedWriter (new FileWriter("scores.txt")));
        
        for (String line : scores)
		{
        	out.println("" + line);
		}	
        out.close();
        } catch (IOException ex) {
            System.err.println("Error with high score file");
        }
	}
	
	
	public void advanceTime() {
/*Time control*/
		time += .1;

/*Enemy speed control*/
		if(levelChange&&speed<=.24)
		{
			speed+=.025;
			levelChange = false;
		}
			
/*Enemy send control*/
		if(enemySent>=4*level*.5)
			allSent=true;
		
		if((time<.2||time%10>9.9)&&enemySent<=4*level*.5)
		{
			enemy.add(new Enemy(0,8,5,RIGHT));
			enemySent +=1;
		}

/*Set enemy direction*/
		for(int n = 0;n<enemy.size();n++)
		{
			float enemyX = enemy.get(n).x;
			float enemyY = enemy.get(n).y;
			int enemydir = enemy.get(n).dir;
			int x1 = Math.round(enemyX);
			int y1 = Math.round(enemyY);
			
			if(Math.abs(x1-enemyX)<.1 && Math.abs(y1-enemyY) <.1)
			{
				int nextRight = (int) (x1+1),nextLeft = (int) (x1-1), nextUp = (int) (y1-1), nextDown = (int) (y1+1); 
			
				if(level%3==0)
					enemydir = RIGHT;
				else if(enemydir == UP)
				{
					if(get(x1,nextUp)!='#')
						enemydir = UP;
					else if(get(nextRight,y1)!='#')
						enemydir=RIGHT;
					else if(get(nextLeft,y1)!='#')
						enemydir=LEFT;
				}
				else if(enemydir == DOWN)
				{
					if(get(x1,nextDown)!='#')
						enemydir = DOWN;
					else if(get(nextRight,y1)!='#')
						enemydir=RIGHT;
					else if(get(nextLeft,y1)!='#')
						enemydir=LEFT;	
				}
				else if(enemydir == RIGHT)
				{
					if(get(nextRight,y1)!='#')
						enemydir=RIGHT;
					else if(get(x1,nextUp)!='#')
						enemydir = UP;
					else if(get(x1,nextDown)!='#')
						enemydir = DOWN;
				}
				else if(enemydir == LEFT)
				{
					if(get(nextLeft,y1)!='#')
						enemydir=LEFT;	
					else if(get(x1,nextUp)!='#')
						enemydir = UP;
					else if(get(x1,nextDown)!='#')
						enemydir = DOWN;
				}
			}
			
			if(enemydir == UP)
				enemyY -=speed;
			if(enemydir == RIGHT)
				enemyX +=speed;
			if(enemydir == DOWN)
				enemyY +=speed;
			if(enemydir == LEFT)
				enemyX -=speed;
			
			enemy.set(n,new Enemy(enemyX,enemyY,enemy.get(n).damage,enemydir));
			
			//-------------------------------
/*Bullet addition*/
			for (int j = 0; j < weapon.size(); j++)
			{
				if ((Math.abs(weapon.get(j).x - (enemy.get(n).x*40)) < 80 && Math.abs(weapon.get(j).y - (enemy.get(n).y*40)) < 80)&&time%1.25>1.2)
				{
					enemyX *=40;
					enemyY *=40;
					int tempdir = 0;
					float diffX=weaponX-enemyX,diffY=weaponY-enemyY;
					
					float weaponX=weapon.get(j).x,weaponY=weapon.get(j).y;
					
					if(weaponY-enemyY>20&&weaponX-enemyX>20)
						tempdir = 1;
					else if(weaponY-enemyY>-20&&weaponX-enemyX>20)
						tempdir = 2;
					
					else if(diffX<-20&&diffY<-20)
						tempdir = 5;
					
					else if(diffX>-20&&diffY<-20)
						tempdir = 4;
					else if(diffX<-20&&diffY<-20)
						tempdir = 3;
					
					else if(diffX<-20&&diffY<20)
						tempdir = 6;
					
					else if(diffX<=-20&&diffY>20)
						tempdir = 7;
					
					else if(diffX>-20&&diffY>20)
						tempdir = 8;

					bullet.add(new Bullet(weapon.get(j).x, weapon.get(j).y, weapon.get(j).damage,40,tempdir));
					
				}
/*Bullet progression*/
				for(int m = 0;m<bullet.size();m++)
				{
					float bulletX = bullet.get(m).x,bulletY = bullet.get(m).y;
					int dist = bullet.get(m).dist;
					int damEn = enemy.get(n).damage;
					if(bullet.get(m).dir==1)
					{
						bulletX -=1.25;
						bulletY -=1.25;
						dist-=1.25;
						
					}
					else if(bullet.get(m).dir==2)
					{
						bulletX -=1.25;
						dist-=1;			
					}
					else if(bullet.get(m).dir==3)
					{
						bulletX -=1.25;
						bulletY +=1.25;
						dist-=1.25;
					}
					else if(bullet.get(m).dir==4)
					{
						bulletY +=1.25;
						dist-=1.25;
					}
					else if(bullet.get(m).dir==5)
					{
						bulletX +=1.25;
						bulletY +=1.25;
						dist-=1.25;
					}
					else if(bullet.get(m).dir==6)
					{
						bulletX +=1.25;
						dist-=1.25;
					}
					else if(bullet.get(m).dir==7)
					{
						bulletX +=1.25;
						bulletY -=1.25;
						dist-=1.25;
					}
					else if(bullet.get(m).dir==8)
					{
						bulletY -=1.25;
						dist-=1.25;
					}
					
					if(Math.abs(bulletX-enemyX)<30&&Math.abs(bulletY-enemyY)<30)
						damEn -= 1;
					
					enemy.set(n,new Enemy(enemy.get(n).x,enemy.get(n).y,damEn,enemy.get(n).dir));				
					bullet.set(m,new Bullet(bulletX,bulletY, weapon.get(j).damage,dist,bullet.get(m).dir));
/*Score and money control*/	
					if(bullet.get(m).dist<=0)
					{
						bullet.remove(m);
					}
					if(enemy.get(n).damage<=0&&enemy.size()==1&&allSent)
					{
						level+=1;
						levelChange = true;
						enemy.add(new Enemy(0,8,5,RIGHT));
						enemy.remove(n);
						score+=10;
						money+=1;
						enemySent=0;
						allSent=false;
					}
					else if(enemy.get(n).damage<=0&&enemy.size()==1)
					{
						enemy.add(new Enemy(0,8,5,RIGHT));
						enemy.remove(n);
						enemySent+=1;
						score+=10;
						money+=1;
					}
					else if(enemy.get(n).damage<=0)
					{
						enemy.remove(n);
						score+=10;
						money+=1;
					}
				}	
				
			}
			if(enemy.size()==1&&get((int)x1+1,(int)y1)=='D')
			{
				level+=1;
				enemy.add(new Enemy(0,8,5,RIGHT));
				enemy.remove(n);
				lives-=1;
				enemySent=0;
				allSent=false;
			}
			else if(get((int)x1+1,(int)y1)=='D')
			{ 
				
				enemy.remove(n);
				lives-=1;
			}	
			
		}//end enemy for loop		
	};
}
