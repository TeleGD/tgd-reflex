package games.reflex;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import app.AppGame;
import app.AppFont;
import app.AppLoader;
import app.AppWorld;




	/*idees :
	 *rajouter un malus si on appuie mal
	 */



public class World extends AppWorld {

	public static final Font BUTTON_FONT = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 18);
	protected final static int GOAL=20;
	private ArrayList<Player> players;

	public World (int ID) {
		super (ID);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (Player p:players) {
			p.render(container, game, context);
			context.setBackground(Color.lightGray);
			context.setColor(Color.black);
			context.fillRect(0, 550, 1280, 170);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update (container, game, delta);
		for (Player p:players) {
			p.update(container, game, delta);
		}

	}

	@Override
	public void play (GameContainer container, StateBasedGame game) {
		this.players = new ArrayList <Player> ();
		int n = ((AppGame) game).appPlayers.size ();
		for (int i = 0; i < n; i++) {
			this.players.add (new Player (((AppGame) game).appPlayers.get (i), n, i));
		};
	}

}
