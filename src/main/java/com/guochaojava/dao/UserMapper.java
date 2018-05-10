package com.guochaojava.dao;


import com.guochaojava.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    User selectUserByEmail(String email);
}