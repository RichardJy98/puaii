package com.uto.puai.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tdoor
 */
@Component
public class Cap {

//    private static final String IP = "http://192.168.2.200:8181";
    private static final String IP = "http://218.2.130.250:8181";
//    private static final String REST = "forerweima";
//    private static final String REST_PASSWORD = "a69705cd-a34c-445b-80af-31478902d779";
    private static final String REST = "rest1";
    private static final String REST_PASSWORD = "a2d445c0-5994-4925-b6e8-246111f7a016";
    private static final String LOGIN_NAME = "test2";

    /**
     * 获取token数据 需OA中设置rest账户
     */
    public static String token() {
        return new JSONObject(HttpUtil.get(IP + "/seeyon/rest/token/" + REST + "/" + REST_PASSWORD)).getStr("id");
    }

    /**
     * 获取token(附件上传专用token)
     */
    private static String fileToken() {
        String url = IP + "/seeyon/rest/token";
        Map<String, Object> data = new HashMap<>(8);
        data.put("userName", REST);
        data.put("password", REST_PASSWORD);
        data.put("loginName", LOGIN_NAME);
        String tokenRes = HttpRequest.post(url).body(JSONUtil.toJsonStr(data)).execute().body();
        return new JSONObject(tokenRes).getStr("id");
    }

    /**
     * 获取制日期内无流程表的xml摸板 (模板编号,开始日期,结束日期)
     */
    public static String getWlcb(String tempLateCode, String beginDate, String endDate) {
        RestTemplate restTemplate = new RestTemplate();
        String url = IP + "/seeyon/rest/form/export/" + tempLateCode + "?beginDateTime=" + beginDate + " 00:00:00&endDateTime=" + endDate + " 00:00:00&token=" + token();
        System.err.println(url);
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 获取制日期内流程表的xml摸板 (模板编号,开始日期,结束日期)
     */

    public static String getLcb(String tempLateCode, String beginDate, String endDate) {
        RestTemplate restTemplate = new RestTemplate();
        String url = IP + "/seeyon/rest/flow/fromtemplatexml/" + tempLateCode + "?beginDateTime=" + beginDate + " 00:00:00&endDateTime=" + endDate + " 00:00:00&token=" + token();
        System.err.println(url);
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 发起流程表 (模板编号,json数据)
     */
    public static String postLcb(String tempLateCode, String jsonString) {
        String url = IP + "/seeyon/rest/flow/" + tempLateCode + "?token=" + token();
        System.err.println(url);
        Map<String, Object> data = new HashMap<>(8);
        data.put("templateCode", tempLateCode);
        data.put("senderLoginName", "seeyon1");
        data.put("subject", "员工信息登记");
        data.put("transfertype", "json");
        data.put("data", JSONUtil.parseObj(jsonString));
        return HttpRequest.post(url)
                .body(JSONUtil.toJsonStr(data)).execute().body();
    }

    /**
     * 发起流程表 (模板编号,数据,附件)
     */
    public static String initiationProcessChart(String tempLateCode, String jsonString,String token) {
        String url = IP + "/seeyon/rest/flow/" + tempLateCode + "?token=" + token;
        String uri = IP + "/seeyon/rest/attachment?applicationCategory=0&extensions=&firstSave=true&token=" + token;
        //System.err.println(url);
        Map<String, Object> data = new HashMap<>(8);
        data.put("templateCode", tempLateCode);
        data.put("senderLoginName", LOGIN_NAME);
        //data.put("subject", "员工信息登记");
        data.put("transfertype", "json");
        data.put("data", JSONUtil.parseObj(jsonString));
        //System.err.println(JSONUtil.toJsonStr(data));
        return HttpRequest.post(url).body(JSONUtil.toJsonStr(data)).execute().body();
    }

    /**
     * 附件上传
     * @return
     */
    public static JSONObject formFileUpload(String filePath) {
        String fileToken = fileToken();
        String url = IP + "/seeyon/rest/attachment?applicationCategory=0&extensions=&firstSave=true&token=" + fileToken;
        System.err.println("附件上传(POST)" + url);
        Map<String, Object> data = new HashMap<>(8);
        data.put("file", FileUtil.file(filePath));
        return new JSONObject(HttpUtil.post(url, data)).set("token",fileToken);
    }

    /**
     * 发起无流程表单
     */
    public static String initiateNoflowForm(String templateCode, String dataXml) {
        Map<String, String> paramMap = new HashMap<>(8);
        paramMap.put("loginName", LOGIN_NAME);
        paramMap.put("dataXml", dataXml);
        String url = IP + "/seeyon/rest/form/import/" + templateCode + "?token=" + token();
        return HttpRequest.post(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(paramMap)).timeout(10000).execute().body();
    }


    public static void main(String[] args) {
//        System.err.println(getLcb("formmain_0157","2020-08-16","2021-11-30"));
        System.err.println(fileToken());
//        System.err.println(HttpUtil.get(IP + "/seeyon/rest/flow/data/-9175891578564688965?token="+token()));
    }


}
