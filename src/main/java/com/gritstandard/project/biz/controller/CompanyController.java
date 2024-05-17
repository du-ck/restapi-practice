package com.gritstandard.project.biz.controller;

import com.gritstandard.project.biz.dto.response.SearchCompanyListDTO;
import com.gritstandard.project.biz.service.CompanyService;
import com.gritstandard.project.biz.service.UserService;
import com.gritstandard.project.common.data.ErrorResponse;
import com.gritstandard.project.common.data.ResponseData;
import com.gritstandard.project.common.exception.custom.MissingParameterException;
import com.gritstandard.project.common.exception.custom.ResultNotFoundException;
import com.gritstandard.project.common.exception.custom.UnauthorizedAccessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Company", description = "Company API")
public class CompanyController {

    private final UserService userService;
    private final CompanyService companyService;

    /**
     * Company List 조회 API
     * super 관리자(admin)는 company list를 조회할 수 있는데
     * 이 때 Company group_number별 User도 함께 조회되어야 한다
     *
     * 로그인 과정 진행 후로 가정
     * @param page 페이지
     * @param size 조회 시 불러올 데이터의 갯수 (사람 기준 )
     * @param userId 권한 체크용 userId
     * @param companyName 조회하고자 하는 companyName ( 공백일 경우 전체 조회 )
     * @return
     */
    @GetMapping("/company-list")
    @Operation(summary = "company list 조회", description = "company list 를 그룹넘버 별 User정보와 함께 조회한다. ( super관리자 (admin)만 가능)")
    public ResponseEntity<ResponseData> searchCompanyList(
            @Schema(description = "페이지", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "0")
            @RequestParam(name = "page", required = false) String page,
            @Schema(description = "조회 시 불러올 데이터의 갯수 (사람 기준 )", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "100")
            @RequestParam(name = "size", required = false) String size,
            @Schema(description = "로그인ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "user2")
            @RequestParam String userId,
            @Schema(description = "조회하고자 하는 회사 이름")
            @RequestParam(required = false) String companyName) {

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.createDefault())
                .status(HttpStatus.BAD_REQUEST)
                .data(null).build();

        if (!StringUtils.hasText(userId)) {
            throw new MissingParameterException("아이디를 입력해주세요");
        }
        if (!StringUtils.hasText(page)) {
            page = "0";
        }
        if (!StringUtils.hasText(size)) {
            size = "100";
        }

        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));

        //userId 의 권한 체크
        if (!userService.getUserAuth(userId).equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("권한이 없습니다");
        }
        List<SearchCompanyListDTO> resultList = companyService.getCompanyListWithUsers(companyName, pageRequest);
        if (CollectionUtils.isEmpty(resultList)) {
            throw new ResultNotFoundException("데이터가 없습니다");
        }
        responseData.setData(resultList);
        responseData.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(responseData, headers, HttpStatus.OK);
    }


    @DeleteMapping("/company-groupnumber")
    @Operation(summary = "company의 groupnumber 삭제", description = " company에 속하는 group_number를 삭제한다. ( company관리자 (com_id로 권한)만 가능)")
    public ResponseEntity<ResponseData> deleteCompanyNumber(
            @Schema(description = "로그인ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "user7")
            @RequestParam String userId,
            @Schema(description = "삭제하고자 하는 company group number", requiredMode = Schema.RequiredMode.REQUIRED, example = "grp10")
            @RequestParam String companyGroupNumber
    ) {

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        ResponseData responseData = ResponseData.builder()
                .error(ErrorResponse.createDefault())
                .status(HttpStatus.BAD_REQUEST)
                .data(null).build();

        if (!StringUtils.hasText(userId)) {
            throw new MissingParameterException("아이디를 입력해주세요");
        }
        if (!StringUtils.hasText(companyGroupNumber)) {
            throw new MissingParameterException("그룹넘버를 입력해주세요");
        }
        String userAuth = userService.getUserAuth(userId);
        if (!StringUtils.hasText(userAuth) || !userAuth.contains("COM")) {
            throw new UnauthorizedAccessException("권한이 없습니다");
        }

        //company 권한은 COM + COMPANY_ID 로 가정한다.
        // ex ) COM2

        long companyId = Long.parseLong(userAuth.substring(3));
        if (!companyService.deleteGroupNumber(companyId, companyGroupNumber)) {
            throw new DataIntegrityViolationException("삭제할 수 없는 그룹넘버 입니다");
        }
        responseData.setData(true);
        responseData.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(responseData, headers, HttpStatus.OK);
    }
}
