package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@Api(value = "v1", description = "用户管理系统")
@RestController
@RequestMapping("v1")
public class userManage {
    @Autowired
    private SqlSessionTemplate template;

    /**
     * login
     *
     * @param response
     * @param user
     * @return
     */
    @ApiOperation(value = "登录接口", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(HttpServletResponse response,
                         @RequestBody User user) {
        int i = template.selectOne("login", user);
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        log.info("查询到的结果为:" + i);
        if (i == 1) {
            log.info("登录的用户为：" + user.getUserName());
            return true;
        }
        return false;
    }

    /**
     * add
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户接口", httpMethod = "POST")
    public boolean addUser(HttpServletRequest request,
                           @RequestBody User user) {
        Boolean b = veritfCookies(request);
        int result = 0;

        if (b != null) {
            result = template.insert("addUser", user);

        }
        if (result > 0) {
            log.info("新增用户数为:" + result);
            return true;
        }
        return false;
    }

    /**
     * getUserList/info
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/getUserListInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户信息列表", httpMethod = "POST")
    public List<User> getUserList(HttpServletRequest request,
                                  @RequestBody User user) {

        Boolean l = veritfCookies(request);
        if (l == true) {
            List<User> users = template.selectList("getUserListInfo", user);
            return users;
        } else {
            return null;
        }
    }

    /***
     *
     * update/deleteUser
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户信息", httpMethod = "POST")
    public int updateUser(HttpServletRequest request,
                          @RequestBody User user) {
        Boolean aBoolean = veritfCookies(request);
        int i = 0;
        if (aBoolean == true) {
            i = template.update("updateUser", user);
        }else {
            return 0;
        }
        log.info("更新条数为："+i);
        return i;
    }

    /**
     * veritfCookies
     *
     * @param request
     * @return
     */
    private Boolean veritfCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            log.info("COOKIES为空");
            return false;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                log.info("cookie验证通过");
                return true;
            }

        }
        return false;
    }


}
