package com.BookStore.client.user.dao;

import com.BookStore.beans.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();

    int updateByActiveCode(@Param("code") String activeCode);

    int findEmail(String email);

    int findUsername(String userName);

    User findUsernameLogin(User u);
}