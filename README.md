# TEST GAME
> 자바를 공부하며 만들어보고 있는 간단한 게임.

## Reference Video
이 게임은 RealTutsGML의 "Java Programming: Let's Build a Game" 튜토리얼을 기반으로 합니다.  
**[ YouTube Playlist ](https://www.youtube.com/watch?v=1gir2R7G9ws&list=PLWms45O3n--6TvZmtFHaCWRZwEqnz2MHa)**

## 사용 기술
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)

## 플레이 방법
### 조작
- **방향키 (Arrow Key)**: 플레이어 조작
- **P**: 게임 일시정지

## 등장 오브젝트 설명
- **빨간색 사각형**
  - 플레이어가 먹어서 없애야 하는 오브젝트로, 일정 간격으로 생성됩니다.
  - 화면 내 빨간색 사각형의 개수가 **5개를 초과하면 게임 오버**.
  - 점수가 높아질수록 생성 주기가 짧아집니다.

- **초록색 사각형**
  - 피해야 하는 오브젝트로, 일정 시간마다 소환되었다가 사라집니다.
  - **닿으면 체력을 1칸 잃습니다.**
  - 점수가 높아질수록 생성 주기가 짧아지고, 이동 속도도 증가합니다.
  
- **노란색 사각형**
  - 특정 점수대에 소환되며, 플레이어를 천천히 따라다니는 오브젝트입니다.
  - **닿으면 체력을 1칸 잃습니다.**
  
- **회복 아이템**
  - 현재 버전에서는 게임 중 딱 한 번만 소환됩니다.
  - **체력 5칸을 회복**하며, 최대 10칸까지 회복 가능합니다.

## '보통'과 '어려운'의 차이점
- 빨간색 사각형의 기본 속도가 더 빨라집니다.
- 초록색 사각형이 **파란색**으로 바뀌고, 속도도 증가합니다.
- 노란색 사각형의 기본 속도가 더 빨라집니다.
- 빨간색과 초록색 사각형의 기본 생성 주기가 더 빨라집니다.

## TODO
- [ ] 게임 클리어 화면 추가 및 특정 점수대에서 게임 종료 구현
- [ ] 회복 아이템 기능 완성하기
- [ ] 효과음 수정
- [ ] 탄환 패턴 담당 클래스 최적화
- [ ] 플레이 기록 데이터베이스를 OracleXE에서 MariaDB로 이전
