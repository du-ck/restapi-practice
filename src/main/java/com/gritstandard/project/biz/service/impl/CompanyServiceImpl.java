package com.gritstandard.project.biz.service.impl;

import com.gritstandard.project.biz.dto.CompanyDTO;
import com.gritstandard.project.biz.dto.UserDTO;
import com.gritstandard.project.biz.dto.response.SearchCompanyListDTO;
import com.gritstandard.project.biz.dto.response.SearchCompanyListUserDTO;
import com.gritstandard.project.biz.entity.Company;
import com.gritstandard.project.biz.repository.CompanyRepository;
import com.gritstandard.project.biz.service.CompanyService;
import com.gritstandard.project.common.util.ParsingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * 로그인 시 User의 group number 검증을 위한 db 조회
     * @param userInfo
     * @return User가 속한 Company의 group_number 정상 여부
     */
    @Override
    public boolean checkCompanyGroupNumber(UserDTO userInfo) {
        //user 의 company name 으로 Company 조회 >> 해당 Company 의 group_number 에 contain 되어있는지 확인
        Optional<Company> findCompany = companyRepository.findByNameAndGroupNumberLike(userInfo.getCompanyName(), userInfo.getGroupNumber());
        if (findCompany.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public List<SearchCompanyListDTO> getCompanyListWithUsers(String companyName, PageRequest pageRequest) {
        List<SearchCompanyListDTO> reponseList = new ArrayList<>();
        List<Object[]> resultList = companyRepository.findByNameOrderByIdDesc(companyName, pageRequest).getContent();
        Set<String> allKeys = new HashSet<>();

        //SearchCompanyListDTO 에 담기위한 작업

        //> company 별로 구분하기 위해 COMPANY_NAME 기준으로 수집
        for (Object[] item : resultList) {
            allKeys.add(String.valueOf(item[1]));
        }

        //SearchCompanyListDTO 에 맞게 담는다
        for (String key : allKeys) {
            CompanyDTO company = new CompanyDTO();
            List<SearchCompanyListUserDTO> userDTOList = new ArrayList<>();
            company.setName(key);
            for (Object[] item : resultList) {
                if (String.valueOf(item[1]).equalsIgnoreCase(key)) {
                    company.setGroupNumber(String.valueOf(item[2]));
                    userDTOList.add(SearchCompanyListUserDTO.builder()
                            .loginId(String.valueOf(item[4]))
                            .userGroupNumber(String.valueOf(item[5]))
                            .userName(String.valueOf(item[6]))
                            .phoneNumber(String.valueOf(item[7]))
                            .userCompanyName(String.valueOf(item[8]))
                            .build());
                }
            }
            reponseList.add(SearchCompanyListDTO.builder()
                    .company(company)
                    .users(userDTOList)
                    .build());
        }

        return reponseList;
    }

    @Override
    public boolean deleteGroupNumber(long companyId, String deleteCompanyGroupNumber) {
        Optional<Company> companyInfo = companyRepository.findById(companyId);
        if (companyInfo.isPresent()) {
            Company company = companyInfo.get();
            String companyGroupNumber = company.getGroupNumber();
            List<String> parsingGroupNumber = ParsingUtil.ParsingGroupNumber(companyGroupNumber);

            //파싱된 첫번째 그룹넘버는 default 이므로 삭제 불가능
            if (parsingGroupNumber.get(0).equalsIgnoreCase(deleteCompanyGroupNumber)) {
                return false;
            }
            boolean removeResult = parsingGroupNumber.remove(deleteCompanyGroupNumber);
            //삭제할 groupnumber 가 없음
            if (!removeResult) {
                return false;
            }
            String result = String.join(",", parsingGroupNumber);
            company.setGroupNumber(result);

            //update
            companyRepository.save(company);
            return true;
        }
        return false;
    }
}
