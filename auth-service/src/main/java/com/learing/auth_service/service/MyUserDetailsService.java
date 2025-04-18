package com.learing.auth_service.service;

import com.learing.auth_service.client.UserClient;
import com.learing.auth_service.dto.UserDTO;
import com.learing.auth_service.exception.AppException;
import com.learing.auth_service.exception.ErrorCode;
import com.learing.auth_service.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserClient userClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userClient.getUserByUsername(username);
        if (user == null) throw new AppException(ErrorCode.USER_IS_NOT_EXISTS);
        return new UserPrincipal(user);
    }
}
