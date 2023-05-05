package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class Trajectory {
	private int[] xArray;
	private int[] yArray;
	private Color trajectoryColor;

	public Trajectory() {
		xArray = new int[0];
		yArray = new int[0];

		switch (SettingsPanel.backgroundColor) {
		case 0:
			trajectoryColor = Color.DARK_GRAY;
			break;
		case 1:
			trajectoryColor = Color.LIGHT_GRAY;
			break;
		case 2:
			trajectoryColor = Color.DARK_GRAY;
			break;
		case 3:
			trajectoryColor = Color.LIGHT_GRAY;
			break;
		case 4:
			trajectoryColor = Color.LIGHT_GRAY;
			break;
		}
	}

	public void add(int x, int y) {
		int[] xtmp = new int[xArray.length + 1];
		int[] ytmp = new int[yArray.length + 1];
		System.arraycopy(xArray, 0, xtmp, 0, xArray.length);
		System.arraycopy(yArray, 0, ytmp, 0, yArray.length);
		xtmp[xtmp.length - 1] = x;
		ytmp[ytmp.length - 1] = y;
		xArray = xtmp;
		yArray = ytmp;
	}

	public void cut() {
		if (xArray.length >= 200 && yArray.length >= 200) {
			System.arraycopy(xArray, 1, xArray, 0, xArray.length - 1);
			System.arraycopy(yArray, 1, yArray, 0, yArray.length - 1);
			xArray = Arrays.copyOf(xArray, xArray.length - 1);
			yArray = Arrays.copyOf(yArray, yArray.length - 1);
		}
	}

	public void drawTrack(Graphics g) {
		if (xArray.length >= 2 & yArray.length >= 2) {
			for (int i = 1; i < xArray.length; i++) {
				g.setColor(trajectoryColor);
				g.drawLine(xArray[i], yArray[i], xArray[i - 1], yArray[i - 1]);
			}
		}
	}
}
