import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SolarSystem extends JFrame {
	private JLabel lblSola = new JLabel("태양");
	private JLabel lblEarth = new JLabel("지구");
	
	private JLabel lblMoon1 = new JLabel("달");
	private JLabel lblMoon2 = new JLabel("달");
	
	private JLabel lblMercury = new JLabel("수성");
	private JLabel lblVenus = new JLabel("금성");
	JButton btn_Start = new JButton("Start");
	JButton btn_Pause = new JButton("Pause");
	JButton btn_Exit = new JButton("Exit");

	int left, top;
	int moonLeft, moonTop;
	double angle;
	boolean a = true;
	double angle1, angle2, angle3, angle4;
	double moonAngle1;
	double moonAngle2;

	void move() {
		ActionListener ac = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				Thread Mercury = new Thread(new Runnable() {
					@Override
					public void run() {
						while (a) {
							int left = 450 + (int) (172 * Math.cos(angle1));
							int top = 455 + (int) (160 * Math.sin(angle1));
							lblMercury.setLocation(left, top);
							angle1 += 0.0000002;
						}
					}
				});

				Thread Venus = new Thread(new Runnable() {
					@Override
					public void run() {
						while (a) {
							int left = 440 + (int) (253 * Math.cos(angle2));
							int top = 350 + (int) (257 * Math.sin(angle2));
							lblVenus.setLocation(left, top);
							angle2 += 0.00000011;

						}
					}
				});
				
				Thread Earth = new Thread(new Runnable() {
					@Override
					public void run() {
						while (a) {
							int left = 445 + (int) (450 * Math.cos(angle3));
							int top = 450 + (int) (500 * Math.sin(angle3));
							lblEarth.setLocation(left, top);
							angle3 += 0.0000001;
						}
					}
				});

				Thread Moon1 = new Thread(new Runnable() {
					@Override
					public void run() {
						while (a) {
							int moonLeft = lblEarth.getLocation().x + (int) (80 * Math.cos(moonAngle1));
							int moonTop = lblEarth.getLocation().y + (int) (80 * Math.sin(moonAngle1));
							lblMoon1.setLocation(moonLeft, moonTop);
							moonAngle1 += 0.0000003;
							System.out.println("달1출력");
						}
					}
				});
				
				Thread Moon2 = new Thread(new Runnable() {
					@Override
					public void run() {
						while (a) {
							int moonLeft = lblEarth.getLocation().x + (int) (80 * Math.cos(moonAngle2));
							int moonTop = lblEarth.getLocation().y + (int) (80 * Math.sin(moonAngle2));
							lblMoon2.setLocation(moonLeft, moonTop);
							moonAngle2 += 0.000001;
							System.out.println("달2출력");
						}
					}
				});
				
				if (src == btn_Start) {
					a = true;
					Mercury.start();
					Venus.start();
					Earth.start();
					Moon1.start();
					Moon2.start();

				} else if (src == btn_Pause) {
					a = false;
				}

				else if (src == btn_Exit) {
					System.exit(0);
				}
			}
		};
		btn_Start.addActionListener(ac);
		btn_Pause.addActionListener(ac);
		btn_Exit.addActionListener(ac);
	}
	void init() {
		getContentPane().setLayout(null);
		getContentPane().add(lblSola);
		getContentPane().add(lblEarth);
		
		getContentPane().add(lblMoon1);
		getContentPane().add(lblMoon2);
		
		getContentPane().add(lblMercury);
		getContentPane().add(lblVenus);
		getContentPane().add(btn_Start);
		getContentPane().add(btn_Pause);
		getContentPane().add(btn_Exit);

		lblSola.setIcon(new ImageIcon("src/sun1.png"));
		lblSola.setBounds(420, 420, 120, 120);

		lblMoon1.setIcon(new ImageIcon("src/moon1.png"));
		lblMoon1.setBounds(420, 420, 30, 30);
		
		lblMoon2.setIcon(new ImageIcon("src/moon1.png"));
		lblMoon2.setBounds(420, 420, 40, 40);

		lblEarth.setIcon(new ImageIcon("src/bluemarble1.png"));
		lblEarth.setBounds(420, 420, 60, 60);

		lblMercury.setIcon(new ImageIcon("src/mercury1.png"));
		lblMercury.setBounds(420, 420, 30, 30);

		lblVenus.setIcon(new ImageIcon("src/Venus1.png"));
		lblVenus.setBounds(420, 420, 35, 35);

		btn_Start.setBounds(50, 40, 111, 50);
		btn_Pause.setBounds(50, 101, 111, 50);
		btn_Exit.setBounds(50, 161, 111, 50);

		setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	SolarSystem() {
		getContentPane().setBackground(Color.BLACK);
		init();
		move();
	}

	public static void main(String[] args) {
		new SolarSystem();
	}
}
