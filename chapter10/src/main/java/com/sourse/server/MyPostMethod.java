package com.sourse.server;

import com.sourse.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "This is all post methods")
@RequestMapping("/V1")
public class MyPostMethod {
    private static Cookie cookie;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "This is a login interface", httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName", required = true) String userName,
                        @RequestParam(value = "passWord", required = true) String passWord) {
        if (userName.equals("zhangsan") && passWord.equals("123456")) {
            cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            return "登录成功";
        }
        return "登录失败";
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ApiOperation(value = "Get user table information", httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                              @RequestBody User u) {

        User user;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("login")
                    && c.getValue().equals("true")
                    && u.getUserName().equals("zhangsan")
                    && u.getPassWord().equals("123456")) {
                user = new User();
                user.setName("lisi");
                user.setAge("18");
                user.setSex("man");
                return user.toString();
            }
        }


        return "参数不合法";
    }

}
