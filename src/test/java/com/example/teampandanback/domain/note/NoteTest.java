package com.example.teampandanback.domain.note;

import com.example.teampandanback.domain.project.Project;
import com.example.teampandanback.domain.user.User;
import com.example.teampandanback.dto.note.request.NoteUpdateRequestDto;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NoteTest {

    public Note randomNote;

    @Test
    @BeforeEach
    public void setUp() {
        User user = mock(User.class);
        Project project = mock(Project.class);
        when(user.getUserId()).thenReturn(1541L);
        when(project.getProjectId()).thenReturn(1111L);

        this.randomNote = Note.builder()
                .title("randomTitle")
                .content("randomContent")
                .deadline(LocalDate.of(2020, 10, 9))
                .step(Step.TODO)
                .user(user)
                .project(project)
                .previousId(9998L)
                .nextId(9999L)
                .build();
    }

    //TODO 다 안했음
    @Test
    @DisplayName("노트 객체를 생성하면 Lombok Getter를 통해 값을 확인할 수 있다.")
    public void noteTest() {
        assertThat(randomNote.getNoteId()).isEqualTo(null);
        assertThat(randomNote.getTitle()).isEqualTo("randomTitle");
        assertThat(randomNote.getContent()).isEqualTo("randomContent");
        assertThat(randomNote.getDeadline()).isEqualTo(LocalDate.of(2020, 10, 9));
        assertThat(randomNote.getStep()).isEqualTo(Step.TODO);
        assertThat(randomNote.getProject().getProjectId()).isEqualTo(1111L);
        assertThat(randomNote.getUser().getUserId()).isEqualTo(1541L);
        assertThat(randomNote.getPreviousId()).isEqualTo(9998L);
        assertThat(randomNote.getNextId()).isEqualTo(9999L);
    }

    @Test
    @DisplayName("노트 수정 시 노트 내용 업데이트하면 Title, Content, Deadline이 수정된다.")
    public void noteUpdateTest() {
        NoteUpdateRequestDto noteUpdateRequestDto = mock(NoteUpdateRequestDto.class);
        when(noteUpdateRequestDto.getContent()).thenReturn("randomContentUpdate");
        when(noteUpdateRequestDto.getTitle()).thenReturn("randomTitleUpdate");

        randomNote.update(noteUpdateRequestDto, LocalDate.of(2021, 10, 10));

        assertThat(randomNote.getTitle()).isEqualTo("randomTitleUpdate");
        assertThat(randomNote.getContent()).isEqualTo("randomContentUpdate");
        assertThat(randomNote.getDeadline()).isEqualTo(LocalDate.of(2021, 10, 10));
    }

    @Test
    @DisplayName("노트 이동 시 PreviousId 수정하면 값을 업데이트다.")
    public void noteUpdatePreviousId() {
        randomNote.updatePreviousId(0L);
        
        assertThat(randomNote.getPreviousId()).isEqualTo(0L);
    }

    @Test
    @DisplayName("노트 이동 시 NextId 수정하면 값을 없데이트한다.")
    public void noteUpdateNextIdTest() {
        randomNote.updateNextId(999L);

        assertThat(randomNote.getNextId()).isEqualTo(999L);
    }

    @Test
    @DisplayName("노트 이동 시 PreviousId NextId 모두 수정하면 값을 업데이트한다.")
    public void noteUpdatePreviousIdAndNextIdTest(){
        randomNote.updatePreviousIdAndNextId(999L, 999L);

        assertThat(randomNote.getPreviousId()).isEqualTo(999L);
        assertThat(randomNote.getNextId()).isEqualTo(999L);
    }

    //TODO 함수 이름 바꾸기
    @Test
    @DisplayName("노트 이동 시 Step 수정하면 값을 업데이트한다.")
    public void updateStepWhileMoveNoteTest() {
        randomNote.updateStep(Step.STORAGE);

        assertThat(randomNote.getStep()).isEqualTo(Step.STORAGE);
    }

    //TODO
    @Test
    @DisplayName("노트 내용 수정 시 Locked 수정하면 값을 업데이트한다.")
    public void setLockedTest() {
        assertThat(randomNote.getLocked()).isEqualTo(Boolean.FALSE);
        randomNote.setLocked(Boolean.TRUE);

        assertThat(randomNote.getLocked()).isEqualTo(Boolean.TRUE);
    }

    //TODO
    @Test
    @DisplayName("노트 내용 수정 시 Writing 수정하면 값을 업데이트한다.")
    public void setWritingTest() {
        assertThat(randomNote.getWriting()).isEqualTo(Boolean.FALSE);
        randomNote.setWriting(Boolean.TRUE);

        assertThat(randomNote.getWriting()).isEqualTo(Boolean.TRUE);
    }

    @Test
    @DisplayName("노트 내용 수정 시 Writer 수정하면 값을 업데이트한다.")
    public void setWriterId() {
        randomNote.setWriterId(9999L);

        assertThat(randomNote.getWriterId()).isEqualTo(9999L);
    }
}
