package Entity.Enemies;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
public class Spider extends Enemy{
	
	private BufferedImage[] sprite;
	
	public Spider (TileMap tm)
	{
		super(tm);
		moveSpeed=1;
		maxSpeed=1.2;
		fallSpeed=1;
		maxFallSpeed=1.2;
		width=30;
		height=30;
		cwidth=20;
		cheight=20;
		health=maxHealth=3;
		damage=3;
		
		//lood sprites
		try{
			
		BufferedImage spritesheet=ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/arachnik.gif"));
		sprite=new BufferedImage[3];
		sprite[0]=spritesheet.getSubimage(0,0, width, height);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		animation=new Animation();
		animation.setFrames(sprite);
		animation.setDelay(300);
		
		right=true;
		
	}

	private void getNextPosition(){
		dx=0;
		if(left)
		{
			dy-=moveSpeed;
			if(dy<-maxSpeed)
				dy=-maxSpeed;
		}
		else 
			if(right)
		{
			dy+=moveSpeed;
			if(dy>maxSpeed)
				dy=maxSpeed;
		}
	}
	public void update()
	{
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//check flinching
		if(flinching)
		{
			long elapsed=(System.nanoTime()-flinchTimer)/1000000;
			if(elapsed>400)
			{
				flinching=false;
			}
		}
		//if it hits a wall, change direction
		
	}
	
	public void drow(Graphics2D g)
	{
		if(notOnScreen())
			return;
		
		setMapPosition();
		
		super.draw(g);
		
		
	}
	
}
