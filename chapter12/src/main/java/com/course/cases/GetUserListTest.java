package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
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
import java.util.List;

public class GetUserListTest {
    @Test(dependsOnGroups = "loginTrue", description = "获取性别为男的用户")
    public void getUserListCase() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase", "man");
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        //发送请求，获取结果
        JSONArray arrayResult = getJsonResult(getUserListCase);
        List<User> userList = sqlSession.selectList(getUserListCase.getExpected(), getUserListCase);
        for (User u :
                userList) {
            System.out.println("list获取的user:" + u.toString());
        }
        JSONArray userListJson = new JSONArray(userList);
        Assert.assertEquals(userListJson.length(), arrayResult.length());
        for (int i = 0; i < arrayResult.length(); i++) {
            JSONObject expect = (JSONObject) arrayResult.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(), actual.toString());
        }

    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName", getUserListCase.getUserName());
        param.put("age", getUserListCase.getAge());
        param.put("sex", getUserListCase.getSex());
        post.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        String result;
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONArray jsonArray = new JSONArray(result);
        System.out.println("list集合为: "+ result);
        return jsonArray;

    }

}
