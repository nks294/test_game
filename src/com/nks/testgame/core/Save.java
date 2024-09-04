package com.nks.testgame.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.nks.testgame.core.Game.STATE;

public class Save extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 8570553399285337092L;

	private LineBorder border = new LineBorder(Color.white, 1);
	private JTextField input = new JTextField();
	private JButton button;
	private JLabel label;
	private JLabel alert = new JLabel();
	private JLabel alert2 = new JLabel();
	
	Game game;
	HUD hud;
	
	public Save(Game game, HUD hud) {
		this.game = game;
		this.hud = hud;
		
		setBackground(Color.black);
		setLayout(null);
		
		placeText(label, 50, "기록 저장", 0, 65);
		//placeText(label, 30, "내 기록: " + hud.getScore() + " 포인트", 0, 140);
		
		placeInput(input, 30, "플레이어", 0, 280, 360, 64);

		placeButton(button, 30, "저장하다", -100, 420, 160, 64);
		placeButton(button, 30, "돌아가다", 100, 420, 160, 64);
		
		placeAlert();
		placeAlert2();
		
		placeText(label, 18, "여기에 닉네임을 입력.", 0, 243);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("저장하다")) {
			if (input.getText().length() > 10) {
				alert.setVisible(true);
				alert2.setVisible(false);
			} else if (input.getText().length() < 2) {
				alert.setVisible(false);
				alert2.setVisible(true);
			} else {
				game.saveScore(input.getText());
				hud.setScore(0);
				Game.gameState = STATE.Menu;
				Window.changeLayout("main");
			}
		}
		
		if (e.getActionCommand().equals("돌아가다")) {
			hud.setScore(0);
			Game.gameState = STATE.Menu;
			Window.changeLayout("main");
		}
		
	}
	
	private void placeInput(JTextField input, int fontSize, String txt, int x, int y, int width, int height) {
		input.setFont(new Font("nanumgothic", 1, fontSize));
		input.setBounds((Game.WIDTH / 2 - 10) - (360 / 2) + x, y, width, height);
		input.setForeground(Color.white);
		input.setBackground(Color.black);
		input.setCaretColor(Color.white);
		input.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0,15,0,0)));
		input.setText(txt);
		this.add(input);
	}
	
	private void placeButton(JButton button, int fontSize, String txt, int x, int y, int width, int height) {
		button = new JButton(txt);
		button.setActionCommand(txt);
		button.setFont(new Font("nanumgothic", 1, fontSize));
		button.setBounds((Game.WIDTH / 2 - 10) - (width / 2) - x, y, width, height);
		button.setForeground(Color.white);
		button.setBackground(Color.black);
		button.setBorder(border);
		button.setFocusPainted(false);
		button.addActionListener(this);
		this.add(button);
	}
	
	private void placeText(JLabel label, int fontSize, String txt, int x, int y) {
		label = new JLabel();
		label.setVisible(false);
		label.setFont(new Font("nanumgothic", 1, fontSize));
		label.setText(txt);
		label.setBounds((Game.WIDTH / 2 - 10) - (getTextWidth(label) / 2) + x, y, getTextWidth(label) + 10, getTextHeight(label) + 10);
		label.setForeground(Color.white);
		label.setBackground(Color.black);
		label.setBorder(null);
		label.setVisible(true);
		this.add(label);
	}
	
	private void placeAlert () {
		alert.setFont(new Font("nanumgothic", 1, 18));
		alert.setText("너무 길어요. (10글자 이내)");
		alert.setBounds((Game.WIDTH / 2 - 10) - (getTextWidth(alert) / 2) + 0, 347, getTextWidth(alert) + 10, getTextHeight(alert) + 10);
		alert.setForeground(Color.white);
		alert.setBackground(Color.black);
		alert.setBorder(null);
		alert.setVisible(false);
		this.add(alert);
	}

	private void placeAlert2 () {
		alert2.setFont(new Font("nanumgothic", 1, 18));
		alert2.setText("적어도 2글자 이상은 입력해주세요.");
		alert2.setBounds((Game.WIDTH / 2 - 10) - (getTextWidth(alert2) / 2) + 0, 347, getTextWidth(alert2) + 10, getTextHeight(alert2) + 10);
		alert2.setForeground(Color.white);
		alert2.setBackground(Color.black);
		alert2.setBorder(null);
		alert2.setVisible(false);
		this.add(alert2);
	}
	
	private int getTextWidth(JLabel label) {
		return (int)label.getPreferredSize().getWidth();
	}

	private int getTextHeight(JLabel label) {
		return (int)label.getPreferredSize().getHeight();
	}
	
}