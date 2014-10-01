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

/*
 * WHAT ELSE DO WE NEED? -GAME OVER SCREEN -SOME POP OUT MESSAGE/TEXT/IMAGE
 * INDICATING WHAT KIND OF SCORE DID YOU GET EX. PERFECT / WOW! EXACT MUCH GREAT
 * / MEDJO OFF BEAT LANG COOL / WALA NA SA BEAT PERO PASOK PARIN BAD / SOBRANG
 * SABIT LANG -PROPER SCORING (SEE getScore() FUNCTION ON GameFrame.java) EX.
 * 1000 = PERFECT / WOW! EXACT MUCH 700 = GREAT / MEDJO OFF BEAT LANG 500 = COOL
 * / WALA NA SA BEAT PERO PASOK PARIN 100 = BAD / SOBRANG SABIT LANG -DISPLAY
 * THE SCORE -MUSIC AND RIGHT SPEED OR TEMPO OF THE BUFFER -ERROR CHECKING MAYBE
 * :))
 */
