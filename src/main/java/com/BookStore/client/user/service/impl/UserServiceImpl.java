package com.BookStore.client.user.service.impl;

import com.BookStore.beans.User;
import com.BookStore.client.user.dao.UserDao;
import com.BookStore.client.user.service.UserService;
import com.BookStore.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao dao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(User record) {

        String msg = "请激活用户 ," +
                "点击" +
                "<a href='http://localhost:8080/bookstore/user/activeUser?activeCode="
                + record.getActivecode() + "'>这里激活</a>";
        try {
            MailUtils.sendMail(record.getEmail(), msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return dao.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    @Override
    public List<User> selectAll() {
        return dao.selectAll();
    }

    @Override
    public int updateByActiveCode(String activeCode) {
        return dao.updateByActiveCode(activeCode);
    }

    @Override
    public int findEmail(String email) {
        return dao.findEmail(email);
    }

    @Override
    public int findUsername(String userName) {
        return dao.findUsername(userName);
    }

    @Override
    public User findUsernameLogin(User u) {
        return dao.findUsernameLogin(u);
    }
}
