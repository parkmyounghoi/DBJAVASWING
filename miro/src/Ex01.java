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
	//깊이우선탐색 방법에서 좌표값들을 저장할 스택
	Stack<Short_xy> stack2 = new Stack<>();
	//깊이우선탐색 방법으로 만들어진 좌표값 순서를 반대로 하기위한 스택
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
	JButton btn3 = new JButton("도착지재설정");
	JLabel lab1 = new JLabel();
	JLabel lab2 = new JLabel();
	JLabel lab3 = new JLabel();
	JLabel timer = new JLabel("00:00");
	JLabel state = new JLabel("재생중");
	JCheckBox cb1 = new JCheckBox("좌선법",false);
	JCheckBox cb2 = new JCheckBox("우선법",false);
	JCheckBox cb3 = new JCheckBox("깊이우선",false);
	ImageIcon icon1 = new ImageIcon("src/p1_front.png");
	ImageIcon icon2 = new ImageIcon("src/p2_front.png");
	ImageIcon icon3 = new ImageIcon("src/p3_front.png");
	JTextArea ta = new JTextArea(25, 50);
	JScrollPane sc = new JScrollPane(ta);
	Font font = new Font("돋움", Font.BOLD, 15);
	/*
	 * 타일	
	 * 0	- 땅
	 * 1	- 물
	 * 2	- 출발지
	 * 3	- 도착지
	 */
	Ex01() {
		addKeyListener(this);
		f.setSize(300, 1000);
		f.setTitle("리모콘");
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
		//textarea 입력가능(true), 입력불가능(false)
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
		timer.setFont(new Font("돋움", Font.BOLD, 60));
		state.setFont(new Font("돋움", Font.BOLD, 60));
		state.setHorizontalAlignment(JLabel.CENTER);
		timer.setHorizontalAlignment(JLabel.CENTER);
		cb1.setBounds(25, 370, 70, 20);
		cb2.setBounds(115, 370, 70, 20);
		cb3.setBounds(205, 370, 80, 20);
		sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//수직 스크롤바만 생성되도록 함
		ta.setLineWrap(true);
		ta.setFont(font);
		setSize(1000, 1000);
		setTitle("미로찾기");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		new Thread(this).start();
		//쓰레드 시작
		for(int x=0; x<20; x++) { //미로 배열에 1번(벽)으로 설정된 좌표값들을 읽어 방문배열에 true로 변경
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
		buffg.setFont(new Font("돋움", Font.BOLD, 30));
		for(int x=0; x<20; x++) {
			for(int y=0; y<20; y++) {
				//미로배열을 읽어서 0번은 기본땅 이미지, 1번은 물 이미지, 2번은 출발지 이미지, 3번은 도착지 이미지를 입힘
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
					//캐릭터 이미지 그리는 메소드
					//현재의 방향에 따라 이미지를 변경함
					left_img = tk.getImage("src/p1_right.png");
					if(left_left == true) {
						left_img = tk.getImage("src/p1_left.png");
					} else if(left_up == true) {
						left_img = tk.getImage("src/p1_up.png");
					} else if(left_down == true) {
						left_img = tk.getImage("src/p1_down.png");
					}
					buffg.drawImage(left_img, left_char_x, left_char_y, this);
					buffg.drawString("좌선법", left_char_x-25, left_char_y);
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
					buffg.drawString("우선법", right_char_x-25, right_char_y);
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
					buffg.drawString("깊이우선", short_char_x-25, short_char_y);
				}
			}
		}
		g.drawImage(buffImage, 0, 0, this);
		//버퍼이미지에 그린 이미지들을 프레임그래픽에 그림
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				if(pause == false) {
					state.setText("재생중");
				} else {
					state.setText("일시정지");
				}
				ta.setCaretPosition(ta.getDocument().getLength());
				//jtextarea의 내용이 아래로 초과될때 자동으로 스크롤을 맨밑으로 내려줌
				f.setLocation(getLocation().x+getWidth()+9, getLocation().y);
				//메인 프레임의 좌표가 바뀔때마다 서브프레임이 메인프레임의 좌표값을 받아서 이동함
				tile[exit_x][exit_y] = 3;
				repaint();
				//화면 그리기
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
				//pause 변수가 false일때만 알고리즘 실행
				//true일경우 연산만 멈추고 화면 그리기는 계속됨
				Thread.sleep(16);
				//쓰레드 16밀리초 동안 대기
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void left_move() {
		//좌선법 이동메소드
		if(left_right == true) {
			left_char_x += 5;
			if(left_char_x % 50 == 0) {
				left_x++;
				ta.append("[좌선법] x = " + left_x + ", y = " + left_y + "\n");
				left_move = false;
			}
		}
		if(left_left == true) {
			left_char_x -= 5;
			if(left_char_x % 50 == 0) {
				left_x--;
				ta.append("[좌선법] x = " + left_x + ", y = " + left_y + "\n");
				left_move = false;			
			}
		}
		if(left_up == true) {
			left_char_y -= 5;
			if(left_char_y % 50 == 0) {
				left_y--;
				ta.append("[좌선법] x = " + left_x + ", y = " + left_y + "\n");
				left_move = false;			
			}
		}
		if(left_down == true) {
			left_char_y += 5;
			if(left_char_y % 50 == 0) {
				left_y++;
				ta.append("[좌선법] x = " + left_x + ", y = " + left_y + "\n");
				left_move = false;
			}
		}
		if(tile[left_y][left_x] == 3) {
			ta.append("[좌선법] "+ min + "분 "+sec+"초 탈출\n");
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
			//현재 인접배열에 벽이 있는지 확인함
			//벽이 있다면 true, 없으면 false
			
			if(tile[left_y][left_x+1] == 3) {
				//인접배열에 도착지가 있는지 확인하고
				//도착지가 있다면 최우선적으로 그쪽으로 이동
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
				//인접배열에 도착지가 없다면
				//현재 향하고 있는 방향에서 반시계방향 90도에 길이 있는지 확인함
				//길이 있다면 왼쪽벽을 타야하기에 그쪽으로 이동
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
				//인접배열에 도착지가 없고, 현재 이동중인 방향의 반시계 90도 방향에도 길이없으면서
				//현재 이동중인 방향에 벽이 있을경우 시계방향 90도 방향으로 이동함
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
				//삼면이 전부 벽일경우 비어있는 한쪽으로 이동함
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
				ta.append("[우선법] x = " + right_x + ", y = " + right_y + "\n");
				right_move = false;				
			}
		}
		if(right_left == true) {
			right_char_x -= 5;
			if(right_char_x % 50 == 0) {
				right_x--;
				ta.append("[우선법] x = " + right_x + ", y = " + right_y + "\n");
				right_move = false;							
			}
		}
		if(right_up == true) {
			right_char_y -= 5;
			if(right_char_y % 50 == 0) {
				right_y--;
				ta.append("[우선법] x = " + right_x + ", y = " + right_y + "\n");
				right_move = false;							
			}
		}
		if(right_down == true) {
			right_char_y += 5;
			if(right_char_y % 50 == 0) {
				right_y++;
				ta.append("[우선법] x = " + right_x + ", y = " + right_y + "\n");
				right_move = false;		
			}
		}
		if(tile[right_y][right_x] == 3) {
			ta.append("[우선법] "+ min + "분 "+sec+"초 탈출\n");
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
		//깊이우선탐색으로 찾아낸 경로대로 이동함
		if(stack2.isEmpty() != true && short_move == false) {
			//현재 스택2에 값이 들어있을경우 실행됨
			Short_xy sxy = stack2.pop();
			//스택2에서 좌표를 빼고 그 좌표를 sxy변수에 담음
			ta.append("[깊이우선탐색] x = " + sxy.x + ", y = " + sxy.y + "\n");
			//textarea ta에 깊이우선탐색이 이동할때마다 현재 좌표값을 출력함
			if(short2_x < sxy.x) {
				//현재 깊이우선탐색 캐릭터의 x좌표보다 sxy변수에 저장되어있는 x좌표가 더 클경우
				//오른쪽으로 이동하게 함
				short_right = true;
				short_up = false;
				short_down = false;
				short_left = false;
				short_move = true;
			} else if(short2_x > sxy.x) {
				//현재 깊이우선탐색 캐릭터의 x좌표보다 sxy변수에 저장되어있는 x좌표가 더 작을경우
				//왼쪽으로 이동하게 함
				short_left = true;
				short_up = false;
				short_right = false;
				short_down = false;
				short_move = true;
			} else if(short2_y > sxy.y) {
				//현재 깊이우선탐색 캐릭터의 y좌표보다 sxy변수에 저장되어있는 y좌표가 더 작을경우
				//위쪽으로 이동하게 함
				short_up = true;
				short_down = false;
				short_right = false;
				short_left = false;
				short_move = true;
			} else if(short2_y < sxy.y) {
				//현재 깊이우선탐색 캐릭터의 y좌표보다 sxy변수에 저장되어있는 y좌표가 더 클경우
				//아래쪽으로 이동하게 함
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
			//이동하다가 현재 밟고있는 땅이 도착지일경우
			//몇분 몇초에 탈출했는지 알려주고 연산종료
			ta.append("[깊이우선탐색] "+ min + "분 "+sec+"초 탈출\n");
			short_count = true;
			short_exit = true;
		} 
	}
	public void short_Check() {
		//깊이우선탐색의 경로탐색 알고리즘
		while(short_check == false) {
			boolean Check_left, Check_right, Check_up, Check_down;
			
			Check_up = tile_visit[short_y-1][short_x] == true ? true : false;
			Check_down = tile_visit[short_y+1][short_x] == true ? true : false;
			Check_left = tile_visit[short_y][short_x-1] == true ? true : false;
			Check_right = tile_visit[short_y][short_x+1] == true ? true : false;
			//tile_visit로 현재 인접한 배열에서 이동할수있는 길이 있는지 검사한다
			//길이 없으면 true, 있다면 false 반환
			
			if(tile_visit[short_y][short_x] == false) {
				//현재 밟고있는 지형이 밟을수있는 땅이거나, 방문하지않았던 땅이라면
				//스택에 좌표를 저장하고 방문배열에 방문했다고 true로 변경
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
				//도착지를 찾으면 short_count를 true로 변경
				short_count = true;
			} else if(Check_up == true && Check_down == true &&
				Check_right == true && Check_left == true) {
				//인접배열이 전부 방문했던 땅이거나 벽일경우 실행됨
				stack.pop();
				//막다른길을 빠져나오기위해 가장 최근에 입력한 좌표를 뺌
				short_x = stack.peek().x;
				short_y = stack.peek().y;
				//최근에 입력한 좌표가 빠졌으니 그 이전의 좌표가 스택에 담겨있고
				//그 값을 읽어와 경로탐색 좌표로 설정
				//이것을 반복하면 방문하지않은 길이 나올때까지 되돌아갈수있음
			}

			while(short_count == true) {
				//도착지를 찾으면 이게 실행됨
				stack2.push(stack.pop());
				//스택에 저장된 좌표들은 도착지부터 출발지까지의 경로이기때문에
				//스택2에 다시 집어넣어서 순서를 반대로 바꿈
				if(stack.isEmpty() == true) {
					//스택에 있는 값이 다 빠졌다면
					stack2.pop();
					//스택2에 제일 첫번째값(출발지 좌표)를 뺌
					//출발지 좌표를 빼는 이유는 어차피 모든 알고리즘은
					//출발지 좌표에서 시작하기로 되어있기때문에
					//굳이 출발지 좌표로 이동할 필요가 없음
					short_check = true;
					short_count = false;
				}
			}
		}
	}
	public void Timer() { 
		//쓰레드 1회전마다 sleep(16밀리초)를 누적시켜 1000밀리초(1초)가 되면
		//누적값을 0으로 변경후 sec 변수를 1 증가시킴
		//sec 변수값이 60이 되면  min 변수값을 1 증가시키고 sec 변수값을 0으로 변경
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
			//btn1을 클릭하면 라디오박스에 체크되어있는 알고리즘만 실행됨
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
			//btn2를 클릭하면 연산에 필요한 변수들의 값을
			//프로그램이 처음 실행됐을때의 상태로 바꿈
			ta.setText(null);
			//reset시 textarea 초기화
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
			for(int x=0; x<20; x++) { //방문배열 기존상태로 변경
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
			//스택에 쌓여있는 값들을 모두 빼냄
			short_check = false;
			short_count = false;
			short_Check();
			//reset시 깊이우선탐색으로 경로재탐색
		}
		if(obj == btn3) {
			//도착지재설정 버튼 누르면 임의로 저장된 도착지 좌표로 변경
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
			//reset시 textarea 초기화
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
			for(int x=0; x<20; x++) { //방문배열 기존상태로 변경
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
			// 변경된 도착지에 맞게 스택을 비우고 경로재탐색
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
		//스페이스바를 누르면 정지중일땐 재생되고, 재생중일땐 정지함
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
	//스택에 넣을 좌표객체
	int x;
	int y;
	
	Short_xy(int x, int y) {
		this.x = x;
		this.y = y;
	}
}