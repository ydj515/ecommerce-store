package org.example.userservice.model.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequest {

    @Size(min=2, message="이름은 최소 2글자 이상입니다.")
    @NotNull(message="이름 입력은 필수 입니다.")
    private String userId;

    @NotNull(message="이메일 입력은 필수 입니다.")
    @Size(min=5, message="이메일은 최소 5글자 이상입니다.")
    @Email(message = "이메일 형식을 확인하세요", regexp = "^[A-Za-z0-9+_.-]+@(.+)$\n")
    private String email;

    @Size(min=2, message="이름은 최소 2글자 이상입니다.")
    @NotNull(message="이름 입력은 필수 입니다.")
    private String name;

    @NotNull(message="비밀번호 입력은 필수 입니다.")
    @Size(min=2, message="비밀번호는 최소 2글자 이상입니다.")
    private String password;

}
