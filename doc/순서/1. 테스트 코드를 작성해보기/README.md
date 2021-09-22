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