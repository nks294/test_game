package main.java.com.nks.testgame.core;

import java.util.Date;

public class ScoreData {
    private int score;
    private int skin;
    private String name;
    private Date date;

    public ScoreData(int score, int skin, String name, Date date) {
        this.score = score;
        this.skin = skin;
        this.name = name;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ScoreData{" +
                "score=" + score +
                ", skin=" + skin +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}