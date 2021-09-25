# 코드 (테스트 코드 리팩토링 전)
~~~java
@DataJpaTest
public class UserProjectMappingRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectMappingRepository userProjectMappingRepository;
    
    private User user;
    private User userWithId;
    private Project project;
    private Project projectWithId;
    private UserProjectmapping userProjectmapping;

    @Test
    @DisplayName("유저-프로젝트 매핑이 불일치하면 Exception 반환한다")
    public void findByUserIdAndProjectIdJoinTest(){
        //given
        user = User.builder()
                .name("강승연")
                .email("tmddusgood@gmail.com")
                .picture("pictureURL")
                .kakaoId(1234L)
                .password("0000")
                .build();

        project = Project.builder()
                .title("random project")
                .detail("none")
                .build();
        
        userWithId = userRepository.save(user);
        projectWithId = projectRepository.save(project);
        
        userProjectMappingRepository.save(UserProjectMapping.builder()
                    .userProjectRole(UserProjectRole.OWNER)
                    .user(userWithId)
                    .project(projectRepository.save(projectWithId)
                    .build()));

        //when
        Exception exception = assertThrows(Exception.class,
                () -> userProjectMappingRepository.findByUserIdAndProjectIdOrThrowsException(1L, 2L));

        //then
        assertThat(exception.getCause().getMessage()).isEqualTo("해당 프로젝트에 소속된 유저가 아닙니다.");
    }

}
~~~
# 설명
* 레파지토리 테스트이므로 @DataJpaTest를 사용합니다
* 이 때 사용되는 userRepository와 projectRepository는 가짜 객체가 아니므로 @Autowired를 사용합니다.
* 처음 저장되는 user와 project는 id 값이 없어서, 만든 후 바로 저장하면 constraint 조건 때문에 오류가 납니다.
* 따라서 저장해서 돌려받은 객체를 userProjectMapping 테이블에 다시 저장한 값을 확인해야 합니다.
* 그렇게 저장한 객체에 매핑이 되어있지 않은 유저와 프로젝트의 아이디를 넣으면 에러가 발생할 것입니다.
* 그리고 그 메시지는 입력한 메시지와 같아야 합니다.