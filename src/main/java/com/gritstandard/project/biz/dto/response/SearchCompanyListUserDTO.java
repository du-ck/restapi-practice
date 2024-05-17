package com.gritstandard.project.biz.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchCompanyListUserDTO {
    private String loginId;
    private String userGroupNumber;
    private String userName;
    private String phoneNumber;
    private String userCompanyName;
}
