package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Player extends GameObject {
	Random r = new Random();
	private int borderX;
	private int borderY;

	public Player(int x, int y, ID id) {
		super(x, y, id);
		velX = r.nextInt(10) - 5;
		velY = r.nextInt(10) - 5;

		borderX = 10;
		borderY = 10;
	}

	public void tick() {
		if (x <= Game.WIDTH - borderX / 2 & x >= borderX / 2 & y <= Game.HEIGHT - borderY / 2 & y >= borderY / 2) {
			x += velX;
			y += velY;
		}

		// Check borders------------
		if ((x + velX) + borderX / 2 > Game.WIDTH) {
			x += Game.WIDTH - x - borderX / 2;
		}
		if ((x + velX) - borderX / 2 < 0) {
			x += (0 - x) + borderX / 2;
		}
		if ((y + velY) + borderY / 2 > Game.HEIGHT - 25) {
			y += Game.HEIGHT - 25 - y - borderY / 2;
		}
		if ((y + velY) - borderY / 2 < 0) {
			y += (0 - y) + borderY / 2;
		}

		// Check collision------
		if (x - borderX / 2 == 0) {
			velX = -velX;
		}
		if (x + borderX / 2 == Game.WIDTH) {
			velX = -velX;
		}
		if (y - borderY / 2 == 0) {
			velY = -velY;
		}
		if (y + borderY / 2 == Game.HEIGHT - 25) {
			velY = -velY;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x - borderX / 2, y - borderY / 2, borderX, borderY);
	}

	public GameObject checkNear(LinkedList<GameObject> objects) {
		GameObject object = null;
		return object;
	}
}
