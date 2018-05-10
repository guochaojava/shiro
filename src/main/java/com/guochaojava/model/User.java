package com.guochaojava.model;

import lombok.Data;

@Data
public class User {
    /**
     * 对应数据库 user.id
     * 
     */
    private Long id;

    /**
     * 对应数据库 user.email
     * 邮箱|登录帐号
     */
    private String email;

    /**
     * 对应数据库 user.nick_name
     * 用户昵称
     */
    private String nickName;

    /**
     * 对应数据库 user.password
     * 密码
     */
    private String password;

    /**
     * 对应数据库 user.create_time
     * 创建时间
     */
    private String createTime;

    /**
     * 对应数据库 user.last_login_time
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 对应数据库 user.status
     * 1:有效，0:禁止登录
     */
    private Integer status;
}