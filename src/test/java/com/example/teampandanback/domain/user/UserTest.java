package com.example.teampandanback.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    @DisplayName("User Managed By Lombok Should Work")
    public void UserTest(){
        User user = User.builder()
                .name("강승연")
                .password("0000")
                .picture("pictureURL")
                .email("tmddusgood@gmail.com")
                .kakaoId(154109239L)
                .build();

        assertThat(user.getUserId()).isEqualTo(null);
        assertThat(user.getName()).isEqualTo("강승연");
        assertThat(user.getPassword()).isEqualTo("0000");
        assertThat(user.getEmail()).isEqualTo("tmddusgood@gmail.com");
        assertThat(user.getPicture()).isEqualTo("pictureURL");
        assertThat(user.getKakaoId()).isEqualTo(154109239L);
    }

    @Test
    @DisplayName("User Update Should Work")
    public void UserUpdateTest(){
        User user = User.builder()
                .name("강승연")
                .password("0000")
                .picture("pictureURL")
                .email("tmddusgood@gmail.com")
                .kakaoId(154109239L)
                .build();

        User updateUser = user.update("홍길동", "newPictureURL");

        assertThat(updateUser.getName()).isEqualTo("홍길동");
        assertThat(updateUser.getPicture()).isEqualTo("newPictureURL");
    }
}
