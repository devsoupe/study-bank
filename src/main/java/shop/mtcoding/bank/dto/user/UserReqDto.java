package shop.mtcoding.bank.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

public class UserReqDto {

    @Getter
    @Setter
    public static class JoinReqDto {

        // 영문, 숫자는 되고, 길이 최소 2~20자 이내
        // null이거나, 공백일 수 없다
        @Pattern(regexp = "", message = "영문/숫자 2~20자 이내로 작성해주세요")
        @NotEmpty
        private String username;

        // 길이 4~20
        @NotEmpty
        private String password;

        // 이메일 형식
        @NotEmpty
        private String email;

        // 영어, 한글 1~20
        @NotEmpty
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }
}
