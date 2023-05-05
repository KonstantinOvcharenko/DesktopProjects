package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import objects.Ball;
import objects.SettingsPanel;

public class App extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private Boolean running = false;
	private Random r;
	Ball ball;
	Color backColor;

	public App() {
		// выбор цвета объекта
		switch (SettingsPanel.ballColor) {
		case 0:
			ball = new Ball(Window.WIDTH / 2, Window.HEIGHT / 2, new Color(139, 0, 0));
			break;
		case 1:
			ball = new Ball(Window.WIDTH / 2, Window.HEIGHT / 2, new Color(255, 140, 0));
			break;
		case 2:
			ball = new Ball(Window.WIDTH / 2, Window.HEIGHT / 2, new Color(0, 100, 0));
			break;
		case 3:
			ball = new Ball(Window.WIDTH / 2, Window.HEIGHT / 2, new Color(0, 0, 139));
			break;
		case 4:
			ball = new Ball(Window.WIDTH / 2, Window.HEIGHT / 2, new Color(0, 0, 0));
			break;
		case 5:
			ball = new Ball(Window.WIDTH / 2, Window.HEIGHT / 2, new Color(255, 200, 0));
			break;
		case 6:
			ball = new Ball(Window.WIDTH / 2, Window.HEIGHT / 2, new Color(255, 255, 255));
		}

		switch (SettingsPanel.backgroundColor) {
		case 0:
			backColor = Color.WHITE;
			break;
		case 1:
			backColor = new Color(128, 128, 128);
			break;
		case 2:
			backColor = new Color(255, 255, 173);
			break;
		case 3:
			backColor = new Color(0, 128, 0);
			break;
		case 4:
			backColor = Color.BLACK;
			break;
		}
		r = new Random();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.stop();
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
		ball.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(backColor);
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		ball.render(g);
		g.dispose();
		bs.show();
	}
}