package com.guochaojava.service.impl;

import com.guochaojava.dao.UserMapper;
import com.guochaojava.model.User;
import com.guochaojava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author GuoChao.
 * @since
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper dao;

    @Override
    public User selectUserByEmail(String email) {
        return dao.selectUserByEmail(email);
    }

    @Override
    public Set<String> selectRolesByEmail(String email) {
        return null;
    }

    @Override
    public Set<String> selectPermissionsByEmail(String email) {
        return null;
    }
}