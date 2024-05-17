package com.gritstandard.project.biz.repository;

import com.gritstandard.project.biz.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByNameAndGroupNumberLike(String userCompanyName, String groupNumber);


    @Query(value = "SELECT c.id AS company_id, c.name AS company_name, c.group_number AS company_group_number, " +
            "u.id AS user_id, u.login_id, u.group_number AS user_group_number, u.name AS user_name, u.phone_number, u.company_name AS user_company_name" +
            " from company as c " +
            " LEFT JOIN users AS u ON" +
            " c.name = u.company_name" +
            " and c.group_number LIKE CONCAT('%', u.group_number, '%')" +
            " where LOWER(c.name) LIKE CONCAT('%', LOWER(:companyName), '%')", nativeQuery = true)
    Page<Object[]> findByNameOrderByIdDesc(@Param("companyName") String companyName, Pageable pageable);



}
