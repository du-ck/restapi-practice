package com.gritstandard.project.biz.service.impl;

import com.gritstandard.project.biz.dto.UserDTO;
import com.gritstandard.project.biz.entity.Company;
import com.gritstandard.project.biz.entity.Users;
import com.gritstandard.project.biz.repository.CompanyRepository;
import com.gritstandard.project.biz.repository.UserRepository;
import com.gritstandard.project.biz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * 로그인 시도한 유저의 정보로 db 조회
     * @param user
     * @return
     */
    @Override
    public UserDTO getUserInfo(UserDTO user) {
        Optional<Users> findUser = userRepository.findByLoginIdAndPasswordAndGroupNumber(user.getLoginId(), user.getPassword(), user.getGroupNumber());
        if (findUser.isPresent()) { 
            UserDTO userInfo = UserDTO.toDTO(findUser.get());
            if (userInfo != null) {
                return userInfo;
            }
        }
        return null;
    }

    @Override
    public String getUserAuth(String userId) {
        Optional<Users> findUser = userRepository.findByLoginId(userId);
        if (findUser.isPresent()) {
            return findUser.get().getAuth();
        }
        return "";
    }
}
