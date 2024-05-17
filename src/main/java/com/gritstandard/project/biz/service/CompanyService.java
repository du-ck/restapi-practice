package com.gritstandard.project.biz.service;

import com.gritstandard.project.biz.dto.UserDTO;
import com.gritstandard.project.biz.dto.response.SearchCompanyListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    public boolean checkCompanyGroupNumber(UserDTO userInfo);
    public List<SearchCompanyListDTO> getCompanyListWithUsers(String companyName, PageRequest pageRequest);

    public boolean deleteGroupNumber(long companyId, String deleteCompanyGroupNumber);
}
