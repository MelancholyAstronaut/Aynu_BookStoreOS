package com.BookStore.client.user.service;

import com.BookStore.beans.User;

import java.util.List;

public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();

    int updateByActiveCode(String activeCode);

    int findEmail(String email);

    int findUsername(String userName);

    User findUsernameLogin(User u);
}
