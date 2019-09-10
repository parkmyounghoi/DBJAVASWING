import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

import javax.swing.*;

public class Ex01 extends JFrame implements Runnable, ActionListener, KeyListener {
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image left_img, right_img, short_img;
	Graphics buffg = null;
	Image buffImage;
	boolean short_count, short_check;
	Stack<Short_xy> stack = new Stack<>();
	//���̿켱Ž�� ������� ��ǥ������ ������ ����
	Stack<Short_xy> stack2 = new Stack<>();
	//���̿켱Ž�� ������� ������� ��ǥ�� ������ �ݴ�� �ϱ����� ����
	int left_x = 1, left_y = 2;
	int right_x = 1, right_y = 2;
	int short_x = 1, short_y = 2;
	int short2_x = 1, short2_y = 2;
	int exit_x = 18, exit_y = 19;
	int min = 0, sec = 0, sec_count = 0;
	int left_char_x = 50, left_char_y = 100;
	int right_char_x = 50, right_char_y = 100;
	int short_char_x = 50, short_char_y = 100;
	boolean left_exit = true, left_move;
	boolean right_exit = true, right_move;
	boolean short_exit = true, short_move;
	boolean left_up, left_down, left_left, left_right;
	boolean right_up, right_down, right_left, right_right;
	boolean short_up, short_down, short_left, short_right;
	boolean pause;
	boolean timer_start;
	int[][] tile = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		   ,{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1}
		   ,{1, 2, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1}
		   ,{1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1}
		   ,{1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1}
		   ,{1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1}
		   ,{1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1}
		   ,{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1}
		   ,{1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1}
		   ,{1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1}
		   ,{1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1}
		   ,{1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1}
		   ,{1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1}
		   ,{1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1}
		   ,{1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1}
		   ,{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1}
		   ,{1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1}
		   ,{1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}
		   ,{1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1}
		   ,{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	boolean[][] tile_visit = new boolean[20][21];
	JFrame f = new JFrame();
	JButton btn1 = new JButton("START");
	JButton btn2 = new JButton("RESET");
	JButton btn3 = new JButton("�������缳��");
	JLabel lab1 = new JLabel();
	JLabel lab2 = new JLabel();
	JLabel lab3 = new JLabel();
	JLabel timer = new JLabel("00:00");
	JLabel state = new JLabel("�����");
	JCheckBox cb1 = new JCheckBox("�¼���",false);
	JCheckBox cb2 = new JCheckBox("�켱��",false);
	JCheckBox cb3 = new JCheckBox("���̿켱",false);
	ImageIcon icon1 = new ImageIcon("src/p1_front.png");
	ImageIcon icon2 = new ImageIcon("src/p2_front.png");
	ImageIcon icon3 = new ImageIcon("src/p3_front.png");
	JTextArea ta = new JTextArea(25, 50);
	JScrollPane sc = new JScrollPane(ta);
	Font font = new Font("����", Font.BOLD, 15);
	/*
	 * Ÿ��	
	 * 0	- ��
	 * 1	- ��
	 * 2	- �����
	 * 3	- ������
	 */
	Ex01() {
		addKeyListener(this);
		f.setSize(300, 1000);
		f.setTitle("������");
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.add(btn1);
		f.add(btn2);
		f.add(lab1);
		f.add(lab2);
		f.add(lab3);
		f.add(cb1);
		f.add(cb2);
		f.add(cb3);
		f.add(sc);
		f.add(timer);
		f.add(state);
		f.add(btn3);
		f.setLayout(null);
		ta.setEditable(false);
		//textarea �Է°���(true), �ԷºҰ���(false)
		btn1.setBounds(0, 500, 150, 100);
		btn2.setBounds(150, 500, 150, 100);
		btn3.setBounds(0, 450, 300, 50);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		sc.setBounds(0, 600, 295, 390);
		lab1.setIcon(icon1);
		lab2.setIcon(icon2);
		lab3.setIcon(icon3);
		lab1.setBounds(15, 250, 100, 100);
		lab2.setBounds(115, 250, 100, 100);
		lab3.setBounds(215, 250, 100, 100);
		timer.setBounds(0, 0, 300, 200);
		state.setBounds(0, 100, 300, 200);
		timer.setFont(new Font("����", Font.BOLD, 60));
		state.setFont(new Font("����", Font.BOLD, 60));
		state.setHorizontalAlignment(JLabel.CENTER);
		timer.setHorizontalAlignment(JLabel.CENTER);
		cb1.setBounds(25, 370, 70, 20);
		cb2.setBounds(115, 370, 70, 20);
		cb3.setBounds(205, 370, 80, 20);
		sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//���� ��ũ�ѹٸ� �����ǵ��� ��
		ta.setLineWrap(true);
		ta.setFont(font);
		setSize(1000, 1000);
		setTitle("�̷�ã��");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		new Thread(this).start();
		//������ ����
		for(int x=0; x<20; x++) { //�̷� �迭�� 1��(��)���� ������ ��ǥ������ �о� �湮�迭�� true�� ����
			for(int y=0; y<21; y++) {
				if(tile[x][y] == 1) {
					tile_visit[x][y] = true;
				} else {
					tile_visit[x][y] = false;
				}
			}
		}
	}
	public void paint(Graphics g) {
		buffImage = createImage(1000, 1000);
		buffg = buffImage.getGraphics();
		update(g);
	}
	public void update(Graphics g) {
		buffg.setFont(new Font("����", Font.BOLD, 30));
		for(int x=0; x<20; x++) {
			for(int y=0; y<20; y++) {
				//�̷ι迭�� �о 0���� �⺻�� �̹���, 1���� �� �̹���, 2���� ����� �̹���, 3���� ������ �̹����� ����
				if(tile[y][x] == 0 || tile[y][x] >= 4) {
					left_img = tk.getImage("src/castleCenter.png");
					buffg.drawImage(left_img, x*50, y*50, this);
				} else if(tile[y][x] == 1) {
					left_img = tk.getImage("src/liquidWater.png");
					buffg.drawImage(left_img, x*50, y*50, this);
				} else if(tile[y][x] == 2) {
					left_img = tk.getImage("src/castleCenter.png");
					buffg.drawImage(left_img, x*50, y*50, this);
					left_img = tk.getImage("src/window.png");
					buffg.drawImage(left_img, x*50, y*50, this);
				} else if(tile[y][x] == 3) {
					left_img = tk.getImage("src/castleCenter.png");
					buffg.drawImage(left_img, x*50, y*50, this);
					left_img = tk.getImage("src/signExit.png");
					buffg.drawImage(left_img, x*50, y*50, this);
				}
				if(left_exit == false) {
					//ĳ���� �̹��� �׸��� �޼ҵ�
					//������ ���⿡ ���� �̹����� ������
					left_img = tk.getImage("src/p1_right.png");
					if(left_left == true) {
						left_img = tk.getImage("src/p1_left.png");
					} else if(left_up == true) {
						left_img = tk.getImage("src/p1_up.png");
					} else if(left_down == true) {
						left_img = tk.getImage("src/p1_down.png");
					}
					buffg.drawImage(left_img, left_char_x, left_char_y, this);
					buffg.drawString("�¼���", left_char_x-25, left_char_y);
				}
				if(right_exit == false) {
					right_img = tk.getImage("src/p2_right.png");
					if(right_left == true) {
						right_img = tk.getImage("src/p2_left.png");
					} else if(left_up == true) {
						right_img = tk.getImage("src/p2_up.png");
					} else if(left_down == true) {
						right_img = tk.getImage("src/p2_down.png");
					}
					buffg.drawImage(right_img, right_char_x, right_char_y, this);
					buffg.drawString("�켱��", right_char_x-25, right_char_y);
				}
				if(short_exit == false) {
					short_img = tk.getImage("src/p3_right.png");
					if(short_left == true) {
						short_img = tk.getImage("src/p3_left.png");
					} else if(short_up == true) {
						short_img = tk.getImage("src/p3_up.png");
					} else if(short_down == true) {
						short_img = tk.getImage("src/p3_down.png");
					}
					buffg.drawImage(short_img, short_char_x, short_char_y, this);
					buffg.drawString("���̿켱", short_char_x-25, short_char_y);
				}
			}
		}
		g.drawImage(buffImage, 0, 0, this);
		//�����̹����� �׸� �̹������� �����ӱ׷��ȿ� �׸�
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				if(pause == false) {
					state.setText("�����");
				} else {
					state.setText("�Ͻ�����");
				}
				ta.setCaretPosition(ta.getDocument().getLength());
				//jtextarea�� ������ �Ʒ��� �ʰ��ɶ� �ڵ����� ��ũ���� �ǹ����� ������
				f.setLocation(getLocation().x+getWidth()+9, getLocation().y);
				//���� �������� ��ǥ�� �ٲ𶧸��� ������������ ������������ ��ǥ���� �޾Ƽ� �̵���
				tile[exit_x][exit_y] = 3;
				repaint();
				//ȭ�� �׸���
				if(pause == false) {
					if(left_exit == false) {
						left_Check();
						left_move();
					}
					if(right_exit == false) {
						right_Check();
						right_move();
					}
					short_Check();
					if(short_exit == false) {
						short_move();
					}
					if(timer_start == true) {
						Timer();					
					}
				}
				//pause ������ false�϶��� �˰��� ����
				//true�ϰ�� ���길 ���߰� ȭ�� �׸���� ��ӵ�
				Thread.sleep(16);
				//������ 16�и��� ���� ���
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void left_move() {
		//�¼��� �̵��޼ҵ�
		if(left_right == true) {
			left_char_x += 5;
			if(left_char_x % 50 == 0) {
				left_x++;
				ta.append("[�¼���] x = " + left_x + ", y = " + left_y + "\n");
				left_move = false;
			}
		}
		if(left_left == true) {
			left_char_x -= 5;
			if(left_char_x % 50 == 0) {
				left_x--;
				ta.append("[�¼���] x = " + left_x + ", y = " + left_y + "\n");
				left_move = false;			
			}
		}
		if(left_up == true) {
			left_char_y -= 5;
			if(left_char_y % 50 == 0) {
				left_y--;
				ta.append("[�¼���] x = " + left_x + ", y = " + left_y + "\n");
				left_move = false;			
			}
		}
		if(left_down == true) {
			left_char_y += 5;
			if(left_char_y % 50 == 0) {
				left_y++;
				ta.append("[�¼���] x = " + left_x + ", y = " + left_y + "\n");
				left_move = false;
			}
		}
		if(tile[left_y][left_x] == 3) {
			ta.append("[�¼���] "+ min + "�� "+sec+"�� Ż��\n");
			left_exit = true;
			left_move = true;
		}
		
	}
	public void left_Check() {
		
		if(left_move == false) {
			
			boolean Check_left, Check_right, Check_up, Check_down;
			
			Check_up = tile[left_y-1][left_x] == 1 ? true : false;
			Check_down = tile[left_y+1][left_x] == 1 ? true : false;
			Check_left = tile[left_y][left_x-1] == 1 ? true : false;
			Check_right = tile[left_y][left_x+1] == 1 ? true : false;
			//���� �����迭�� ���� �ִ��� Ȯ����
			//���� �ִٸ� true, ������ false
			
			if(tile[left_y][left_x+1] == 3) {
				//�����迭�� �������� �ִ��� Ȯ���ϰ�
				//�������� �ִٸ� �ֿ켱������ �������� �̵�
				left_right = true;
				left_up = false;
				left_down = false;
				left_left = false;
				left_move = true;
			} else if(tile[left_y][left_x-1] == 3) {
				left_left = true;
				left_right = false;
				left_up = false;
				left_down = false;
				left_move = true;
			} else if(tile[left_y+1][left_x] == 3) {
				left_down = true;
				left_right = false;
				left_up = false;
				left_left = false;
				left_move = true;
			} else if(tile[left_y-1][left_x] == 3) {
				left_up = true;
				left_right = false;
				left_down = false;
				left_left = false;
				left_move = true;
			} else if(tile[left_y+1][left_x] == 0 && left_left == true) {
				//�����迭�� �������� ���ٸ�
				//���� ���ϰ� �ִ� ���⿡�� �ݽð���� 90���� ���� �ִ��� Ȯ����
				//���� �ִٸ� ���ʺ��� Ÿ���ϱ⿡ �������� �̵�
				left_right = false;
				left_up = false;
				left_down = true;
				left_left = false;
				left_move = true;
			} else if(tile[left_y][left_x+1] == 0 && left_down == true) {
				left_right = true;
				left_up = false;
				left_down = false;
				left_left = false;
				left_move = true;
			} else if(tile[left_y][left_x-1] == 0 && left_up == true) {
				left_right = false;
				left_up = false;
				left_down = false;
				left_left = true;
				left_move = true;
			} else if(tile[left_y-1][left_x] == 0 && left_right == true) {
				left_right = false;
				left_up = true;
				left_down = false;
				left_left = false;
				left_move = true;
			} else if(Check_left == true && Check_down == true && tile[left_y-1][left_x] == 0 && left_left == true) {
				//�����迭�� �������� ����, ���� �̵����� ������ �ݽð� 90�� ���⿡�� ���̾����鼭
				//���� �̵����� ���⿡ ���� ������� �ð���� 90�� �������� �̵���
				left_right = false;
				left_up = true;
				left_down = false;
				left_left = false;
				left_move = true;
			} else if(Check_down == true && Check_right == true && tile[left_y][left_x-1] == 0 && left_down == true) {
				left_right = false;
				left_up = false;
				left_down = false;
				left_left = true;
				left_move = true;
			} else if(Check_up == true && Check_left == true && tile[left_y][left_x+1] == 0 && left_up == true) {
				left_right = true;
				left_up = false;
				left_down = false;
				left_left = false;
				left_move = true;
			} else if(Check_right == true && Check_up == true && tile[left_y+1][left_x] == 0 && left_right == true) {
				left_right = false;
				left_up = false;
				left_down = true;
				left_left = false;
				left_move = true;
			} else if(Check_right == true && Check_up == true && Check_left == true) {
				//����� ���� ���ϰ�� ����ִ� �������� �̵���
				left_right = false;
				left_up = false;
				left_down = true;
				left_left = false;
				left_move = true;
			} else if(Check_down == true && Check_up == true && Check_left == true) {
				left_right = true;
				left_up = false;
				left_down = false;
				left_left = false;
				left_move = true;
			} else if(Check_down == true && Check_right == true && Check_left == true) {
				left_right = false;
				left_up = true;
				left_down = false;
				left_left = false;
				left_move = true;
			} else if(Check_right == true && Check_up == true && Check_down == true) {
				left_right = false;
				left_up = false;
				left_down = false;
				left_left = true;
				left_move = true;
			}
		}
	}
	public void right_move() {
		if(right_right == true) {
			right_char_x += 5;
			if(right_char_x % 50 == 0) {
				right_x++;
				ta.append("[�켱��] x = " + right_x + ", y = " + right_y + "\n");
				right_move = false;				
			}
		}
		if(right_left == true) {
			right_char_x -= 5;
			if(right_char_x % 50 == 0) {
				right_x--;
				ta.append("[�켱��] x = " + right_x + ", y = " + right_y + "\n");
				right_move = false;							
			}
		}
		if(right_up == true) {
			right_char_y -= 5;
			if(right_char_y % 50 == 0) {
				right_y--;
				ta.append("[�켱��] x = " + right_x + ", y = " + right_y + "\n");
				right_move = false;							
			}
		}
		if(right_down == true) {
			right_char_y += 5;
			if(right_char_y % 50 == 0) {
				right_y++;
				ta.append("[�켱��] x = " + right_x + ", y = " + right_y + "\n");
				right_move = false;		
			}
		}
		if(tile[right_y][right_x] == 3) {
			ta.append("[�켱��] "+ min + "�� "+sec+"�� Ż��\n");
			right_exit = true;
		}		
	}
	public void right_Check() {
		
		if(right_move == false) {
			
			boolean Check_left, Check_right, Check_up, Check_down;
			
			Check_up = tile[right_y-1][right_x] == 1 ? true : false;
			Check_down = tile[right_y+1][right_x] == 1 ? true : false;
			Check_left = tile[right_y][right_x-1] == 1 ? true : false;
			Check_right = tile[right_y][right_x+1] == 1 ? true : false;
			
			if(tile[right_y][right_x+1] == 3) {
				right_right = true;
				right_up = false;
				right_down = false;
				right_left = false;
				right_move = true;
			} else if(tile[right_y][right_x-1] == 3) {
				right_left = true;
				right_right = false;
				right_up = false;
				right_down = false;
				right_move = true;
			} else if(tile[right_y+1][right_x] == 3) {
				right_down = true;
				right_right = false;
				right_up = false;
				right_left = false;
				right_move = true;
			} else if(tile[right_y-1][right_x] == 3) {
				right_up = true;
				right_right = false;
				right_down = false;
				right_left = false;
				right_move = true;
			} else if(tile[right_y-1][right_x] == 0 && right_left == true) {
				right_right = false;
				right_up = true;
				right_down = false;
				right_left = false;
				right_move = true;
			} else if(tile[right_y][right_x-1] == 0 && right_down == true) {
				right_right = false;
				right_up = false;
				right_down = false;
				right_left = true;
				right_move = true;
			} else if(tile[right_y][right_x+1] == 0 && right_up == true) {
				right_right = true;
				right_up = false;
				right_down = false;
				right_left = false;
				right_move = true;
			} else if(tile[right_y+1][right_x] == 0 && right_right == true) {
				right_right = false;
				right_up = false;
				right_down = true;
				right_left = false;
				right_move = true;
			} else if(Check_left == true && Check_up == true && tile[right_y+1][right_x] == 0 && right_left == true) {
				right_right = false;
				right_up = false;
				right_down = true;
				right_left = false;
				right_move = true;
			} else if(Check_down == true && Check_left == true && tile[right_y][right_x+1] == 0 && right_down == true) {
				right_right = true;
				right_up = false;
				right_down = false;
				right_left = false;
				right_move = true;
			} else if(Check_up == true && Check_right == true && tile[right_y][right_x-1] == 0 && right_up == true) {
				right_right = false;
				right_up = false;
				right_down = false;
				right_left = true;
				right_move = true;
			} else if(Check_right == true && Check_down == true && tile[right_y-1][right_x] == 0 && right_right == true) {
				right_right = false;
				right_up = true;
				right_down = false;
				right_left = false;
				right_move = true;
			} else if(Check_right == true && Check_up == true && Check_left == true) {
				right_right = false;
				right_up = false;
				right_down = true;
				right_left = false;
				right_move = true;
			} else if(Check_down == true && Check_up == true && Check_left == true) {
				right_right = true;
				right_up = false;
				right_down = false;
				right_left = false;
				right_move = true;
			} else if(Check_down == true && Check_right == true && Check_left == true) {
				right_right = false;
				right_up = true;
				right_down = false;
				right_left = false;
				right_move = true;
			} else if(Check_right == true && Check_up == true && Check_down == true) {
				right_right = false;
				right_up = false;
				right_down = false;
				right_left = true;
				right_move = true;
			}
		}
	}
	public void short_move() {
		//���̿켱Ž������ ã�Ƴ� ��δ�� �̵���
		if(stack2.isEmpty() != true && short_move == false) {
			//���� ����2�� ���� ���������� �����
			Short_xy sxy = stack2.pop();
			//����2���� ��ǥ�� ���� �� ��ǥ�� sxy������ ����
			ta.append("[���̿켱Ž��] x = " + sxy.x + ", y = " + sxy.y + "\n");
			//textarea ta�� ���̿켱Ž���� �̵��Ҷ����� ���� ��ǥ���� �����
			if(short2_x < sxy.x) {
				//���� ���̿켱Ž�� ĳ������ x��ǥ���� sxy������ ����Ǿ��ִ� x��ǥ�� �� Ŭ���
				//���������� �̵��ϰ� ��
				short_right = true;
				short_up = false;
				short_down = false;
				short_left = false;
				short_move = true;
			} else if(short2_x > sxy.x) {
				//���� ���̿켱Ž�� ĳ������ x��ǥ���� sxy������ ����Ǿ��ִ� x��ǥ�� �� �������
				//�������� �̵��ϰ� ��
				short_left = true;
				short_up = false;
				short_right = false;
				short_down = false;
				short_move = true;
			} else if(short2_y > sxy.y) {
				//���� ���̿켱Ž�� ĳ������ y��ǥ���� sxy������ ����Ǿ��ִ� y��ǥ�� �� �������
				//�������� �̵��ϰ� ��
				short_up = true;
				short_down = false;
				short_right = false;
				short_left = false;
				short_move = true;
			} else if(short2_y < sxy.y) {
				//���� ���̿켱Ž�� ĳ������ y��ǥ���� sxy������ ����Ǿ��ִ� y��ǥ�� �� Ŭ���
				//�Ʒ������� �̵��ϰ� ��
				short_down = true;
				short_up = false;
				short_right = false;
				short_left = false;
				short_move = true;
			}
		}
		if(short_right == true) {
			short_char_x += 5;
			if(short_char_x % 50 == 0) {
				short2_x++;
				short_right = false;
				short_move = false;				
			}
		} else if(short_left == true) {
			short_char_x -= 5;
			if(short_char_x % 50 == 0) {
				short2_x--;
				short_left = false;
				short_move = false;							
			}
		} else if(short_up == true) {
			short_char_y -= 5;
			if(short_char_y % 50 == 0) {
				short2_y--;
				short_up = false;
				short_move = false;							
			}
		} else if(short_down == true) {
			short_char_y += 5;
			if(short_char_y % 50 == 0) {
				short2_y++;
				short_down = false;
				short_move = false;		
			}
		}
		if(tile[short2_y][short2_x] == 3) {
			//�̵��ϴٰ� ���� ����ִ� ���� �������ϰ��
			//��� ���ʿ� Ż���ߴ��� �˷��ְ� ��������
			ta.append("[���̿켱Ž��] "+ min + "�� "+sec+"�� Ż��\n");
			short_count = true;
			short_exit = true;
		} 
	}
	public void short_Check() {
		//���̿켱Ž���� ���Ž�� �˰���
		while(short_check == false) {
			boolean Check_left, Check_right, Check_up, Check_down;
			
			Check_up = tile_visit[short_y-1][short_x] == true ? true : false;
			Check_down = tile_visit[short_y+1][short_x] == true ? true : false;
			Check_left = tile_visit[short_y][short_x-1] == true ? true : false;
			Check_right = tile_visit[short_y][short_x+1] == true ? true : false;
			//tile_visit�� ���� ������ �迭���� �̵��Ҽ��ִ� ���� �ִ��� �˻��Ѵ�
			//���� ������ true, �ִٸ� false ��ȯ
			
			if(tile_visit[short_y][short_x] == false) {
				//���� ����ִ� ������ �������ִ� ���̰ų�, �湮�����ʾҴ� ���̶��
				//���ÿ� ��ǥ�� �����ϰ� �湮�迭�� �湮�ߴٰ� true�� ����
				stack.push(new Short_xy(short_x, short_y));
				tile_visit[short_y][short_x] = true;
			}
			if(tile_visit[short_y+1][short_x] == false) {
				short_y++;
				short_up = true;
			} else if(tile_visit[short_y][short_x+1] == false) {
				short_x++;
				short_right = true;
			} else if(tile_visit[short_y-1][short_x] == false) {
				short_y--;
				short_down = true;
			} else if(tile_visit[short_y][short_x-1] == false) {
				short_x--;
				short_left = true;
			} else if(tile[short_y][short_x] == 3) {
				//�������� ã���� short_count�� true�� ����
				short_count = true;
			} else if(Check_up == true && Check_down == true &&
				Check_right == true && Check_left == true) {
				//�����迭�� ���� �湮�ߴ� ���̰ų� ���ϰ�� �����
				stack.pop();
				//���ٸ����� �������������� ���� �ֱٿ� �Է��� ��ǥ�� ��
				short_x = stack.peek().x;
				short_y = stack.peek().y;
				//�ֱٿ� �Է��� ��ǥ�� �������� �� ������ ��ǥ�� ���ÿ� ����ְ�
				//�� ���� �о�� ���Ž�� ��ǥ�� ����
				//�̰��� �ݺ��ϸ� �湮�������� ���� ���ö����� �ǵ��ư�������
			}

			while(short_count == true) {
				//�������� ã���� �̰� �����
				stack2.push(stack.pop());
				//���ÿ� ����� ��ǥ���� ���������� ����������� ����̱⶧����
				//����2�� �ٽ� ����־ ������ �ݴ�� �ٲ�
				if(stack.isEmpty() == true) {
					//���ÿ� �ִ� ���� �� �����ٸ�
					stack2.pop();
					//����2�� ���� ù��°��(����� ��ǥ)�� ��
					//����� ��ǥ�� ���� ������ ������ ��� �˰�����
					//����� ��ǥ���� �����ϱ�� �Ǿ��ֱ⶧����
					//���� ����� ��ǥ�� �̵��� �ʿ䰡 ����
					short_check = true;
					short_count = false;
				}
			}
		}
	}
	public void Timer() { 
		//������ 1ȸ������ sleep(16�и���)�� �������� 1000�и���(1��)�� �Ǹ�
		//�������� 0���� ������ sec ������ 1 ������Ŵ
		//sec �������� 60�� �Ǹ�  min �������� 1 ������Ű�� sec �������� 0���� ����
		sec_count += 16;
		if(sec_count >= 1000) {
			sec_count = 0;
			sec++;
		}
		if(sec == 60) {
			min++;
			sec = 0;
		}
		if(min < 10) {
			if(sec < 10) {
				timer.setText("0"+min+":0"+sec);							
			} else if(sec >= 10) {
				timer.setText("0"+min+":"+sec);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj == btn1) {
			//btn1�� Ŭ���ϸ� �����ڽ��� üũ�Ǿ��ִ� �˰��� �����
			if(cb1.isSelected() == true) {
				left_exit = false;
			}
			if(cb2.isSelected() == true) {
				right_exit = false;
			}
			if(cb3.isSelected() == true) {
				short_exit = false;
			}
			timer_start = true;
		}
		if(obj == btn2) {
			//btn2�� Ŭ���ϸ� ���꿡 �ʿ��� �������� ����
			//���α׷��� ó�� ����������� ���·� �ٲ�
			ta.setText(null);
			//reset�� textarea �ʱ�ȭ
			left_x = 1;
			left_y = 2;
			right_x = 1;
			right_y = 2;
			short_x = 1;
			short_y = 2;
			short2_x = 1;
			short2_y = 2;
			min = 0;
			sec = 0;
			sec_count = 0;
			left_char_x = 50;
			left_char_y = 100;
			right_char_x = 50;
			right_char_y = 100;
			short_char_x = 50;
			short_char_y = 100;
			for(int x=0; x<20; x++) { //�湮�迭 �������·� ����
				for(int y=0; y<21; y++) {
					if(tile[x][y] == 1) {
						tile_visit[x][y] = true;
					} else {
						tile_visit[x][y] = false;
					}
				}
			}
			left_exit = true;
			right_exit = true;
			short_exit = true;
			left_move = false;
			right_move = false;
			short_move = false;
			left_up = false;
			left_down = false;
			left_left = false;
			left_right = false;
			right_up = false;
			right_down = false;
			right_left = false;
			right_right = false;
			short_up = false;
			short_down = false;
			short_left = false;
			short_right = false;
			timer.setText("00:00");
			timer_start = false;
			while(stack.isEmpty() == false) {
				stack.pop();
			}
			while(stack2.isEmpty() == false) {
				stack2.pop();
			}
			//���ÿ� �׿��ִ� ������ ��� ����
			short_check = false;
			short_count = false;
			short_Check();
			//reset�� ���̿켱Ž������ �����Ž��
		}
		if(obj == btn3) {
			//�������缳�� ��ư ������ ���Ƿ� ����� ������ ��ǥ�� ����
			if(exit_x == 18 && exit_y == 19) {
				tile[exit_x][exit_y] = 0;
				exit_x = 17;
				exit_y = 7;
				tile[exit_x][exit_y] = 3;
			} else if(exit_x == 17 && exit_y == 7) {
				tile[exit_x][exit_y] = 0;
				exit_x = 3;
				exit_y = 19;
				tile[exit_x][exit_y] = 3;
			} else if(exit_x == 3 && exit_y == 19) {
				tile[exit_x][exit_y] = 0;
				exit_x = 10;
				exit_y = 10;
				tile[exit_x][exit_y] = 3;
			}  else if(exit_x == 10 && exit_y == 10) {
				tile[exit_x][exit_y] = 0;
				exit_x = 18;
				exit_y = 19;
				tile[exit_x][exit_y] = 3;
			}  
			ta.setText(null);
			//reset�� textarea �ʱ�ȭ
			left_x = 1;
			left_y = 2;
			right_x = 1;
			right_y = 2;
			short_x = 1;
			short_y = 2;
			short2_x = 1;
			short2_y = 2;
			min = 0;
			sec = 0;
			sec_count = 0;
			left_char_x = 50;
			left_char_y = 100;
			right_char_x = 50;
			right_char_y = 100;
			short_char_x = 50;
			short_char_y = 100;
			for(int x=0; x<20; x++) { //�湮�迭 �������·� ����
				for(int y=0; y<21; y++) {
					if(tile[x][y] == 1) {
						tile_visit[x][y] = true;
					} else {
						tile_visit[x][y] = false;
					}
				}
			}
			left_exit = true;
			right_exit = true;
			short_exit = true;
			left_move = false;
			right_move = false;
			short_move = false;
			left_up = false;
			left_down = false;
			left_left = false;
			left_right = false;
			right_up = false;
			right_down = false;
			right_left = false;
			right_right = false;
			short_up = false;
			short_down = false;
			short_left = false;
			short_right = false;
			timer.setText("00:00");
			timer_start = false;
			while(stack.isEmpty() == false) {
				stack.pop();
			}
			while(stack2.isEmpty() == false) {
				stack2.pop();
			}
			short_check = false;
			short_count = false;
			short_Check();
			// ����� �������� �°� ������ ���� �����Ž��
		}
		
	}
	public static void main(String[] args) {
		new Ex01();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		//�����̽��ٸ� ������ �������϶� ����ǰ�, ������϶� ������
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			pause = pause == true ? false : true;
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
class Short_xy {
	//���ÿ� ���� ��ǥ��ü
	int x;
	int y;
	
	Short_xy(int x, int y) {
		this.x = x;
		this.y = y;
	}
}