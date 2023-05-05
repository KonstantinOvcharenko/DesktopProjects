package main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;
import javax.swing.*;
import objects.*;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	public static int selectedMode = 0;// выбранный режим тракетории
	public static int WIDTH = 800, HEIGHT = 567;// разрешение экрана
	static JFrame frame;// основное окно
	DrawPanel drawPanel;// панель отрисовки анимации
	SettingsPanel setPanel;// панель настроек
	JMenuBar menuBar;// меню
	App app;// рендеринг, запуск и остановка анимации
	JMenuItem settings;// элемент меню Настрйки
	JMenuItem exit;// элемент меню Выход
	JMenuItem start;// элемент меню Старт
	JMenuItem stop;// элемент меню Споп
	private boolean isRunning = false;// индикатор работы отрисовки анимации
	JLabel welcomeText;// надпись для текста приветствия
	JPanel textpanel;// панель для текста приветствия
	JPanel addPanel;// вспомогательная панель для выравнивания текста приветствия
	JPanel addPanel2;// вторая вспомогательная панель
	JButton begin;// кнопка начать на экране приветствия
	JButton sets;// кнопка настройки на экране приветствия

	public Window() {
		// проверить есть ли папка с настройками, если нет то создать
		File folder = new File(System.getProperty("user.dir") + "/settings");
		if (!folder.exists()) {
			folder.mkdir();
		}

		// создание загрузчика параметров приложения из .cfg-файла
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(System.getProperty("user.dir") + "/settings/mainconfig.properties");

			// загрузить файл с настройками
			prop.load(input);

			// получить значения настроек
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

		// инициализация основных компонентов приложения
		String text = "Вітаємо у програмі для тренування зору \"Динамічний тест\"!" + "<br>"
				+ "Наша програма дозволяє блаблабла" + "<br>"
				+ "Щоб почати виконання програми натисніть кнопку \"Запустити\"." + "<br>"
				+ "Щоб перейти до налаштувань натисніть кнопук \"Налаштування\".";
		drawPanel = new DrawPanel(new BorderLayout());
		setPanel = new SettingsPanel();
		app = new App();
		frame = new JFrame("Динамічний тест");
		menuBar = new JMenuBar();
		textpanel = new JPanel(new BorderLayout());
		addPanel = new JPanel(new FlowLayout());
		addPanel2 = new JPanel();
		welcomeText = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		begin = new JButton("Запуск");
		sets = new JButton("Налаштування");

		// инициализация меню
		JMenu mainMenu = new JMenu("Меню");
		JMenu program = new JMenu("Програма");
		start = new JMenuItem("Старт");
		stop = new JMenuItem("Стоп");
		settings = new JMenuItem("Налаштування");
		exit = new JMenuItem("Вихід");

		// настройки основного фрейма
		frame.setMaximumSize(new Dimension(1920, 1080));
		frame.setMinimumSize(new Dimension(800, 567));
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(menuBar);// добавляем меню

		// добавление основных компонентов на фрейм
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

		// обработчик кнопки старт
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

		// обработчик стартовой кнопки на экране приветствия
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

		// обработчик кнопки настроек на экране приветствия
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

		// обработчик кнопки стоп
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRunning = false;
				app.stop();

			}
		});

		// обработчик кнопки выхода
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		// обработчик кнопки перехода в настройки
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

	// метод для перезапуска приложения
	public static void restart() {
		frame.dispose();
		main(null);
	}

	// начало выполнения
	public static void main(String[] args) {
		new Window();
	}
}
