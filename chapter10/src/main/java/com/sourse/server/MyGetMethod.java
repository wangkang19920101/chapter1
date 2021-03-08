package com.sourse.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/", description = "这是所有的GET方法")
public class MyGetMethod {
    @RequestMapping(value = "/get/cookies", method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取cookies", httpMethod = "GET")
    public String getCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        return "恭喜你获得cookies成功!";
    }

    @RequestMapping(value = "/get/with/cookies", method = RequestMethod.GET)
    @ApiOperation(value = "这是一个需要携带cookies信息才能访问的get请求!!!", httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return "看看有没有传入cookie";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                return "yes";
            }
        }
        return "这是一个需要携带cookies信息才能访问的get请求!!!";
    }

    /**
     * 需要携带参数访问方法第一种
     *
     * @param Star
     * @param end
     * @return
     */
    @RequestMapping(value = "/get/with/param", method = RequestMethod.GET)
    @ApiOperation(value = "返回一个表单", httpMethod = "GET")
    public Map<String, Integer> getGoodsList(@RequestParam Integer Star,
                                             @RequestParam Integer end) {
        Map<String, Integer> goodList = new HashMap<>();
        goodList.put("苹果", 10);
        goodList.put("衬衫", 100);
        goodList.put("手机", 1000);
        return goodList;
    }

    /**
     * 需要携带参数访问的第二种方法
     */
    @RequestMapping(value = "/get/with/param/{star}/{end}")
    @ApiOperation(value = "返回一个表单2", httpMethod = "GET")
    public Map goodsListGet(@PathVariable Integer star,
                            @PathVariable Integer end) {
        Map<String, Integer> goodList = new HashMap<>();
        goodList.put("苹果", 20);
        goodList.put("衬衫", 200);
        goodList.put("手机", 2000);

        return goodList;
    }


}
