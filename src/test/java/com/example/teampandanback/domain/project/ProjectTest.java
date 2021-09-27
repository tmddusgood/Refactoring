package com.example.teampandanback.domain.project;

import com.example.teampandanback.dto.project.request.ProjectRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectTest {
    @Test
    @DisplayName("Project Managed By Lombok Should Work")
    public void ProjectTest(){
        Project project = Project.builder()
                .title("임의의 프로젝트")
                .detail("없음")
                .build();

        assertThat(project.getProjectId()).isEqualTo(null);
        assertThat(project.getTitle()).isEqualTo("임의의 프로젝트");
        assertThat(project.getDetail()).isEqualTo("없음");
    }

    @Test
    @DisplayName("Project Factory Method [toEntity] Should Work")
    public void ProjectToEntityTest(){
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .title("임의의 프로젝트")
                .detail("없음")
                .build();

        Project project = Project.toEntity(projectRequestDto);

        assertThat(project.getProjectId()).isEqualTo(null);
        assertThat(project.getTitle()).isEqualTo("임의의 프로젝트");
        assertThat(project.getDetail()).isEqualTo("없음");
    }

    @Test
    @DisplayName("Project Method [update] Should Work")
    public void ProjectUpdateTest (){
        Project project = Project.builder()
                .title("임의의 프로젝트")
                .detail("없음")
                .build();

        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .title("임의의 프로젝트 2")
                .detail("없음 2")
                .build();

        project.update(projectRequestDto);

        assertThat(project.getProjectId()).isEqualTo(null);
        assertThat(project.getTitle()).isEqualTo("임의의 프로젝트 2");
        assertThat(project.getDetail()).isEqualTo("없음 2");
    }
}
