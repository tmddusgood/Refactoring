[1. 테스트 코드를 작성해보기](https://github.com/tmddusgood/Refactoring/tree/develop/doc/%EC%88%9C%EC%84%9C/1.%20%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EC%BD%94%EB%93%9C%EB%A5%BC%20%EC%9E%91%EC%84%B1%ED%95%B4%EB%B3%B4%EA%B8%B0)
에서 이어지는 글입니다.

##문제점
### Service 테스트인데 Repository를 테스트하고 있다.
* checkUserProject라는 메소드가 하는 일은 Repository 메소드를 실행한 결과를 리턴하는 것입니다.
    * 인라인 형태로 작성된 것이 단순히 길다는 이유로 메서드 추출을 한 것입니다.
* 즉 서비스에서 자주 호출되는 repository 메소드라는 이유로 도메인 영역에서의 함수를 서비스 영역에서 캡슐화한 것입니다.

### UserMappingRepository 에 협력 객체가 다수
* UserId, ProjectId가 필요한데 이는 애초에 저장되지 않으면 즉 가입이 되지 않으면 생기지 않는 값입니다.
* 그래서 User, Project를 만들어서 가입을 시켜야 하는데..

### 객체 생성 불가
~~~java
        final User userDto = User.builder()
        //..
        .build();
~~~
* SignUpRequestDto를 User 객체에 채우고, 그걸 저장하는 것이 회원가입 절차입니다.
* 그런데 SignUpRequestDto 객체를 만들어 값을 채울 방법이 없네요.
    * 그래서 부득이하게 User 엔티티를 바로 사용하게 되었습니다.
* 하지만 이는 카카오 유저가 가입할 때 쓰는 절차이지 일반 유저가 가입할 때 쓰는 과정이 아니네요.
    * 반면 ProjectRequestDto는 팩토리 메소드를 만들어두어서 활용할 수 있었습니다.

### 협력 객체가 많아질 경우, Bottom to Top 까지 모든 객체를 생성해야 하나?
* 바로 여기서 테스트 더블의 필요성이 등장합니다.
* 별도의 JPA 테스트 환경이 아니라, UserRepository.save()는 반드시 특정 값을 가진 무언가를 리턴하게 하는 게 있었다면요?
* 서비스 테스트에서는 더더욱 Mock의 필요성이 커진다는 것을 알 수 있었습니다.