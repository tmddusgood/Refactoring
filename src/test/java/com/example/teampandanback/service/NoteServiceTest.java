package com.example.teampandanback.service;

import com.example.teampandanback.domain.project.Project;
import com.example.teampandanback.domain.project.ProjectRepository;
import com.example.teampandanback.domain.user.User;
import com.example.teampandanback.domain.user.UserRepository;
import com.example.teampandanback.domain.user_project_mapping.UserProjectMapping;
import com.example.teampandanback.domain.user_project_mapping.UserProjectMappingRepository;
import com.example.teampandanback.domain.user_project_mapping.UserProjectRole;
import com.example.teampandanback.dto.project.request.ProjectRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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
        //given 에러가 발생할 상황 만들기
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
