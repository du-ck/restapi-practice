package com.gritstandard.project.biz.controller;


import com.gritstandard.project.biz.dto.UserDTO;
import com.gritstandard.project.biz.service.CompanyService;
import com.gritstandard.project.biz.service.UserService;
import com.gritstandard.project.common.data.ErrorResponse;
import com.gritstandard.project.common.data.ResponseData;
import com.gritstandard.project.common.exception.custom.MissingParameterException;
import com.gritstandard.project.common.exception.custom.ResultNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;

    /**
     * Login API
     * login_id, password, group_number 를 가지고 로그인 진행
     * 로그인 시 User의 group_number 를 검증
     * @param user
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "사용자 로그인 통과여부를 반환한다")
    public ResponseEntity<ResponseData> login(@RequestBody UserDTO user) {

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.createDefault())
                .status(HttpStatus.BAD_REQUEST)
                .data(null).build();

        if (!StringUtils.hasText(user.getLoginId())) {
            throw new MissingParameterException("아이디를 입력해주세요");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new MissingParameterException("비밀번호를 입력해주세요");
        }
        if (!StringUtils.hasText(user.getGroupNumber())) {
            throw new MissingParameterException("그룹넘버를 입력해주세요");
        }

        //정보 조회
        UserDTO userInfo = userService.getUserInfo(user);
        if (userInfo == null) {
            throw new ResultNotFoundException("사용자가 없습니다");
        }

        //정보가 정상일 경우 group_number 검증
        boolean chkGroupNumber = companyService.checkCompanyGroupNumber(userInfo);
        if (!chkGroupNumber) {
            throw new IllegalArgumentException("정상적인 그룹넘버가 아닙니다");
        }

        responseData.setData("로그인 성공");
        responseData.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(responseData, headers, HttpStatus.OK);
    }
}
