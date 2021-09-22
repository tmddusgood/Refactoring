# 테스트 코드를 작성해보기
## NoteService - checkUserProject()
* 제가 작업한 NoteService에서, 가장 작고 책임이 작은 함수를 골라봤습니다.
## 하는 일
* 유저가 해당 프로젝트에 참여하고 있는지를 확인하는 메소드입니다.
* 만약 해당 프로젝트에 소속된 유저가 아니라면 Exception을 발생시킵니다.
~~~java
    // 해당 유저가 참여하고 있는 Project 인지 확인
    private UserProjectMapping checkUserProject(Long userId, Long projectId) {

        // 유저가 참여하고 있는 Project 인지 확인, 해당 Project 가 실제 존재하는지도 함께 확인 가능
        return userProjectMappingRepository
                .findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new ApiRequestException("해당 프로젝트에 소속된 유저가 아닙니다."));
    }
~~~

## 접근
~~~java
@DataJpaTest
public class NoteServiceTest {

    @Autowired
    private UserProjectMappingRepository userProjectMappingRepository;
    // ..
    
    @Test
    @DisplayName("유저가 프로젝트에 있으면 ApiRequestException 반환한다")
    public void checkUserProejctTest(){
        // ..
    }
}
~~~
* @DataJpaTest
  - 모든 스프링 빈을 호출하지 않고 JPA 관련 빈만 호출하여 테스트를 하기 위함입니다. repository 테스트에서 주로 사용합니다.
  - @Transactional을 메타 어노테이션으로 가지기 때문에, 테스트가 끝나면 rollback이 될 것입니다.
* @SpringBootTest
  - 속 편하게 SpringBootTest를 하면 통합테스트를 하게 됩니다. 모든 빌을 올리고 테스트를 진행하는 방법입니다.
  - 저는 단위 테스트를 하는 것이 목적이기 때문에, 슬라이스 테스트라는 말로도 일컬어지는 @DataJpaTest 애노테이션을 사용하겠습니다.
* given, when, then의 구성을 사용합니다.
* 단언문이 비교할 대상은 변수가 아니라 실제 값과 비교하게 하여 명확하게 합니다.


## 작성된 코드
~~~java
@DataJpaTest
public class NoteServiceTest {

    @Autowired
    private UserProjectMappingRepository userProjectMappingRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private Project project;
    private ProjectRequestDto projectRequestDto;
    private UserProjectMapping userProjectMapping;

    /* 유저가 프로젝트에 있으면 ApiRequestException 반환한다
     */
    @Test
    @DisplayName("checkUserProjectTest - 유저가 프로젝트에 있으면 ApiRequestException 반환한다")
    public void checkUserProejctTest(){

        final User userDto = User.builder()
                .name("강승연")
                .email("tmddusgood@gmail.com")
                .password("0000")
                .picture("pictureUrl")
                .kakaoId(0000L)
                .build();

        final ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .title("임의의 프로젝트")
                .detail("없음")
                .build();

        final User user = userRepository.save(userDto);
        final Project project = projectRepository.save(Project.toEntity(projectRequestDto));

        final UserProjectMapping userProjectMapping = UserProjectMapping.builder()
                .userProjectRole(UserProjectRole.OWNER)
                .user(user)
                .project(project)
                .build();

        userProjectMappingRepository.save(userProjectMapping);

        //when
        Optional<UserProjectMapping> foundUserProjectMapping = userProjectMappingRepository
                .findByUserIdAndProjectId(1L, 1L);

        //then
        assertThat(foundUserProjectMapping.get().getUser().getUserId()).isEqualTo(1L);
        assertThat(foundUserProjectMapping.get().getProject().getProjectId()).isEqualTo(1L);
    }
}
~~~

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

