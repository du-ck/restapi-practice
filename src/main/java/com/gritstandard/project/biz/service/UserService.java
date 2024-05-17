package com.gritstandard.project.biz.service;

import com.gritstandard.project.biz.dto.UserDTO;

public interface UserService {
    public UserDTO getUserInfo(UserDTO user);

    public String getUserAuth(String userId);

}
