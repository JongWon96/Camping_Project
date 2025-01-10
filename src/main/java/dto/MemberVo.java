package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVo {

    private String memberId;    // 사용자 아이디
    private String password;    // 사용자 비밀번호
    private String name;        // 사용자 이름
    private String email;       // 사용자 이메일
    private String address;     // 사용자 주소
    private int phone;          // 사용자 전화번호
    private int gender;         // 사용자 성별
    private int age;            // 사용자 나이



}