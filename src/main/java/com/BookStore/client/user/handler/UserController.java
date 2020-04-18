package com.BookStore.client.user.handler;

import com.BookStore.beans.User;
import com.BookStore.client.user.service.UserService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired

    private UserService userService;

    @RequestMapping("/index")
    public String index() {

        return "client/cart.jsp";
    }

    @PostMapping("/register")
    public String registerUsr(User user, HttpSession session, String code, Model model) {
        //图片验证正确
        String PictureCode = (String) session.getAttribute("checkcode_session");
        if (StringUtils.equals(PictureCode, code)) {
            //插入
            user.setActivecode(UUID.randomUUID().toString());
            userService.insert(user);
            return "redirect:/client/registersuccess.jsp";
        } else {
            model.addAttribute("code_error", "验证码错误");
            return "/client/register.jsp";
        }
    }

    @RequestMapping("/activeUser")
    public String activeUser(String activeCode) {
        //System.out.println(activeCode);
        int res = userService.updateByActiveCode(activeCode);
        if (res > 0) {
            System.out.println("激活成功");

            return "redirect:/client/activesuccess.jsp";
        } else {
            return "redirect:/client/activeFail.jsp";
        }

    }

    @PostMapping("/findEmail")
    @ResponseBody
    public String findEmail(String email) {
        int row = userService.findEmail(email);
        if (row > 0) {
            return "exist";
        } else return "no-exist";
    }

    @PostMapping("/findUsername")
    @ResponseBody
    public String findUsername(String userName) {
        int row = userService.findUsername(userName);
        if (row > 0) {
            return "exist";
        } else return "no-exist";
    }

    @GetMapping("/myAccount")
    public String Login(HttpSession session, HttpServletRequest request) {
        User u = (User) session.getAttribute("login_user");
        if (u == null) {
            u = autologin(request); //自动登录能否成功
            if (u != null) {
                return "/client/myAccount.jsp";
            }
            return "/client/login.jsp";
        } else {
            return "/client/myAccount.jsp";
        }
    }

    private User autologin(HttpServletRequest request) {
        String username = null;
        String password = null;
        Cookie[] c = request.getCookies();
        for (Cookie cookie : c) {
            if (StringUtils.equals(cookie.getName(), "book_store_username")) {
                username = cookie.getValue();
            }
            if (StringUtils.equals(cookie.getName(), "book_store_password")) {
                password = cookie.getValue();
            }
        }
        User u = new User();
        u.setPassword(password);
        u.setUsername(username);
        return userService.findUsernameLogin(u);

    }

    @PostMapping("/login")
    public String login(User u, HttpSession session, String remembered, String autoLogin, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.findUsernameLogin(u);
        if (user != null) {
            if (user.getState() == 1) {
                //auto login impl
                if (StringUtils.equals("1", autoLogin)) {
                    addCookie(autoLogin, user, request, response);
                } else if (StringUtils.equals("1", remembered)) { //记住用户名
                    addCookie(autoLogin, user, request, response);
                }
                session.setAttribute("login_user", u);
                return "/client/myAccount.jsp";
            } else {
                //未激活的用户
                request.setAttribute("login_error", "用户未激活,请前去激活");
                return "/client/login.jsp";
            }
        } else {
            request.setAttribute("login_error", "账号或者密码错误, 请重新登录");
            return "/client/login.jsp";
        }

    }

    private void addCookie(String autoLogin, User user, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.equals("1", autoLogin)) {
            Cookie cookie = new Cookie("book_store_password", user.getPassword());
            cookie.setMaxAge(60 * 60 * 24 * 3); //Sets the maximum age in seconds for this Cookie.
            //cookie 作用在当前客户端
            cookie.setPath(request.getContextPath() + "/");
            response.addCookie(cookie);
        }
        Cookie cookie = new Cookie("book_store_username", user.getUsername());
        cookie.setMaxAge(60 * 60 * 24 * 3); //Sets the maximum age in seconds for this Cookie.
        //cookie 作用在当前客户端
        cookie.setPath(request.getContextPath() + "/");
        response.addCookie(cookie);

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("login_user");
        return "/client/login.jsp";
    }
}
