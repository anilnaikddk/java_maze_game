package maze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controls implements KeyListener {

	private Configurations conf;
	private Player player;

	public Controls(Configurations conf) {
		this.conf = conf;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyBoard.ESC) {
			System.exit(0);
		}
		// PLAYER PLAYS NOW
		if (conf.isMazeGenerated()) {
			player.move(ke.getKeyCode());
		} else {
			// INCREASE OR DECREASE MAZE GENERATION SPEED
			if (ke.getKeyCode() == KeyBoard.UP) {
				conf.pausespeed += 10;
			} else if (ke.getKeyCode() == KeyBoard.DOWN) {
				conf.pausespeed -= 10;
				if (conf.pausespeed <= 0) {
					conf.pausespeed = 1;
				}
			} else if (ke.getKeyCode() == KeyBoard.BACKSPPACE) {
				conf.pausespeed = 0;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
