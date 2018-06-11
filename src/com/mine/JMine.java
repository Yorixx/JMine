package com.mine;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class JMine extends JFrame implements ActionListener, MouseListener {

	/**
	 * @author linwene
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MINE = -1;
	private JPanel Menu, Main;
	public Icon die = new ImageIcon("src/image/bomb.png"); // 踩雷
	public Icon sign = new ImageIcon("src/image/sign.png");// 插旗子
	public Icon doubt = new ImageIcon("src/image/doubt.png");// 插旗子
	public Icon smile = new ImageIcon("src/image/smile.png");// 笑脸
	public Icon cry = new ImageIcon("src/image/cry.png");// 哭脸
	private JLabel emoticon = new JLabel();
	private int length = 10;
	private int width = 10;
	private int BombNum = 10;
	private int StartBombNum = 10;
	private JButton btn[][];
	private JButton replay = new JButton("replay");
	private int map[][];
	private int clean = 0;

	public JMine() {
		super("Minesweeper-2.1");
		UI();
		MakeBomb();
		this.setSize(650, 600);
		this.setLocationRelativeTo(null);//窗口在中间打开
		this.setResizable(false);// 锁定窗口大小
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* 界面 */
	public void UI() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout(0, 40));
		c.setBackground(Color.yellow);
		Main = new JPanel();
		Menu = new JPanel();
		Main.setLayout(new GridLayout(length, width, 0, 0));
		Menu.setLayout(new GridLayout(10, 0, 0, 0));
		Menu.setBackground(Color.yellow);
		c.add(Main, "Center");
		c.add(Menu, "East");
		btn = new JButton[length][width];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				btn[i][j] = new JButton("");
				btn[i][j].setBackground(Color.cyan);
				btn[i][j].addActionListener(this);
				btn[i][j].addMouseListener(this);
				Main.add(btn[i][j]);
			}
		}
		Menu.add(replay);
		Menu.add(emoticon);
		emoticon.setIcon(smile);
		replay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				replay();
			}
		});
	}

	/* 布雷 */
	public void MakeBomb() {
		map = new int[length][width];
		for (int i = 0; i < BombNum; i++) {
			int x = (int) (Math.random() * length - 1);
			int y = (int) (Math.random() * width - 1);
			if (map[x][y] == MINE)
				i--;
			else
				map[x][y] = MINE;
		}
	}

	/* 踩雷 */
	public void Die() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (map[i][j] == MINE) {
					btn[i][j].setBackground(Color.white);
					btn[i][j].setIcon(die);
				}
			}

		}
		emoticon.setIcon(cry);
		JOptionPane
				.showMessageDialog(this, "你太笨了，不适合玩这个游戏!", "You're dead!", 2);
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				btn[i][j].setEnabled(false);
			}
		}
	}

	/* 计算方块周围雷数 */
	public int Count(int i, int j) {
		int count = 0;
		for (int x = i - 1; x < i + 2; x++) {
			for (int y = j - 1; y < j + 2; y++) {
				if ((x >= 0) && (y >= 0) && (x < length) && (y < width)) {
					if (map[x][y] == MINE) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/* 重开游戏 */
	public void replay() {
		clean = 0;
		BombNum = 10;
		emoticon.setIcon(smile);
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				btn[i][j].setEnabled(true);
				btn[i][j].setIcon(null);
				btn[i][j].setText("");
				btn[i][j].setBackground(Color.cyan);
			}
		}
		MakeBomb();
		
	}

	public void click(int i, int j) {
		int MineNum = 0;
		if (i < length && i >= 0 && j >= 0 && j < width) {

			if (map[i][j] == 0) {
				if (i > 0 && i < length - 1 && j > 0 && j < width - 1) {

					if (map[i - 1][j - 1] == MINE)
						MineNum += 1;
					if (map[i][j - 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j - 1] == MINE)
						MineNum += 1;
					if (map[i - 1][j] == MINE)
						MineNum += 1;
					if (map[i + 1][j] == MINE)
						MineNum += 1;
					if (map[i - 1][j + 1] == MINE)
						MineNum += 1;
					if (map[i][j + 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j + 1] == MINE)
						MineNum += 1;
				}
				if (i == 0 && j == 0) {
					if (map[i + 1][j] == MINE)
						MineNum += 1;
					if (map[i][j + 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j + 1] == MINE)
						MineNum += 1;

				}
				if (i > 0 && i < length - 1 && j == 0) {
					if (map[i - 1][j] == MINE)
						MineNum += 1;
					if (map[i + 1][j] == MINE)
						MineNum += 1;
					if (map[i - 1][j + 1] == MINE)
						MineNum += 1;
					if (map[i][j + 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j + 1] == MINE)
						MineNum += 1;
				}
				if (i == length - 1 && j == 0) {
					if (map[i - 1][j] == MINE)
						MineNum += 1;
					if (map[i - 1][j + 1] == MINE)
						MineNum += 1;
					if (map[i][j + 1] == MINE)
						MineNum += 1;
				}
				if (i == 0 && j == width - 1) {
					if (map[i][j - 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j - 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j] == MINE)
						MineNum += 1;

				}
				if (i > 0 && i < length - 1 && j == width - 1) {
					if (map[i - 1][j - 1] == MINE)
						MineNum += 1;
					if (map[i][j - 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j - 1] == MINE)
						MineNum += 1;
					if (map[i - 1][j] == MINE)
						MineNum += 1;
					if (map[i + 1][j] == MINE)
						MineNum += 1;
				}
				if (i == length - 1 && j == width - 1) {
					if (map[i - 1][j - 1] == MINE)
						MineNum += 1;
					if (map[i][j - 1] == MINE)
						MineNum += 1;
					if (map[i - 1][j] == MINE)
						MineNum += 1;
				}
				if (i == 0 && j > 0 && j < width - 1) {
					if (map[i][j - 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j - 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j] == MINE)
						MineNum += 1;
					if (map[i][j + 1] == MINE)
						MineNum += 1;
					if (map[i + 1][j + 1] == MINE)
						MineNum += 1;
				}
				if (i == length - 1 && j > 0 && j < width - 1) {
					if (map[i - 1][j - 1] == MINE)
						MineNum += 1;
					if (map[i][j - 1] == MINE)
						MineNum += 1;
					if (map[i - 1][j] == MINE)
						MineNum += 1;
					if (map[i - 1][j + 1] == MINE)
						MineNum += 1;
					if (map[i][j + 1] == MINE)
						MineNum += 1;
				}
				map[i][j] = 1;
				clean = clean + map[i][j];
				btn[i][j].setEnabled(false);
				btn[i][j].setFont(new Font(null, Font.BOLD, 25));
				btn[i][j].setBackground(Color.white);
				if (MineNum == 0) {
					btn[i][j].setText("");
					click(i - 1, j - 1);
					click(i, j - 1);
					click(i + 1, j - 1);
					click(i - 1, j);
					click(i + 1, j);
					click(i - 1, j + 1);
					click(i, j + 1);
					click(i + 1, j + 1);
				} else
					btn[i][j].setText(MineNum + "");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int count = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (e.getSource() == btn[i][j]) {
					if (map[i][j] == MINE) {
						Die();
					} else {
						count = Count(i, j);
						map[i][j] = 1;
						clean = clean + map[i][j];
						if (count == 0) {
							click(i - 1, j - 1);
							click(i, j - 1);
							click(i + 1, j - 1);
							click(i - 1, j);
							click(i + 1, j);
							click(i - 1, j + 1);
							click(i, j + 1);
							click(i + 1, j + 1);
							btn[i][j].setText("");
							btn[i][j].setEnabled(false);
							btn[i][j].setFont(new Font(null, Font.BOLD, 25));
							btn[i][j].setBackground(Color.white);
						} else {
							btn[i][j].setText(count + "");
							btn[i][j].setEnabled(false);
							btn[i][j].setFont(new Font(null, Font.BOLD, 25));
							btn[i][j].setBackground(Color.white);
						}
						System.out.print(clean + " ");
						if (clean == width * length - StartBombNum) {
							JOptionPane.showMessageDialog(this, "好吧，你胜利了！",
									"You Win", 2);
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 插旗
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (e.getButton() == MouseEvent.BUTTON3) {// 判断是否为鼠标右键
					if (e.getSource() == btn[i][j]) {
						if (btn[i][j].getIcon() == null) {
							btn[i][j].setIcon(sign);
						} else {
							btn[i][j].setIcon(null);
							BombNum++;
						}
						if (map[i][j] == MINE)
							BombNum--;
						if (BombNum == 0)
							JOptionPane.showMessageDialog(this, "好吧，你胜利了！",
									"You Win", 2);
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
