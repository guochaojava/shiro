package com.guochaojava.service;

import com.guochaojava.model.User;

import java.util.Set;

public interface UserService {
    User selectUserByEmail(String email);

    Set<String> selectRolesByEmail(String email);

    Set<String> selectPermissionsByEmail(String email);
}
