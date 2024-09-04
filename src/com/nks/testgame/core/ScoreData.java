package com.nks.testgame.core;

import java.util.Date;

import lombok.Data;

@Data
public class ScoreData {
	
	protected int score;
	protected int skin;
	protected String name;
	protected Date date;
	
	public ScoreData(int score, int skin, String name, Date date) {
		this.score = score;
		this.skin = skin;
		this.name = name;
		this.date = date;
	}
}
