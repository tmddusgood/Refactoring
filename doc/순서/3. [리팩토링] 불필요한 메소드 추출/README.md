[2. 테스트 코드를 작성해보기 - 발견한 문제점](https://github.com/tmddusgood/Refactoring/tree/develop/doc/%EC%88%9C%EC%84%9C/2.%20%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EC%BD%94%EB%93%9C%EB%A5%BC%20%EC%9E%91%EC%84%B1%ED%95%B4%EB%B3%B4%EA%B8%B0%20-%20%EB%B0%9C%EA%B2%AC%ED%95%9C%20%EB%AC%B8%EC%A0%9C%EC%A0%90)
에서 이어지는 글입니다.
# 리팩토링 카탈로그
### 대상
* NoteService.checkUserProject()
### 이름
* 불필요한 메소드 추출 
### 상황 
* 레파지토리에서 정의된 메소드를 다시 추출하여 행동 양식이 정확히 일치하는 메소드를 다시 만들었음
### 문제
* 단지 ‘편의’나 ‘작업을 끝내려는’, 이 경우 오로지 가독성을 높이겠다는 목적에서 메소드를 추출한 케이스
* 절차적인 사고로 만든 이런 메소드는 유일무이하면서 의미있는 책임을 할당받지 못하여 단일 책임 원칙(SRP)를 위반한다.
* NoteService에 있는 메소드들의 추상화 단계가 달라지는 문제
### 해법
* 인라인 메소드를 활용하거나
* [메소드 추출 -> 다시 메소드 추출]의 과정을 했으므로 첫번째 단계에서 필요로 하는 메소드를 제대로 정의한다.
### 결과
* 유일무이하고 의미있는 책임을 가진 메소드를 올바른 레이어에서 정의하여 사용할 수 있음
* 레파지토리에서 정의된 메소드임이 명확해진다.
* 가독성 문제에서도 checkUserProject()를 만들었던 목적에 부합한다.
### 방법
1. checkUserProject() 삭제
2. 새로 만들 메서드 작성 전, 실패하는 테스트 작성
   * 슬라이스 테스트 DataJPAtest 
   * UserProejctMappingRequest에 임의의 유저와 프로젝트 저장하고
     * 유저-프로젝트 매핑이 틀리거나 존재하지 않을 경우 Exception 발생
     * 제대로 존재하는 경우 통과
3. UserMappingRepository에 checkUserProject에서 필요로 하는 행동을 하는 메소드를 정의
4. 테스트 통과하는지 확인
5. 통과한다면 checkUserProject() 대체

