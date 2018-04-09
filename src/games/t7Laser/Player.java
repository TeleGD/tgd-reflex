package games.t7Laser;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import general.AppInput;
import general.AppPlayer;

public class Player{
	
	private int x = 0;
	private int y = 0;
	private float width = 100;
	private float height = 100;
	private Image image;
	private Image down;
	private Image up;
	private Image left;
	private Image right;
	private World w;
	private int score;
	private int lives = 1;
	
	private boolean moveLeft,moveRight,moveUp,moveDown  =false;
	private int controllerID;
	private Color couleur;
	private String name;
	
	public Player(World world, int x, int y, int i, AppPlayer appPlayer) throws SlickException{
		//position initiale
		controllerID = appPlayer.getControllerID ();
		this.couleur= AppPlayer.STROKE_COLORS[appPlayer.getColorID()];
		this.name= appPlayer.getName();
		this.w=world;
		this.x = x;
		this.y = y;
		w.getGrid().getCell(x,y).setContains(i);
		this.score=0;
		this.setImage(new Image(World.DIRECTORY_IMAGES+"Char_down.png"));
		this.down= new Image(World.DIRECTORY_IMAGES+"Char_down.png");
		this.up=new Image(World.DIRECTORY_IMAGES+"Char_up.png");
		this.right=new Image(World.DIRECTORY_IMAGES+"Char_right.png");
		this.left=new Image(World.DIRECTORY_IMAGES+"Char_left.png");
	}
	
	public Color getCouleur() {
		return couleur;
	}


	public String getName() {
		return name;
	}


	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		//Affichage
		image.draw(280+(x*100*w.getRenderScale())+360-w.getGrid().getColumns()*100*w.getRenderScale()/2,y*100*w.getRenderScale()+360-w.getGrid().getColumns()*100*w.getRenderScale()/2,100*w.getRenderScale(),100*w.getRenderScale(),this.couleur);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		moveLeft = input.isControllerLeft(controllerID);
		moveRight = input.isControllerRight(controllerID);
		moveUp = input.isControllerUp(controllerID);
		moveDown = input.isControllerDown(controllerID);
		callMove();
	}
	
	public void callMove() throws SlickException{
		if(moveUp && !moveDown){ //haut
			move(x,y-1);
			this.setImage(up);
			moveUp = false;
		}
		if(moveDown && !moveUp){ //bas
			move(x,y+1);
			this.setImage(down);
			moveDown = false;
		}
		if(moveLeft && !moveRight){ //gauche
			move(x-1,y);
			this.setImage(left);
			moveLeft = false;
		}
		if(moveRight && !moveLeft){ //droite
			move(x+1,y);
			this.setImage(right);
			moveRight = false;
		}
	}
	
	public boolean isMoveUp() {
		return moveUp;
	}


	public boolean isMoveDown() {
		return moveDown;
	}

	public void setMoveUp(boolean b){
		moveUp = b;
	}
	
	public void setMoveDown(boolean b){
		moveDown = b;
	}

	public void move(int x,int y){
		
		if(w.getGrid().MovePlayer(x, y, this)){
			//if move worked
			this.x = x;
			this.y =y;
		}
	}
		
	public boolean isDead(){
		return this.lives <= 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getLives() {
		return lives;
	}

	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public void setLives(int lives) {
		this.lives = lives;
	}


	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}

	public void addScore(int i) {
		this.score = this.score+i;
	}
	
	
}