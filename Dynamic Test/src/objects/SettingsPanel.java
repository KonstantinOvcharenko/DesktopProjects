package objects;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.StyledEditorKit.BoldAction;

import main.Window;

public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	int selectedResIndex;// индекс выбранного разрешения

	public static int backgroundColor = 0; // индекс выбранного цвета фона

	public static int ballColor = 0;// цвета объекта

	public static int objectSize = 1;// размер объекта

	public static int isRandom = 0;// переключатель рандомной смены скорости

	public static int speedRough = 0;// грубо регулируемая скорость

	public static int speedSoft = 0;// мягко регулируемая скорость

	// объявление выпадающих списков
	JComboBox resolutions;
	JComboBox colors;
	JComboBox modes;
	JComboBox sizes;
	JComboBox backgrounds;

	// массивы вариантов для списков
	String[] backColor = { "Білий", "Сірий", "Кремовий", "Зелений", "Чорний" };
	String[] ballSize = { "Маленький", "Середній", "Великий" };
	String[] mode = { "Зліва-направо", "Уверх-вниз", "Фігура Лісажу", "Коло", "Повернута вісімка" };
	String[] cols = { "Червоний", "Помаранчовий", "Зелений", "Синій", "Чорний", "Жовтий", "Білий" };
	public static String[] res = { "800x600", "1024x768", "1366x768", "1600x900", "1920x1080" };

	// объявление остальных элементов
	String selectedItem = null;
	String selectedItem2 = null;
	String selectedColor = null;
	String selectedSize = null;
	String selectedBackground = null;
	JButton resButton;
	JButton saveBall;
	JCheckBox checkSpeed; // флажок рандомной смены скорости
	JSlider setSpeedSliderRough; // грубый регулировщик скорости
	JSlider setSpeedSliderSoft;// мягкий регулировщик скорости
	JSlider setRandomTimeSlider;// регулировщик промежутков смены скорости

	public SettingsPanel() {
		super();

		this.setLayout(new FlowLayout(FlowLayout.LEFT));// выравнивать панели по левой стороне
		JPanel totalPanel = new JPanel(new GridLayout(0, 3, 5, 5));// суперпанель для панелей

		// инициализация компонентов
		resolutions = new JComboBox(res);
		colors = new JComboBox(cols);
		modes = new JComboBox(mode);
		sizes = new JComboBox(ballSize);
		backgrounds = new JComboBox(backColor);
		saveBall = new JButton("Зберегти");
		checkSpeed = new JCheckBox(null, null, Ball.randomize);// флажок рандомизации смены скорости
		resButton = new JButton("Підтвердити");
		JLabel resLabel = new JLabel("Головні налаштування:");
		JPanel SetPanel = new JPanel(new GridLayout(12, 0, 0, 5));// панель базовых настроек
		JPanel setBallPanel = new JPanel(new GridLayout(15, 0, 0, 1));// панель настроек шара
		JLabel setBallLabel = new JLabel("Налаштування рухомого об'єкта:");
		JLabel setSpeedLabel;// вывод скорости объекта
		JLabel setRandomLabel; // вывод времени рандомной смены скорости объекта

		// создание загрузчика параметров приложения из .cfg-файла
		Properties prop = new Properties();
		Properties prop1 = new Properties();
		InputStream input = null;
		InputStream input1 = null;

		try {

			input = new FileInputStream(System.getProperty("user.dir") + "/settings/mainconfig.properties");
			input1 = new FileInputStream(System.getProperty("user.dir") + "/settings/objectconfig.properties");

			// загрузить файл с настройками
			prop.load(input);
			prop1.load(input1);

			// получить значения настроек
			if (prop.getProperty("selectedResIndex") == null) {
			} else {
				selectedResIndex = Integer.parseInt(prop.getProperty("selectedResIndex"));
			}

			if (prop.getProperty("backgroundColor") == null) {
			} else {
				backgroundColor = Integer.parseInt(prop.getProperty("backgroundColor"));
			}

			if (prop1.getProperty("ballColor") == null) {
				ballColor = 0;
			} else {
				ballColor = Integer.parseInt(prop1.getProperty("ballColor"));
			}

			if (prop1.getProperty("objectSize") == null) {
				objectSize = 1;
			} else {
				objectSize = Integer.parseInt(prop1.getProperty("objectSize"));
			}

			if (prop1.getProperty("objectSpeed") == null) {
			} else {
				Ball.SPEED = Double.parseDouble(prop1.getProperty("objectSpeed"));
			}

			if (prop1.getProperty("randomTime") == null) {
			} else {
				Ball.random_time = Integer.parseInt(prop1.getProperty("randomTime"));
			}

			if (prop1.getProperty("isRandom") == null) {
				isRandom = 0;
			} else {
				isRandom = Integer.parseInt(prop1.getProperty("isRandom"));
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

		// настройка ползунков
		// ползунки регулировки скорости
		setSpeedSliderRough = new JSlider(0, 10, (int) Ball.SPEED);
		setSpeedSliderSoft = new JSlider(0, 9, (int) ((Ball.SPEED - (int) Ball.SPEED) * 10));
		setSpeedSliderRough.setPaintLabels(true);
		setSpeedSliderRough.setMajorTickSpacing(1);
		setSpeedSliderSoft.setPaintLabels(true);
		setSpeedSliderSoft.setMajorTickSpacing(1);

		// ползунки регулировки времени между сменами скорости
		setRandomTimeSlider = new JSlider(0, 60, (int) Ball.random_time);
		setRandomTimeSlider.setPaintLabels(true);
		setRandomTimeSlider.setMajorTickSpacing(10);

		// вывод скорости объекта
		setSpeedLabel = new JLabel("Швидкість руху:     " + Ball.SPEED + " с.");

		// вывод времени между сменами скорости
		setRandomLabel = new JLabel("Час між змінами швидкості   " + Ball.random_time + " с.");

		// добавление компонентов на панель базовых настроек
		SetPanel.add(resLabel);
		SetPanel.add(new JLabel("Роздільна здатність"));
		SetPanel.add(resolutions);
		SetPanel.add(new JLabel("Траєкторія руху об'єкта"));
		SetPanel.add(modes);
		SetPanel.add(new JLabel("Колір фону"));
		SetPanel.add(backgrounds);
		SetPanel.add(resButton);

		// добавление компонентов на панель настроек шара
		setBallPanel.add(setBallLabel);
		setBallPanel.add(new JLabel("Колір об'єкта"));
		setBallPanel.add(colors);
		setBallPanel.add(new JLabel("Розмір об'єкта"));
		setBallPanel.add(sizes);
		setBallPanel.add(setSpeedLabel);// регулировка скорости
		setBallPanel.add(new JLabel("Грубо"));
		setBallPanel.add(setSpeedSliderRough);
		setBallPanel.add(new JLabel("М'яко"));
		setBallPanel.add(setSpeedSliderSoft);
		setBallPanel.add(new JLabel("Випадкова зміна швидкості"));
		setBallPanel.add(checkSpeed);
		setBallPanel.add(setRandomLabel);
		setBallPanel.add(setRandomTimeSlider);
		setBallPanel.add(saveBall);

		// добавление границ для панелей
		setBallPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		SetPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());

		// отображение в списке выбранного последний раз разрешения
		resolutions.setSelectedIndex(selectedResIndex);

		// отображение в списке выбранной последний раз траектории
		modes.setSelectedIndex(Window.selectedMode);

		// отображение в списке выбранного последний раз цвета шара
		colors.setSelectedIndex(ballColor);

		// отображение в списке выбранного последний раз размера
		sizes.setSelectedIndex(objectSize);

		// отображение в списке выбранного последний раз фона
		backgrounds.setSelectedIndex(backgroundColor);

		// отображение флажка выбора рандома
		if (isRandom == 0) {
			checkSpeed.setSelected(false);
		} else if (isRandom == 1) {
			checkSpeed.setSelected(true);
		}

		checkSpeed.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (isRandom == 0) {
					isRandom = 1;
					Ball.randomize = true;
				} else if (isRandom == 1) {
					isRandom = 0;
					Ball.randomize = false;
				}
			}
		});

		// добавление всех компонентов на суперпанель
		totalPanel.add(SetPanel);
		totalPanel.add(setBallPanel);
		this.add(totalPanel);

		// обработчик выбора из списка разрешений экрана
		resolutions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedItem = (String) resolutions.getSelectedItem();
				selectedResIndex = resolutions.getSelectedIndex();

			}
		});

		// обработчик выбора из списка траекторий
		modes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedItem2 = (String) modes.getSelectedItem();
				Window.selectedMode = modes.getSelectedIndex();
			}
		});

		// обработчик выбора из списка фонов
		backgrounds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedBackground = (String) backgrounds.getSelectedItem();
				backgroundColor = backgrounds.getSelectedIndex();
			}
		});

		// обработчик выбора из списка цветов объекта
		colors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedColor = (String) colors.getSelectedItem();
				ballColor = colors.getSelectedIndex();
			}
		});

		// обработчик выбора из списка размеров объекта
		sizes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSize = (String) sizes.getSelectedItem();
				objectSize = sizes.getSelectedIndex();
			}
		});

		// обработчик настройки скорости объекта (грубый)
		setSpeedSliderRough.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = setSpeedSliderRough.getValue();
				speedRough = value;
				Ball.SPEED = speedRough + ((double) speedSoft) / 10;
				String strSpeed = String.valueOf(Ball.SPEED);
				setSpeedLabel.setText("Швидкість руху: " + Ball.SPEED + " с.");
			}
		});

		// обработчик настройки скорости объекта (мягкий)
		setSpeedSliderSoft.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = setSpeedSliderSoft.getValue();
				speedSoft = value;
				Ball.SPEED = speedRough + ((double) speedSoft) / 10;
				String strSpeed = String.valueOf(Ball.SPEED);
				setSpeedLabel.setText("Швидкість руху: " + Ball.SPEED + " с.");
			}
		});

		// обработчик настройки времени между сменами скорости объекта
		setRandomTimeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = setRandomTimeSlider.getValue();
				Ball.random_time = value;
				setRandomLabel.setText("Час між змінами швидкості   " + Ball.random_time + " с.");
			}
		});

		// обработчик кнопки подтверждения выбора разрешения экрана, траектории и фона
		resButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				OutputStream output = null;

				try {
					output = new FileOutputStream(System.getProperty("user.dir") + "/settings/mainconfig.properties");

					if (selectedItem != null) {
						// записать значения в properties
						switch (selectedResIndex) {
						case 0:
							prop.setProperty("WIDTH", "800");
							prop.setProperty("HEIGHT", "567");
							break;
						case 1:
							prop.setProperty("WIDTH", "1024");
							prop.setProperty("HEIGHT", "735");
							break;
						case 2:
							prop.setProperty("WIDTH", "1366");
							prop.setProperty("HEIGHT", "735");
							break;
						case 3:
							prop.setProperty("WIDTH", "1600");
							prop.setProperty("HEIGHT", "867");
							break;
						case 4:
							prop.setProperty("WIDTH", "1920");
							prop.setProperty("HEIGHT", "1047");
							break;
						}

						// записываем индекс выбранного пункта разрешения
						prop.setProperty("selectedResIndex", String.valueOf(selectedResIndex));
					} else {
						prop.setProperty("WIDTH", String.valueOf(Window.WIDTH));
						prop.setProperty("HEIGHT", String.valueOf(Window.HEIGHT));
						prop.setProperty("selectedResIndex", String.valueOf(selectedResIndex));
					}

					if (selectedItem2 != null) {
						// записываем индекс выбранной траектории
						prop.setProperty("selectedMode", String.valueOf(Window.selectedMode));
					} else {
						prop.setProperty("selectedMode", String.valueOf(Window.selectedMode));
					}

					if (selectedBackground != null) {
						// записываем индекс выбранного фона
						prop.setProperty("backgroundColor", String.valueOf(backgroundColor));
					} else {
						prop.setProperty("backgroundColor", String.valueOf(backgroundColor));
					}

					// сохранить файл настроек в корневом каталоге
					prop.store(output, null);

				} catch (IOException io) {
					io.printStackTrace();
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				}
				Window.restart();// перезапустить окно после смены разрешения экрана
			}
		});

		// обработчик кнопки подтверждения настроек объекта
		saveBall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				OutputStream output = null;

				try {
					output = new FileOutputStream(System.getProperty("user.dir") + "/settings/objectconfig.properties");

					if (selectedColor != null) {
						prop.setProperty("ballColor", String.valueOf(ballColor));
					} else {
						prop.setProperty("ballColor", String.valueOf(ballColor));
					}

					if (selectedSize != null) {
						prop.setProperty("objectSize", String.valueOf(objectSize));
					} else {
						prop.setProperty("objectSize", String.valueOf(objectSize));
					}

					// сохранить введенную скорость
					prop.setProperty("objectSpeed", String.valueOf(Ball.SPEED));

					// сохранить введенное время между рандомом
					prop.setProperty("randomTime", String.valueOf(Ball.random_time));

					// сохранить флажок рандомизации
					prop.setProperty("isRandom", String.valueOf(isRandom));

					// сохранить файл настроек в корневом каталоге
					prop.store(output, null);

				} catch (IOException io) {
					io.printStackTrace();
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				}
				Window.restart();// перезапустить окно после принятия настроек объекта
			}
		});
	}
}
