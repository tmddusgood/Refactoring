package com.example.teampandanback.domain.user_project_mapping;

import com.example.teampandanback.domain.project.Project;
import com.example.teampandanback.domain.project.ProjectRepository;
import com.example.teampandanback.domain.user.User;
import com.example.teampandanback.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;

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
