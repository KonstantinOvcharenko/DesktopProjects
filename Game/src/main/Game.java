package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	// private static final long serialVersionUID = 6691247796639148462L;
	public static final int WIDTH = 1024, HEIGHT = 768;
	private Thread thread;
	private Boolean running = false;
	private Random r;
	private Handler handler;
	Enemy enemy = new Enemy(512, 384, ID.Enemy, Color.green);
	Enemy enemy1 = new Enemy(400, 500, ID.Enemy1, Color.red);
	int amount_of_objects = 300;

	

	public Game() {
		new Window(WIDTH, HEIGHT, "Game", this);
		handler = new Handler();
		r = new Random();
		handler.addObject(enemy);
		handler.addObject(enemy1);
		for (int i = 0; i < amount_of_objects; i++) {
		handler.addObject(new Player(r.nextInt(WIDTH - 5) + 5, r.nextInt(HEIGHT - 5)
		 + 5, ID.Player));
		 }

	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		handler.tick();
		handler.removeObject(enemy.checkNear(handler.getAll()));
		handler.removeObject(enemy1.checkNear(handler.getAll()));
//		enemy.checkFriend(handler.object);
//		enemy1.checkFriend(handler.object);

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		handler.render(g);
		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		new Game();
	}
}
