package com.gritstandard.project.biz.dto.response;

import com.gritstandard.project.biz.dto.CompanyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchCompanyListDTO {
    private CompanyDTO company;
    private List<SearchCompanyListUserDTO> users;
}
