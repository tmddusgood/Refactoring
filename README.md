# PandaN Refactoring (진행 중)
### PandaN Backend 리팩토링 프로젝트입니다.
- [PandaN 소개 보러가기](https://github.com/tmddusgood/Refactoring/tree/develop/doc/PandaN_Original)

## 목표 및 기준
* 설계 원칙, 패턴, 클린 코드, 스프링 원칙 등을 준수
* 테스트 코드 기반의 점진적인 개선
* 담당 영역 우선
    * 노트 도메인 해당 영역
    * PandanUtils
    * 검색

## 전략
### 리팩토링
1. 테스트 코드 작성
2. 문제점 파악 (feat. 소프트웨어 악취를 제거하는 리팩토링)
3. 리팩토링 카탈로그 작성 (feat. 자바로 배우는 리팩토링 입문)
4. 실패하는 테스트 코드 작성 (feat. Effective Unit Testing)
5. 리팩토링 적용을 위한 클래스/메소드 작성
6. 테스트 통과 시 리팩토링 적용
7. 테스트 코드 리팩토링
### 테스트
* 도메인 엔티티 객체 단위 테스트
* 도메인 레파지토리 단위 테스트
  * 슬라이스 테스트
* 서비스 계층 단위 테스트
  * 협력 객체와 대상 객체 구분 - 테스트 더블 활용
* 컨트롤러 테스트 (내장 웹 컨테이너 통합 테스트)

## 들어가기
* [01. 테스트 코드를 작성해보기](https://github.com/tmddusgood/Refactoring/tree/develop/doc/%EC%88%9C%EC%84%9C/01.%20%5B%ED%85%8C%EC%8A%A4%ED%8A%B8%5D%20%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EC%BD%94%EB%93%9C%EB%A5%BC%20%EC%9E%91%EC%84%B1%ED%95%B4%EB%B3%B4%EA%B8%B0)
* [02. 발견한 문제](https://github.com/tmddusgood/Refactoring/tree/develop/doc/%EC%88%9C%EC%84%9C/02.%20%5B%ED%85%8C%EC%8A%A4%ED%8A%B8%5D%20%EB%B0%9C%EA%B2%AC%ED%95%9C%20%EB%AC%B8%EC%A0%9C)
* [03. 불필요한 메소드 추출](https://github.com/tmddusgood/Refactoring/tree/develop/doc/%EC%88%9C%EC%84%9C/03.%20%5B%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81%5D%20%EB%B6%88%ED%95%84%EC%9A%94%ED%95%9C%20%EB%A9%94%EC%86%8C%EB%93%9C%20%EC%B6%94%EC%B6%9C) 
* [04. 실패하는 테스트 코드 작성](https://github.com/tmddusgood/Refactoring/tree/develop/doc/%EC%88%9C%EC%84%9C/04.%20%5B%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81%5D%20%EC%8B%A4%ED%8C%A8%ED%95%98%EB%8A%94%20%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EC%BD%94%EB%93%9C%20%EC%9E%91%EC%84%B1)
* [05. 리팩토링 적용을 위한 클래스/메소드 작성 및 적용](https://github.com/tmddusgood/Refactoring/tree/develop/doc/%EC%88%9C%EC%84%9C/05.%20%5B%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81%5D%20%ED%83%80%EA%B2%9F%20%EB%A9%94%EC%86%8C%EB%93%9C%20%EC%9E%91%EC%84%B1%2C%20%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EB%B0%8F%20%EC%A0%81%EC%9A%A9)
* [06. 테스트 코드 리팩토링](https://github.com/tmddusgood/Refactoring/tree/develop/doc/%EC%88%9C%EC%84%9C/06.%20%5B%ED%85%8C%EC%8A%A4%ED%8A%B8%5D%20%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EC%BD%94%EB%93%9C%20%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81)

## 리팩토링 목록 (진행 중)
* 매직 넘버를 기호 상수로 치환
* 클래스 추출
* 메서드 추출
* 제어 플래그 삭제
* 분류 코드를 상태/전략 패턴으로 치환
* 생성자를 팩토리 메서드로 치환
* 임시 변수를 인라인화
* 메서드 매개변수 정리
## 부록
* [테스트 가능한 설계]()
* [리팩토링 카탈로그]()

## 레퍼런스
* 자바로 배우는 리팩토링 입문 - 유키 히로시 / 2006
* 자바와 JUnit을 활용한 실용주의 단위 테스트 - 제프 랭어 외 2명 / 2015
* Effective Unit Testing - 2013
* 소프트웨어 악취를 제거하는 리팩토링 - 가네쉬 사마스얌, 기리쉬 서야나라야나 외 1명 / 2015