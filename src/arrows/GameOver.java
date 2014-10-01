package arrows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.font.SystemFont;

import java.awt.event.KeyEvent;

public class GameOver extends GameObject {

    private String BG = "./resources/img/gameover.png";
    private Block gameover;
    private SystemFont font;
	
	public GameOver(GameEngine parent) {
		super(parent);
	}

	@Override
	public void initResources() {
		gameover = new Block(getImage(BG), 0, 0);
		font = new SystemFont(new Font("VCR OSD MONO", Font.BOLD, 80), Color.WHITE);
	}

	@Override
	public void render(Graphics2D gd) {
		gameover.render(gd);
		font.drawString(gd, ""+Arrows.score, 290, 340);
	}

	@Override
	public void update(long l) {
		if (keyPressed(KeyEvent.VK_ENTER)) {
                    parent.nextGameID = 0;
                    finish(); 
                    
                }
        }

}
