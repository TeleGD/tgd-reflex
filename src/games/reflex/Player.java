package games.reflex;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppInput;
import app.AppLoader;
import app.AppPlayer;

public class Player {
	private static final int nbImages=3;
	private final static int COOL = 1000;
	private int cooldown;
	private int pos; //pos designe le milieu
	private int state;
	private Color color;
	private Image image;
	private Random rand;
	private int controllerID;
	private boolean next;
	private boolean fini;
	private Button bouton;

	public Player(AppPlayer appPlayer, int n, int i) {
		state=0;
		fini=false;
		next = true;
		cooldown=COOL;
		this.controllerID = appPlayer.getControllerID();
		rand = new Random();
		pos=1280*(2*i+1)/(2*n);
		image = AppLoader.loadPicture("/images/reflex/p"+rand.nextInt(nbImages)+".png");
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		image.draw(pos-image.getWidth()/2, 550-state*image.getHeight()/World.GOAL);
		if (bouton!=null && cooldown==COOL) {
			bouton.render(container, game, context);
		}
	}

	public int getPos() {
		return pos;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (!fini) {
			if (next) {
				if (cooldown<=0) {
					next = false;
					bouton = new ButtonP(this, rand.nextInt(4));
					cooldown = COOL;
				} else {
					cooldown -= delta;
				}
			} else {
				AppInput appInput = (AppInput) container.getInput();
				if (mauvaisBouton(appInput) && state>0) {
					state-=1;
					next = true;
				} else {
					if (bonBouton(appInput)) {
						state+=1;
						next=true;
					}
				}

				if (state==World.GOAL) {
					//victoire
					fini=true;
				}
			}
		}

	}

	public boolean bonBouton(AppInput appInput) {
		return appInput.isButtonPressed((int) Math.pow(2,bouton.getNumero()),controllerID);
	}

	public boolean mauvaisBouton(AppInput appInput) {
		boolean test=false;
		for (int i=0;i<4;i++) {
			if (!test && i!=bouton.getNumero() && appInput.isButtonPressed((int) Math.pow(2,i),controllerID)) {
				test=true;
			}
		}
		return test;
	}

}
