package arrows;

import java.awt.Dimension;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;

//SEE COMMENTS BELOW FOR THINGS TO DO (SCROLL DOWN MORE) ... :)))

public class Arrows extends GameEngine {

	public static int speed;
	public static int levelselected;
	public static int score;

	@Override
	public GameObject getGame(int gameID) {
		switch (gameID) {
		case 0:
			return new IntroMenu(this);
		case 1:
			return new GameFrame(this);
		case 2:
			return new GameOver(this);
		}
		return null;
	}

	public static void main(String[] args) {
		GameLoader game = new GameLoader();
		game.setup(new Arrows(), new Dimension(640, 640), false);
		game.start();

	}

}