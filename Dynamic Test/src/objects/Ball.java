package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;
import main.App;
import main.Window;

public class Ball {
	public static int borderX, borderY;
	int x, y;
	private Color color;
	Random r = new Random();
	Polygon pol = new Polygon();
	Trajectory track;

	public Ball(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		track = new Trajectory();
		switch (SettingsPanel.objectSize) {
		case 0:
			borderX = 30 + Window.WIDTH / 100;
			borderY = 30 + Window.WIDTH / 100;
			break;
		case 1:
			borderX = 50 + Window.WIDTH / 100;
			borderY = 50 + Window.WIDTH / 100;
			break;
		case 2:
			borderX = 70 + Window.WIDTH / 100;
			borderY = 70 + Window.WIDTH / 100;
			break;
		default:
			borderX = 30 + Window.WIDTH / 100;
			borderY = 30 + Window.WIDTH / 100;
		}
		if (SettingsPanel.isRandom == 0) {
			randomize = false;
		} else if (SettingsPanel.isRandom == 1) {
			randomize = true;
		}
	}

	// Parameters of L-function
	public static int A;
	public static int B;
	public static int a = 9;
	public static int b = 8;
	public static double shift = Math.PI / 6;
	double t = 0;
	public static double SPEED = 3.0;
	long timer = System.currentTimeMillis();
	public static int random_time = 5;
	double s = SPEED;
	static boolean randomize = false;

	public void tick() {
		if (x <= Window.WIDTH - borderX / 2 & x >= borderX / 2 & y <= Window.HEIGHT - borderY / 2 & y >= borderY / 2) {
			// time between changes of speed
			if ((System.currentTimeMillis() - timer > random_time * 1000) & randomize == true) {
				s = SPEED * (r.nextDouble() + 0.001);
				timer = System.currentTimeMillis();
			}

			switch (Window.selectedMode) {
			case 0:
				// Left-right
				A = Window.WIDTH / 2 - 55;
				x = (int) (A * Math.sin(a * t)) + Window.WIDTH / 2 - 10;
				y = Window.HEIGHT / 2 - 33;
				break;
			case 1:
				// Up-down
				B = Window.HEIGHT / 3 + 20;
				x = Window.WIDTH / 2 - 10;
				y = (int) (B * Math.sin(a * t)) + Window.HEIGHT / 2 - 33;
				break;
			case 2:
				// L-function
				A = Window.WIDTH / 2 - 55;
				B = Window.HEIGHT / 3 + 20;
				x = (int) (A * Math.sin(a * t + shift)) + Window.WIDTH / 2 - 10;
				y = (int) (B * Math.sin(b * t)) + Window.HEIGHT / 2 - 33;
				break;
			case 3:
				// Ring
				A = 140 + Window.WIDTH / 6 - 70;
				B = 140 + Window.WIDTH / 6 - 70;
				x = (int) (A * Math.sin(7 * t)) + Window.WIDTH / 2 - 10;
				y = (int) (B * Math.cos(7 * t)) + Window.HEIGHT / 2 - 33;
				break;
			case 4:
				// Infinity-sign
				A = Window.WIDTH / 2 - 55;
				B = Window.HEIGHT / 6;
				x = (int) (A * Math.cos(4 * t)) + Window.WIDTH / 2 - 10;
				y = (int) (B * Math.sin(8 * t)) + Window.HEIGHT / 2 - 33;
				break;

			}
			// pol.addPoint(x, y);
			track.cut();
			track.add(x, y);
			t += 0.001 * s;// time incrementation

		}
	}

	public void render(Graphics g) {
		// draw way
		g.setColor(Color.gray);
		// g.drawPolygon(pol);
		track.drawTrack(g);

		// draw black ball for border
		g.setColor(Color.BLACK);
		g.fillOval(x - borderX / 2 - 1, y - borderY / 2 - 1, borderX + 2, borderY + 2);

		// draw ball
		g.setColor(color);
		g.fillOval(x - borderX / 2, y - borderY / 2, borderX, borderY);

		// draw cross hair
		Polygon p1 = new Polygon();
		p1.addPoint(x, y + borderY / 2);
		p1.addPoint(x, y - borderY / 2);

		Polygon p2 = new Polygon();
		p2.addPoint(x + borderX / 2, y);
		p2.addPoint(x - borderX / 2, y);

		g.setColor(Color.red);
		g.drawPolygon(p1);
		g.drawPolygon(p2);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getBorderX() {
		return borderX;
	}

	public int getBorderY() {
		return borderY;
	}
}
