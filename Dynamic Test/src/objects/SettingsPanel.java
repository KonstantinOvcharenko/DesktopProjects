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

	int selectedResIndex;// ������ ���������� ����������

	public static int backgroundColor = 0; // ������ ���������� ����� ����

	public static int ballColor = 0;// ����� �������

	public static int objectSize = 1;// ������ �������

	public static int isRandom = 0;// ������������� ��������� ����� ��������

	public static int speedRough = 0;// ����� ������������ ��������

	public static int speedSoft = 0;// ����� ������������ ��������

	// ���������� ���������� �������
	JComboBox resolutions;
	JComboBox colors;
	JComboBox modes;
	JComboBox sizes;
	JComboBox backgrounds;

	// ������� ��������� ��� �������
	String[] backColor = { "�����", "ѳ���", "��������", "�������", "������" };
	String[] ballSize = { "���������", "�������", "�������" };
	String[] mode = { "����-�������", "�����-����", "Գ���� ˳����", "����", "��������� �����" };
	String[] cols = { "��������", "������������", "�������", "����", "������", "������", "�����" };
	public static String[] res = { "800x600", "1024x768", "1366x768", "1600x900", "1920x1080" };

	// ���������� ��������� ���������
	String selectedItem = null;
	String selectedItem2 = null;
	String selectedColor = null;
	String selectedSize = null;
	String selectedBackground = null;
	JButton resButton;
	JButton saveBall;
	JCheckBox checkSpeed; // ������ ��������� ����� ��������
	JSlider setSpeedSliderRough; // ������ ������������ ��������
	JSlider setSpeedSliderSoft;// ������ ������������ ��������
	JSlider setRandomTimeSlider;// ������������ ����������� ����� ��������

	public SettingsPanel() {
		super();

		this.setLayout(new FlowLayout(FlowLayout.LEFT));// ����������� ������ �� ����� �������
		JPanel totalPanel = new JPanel(new GridLayout(0, 3, 5, 5));// ����������� ��� �������

		// ������������� �����������
		resolutions = new JComboBox(res);
		colors = new JComboBox(cols);
		modes = new JComboBox(mode);
		sizes = new JComboBox(ballSize);
		backgrounds = new JComboBox(backColor);
		saveBall = new JButton("��������");
		checkSpeed = new JCheckBox(null, null, Ball.randomize);// ������ ������������ ����� ��������
		resButton = new JButton("ϳ���������");
		JLabel resLabel = new JLabel("������ ������������:");
		JPanel SetPanel = new JPanel(new GridLayout(12, 0, 0, 5));// ������ ������� ��������
		JPanel setBallPanel = new JPanel(new GridLayout(15, 0, 0, 1));// ������ �������� ����
		JLabel setBallLabel = new JLabel("������������ �������� ��'����:");
		JLabel setSpeedLabel;// ����� �������� �������
		JLabel setRandomLabel; // ����� ������� ��������� ����� �������� �������

		// �������� ���������� ���������� ���������� �� .cfg-�����
		Properties prop = new Properties();
		Properties prop1 = new Properties();
		InputStream input = null;
		InputStream input1 = null;

		try {

			input = new FileInputStream(System.getProperty("user.dir") + "/settings/mainconfig.properties");
			input1 = new FileInputStream(System.getProperty("user.dir") + "/settings/objectconfig.properties");

			// ��������� ���� � �����������
			prop.load(input);
			prop1.load(input1);

			// �������� �������� ��������
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

		// ��������� ���������
		// �������� ����������� ��������
		setSpeedSliderRough = new JSlider(0, 10, (int) Ball.SPEED);
		setSpeedSliderSoft = new JSlider(0, 9, (int) ((Ball.SPEED - (int) Ball.SPEED) * 10));
		setSpeedSliderRough.setPaintLabels(true);
		setSpeedSliderRough.setMajorTickSpacing(1);
		setSpeedSliderSoft.setPaintLabels(true);
		setSpeedSliderSoft.setMajorTickSpacing(1);

		// �������� ����������� ������� ����� ������� ��������
		setRandomTimeSlider = new JSlider(0, 60, (int) Ball.random_time);
		setRandomTimeSlider.setPaintLabels(true);
		setRandomTimeSlider.setMajorTickSpacing(10);

		// ����� �������� �������
		setSpeedLabel = new JLabel("�������� ����:     " + Ball.SPEED + " �.");

		// ����� ������� ����� ������� ��������
		setRandomLabel = new JLabel("��� �� ������ ��������   " + Ball.random_time + " �.");

		// ���������� ����������� �� ������ ������� ��������
		SetPanel.add(resLabel);
		SetPanel.add(new JLabel("�������� ��������"));
		SetPanel.add(resolutions);
		SetPanel.add(new JLabel("�������� ���� ��'����"));
		SetPanel.add(modes);
		SetPanel.add(new JLabel("���� ����"));
		SetPanel.add(backgrounds);
		SetPanel.add(resButton);

		// ���������� ����������� �� ������ �������� ����
		setBallPanel.add(setBallLabel);
		setBallPanel.add(new JLabel("���� ��'����"));
		setBallPanel.add(colors);
		setBallPanel.add(new JLabel("����� ��'����"));
		setBallPanel.add(sizes);
		setBallPanel.add(setSpeedLabel);// ����������� ��������
		setBallPanel.add(new JLabel("�����"));
		setBallPanel.add(setSpeedSliderRough);
		setBallPanel.add(new JLabel("�'���"));
		setBallPanel.add(setSpeedSliderSoft);
		setBallPanel.add(new JLabel("��������� ���� ��������"));
		setBallPanel.add(checkSpeed);
		setBallPanel.add(setRandomLabel);
		setBallPanel.add(setRandomTimeSlider);
		setBallPanel.add(saveBall);

		// ���������� ������ ��� �������
		setBallPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		SetPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());

		// ����������� � ������ ���������� ��������� ��� ����������
		resolutions.setSelectedIndex(selectedResIndex);

		// ����������� � ������ ��������� ��������� ��� ����������
		modes.setSelectedIndex(Window.selectedMode);

		// ����������� � ������ ���������� ��������� ��� ����� ����
		colors.setSelectedIndex(ballColor);

		// ����������� � ������ ���������� ��������� ��� �������
		sizes.setSelectedIndex(objectSize);

		// ����������� � ������ ���������� ��������� ��� ����
		backgrounds.setSelectedIndex(backgroundColor);

		// ����������� ������ ������ �������
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

		// ���������� ���� ����������� �� �����������
		totalPanel.add(SetPanel);
		totalPanel.add(setBallPanel);
		this.add(totalPanel);

		// ���������� ������ �� ������ ���������� ������
		resolutions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedItem = (String) resolutions.getSelectedItem();
				selectedResIndex = resolutions.getSelectedIndex();

			}
		});

		// ���������� ������ �� ������ ����������
		modes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedItem2 = (String) modes.getSelectedItem();
				Window.selectedMode = modes.getSelectedIndex();
			}
		});

		// ���������� ������ �� ������ �����
		backgrounds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedBackground = (String) backgrounds.getSelectedItem();
				backgroundColor = backgrounds.getSelectedIndex();
			}
		});

		// ���������� ������ �� ������ ������ �������
		colors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedColor = (String) colors.getSelectedItem();
				ballColor = colors.getSelectedIndex();
			}
		});

		// ���������� ������ �� ������ �������� �������
		sizes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSize = (String) sizes.getSelectedItem();
				objectSize = sizes.getSelectedIndex();
			}
		});

		// ���������� ��������� �������� ������� (������)
		setSpeedSliderRough.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = setSpeedSliderRough.getValue();
				speedRough = value;
				Ball.SPEED = speedRough + ((double) speedSoft) / 10;
				String strSpeed = String.valueOf(Ball.SPEED);
				setSpeedLabel.setText("�������� ����: " + Ball.SPEED + " �.");
			}
		});

		// ���������� ��������� �������� ������� (������)
		setSpeedSliderSoft.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = setSpeedSliderSoft.getValue();
				speedSoft = value;
				Ball.SPEED = speedRough + ((double) speedSoft) / 10;
				String strSpeed = String.valueOf(Ball.SPEED);
				setSpeedLabel.setText("�������� ����: " + Ball.SPEED + " �.");
			}
		});

		// ���������� ��������� ������� ����� ������� �������� �������
		setRandomTimeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = setRandomTimeSlider.getValue();
				Ball.random_time = value;
				setRandomLabel.setText("��� �� ������ ��������   " + Ball.random_time + " �.");
			}
		});

		// ���������� ������ ������������� ������ ���������� ������, ���������� � ����
		resButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				OutputStream output = null;

				try {
					output = new FileOutputStream(System.getProperty("user.dir") + "/settings/mainconfig.properties");

					if (selectedItem != null) {
						// �������� �������� � properties
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

						// ���������� ������ ���������� ������ ����������
						prop.setProperty("selectedResIndex", String.valueOf(selectedResIndex));
					} else {
						prop.setProperty("WIDTH", String.valueOf(Window.WIDTH));
						prop.setProperty("HEIGHT", String.valueOf(Window.HEIGHT));
						prop.setProperty("selectedResIndex", String.valueOf(selectedResIndex));
					}

					if (selectedItem2 != null) {
						// ���������� ������ ��������� ����������
						prop.setProperty("selectedMode", String.valueOf(Window.selectedMode));
					} else {
						prop.setProperty("selectedMode", String.valueOf(Window.selectedMode));
					}

					if (selectedBackground != null) {
						// ���������� ������ ���������� ����
						prop.setProperty("backgroundColor", String.valueOf(backgroundColor));
					} else {
						prop.setProperty("backgroundColor", String.valueOf(backgroundColor));
					}

					// ��������� ���� �������� � �������� ��������
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
				Window.restart();// ������������� ���� ����� ����� ���������� ������
			}
		});

		// ���������� ������ ������������� �������� �������
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

					// ��������� ��������� ��������
					prop.setProperty("objectSpeed", String.valueOf(Ball.SPEED));

					// ��������� ��������� ����� ����� ��������
					prop.setProperty("randomTime", String.valueOf(Ball.random_time));

					// ��������� ������ ������������
					prop.setProperty("isRandom", String.valueOf(isRandom));

					// ��������� ���� �������� � �������� ��������
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
				Window.restart();// ������������� ���� ����� �������� �������� �������
			}
		});
	}
}
