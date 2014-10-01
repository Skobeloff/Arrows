package arrows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.font.SystemFont;

public class GameFrame extends GameObject {

	private static final String BG = "./resources/img/bg.png";
	private static final String BUFFER = "./resources/img/buffer.png";
	private static final String HIT = "./resources/img/hit.png";
	private static final String HIT2 = "./resources/img/hit2.png";

	private static final String UP = "./resources/img/up.png";
	private static final String DOWN = "./resources/img/down.png";
	private static final String LEFT = "./resources/img/left.png";
	private static final String RIGHT = "./resources/img/right.png";

	private static final String UP2 = "./resources/img/up2.png";
	private static final String DOWN2 = "./resources/img/down2.png";
	private static final String LEFT2 = "./resources/img/left2.png";
	private static final String RIGHT2 = "./resources/img/right2.png";

	private static final String SFX = "./resources/snd/Click.wav";
	private static final String MUSIC1 = "./resources/snd/piano_introduction.mid";
	private static final String MUSIC2 = "./resources/snd/terra_no_kesshin.mid";
	private static final String MUSIC3 = "./resources/snd/ffxiii_flash.mid";

	private static final String BAD = "./resources/img/bad.png";
	private static final String GREAT = "./resources/img/great.png";
	private static final String COOL = "./resources/img/cool.png";
	private static final String PERFECT = "./resources/img/perfect.png";
	private static final String MISS = "./resources/img/miss.png";
	private static final String BLANK = "./resources/img/blank.png";

	private int dimension = 10;

	private Block background;
	private Block hit;
	private Block buffer;
	private Block rating;

	private Block[] arrows = new Block[7];
	private int[] arrowsID = new int[7];

	private SpriteGroup ARROWS;

	private int pressed = 0;
	private int counter = 0; // increments when you typed correctly

	private int score = 0;
	private int secCtr;
	private Timer secTimer;

	private int startX = 22; // starting position of buffer
	private int endX = 582; // end position of buffer
	private int lowerRange = 450; // lower range of score area
	private int upperRange = 540; // upper range of score area
	private int range = (upperRange - lowerRange) / 7;

	boolean created = false;
	boolean scored = false;

	private int speed;
	private Timer delay;
	
	private SystemFont font1;
	private SystemFont font2;

	public GameFrame(GameEngine parent) {
		super(parent);
	}

	@Override
	public void initResources() {
		background = new Block(getImage(BG), 0, 0);
		hit = new Block(getImage(HIT), 481, 352);
		buffer = new Block(getImage(BUFFER), startX, 352);
		font1 = new SystemFont(new Font("VCR OSD MONO", Font.BOLD, 60), Color.RED);
		font2 = new SystemFont(new Font("VCR OSD MONO", Font.BOLD, 36), Color.GRAY);

		rating = new Block(getImage(BLANK), 0, 0);
		speed = Arrows.speed;
		delay = new Timer(speed);
		if (Arrows.levelselected == 1) {
			playMusic(MUSIC1);
			secCtr = 60 + 39;
			secTimer = new Timer(1000);
		} else if (Arrows.levelselected == 2) {
			playMusic(MUSIC2);
			secCtr = 60 + 20;
			secTimer = new Timer(1000);
		} else if (Arrows.levelselected == 3) {
			playMusic(MUSIC3);
			secCtr = 60 + 60 + 32;
			secTimer = new Timer(1000);
		}

		createSet();
	}

	@Override
	public void render(Graphics2D gd) {
		background.render(gd);
		hit.render(gd);
		buffer.render(gd);
		ARROWS.render(gd);
		rating.render(gd);
		
		font1.drawString(gd, ""+score, 30, 444);
		font2.drawString(gd, "SCORE", 50, 505);
		font1.drawString(gd, ""+secCtr, 475, 444);
		font2.drawString(gd, "TIMER", 475, 505);
	}

	@Override
	public void update(long l) {
		if (secTimer.action(l)) {
			displayTime();
		}
		if (delay.action(l))
			moveBuffer();

		readInput();
		background.update(l);
		hit.update(l);
		buffer.update(l);
		ARROWS.update(l);
		if (secCtr == 0) {
			Arrows.score = score;
			parent.nextGameID = 2;
			finish();
		}

	}

	public void displayTime() {
		secCtr--;
	}

	// creating a random set of arrows
	public void createSet() {
		int max = 4;
		int min = 1;
		int range = max - min + 1;
		int rand;
		int x = 26;

		counter = 0;
		ARROWS = new SpriteGroup("Arrows");

		for (int i = 0; i < 7; i++) {
			rand = (int) (Math.random() * range) + min;
			arrowsID[i] = rand;
			arrows[i] = new Block(getImage(assignArrows(rand)), x, 242);
			x += 85;
			ARROWS.add(arrows[i]);
		}
	}

	public String assignArrows(int x) {
		String ARROW = "";
		switch (x) {
		case 1:
			ARROW = UP;
			break;
		case 2:
			ARROW = RIGHT;
			break;
		case 3:
			ARROW = DOWN;
			break;
		case 4:
			ARROW = LEFT;
			break;
		}
		return ARROW;
	}

	public void readInput() {

		if (keyPressed(KeyEvent.VK_UP)) {
			if (counter == 7) {
				counter = 0;
				resetArrows();
			} else {
				pressed = 1;
				checkInput();
			}

		} else if (keyPressed(KeyEvent.VK_RIGHT)) {
			if (counter == 7) {
				counter = 0;
				resetArrows();
			} else {
				pressed = 2;
				checkInput();
			}
		} else if (keyPressed(KeyEvent.VK_DOWN)) {
			if (counter == 7) {
				counter = 0;
				resetArrows();
			} else {
				pressed = 3;
				checkInput();
			}
		} else if (keyPressed(KeyEvent.VK_LEFT)) {
			if (counter == 7) {
				counter = 0;
				resetArrows();
			} else {
				pressed = 4;
				checkInput();
			}
		} else if (keyPressed(KeyEvent.VK_SPACE)) {
			hit.setImage(getImage(HIT2));
			playSound(SFX);
			if (counter == 7 && lowerRange < buffer.getX()
					&& buffer.getX() < upperRange) {
				getScore();
				createSet();
				scored = true;
			} else {
				rating.setImage(getImage(MISS));
				resetArrows();
			}
		} else if (keyPressed(KeyEvent.VK_ESCAPE)) {
			parent.nextGameID = 0;
			finish();
		}

	}

	public void checkInput() {
		switch (pressed) {
		case 1:
			if (arrowsID[counter] == 1) {
				arrows[counter].setImage(getImage(UP2));
				counter++;
			} else {
				counter = 0;
				resetArrows();
			}
			break;
		case 2:
			if (arrowsID[counter] == 2) {
				arrows[counter].setImage(getImage(RIGHT2));
				counter++;
			} else {
				counter = 0;
				resetArrows();
			}
			break;
		case 3:
			if (arrowsID[counter] == 3) {
				arrows[counter].setImage(getImage(DOWN2));
				counter++;
			} else {
				counter = 0;
				resetArrows();
			}
			break;
		case 4:
			if (arrowsID[counter] == 4) {
				arrows[counter].setImage(getImage(LEFT2));
				counter++;
			} else {
				counter = 0;
				resetArrows();
			}
			break;
		}
	}

	public void resetArrows() {
		for (int i = 0; i < 7; i++) {
			arrows[i].setImage(getImage(assignArrows(arrowsID[i])));
		}
		scored = false;
	}

	public void moveBuffer() {
		if (buffer.getX() < endX) {
			buffer.setX(buffer.getX() + dimension);
		} else {
			buffer.setX(startX);
			created = false;
			scored = false;
			hit.setImage(getImage(HIT));
		}

		if (buffer.getX() > upperRange && created == false) {

			if (counter < 7 && scored == false) {
				rating.setImage(getImage(MISS));
			}

			created = true;
			createSet();

		}
	}

	public void getScore() {
		if (buffer.getX() >= lowerRange && buffer.getX() <= lowerRange + range || 
		buffer.getX() <= upperRange && buffer.getX() >= upperRange - range) {
			rating.setImage(getImage(BAD));
			score += 25;
		} else if (buffer.getX() >= lowerRange && buffer.getX() <= lowerRange + range * 2 || 
		buffer.getX() <= upperRange && buffer.getX() >= upperRange - range * 2) {
			rating.setImage(getImage(COOL));
			score += 50;
		} else if (buffer.getX() >= lowerRange && buffer.getX() <= lowerRange + range * 3 || 
		buffer.getX() <= upperRange && buffer.getX() >= upperRange - range * 3) {
			rating.setImage(getImage(GREAT));
			score += 75;
		} else if (buffer.getX() >= lowerRange && buffer.getX() <= lowerRange + range * 4 || 
		buffer.getX() <= upperRange && buffer.getX() >= upperRange - range * 4) {
			rating.setImage(getImage(PERFECT));
			score += 100;
		}

	}

}
