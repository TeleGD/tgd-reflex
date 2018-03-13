package games.battle;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import general.AppGame;
public class World extends BasicGameState {
	static private float jump (float x, float h, float d) {
		// y = (4h / d) (x - x² / d)
		return (float) ((0 <= x && x < d) ? (4 * h * (Math.pow (x, 2) / d - x) / d) : 0);
	};
	private int ID;
	private StateBasedGame game;
	private int width;
	// private int height;
	private Color backgroundColor;
	private Color fillColor;
	private Color strokeColor;
	private boolean key_enter;
	private boolean key_space;
	private boolean key_left;
	private boolean key_right;
	private Player player;
	public World (int ID) {
		this.ID = ID;
	};
	public int getID () {
		return this.ID;
	};
	public void init (GameContainer container, StateBasedGame game) {
		int radius;
		radius = 32;
		this.game = game;
		this.width = container.getWidth () + radius * 2;
		// this.height = container.getHeight () + radius * 2;
		this.backgroundColor = new Color (255, 255, 255, 0);
		this.fillColor = new Color (51, 153, 102);
		this.strokeColor = new Color (0, 102, 51);
		this.key_enter = false;
		this.key_space = false;
		this.key_left = false;
		this.key_right = false;
		this.player = new Player (0, radius);
	};
	public void enter (GameContainer container, StateBasedGame game) {
		this.init (container, game);
	};
	public void update (GameContainer container, StateBasedGame game, int delta) {
		this.player.jumpDuration -= delta;
		this.player.gape = !this.key_enter;
		this.player.jump = this.key_space;
		this.player.x += this.key_right && !this.key_left ? delta * .48 : (!this.key_right && this.key_left) ? -delta * .48 : 0;
		this.player.x = (this.player.x >= width / 2) ? this.player.x % this.width - this.width : this.player.x;
		this.player.x = (this.player.x < -width / 2) ? this.player.x % this.width + this.width : this.player.x;
		this.player.y = World.jump ((float) this.player.jumpDuration / 1000, (float) this.player.radius * 4, .8f);
		this.player.direction = this.key_right && !this.key_left ? 0 : (!this.key_right && this.key_left ? 1 : this.player.direction);
		if (this.player.jump && this.player.jumpDuration <= 0) {
			this.player.jumpDuration = 800;
		};
	};
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		int width = container.getWidth ();
		int height = container.getHeight ();
		context.setAntiAlias (true);
		context.setBackground (this.backgroundColor);
		context.setLineWidth (2);
		context.setColor (this.fillColor);
		context.fillRect (0, height / 2, width, height / 2);
		context.setColor (this.strokeColor);
		context.drawLine (0, height / 2, width, height / 2);
		this.player.render (container, game, context);
	};
	public void controllerButtonPressed (int controller, int button) {
		switch (button) {
			case 1:
				this.key_enter = true;
				break;
			case 2:
			case 3:
			case 4:
				this.key_space = true;
				break;
		};
	};
	public void controllerButtonReleased (int controller, int button) {
		switch (button) {
			case 1:
				this.key_enter = false;
				break;
			case 2:
			case 3:
			case 4:
				this.key_space = false;
				break;
		};
	};
	public void controllerLeftPressed (int controller) {
		this.key_left = true;
	};
	public void controllerLeftReleased (int controller) {
		this.key_left = false;
	};
	public void controllerRightPressed (int controller) {
		this.key_right = true;
	};
	public void controllerRightReleased (int controller) {
		this.key_right = false;
	};
	public void keyPressed (int key, char c) {
		switch (key) {
			case Input.KEY_ENTER:
				this.key_enter = true;
				break;
			case Input.KEY_SPACE:
				this.key_space = true;
				break;
			case Input.KEY_LEFT:
				this.key_left = true;
				break;
			case Input.KEY_RIGHT:
				this.key_right = true;
				break;
			case Input.KEY_ESCAPE:
				this.game.enterState (AppGame.MENUS_GAMES_MENU, new FadeOutTransition (), new FadeInTransition ());
				break;
			default:
				super.keyPressed (key, c);
		};
	};
	public void keyReleased (int key, char c) {
		switch (key) {
			case Input.KEY_ENTER:
				this.key_enter = false;
				break;
			case Input.KEY_SPACE:
				this.key_space = false;
				break;
			case Input.KEY_LEFT:
				this.key_left = false;
				break;
			case Input.KEY_RIGHT:
				this.key_right = false;
		};
	};
};
