package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue", description = "添加用户接口")
    public void addUserCase() throws IOException, InterruptedException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        AddUserCase addUserCase = sqlSession.selectOne("addUserCase", 1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);
        //发送请求，获取结果
        String result = getResult(addUserCase);
        //验证返回结果
        Thread.sleep(2000);
        User user = sqlSession.selectOne("addUser", addUserCase);
        System.out.println("查询数据库结果为：" + user.toString());
        Assert.assertEquals(addUserCase.getExpected(), result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName", addUserCase.getUserName());
        param.put("password", addUserCase.getPassword());
        param.put("sex", addUserCase.getSex());
        param.put("age", addUserCase.getAge());
        param.put("permission", addUserCase.getPermission());
        param.put("isDelete", addUserCase.getIsDelete());
        post.setHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(stringEntity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        String result;
        HttpResponse httpResponse = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(result);
        return result;
    }


}


