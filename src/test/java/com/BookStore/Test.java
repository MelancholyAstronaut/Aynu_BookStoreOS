package com.BookStore;

import com.BookStore.client.user.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration({"/application.xml"})
public class Test {
    @Autowired
    private UserService userService;

    @org.junit.Test
    public void all() {
        userService.selectAll().forEach(e -> System.out.println(e));
    }

}
