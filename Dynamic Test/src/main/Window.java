package main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;
import javax.swing.*;
import objects.*;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	public static int selectedMode = 0;// ��������� ����� ����������
	public static int WIDTH = 800, HEIGHT = 567;// ���������� ������
	static JFrame frame;// �������� ����
	DrawPanel drawPanel;// ������ ��������� ��������
	SettingsPanel setPanel;// ������ ��������
	JMenuBar menuBar;// ����
	App app;// ���������, ������ � ��������� ��������
	JMenuItem settings;// ������� ���� ��������
	JMenuItem exit;// ������� ���� �����
	JMenuItem start;// ������� ���� �����
	JMenuItem stop;// ������� ���� ����
	private boolean isRunning = false;// ��������� ������ ��������� ��������
	JLabel welcomeText;// ������� ��� ������ �����������
	JPanel textpanel;// ������ ��� ������ �����������
	JPanel addPanel;// ��������������� ������ ��� ������������ ������ �����������
	JPanel addPanel2;// ������ ��������������� ������
	JButton begin;// ������ ������ �� ������ �����������
	JButton sets;// ������ ��������� �� ������ �����������

	public Window() {
		// ��������� ���� �� ����� � �����������, ���� ��� �� �������
		File folder = new File(System.getProperty("user.dir") + "/settings");
		if (!folder.exists()) {
			folder.mkdir();
		}

		// �������� ���������� ���������� ���������� �� .cfg-�����
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(System.getProperty("user.dir") + "/settings/mainconfig.properties");

			// ��������� ���� � �����������
			prop.load(input);

			// �������� �������� ��������
			if (prop.getProperty("WIDTH") == null && prop.getProperty("HEIGHT") == null) {
				WIDTH = 800;
				HEIGHT = 567;
			} else {
				WIDTH = Integer.parseInt(prop.getProperty("WIDTH"));
				HEIGHT = Integer.parseInt(prop.getProperty("HEIGHT"));
			}
			if (prop.getProperty("selectedMode") == null) {
				selectedMode = 0;
			} else {
				selectedMode = Integer.parseInt(prop.getProperty("selectedMode"));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// ������������� �������� ����������� ����������
		String text = "³���� � ������� ��� ���������� ���� \"��������� ����\"!" + "<br>"
				+ "���� �������� �������� ���������" + "<br>"
				+ "��� ������ ��������� �������� �������� ������ \"���������\"." + "<br>"
				+ "��� ������� �� ����������� �������� ������ \"������������\".";
		drawPanel = new DrawPanel(new BorderLayout());
		setPanel = new SettingsPanel();
		app = new App();
		frame = new JFrame("��������� ����");
		menuBar = new JMenuBar();
		textpanel = new JPanel(new BorderLayout());
		addPanel = new JPanel(new FlowLayout());
		addPanel2 = new JPanel();
		welcomeText = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		begin = new JButton("������");
		sets = new JButton("������������");

		// ������������� ����
		JMenu mainMenu = new JMenu("����");
		JMenu program = new JMenu("��������");
		start = new JMenuItem("�����");
		stop = new JMenuItem("����");
		settings = new JMenuItem("������������");
		exit = new JMenuItem("�����");

		// ��������� ��������� ������
		frame.setMaximumSize(new Dimension(1920, 1080));
		frame.setMinimumSize(new Dimension(800, 567));
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(menuBar);// ��������� ����

		// ���������� �������� ����������� �� �����
		program.add(start);
		program.add(new JSeparator());
		program.add(stop);
		mainMenu.add(settings);
		mainMenu.add(new JSeparator());
		mainMenu.add(exit);
		menuBar.add(mainMenu);
		menuBar.add(program);
		textpanel.add(addPanel, BorderLayout.NORTH);
		textpanel.add(addPanel2, BorderLayout.CENTER);
		drawPanel.add(app);
		addPanel.add(welcomeText);
		addPanel2.add(sets);
		addPanel2.add(begin);
		drawPanel.add(textpanel, BorderLayout.NORTH);
		frame.add(drawPanel, BorderLayout.CENTER);
		frame.setVisible(true);

		// ���������� ������ �����
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRunning == false) {
					app.start();
					frame.remove(setPanel);
					textpanel.setVisible(false);
					drawPanel.remove(textpanel);
					drawPanel.setVisible(true);
					isRunning = true;
				}
			}
		});

		// ���������� ��������� ������ �� ������ �����������
		begin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRunning == false) {
					app.start();
					frame.remove(setPanel);
					textpanel.setVisible(false);
					drawPanel.remove(textpanel);
					drawPanel.setVisible(true);
					isRunning = true;
				}
			}
		});

		// ���������� ������ �������� �� ������ �����������
		sets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRunning)
					app.stop();
				frame.add(setPanel);
				drawPanel.setVisible(false);
				isRunning = false;
			}
		});

		// ���������� ������ ����
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRunning = false;
				app.stop();

			}
		});

		// ���������� ������ ������
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		// ���������� ������ �������� � ���������
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRunning)
					app.stop();
				frame.add(setPanel);
				drawPanel.setVisible(false);
				isRunning = false;
			}
		});
	}

	// ����� ��� ����������� ����������
	public static void restart() {
		frame.dispose();
		main(null);
	}

	// ������ ����������
	public static void main(String[] args) {
		new Window();
	}
}
