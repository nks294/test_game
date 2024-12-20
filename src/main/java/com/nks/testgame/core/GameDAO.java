package main.java.com.nks.testgame.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

// dao 라는 클래스를 생성해서
// 메인 메소드에 객체를 만들어서 DB작업 진행

public class GameDAO {
	
	public ArrayList<ScoreData> scoreList;

	private Connection con = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public GameDAO() {
		try {
      EnvLoader.loadEnv(".env");
        final String driver = "org.mariadb.jdbc.Driver";
        final String url = System.getProperty("TESTGAME_DB_URL"); 
        final String userid = System.getProperty("TESTGAME_DB_USER");
        final String passwd = System.getProperty("TESTGAME_DB_PASS");

		// 드라이버 메모리에 로딩
		Class.forName(driver);
		// 만들어둔 string 변수 사용해서 oraclexe에 연결
		con = DriverManager.getConnection(url, userid, passwd);
		System.out.println("DB 연결에 성공했습니다.");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결 실패: " + e.getMessage());
		} catch (IOException e) {
      e.printStackTrace();
    }
	}

	// 저장된 점수 데이터 불러오는 메소드
	public ArrayList<ScoreData> loadPlayerRecord(int difficulty) { 
		scoreList = new ArrayList<ScoreData>();
		try {
			// 실행할 쿼리문 작성
            String sql = "SELECT skin, name, score, record_date " +
                         "FROM ( " +
                         "    SELECT skin, name, score, record_date, " +
                         "           (@rownum := @rownum + 1) AS SCORE_RANK " +
                         "    FROM TEST_GAME_SCORE, (SELECT @rownum := 0) AS r " +
                         "    WHERE difficulty = ? " +
                         "    ORDER BY score DESC " +
                         ") ranked " +
                         "WHERE SCORE_RANK < 16";
			// 생성해둔 prepareStatement 객채에 작성해둔 쿼리문 추가
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, difficulty);
			// statement 의 쿼리 실행 결과를 resultset 으로 받아서 실행
			rs = stmt.executeQuery();

			// 모든 데이터 정보 저장
			while(rs.next()) {
				int skin = rs.getInt(1);
				String name = rs.getString(2);
				int score = rs.getInt(3);
				Date date = rs.getDate(4);
				ScoreData scoreData = new ScoreData(score, skin, name, date);
				scoreList.add(scoreData);
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("쿼리 실행 실패: " + e.getMessage());
		} 
		return scoreList;
	}
	
	// 데이터 저장 메소드
	public void savePlayerRecord(ScoreData score, int difficulty) { 
		try {
			// 실행할 쿼리문 작성
            String sql = "INSERT INTO TEST_GAME_SCORE (difficulty, skin, name, score, record_date) VALUES (?, ?, ?, ?, NOW())";
            // 생성해둔 stmt 변수에 쿼리문 추가
			stmt = con.prepareStatement(sql);
			// 쿼리문의 변수들 순서와 타입 지정
			stmt.setInt(1, difficulty);
			stmt.setInt(2, score.getSkin());
			stmt.setString(3, score.getName());
			stmt.setInt(4, score.getScore());
			// 쿼리문 실행
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("쿼리 실행 실패: " + e.getMessage());
		} 
	}
	
	// sql 접속 테스트용
	public void testSQL() {
		try {
			// 실행할 쿼리문 작성
			String sql = "SELECT NOW()";
            // 쿼리 실행 준비를 위해 statement 객체 생성
			stmt = con.prepareStatement(sql);
			// statement 의 쿼리 실행 결과를 resultset 으로 받아서 실행
			rs = stmt.executeQuery();
			// 결과 출력문
			while(rs.next()) {
				String currentTimestamp = rs.getString(1);
				System.out.println("현재시간 " + currentTimestamp + "\n");
			}
		} catch (SQLException e) {
			System.out.println("쿼리 실행 실패: " + e.getMessage());
		} 
	}
	
}
