package com.course.httpclient.cookies;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MycCookiesForPost {
    private String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void getCookiesTest() throws IOException {
        String result;
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url + uri;
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(result);
        store = client.getCookieStore();
        List<Cookie> cookiesList = store.getCookies();
        for (Cookie cookie : cookiesList) {
            String key = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name : " + key + "    cookie value : "
                    + value);
        }
    }

    @Test(dependsOnMethods = {"getCookiesTest"})
    public void testPostMethod() throws IOException {
        String uri = bundle.getString("test.post.with.cookies");
        String testUrl = this.url + uri;
        String result;
        //????????????client?????????????????????????????????
        DefaultHttpClient client = new DefaultHttpClient();
        //????????????post??????
        HttpPost post = new HttpPost(testUrl);
        //????????????
        JSONObject param = new JSONObject();
        param.put("name", "huhansan");
        param.put("age", "18");
        //?????????????????????
        post.setHeader("content-type", "application/json");
        //?????????????????????????????????
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        client.setCookieStore(store);
        HttpResponse response = client.execute(post);
        result = EntityUtils.toString(response.getEntity());
        System.out.println(result);
        JSONObject jsonObject = new JSONObject(result);
        String success = (String)jsonObject.get("huhansan");
        Assert.assertEquals(success,"success");
        String status = (String)jsonObject.get("status");
        Assert.assertEquals(status,"1");
    }
}
