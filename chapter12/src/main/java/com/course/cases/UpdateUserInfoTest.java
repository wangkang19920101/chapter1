package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;

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

public class UpdateUserInfoTest {
//    @Test(dependsOnGroups = "loginTrue", description = "更新用户")
//    public void updateUserInfoCase() throws IOException, InterruptedException {
//        SqlSession sqlSession = DataBaseUtil.getSqlSession();
//        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 1);
//        System.out.println(updateUserInfoCase.toString());
//        System.out.println(TestConfig.updateUserInfoUrl);
//        int result = getResult(updateUserInfoCase);
//        Thread.sleep(2000);
//        User user = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
//        System.out.println("数据库数据为："+user.toString());
//        Assert.assertNotNull(user);
//        Assert.assertNotNull(result);
//
//
//    }
//
//    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
//        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
//        JSONObject param = new JSONObject();
//        param.put("id", updateUserInfoCase.getUserId());
//        param.put("userName", updateUserInfoCase.getUserName());
//        param.put("sex", updateUserInfoCase.getSex());
//        param.put("age", updateUserInfoCase.getAge());
//        param.put("permission", updateUserInfoCase.getPermission());
//        param.put("isDelete", updateUserInfoCase.getIsDelete());
//        post.setHeader("content-type", "application/json");
//        StringEntity entity = new StringEntity(param.toString(), "utf-8");
//        post.setEntity(entity);
//        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
//        String result;
//        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
//        result = EntityUtils.toString(response.getEntity());
//        System.out.println(result);
//        return Integer.parseInt(result);
//    }
//
//    @Test(dependsOnGroups = "loginFalse", description = "删除用户")
//    public void deleteUserInfoTest() throws IOException, InterruptedException {
//        SqlSession sqlSession = DataBaseUtil.getSqlSession();
//        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 2);
//        System.out.println(updateUserInfoCase.toString());
//        System.out.println(TestConfig.updateUserInfoUrl);
//        int result = getResult(updateUserInfoCase);
//        Thread.sleep(2000);
//        User user = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
//        System.out.println("数据库数据为：" + user.toString());
//        Assert.assertNotNull(user);
//        Assert.assertNotNull(result);
//
//    }

    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession session = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);


        //下边为写完接口的代码
        int result = getResult(updateUserInfoCase);
        /**
         * 下边这两行跟着测试的课讲
         */
        //获取更新后的结果
        Thread.sleep(2000);
        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        System.out.println(user.toString());



        Assert.assertNotNull(user);
        Assert.assertNotNull(result);

    }

    @Test(dependsOnGroups = "loginTrue",description = "删除用户")
    public void deleteUser() throws IOException, InterruptedException {
        SqlSession session = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);


        //下边为写完接口的代码
        int result = getResult(updateUserInfoCase);

        /**
         * 下边这两行跟着测试的课讲
         */
        Thread.sleep(2000);
        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        System.out.println(user.toString());


        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }


    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id",updateUserInfoCase.getUserId());
        param.put("userName",updateUserInfoCase.getUserName());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("age",updateUserInfoCase.getAge());
        param.put("permission",updateUserInfoCase.getPermission());
        param.put("isDelete",updateUserInfoCase.getIsDelete());
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        return Integer.parseInt(result);

    }





}
