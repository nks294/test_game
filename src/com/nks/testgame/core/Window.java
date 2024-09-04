package com.nks.testgame.core;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = -6878585894169830240L;

	private static CardLayout card = new CardLayout();
	private static JFrame frame = new JFrame();
	
	public Window(int width, int height, String title, Game game, Save save) {
		Dimension winSize = new Dimension(width, height);
		
		frame.setTitle(title);
		
		frame.setLayout(card);
		frame.setPreferredSize(winSize);
		frame.setMaximumSize(winSize);
		frame.setMinimumSize(winSize);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.add(game, "main");
		frame.add(save, "save");
		frame.setVisible(true);
		
		game.start();
	}
	
	public static void changeLayout(String name) {
		card.show(frame.getContentPane(), name);
	}

}
