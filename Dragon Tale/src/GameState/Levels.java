package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

 
public class Levels extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	public String s="/Maps/level1.map";
	private Player player;
	public int count=0;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private HUD hud;
	static int level=1;
	private AudioPlayer bgMusic;
	private int counter=2;
	public boolean t;
	
	public  Levels(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public Player getPlayer()
	{
		return player;
	}
	
	
	public void init() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		//control the map in play
		tileMap.loadMap(s);
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		player = new Player(tileMap);
		player.setPosition(100, 100);
		if(level==1){
			bgMusic = new AudioPlayer("/Music/sound.mp3");
			bgMusic.play();
			populateEnemies1();
		}
		if(level==2)
			populateEnemies2();
		if(level==3) 
		{
			bgMusic.stop();
			bgMusic = new AudioPlayer("/Music/bosslevel.mp3");
			bgMusic.play();
			populateEnemiesBoss();
			
		}
		explosions = new ArrayList<Explosion>();
		hud = new HUD(player);
	}
	private void populateEnemies2() {
			enemies = new ArrayList<Enemy>();
		
		Slugger sl;
		int i;
		SmallBass bsl;
		Point[] pointssl = new Point[] {
			new Point(500, 125),
			new Point(584, 79),
			new Point(656, 238),
			new Point(1441, 124),
		};
		Point[] pointsb = new Point[] {
				new Point(2850, 165),
				new Point(3025, 212),
			};
	
		for( i = 0; i < pointssl.length; i++) {
			sl = new Slugger(tileMap);
			sl.setPosition(pointssl[i].x, pointssl[i].y);
			enemies.add(sl);
		}
		
		for( i = 0; i < pointsb.length; i++) {
		bsl=new SmallBass(tileMap);
		bsl.setPosition(pointsb[i].x, pointsb[i].y);
		enemies.add(bsl);
		}	
	
		
	}

	private void populateEnemiesBoss() {
		// TODO Auto-generated method stub
		Point pointssl =  new Point(200, 100);
		BigBoss bsl;
		bsl=new BigBoss(tileMap);
		bsl.setPosition(pointssl.x, pointssl.y);
		enemies.add(bsl);
	}

	private void sets() {
	 s="/Maps/level1-1.map";
	}
	
	private void populateEnemies1() {
		enemies = new ArrayList<Enemy>();	
		Slugger sl;
		int i;
		SmallBass bsl;
		Spider sp;
		Point[] pointssl = new Point[] {
			new Point(200, 100),
			new Point(860, 200),
			new Point(1525, 200),
			new Point(1680, 200),
			new Point(1800, 200),
			new Point(3060, 200)
		};
	
		for( i = 0; i < pointssl.length; i++) {
			sl = new Slugger(tileMap);
			sl.setPosition(pointssl[i].x, pointssl[i].y);
			enemies.add(sl);
		}
		bsl=new SmallBass(tileMap);
		bsl.setPosition(pointssl[i-1].x, pointssl[i-1].y);
		enemies.add(bsl);
	}
		public void update() {
		int i;
		int j;
		// update player
		player.update();
		int Health;
		Health=player.getHealth();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		if(level==1)
		{
			t=checkFall1();	
		}
		boolean b=true;
		if(level==2 && b)
		{
			t=checkFall2();
			b=false;
		}
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		// update all enemies
		for( Health = 0; Health < enemies.size(); Health++) {
			Enemy e = enemies.get(Health);
			e.update();
			if(e.isDead()) {
				enemies.remove(Health);
				Health--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
			}
			if(enemies.size()==0)
			{	
				j=changLevel();
				level=j;
				init();
			}
			
		}
		
		// update explosions
		for( Health = 0; Health < explosions.size(); Health++) {
			explosions.get(Health).update();
			if(explosions.get(Health).shouldRemove()) {
				explosions.remove(Health);
				Health--;
			}
		}
		if(player.isDead())
		{
			
			gsm.setState(GameStateManager.MENUSTATE);
			gsm.setPlayer(player);
		}
	}
	
	private boolean checkFall1() {
			if(player.gety()>200)
			{
				player.hit(30);
				return true;
			}
			return false;
		}

	private boolean checkFall2() {
		if(player.gety()>260)
		{
			player.hit(30);
			return true;
		}
		return false;
	}
	private  int changLevel() {
		if(counter==4)
		{
			gsm.setState(GameStateManager.MENUSTATE);
			gsm.setPlayer(player);
		}
		String p="/Maps/level"+counter+".map";
		s=p;
		if (counter==3)
		{
			 p="/Maps/booslevel.map";
			s=p;
		}		
		counter++;
		return counter-1;
	}

	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
	}
	
}












