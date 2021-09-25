# 코드
~~~java
@DataJpaTest
@AutoConfigureTestDatabase(connection = H2)
public class UserProjectMappingRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectMappingRepository userProjectMappingRepository;

    @Test
    @DisplayName("유저-프로젝트 매핑이 불일치하면 Exception 반환한다")
    public void findByUserIdAndProjectIdJoinTest(){
        //given
        userProjectMappingRepository.save(UserProjectMapping.builder()
                    .userProjectRole(UserProjectRole.OWNER)
                    .user(userRepository.save(User.builder()
                                    .name("강승연")
                                    .email("tmddusgood@gmail.com")
                                    .picture("pictureURL")
                                    .kakaoId(1234L)
                                    .password("0000")
                                    .build()))
                    .project(projectRepository.save(Project.builder()
                                .title("random project")
                                .detail("none")
                                .build()))
                    .build());

        //when
        Exception exception = assertThrows(Exception.class,
                () -> userProjectMappingRepository.findByUserIdAndProjectIdOrThrowsException(1L, 2L));

        //then
        assertThat(exception.getCause().getMessage()).isEqualTo("해당 프로젝트에 소속된 유저가 아닙니다.");
    }

}
~~~
#설명
* 매개 변수의 인라인화 적용
    * projectWithID 는 결국 projectRepository.save(project)의 결과물과 같습니다
    * userWithID 역시 userRepository.save(user)의 결과물과 같습니다.
* 매개 변수로서 참조 변수가 들어가는 것이 아니라, 실제 값이 보이기 때문에 더 명확한 코드가 될 수 있습니다.