package com.gritstandard.project.biz.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gritstandard.project.biz.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Schema(description = "로그인ID", requiredMode = RequiredMode.REQUIRED, example = "user2")
    private String loginId;
    @Schema(description = "로그인 Password", requiredMode = RequiredMode.REQUIRED, example = "user2")
    private String password;
    @Schema(description = "User가 속한 그룹넘버", requiredMode = RequiredMode.REQUIRED, example = "grp2")
    private String groupNumber;
    @Schema(hidden = true)
    private String companyName;

    public static UserDTO toDTO(Users userEntity) {
        return UserDTO.builder()
                .loginId(userEntity.getLoginId())
                .password(userEntity.getPassword())
                .groupNumber(userEntity.getGroupNumber())
                .companyName(userEntity.getCompanyName())
                .build();
    }
}
