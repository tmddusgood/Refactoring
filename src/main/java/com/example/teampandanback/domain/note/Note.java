package com.example.teampandanback.domain.note;

import com.example.teampandanback.domain.Timestamped;
import com.example.teampandanback.domain.project.Project;
import com.example.teampandanback.domain.user.User;
import com.example.teampandanback.dto.note.request.NoteCreateRequestDto;
import com.example.teampandanback.dto.note.request.NoteUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

//TODO 함수 이름, 기타 리팩토링 필요
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
public class Note extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Long noteId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "DEADLINE")
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "STEP")
    private Step step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Column(name = "PREVIOUS")
    private Long previousId;

    @Column(name = "NEXT")
    private Long nextId;

    @Column(name = "LOCKED")
    private Boolean locked;

    @Column(name = "WRITING")
    private Boolean writing;

    @Column(name = "WRITER_ID")
    private Long writerId;


    @Builder
    public Note(String title, String content, LocalDate deadline, Step step, User user, Project project, Long previousId, Long nextId) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.step = step;
        this.user = user;
        this.project = project;
        this.previousId = previousId;
        this.nextId = nextId;
        this.locked = false;
        this.writing = false;
        this.writerId = null;
    }

    public void update(NoteUpdateRequestDto noteUpdateRequestDto, LocalDate updateLocalDate) {
        this.title = noteUpdateRequestDto.getTitle();
        this.content = noteUpdateRequestDto.getContent();
        this.deadline = updateLocalDate;
    }

    public void updatePreviousIdAndNextId(Long previousId, Long nextId) {
        this.previousId = previousId;
        this.nextId = nextId;
    }

    public void updatePreviousId(Long previousId) {
        this.previousId = previousId;
    }

    public void updateNextId(Long nextId) {
        this.nextId = nextId;
    }

    //TODO change name
    public void updateStep(Step step) {
        this.step = step;
    }


    public static Note of(NoteCreateRequestDto noteCreateRequestDto, LocalDate deadline, Step step, User user, Project project, Long previousId, Long nextId) {
        return Note.builder()
                .title(noteCreateRequestDto.getTitle())
                .content(noteCreateRequestDto.getContent())
                .deadline(deadline)
                .step(step)
                .user(user)
                .project(project)
                .previousId(previousId)
                .nextId(nextId)
                .build();
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public void setWriting(Boolean writing) {
        this.writing = writing;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }
}
