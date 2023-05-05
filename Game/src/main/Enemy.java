package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Enemy extends GameObject {
	Random r = new Random();
	private Color color;

	public Enemy(int x, int y, ID id, Color color) {
		super(x, y, id);
		velX = r.nextInt(11) - 5;
		velY = r.nextInt(11) - 5;
		borderX = 10;
		borderY = 10;
		this.color = color;
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

		// Check collision with borders------
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
		g.setColor(color);
		g.fillOval(x - borderX / 2, y - borderY / 2, borderX, borderY);
	}

	public GameObject checkNear(LinkedList<GameObject> objects) {
		GameObject object = null;
		for (int i = 0; i < objects.size(); i++) {
			if (((objects.get(i).getX() < x + borderX / 2) & objects.get(i).getX() > (x - borderX / 2))
					& ((objects.get(i).getY() < y + borderY / 2) & (objects.get(i).getY() > y - borderY / 2))
					& objects.get(i).getClass() != Enemy.class) {
				borderX++;
				borderY++;
				return objects.get(i);

			}

		}
		return object;
	}

	/*public void checkFriend(LinkedList<GameObject> objects) {
		for (int i = 0; i < objects.size(); i++) {
			if ((objects.get(i).getX() - objects.get(i).getBorderX() / 2 == x + borderX / 2)
					& (objects.get(i).getClass() == Enemy.class) & (objects.get(i).getId() != this.getId())) {
				velX = -velX;
			}
			if ((objects.get(i).getX() + objects.get(i).getBorderX() / 2 == x - borderX / 2)
					& (objects.get(i).getClass() == Enemy.class) & (objects.get(i).getId() != this.getId())) {
				velX = -velX;
			}
			if ((objects.get(i).getY() - objects.get(i).getBorderY() / 2 == y + borderY / 2)
					& (objects.get(i).getClass() == Enemy.class) & (objects.get(i).getId() != this.getId())) {
				velY = -velY;
			}
			if ((objects.get(i).getY() + objects.get(i).getBorderY() / 2 == y - borderY / 2)
					& (objects.get(i).getClass() == Enemy.class) & (objects.get(i).getId() != this.getId())) {
				velY = -velY;
			}
		}
	}*/
}
