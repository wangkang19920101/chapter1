package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import com.mongodb.util.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {
    @Test(dependsOnGroups="loginTrue",description = "获取userId为1的用户信息")
    public void getUserInfoCase() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase", 1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);
        JSONArray resultjson = getResultJson(getUserInfoCase);
        System.out.println(resultjson);
        User user = sqlSession.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);
        System.out.println("查数据库的数据为：" + user.toString());
        List userlist = new ArrayList();
        userlist.add(user);
        JSONArray jsonArray = new JSONArray(userlist);
        JSONArray jsonArray1 = new JSONArray(resultjson.getString(0));
        System.out.println("数据库查询的数据：" + jsonArray.toString());
        System.out.println("调用接口返回值：" + resultjson.toString());
        Assert.assertEquals(jsonArray.toString(), jsonArray1.toString());
    }

    private JSONArray getResultJson(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id", getUserInfoCase.getUserId());
        post.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);

        return array;
    }
}
