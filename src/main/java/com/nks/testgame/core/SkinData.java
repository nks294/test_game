package main.java.com.nks.testgame.core;

public class SkinData {

	private final String[] skinName = {
			"에드나","블랜차드","체스터","스테판",
			"타쿠야","리웨이","???","나오코",
			"와!","준비중","준비중","준비중"};
	
	private final String[] skinLabel = {
			"",
			"",
			"",
			"",
			"",
			"",
			"\"???\"",
			"",
			"\"샌즈!\"",
			"",
			"",
			""};
	
	public String getSkinName(int num) {
		return skinName[num];
	}
	
	public String getSkinLabel(int num) {
		return skinLabel[num];
	}
}
