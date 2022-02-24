package com.uto.puai.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.uto.puai.config.Cap;
import com.uto.puai.service.Formmain0551Service;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author tdoor
 * 招聘信息入职流程
 */
@Slf4j
@RestController
public class RecruitmentProcessController {

    @Autowired
    private Formmain0551Service service;

    private static List<Map<String, Object>> filesList = new ArrayList<>(16);

    @PostMapping("/file/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String type = request.getHeaders("type").nextElement();
        Map<String, Object> data = new HashMap<>(8);
        String fileToken = fileToken();
        String url = "http://218.2.130.250:8181/seeyon/rest/attachment?applicationCategory=66&extensions=&firstSave=true&token=" + fileToken;
        data.put("file", MultipartFileToFile(file));
        JSONObject response = new JSONObject(HttpUtil.post(url, data)).set("token", fileToken);
        String id = ((JSONObject) response.getJSONArray("atts").get(0)).getStr("id");
        String fileUrl = ((JSONObject) response.getJSONArray("atts").get(0)).getStr("fileUrl");

        //将上传的文件进行
        data.clear();
        data.put("id", id);
        data.put("fileUrl", fileUrl);
        filesList.add(data);

        log.info("附件返回的信息：【 {} 】", response);
        log.info("文件类型：【 {} 】,文件名称：【 {} 】,文件上传OA返回的ID为: 【 {} 】,文件上传OA返回的URL为: 【 {} 】", type, file.getOriginalFilename(), id, fileUrl);
        JSONObject result = new JSONObject(16);
        result.set("ID", id);
        return result.toString();
    }

    /**
     * 获取token(附件上传专用token)
     */
    private static String fileToken() {
        String url = "http://218.2.130.250:8181" + "/seeyon/rest/token";
        Map<String, Object> data = new HashMap<>(8);
        data.put("userName", "rest1");
        data.put("password", "a2d445c0-5994-4925-b6e8-246111f7a016");
        data.put("loginName", "test2");
        String tokenRes = HttpRequest.post(url).body(JSONUtil.toJsonStr(data)).execute().body();
        return new JSONObject(tokenRes).getStr("id");
    }

    @PostMapping("/recruit")
    public JSONObject employeeInformationRegistration(@RequestBody JSONObject data) {
        log.info("传入数据:{}", data);
        /*List<User> userList = userMapper.selectAllUser();
        Map<String, Long> enumMap = new HashMap<>(1024);
        userList.forEach(u->enumMap.put(u.getShowValue(),u.getId()));
        Map<Long,String> enumPre = new HashMap<>(256);
        List<User> selectEnum = userMapper.selectEnum();
        selectEnum.forEach(e->enumPre.put(e.getId(),e.getShowValue()));*/
        JSONObject values = new JSONObject(data.getStr("values"));

        val xb = new HashMap<String, Object>();
        xb.put("男", "-7116525381120998669");
        xb.put("女", "-7149532827589589085");
//        xb.put("不详","7422359802267517634");
        values.set("性别", xb.get(values.getStr("性别")));

//        val zv = new HashMap<String,Object>();
//        zv.put("是","-8328782199741136113");
//        zv.put("否","-5963733294712140926");
//        values.set("子女", zv.get(values.getStr("子女")));

        val mz = new HashMap<String, Object>();
        mz.put("汉", "-4910774905138428943");
        mz.put("壮", "2396617525796162054");
        mz.put("满", "-4257069193204710701");
        mz.put("回", "5753182399075729455");
        mz.put("苗", "-8424565089213987891");
        mz.put("维吾尔", "-8234834469137463022");
        mz.put("彝", "-3342038484424307009");
        mz.put("土家", "6389481182514072645");
        mz.put("蒙古", "-3431449679284581634");
        mz.put("藏", "-3665114805508617798");
        mz.put("布依", "-7971919020996345236");
        mz.put("侗", "-3223550064391991938");
        mz.put("瑶", "1943148164066528331");
        mz.put("朝鲜", "-62754664009348116");
        mz.put("白", "4149828586674605433");
        mz.put("哈尼", "6739385372899558360");
        mz.put("哈萨克", "6891046083825189930");
        mz.put("黎", "1120629710244236463");
        mz.put("傣", "4711363625278193934");
        mz.put("畲", "-1114101199500524571");
        mz.put("僳僳", "-4143020792815792447");
        mz.put("仡佬", "-6266699691691587067");
        mz.put("东乡", "2637417123249734386");
        mz.put("拉祜", "2050242696982777020");
        mz.put("水", "3663567564214323611");
        mz.put("佤", "-7123072319237439463");
        mz.put("纳西", "4357390395981644207");
        mz.put("羌", "-5671905961239429065");
        mz.put("土", "-2659273119395197474");
        mz.put("仫佬", "2330020217572969476");
        mz.put("锡伯", "3164631186887230150");
        mz.put("柯尔柯孜", "3574139886475756348");
        mz.put("达斡尔", "-7191952422352236858");
        mz.put("景颇", "463356959627888179");
        mz.put("毛南", "7209458303957028758");
        mz.put("撒拉", "8480141294794000635");
        mz.put("布朗", "1287766802462228794");
        mz.put("塔吉克", "-5893015282635419941");
        mz.put("阿昌", "-477415583518406936");
        mz.put("普米", "-5955205728022542633");
        mz.put("鄂温克", "-9149217030611648290");
        mz.put("怒", "-3125898793577721950");
        mz.put("京", "8766320648312998660");
        mz.put("基诺", "-2763266027819286234");
        mz.put("德昂", "75259275875402487");
        mz.put("保安", "9140697556920781050");
        mz.put("俄罗斯", "-6993965158715603506");
        mz.put("裕固", "-1824407515429450643");
        mz.put("乌孜别克", "-989346014189829625");
        mz.put("门巴", "-1371314993271389722");
        mz.put("鄂伦春", "7462213711207285614");
        mz.put("独龙", "801384051945259825");
        mz.put("塔塔尔", "-3516180680026272612");
        mz.put("赫哲", "-549964258761750201");
        mz.put("高山", "-5063108735041254955");
        mz.put("珞巴", "2294578694607095295");
        mz.put("其他", "-523046868210398813");
//        mz.put("汉","汉");
//        mz.put("壮","壮");
//        mz.put("满","满");
//        mz.put("回","回");
//        mz.put("苗","苗");
//        mz.put("维吾尔","维吾尔");
//        mz.put("彝","彝");
//        mz.put("土家","土家");
//        mz.put("蒙古","蒙古");
//        mz.put("藏","藏");
//        mz.put("布依","布依");
//        mz.put("侗","侗");
//        mz.put("瑶","瑶");
//        mz.put("朝鲜","朝鲜");
//        mz.put("白","白");
//        mz.put("哈尼","哈尼");
//        mz.put("哈萨克","哈萨");
//        mz.put("黎","黎");
//        mz.put("傣","傣");
//        mz.put("畲","畲");
//        mz.put("僳僳","僳僳");
//        mz.put("仡佬","仡佬");
//        mz.put("东乡","东乡");
//        mz.put("拉祜","拉祜");
//        mz.put("水","水");
//        mz.put("佤","佤");
//        mz.put("纳西","纳西");
//        mz.put("羌","羌");
//        mz.put("土","土");
//        mz.put("仫佬","仫佬");
//        mz.put("锡伯","锡伯");
//        mz.put("柯尔克孜","柯尔克孜");
//        mz.put("达斡尔","达斡");
//        mz.put("景颇","景颇");
//        mz.put("毛南","毛南");
//        mz.put("撒拉","撒拉");
//        mz.put("布朗","布朗");
//        mz.put("塔吉克","塔吉");
//        mz.put("阿昌","阿昌");
//        mz.put("普米","普米");
//        mz.put("鄂温克","鄂温克");
//        mz.put("怒","怒");
//        mz.put("京","京");
//        mz.put("基诺","基诺");
//        mz.put("德昂","德昂");
//        mz.put("保安","保安");
//        mz.put("俄罗斯","俄罗斯");
//        mz.put("裕固","裕固");
//        mz.put("乌孜别克","乌孜别克");
//        mz.put("门巴","门巴");
//        mz.put("鄂伦春","鄂伦春");
//        mz.put("独龙","独龙");
//        mz.put("塔塔尔","塔塔尔");
//        mz.put("赫哲","赫哲");
//        mz.put("高山","高山");
//        mz.put("珞巴","珞巴");
//        mz.put("其他","其他");
        values.set("民族", mz.get(values.getStr("民族")));


//        val whcd = new HashMap<String,Object>();
//        whcd.put("博士","6387266374992481798");
//        whcd.put("硕士","-5611294050280586492");
//        whcd.put("本科","-1963166336527217249");
//        whcd.put("本科在读","8339964750439927270");
//        whcd.put("大专","8721436896032081488");
//        whcd.put("高中","-3331200158009506180");
//        whcd.put("其他","5686185036222777030");
//        values.set("文化程度", whcd.get(values.getStr("文化程度")));

//        val hyzk = new HashMap<String,Object>();
//        hyzk.put("未婚","-9153439982745927305");
//        hyzk.put("已婚","3266226359105832939");
//        hyzk.put("离异","-3930304165424708358");
//        hyzk.put("丧偶","-4957044510105582833");
//        values.set("婚姻状况", hyzk.get(values.getStr("婚姻状况")));


//        val ssf = new HashMap<String,Object>();
//       ssf.put("河北","-6701732066550462322");
//       ssf.put("山西","134254778534677901");
//       ssf.put("山东","-5603920238486208454");
//       ssf.put("辽宁","8869662668131290142");
//       ssf.put("吉林","-8613003760774395898");
//       ssf.put("江苏","-6080225942425418251");
//       ssf.put("浙江","-1490438410657510907");
//       ssf.put("安徽","3691680382349034677");
//       ssf.put("福建","2164764877687832614");
//       ssf.put("江西","739919921266772050");
//       ssf.put("河南","4931378962012523708");
//       ssf.put("湖北","7662790165881111269");
//       ssf.put("湖南","-1613490695202257631");
//       ssf.put("广东","-4386701861438077710");
//       ssf.put("广西","-3907054981439272490");
//       ssf.put("海南","-4547768690051235444");
//       ssf.put("四川","8923565773713983803");
//       ssf.put("贵州","9087198210984594550");
//       ssf.put("云南","-7113397851702277696");
//       ssf.put("陕西","-7763264926789908635");
//       ssf.put("甘肃","-51219770700904373");
//       ssf.put("青海","4997312904783088103");
//       ssf.put("台湾","3859470649756696720");
//       ssf.put("黑龙江","3414411550429563295");
//       ssf.put("内蒙古","-6911748860270525618");
//       ssf.put("广西壮族自治区","9040175253899986540");
//       ssf.put("西藏","797002520818960912");
//       ssf.put("宁夏","-5548397004517782853");
//       ssf.put("新疆","1984262302027366470");
//       ssf.put("北京","55589022669619498");
//       ssf.put("天津","-3706049756513876934");
//       ssf.put("上海","-3250067866414923041");
//       ssf.put("重庆","-8100823944874210464");
//       ssf.put("香港","-3854619467505056681");
//       ssf.put("澳门","4383646973091332800");
//        values.set("籍贯", ssf.get(values.getStr("籍贯")));
        val zzmm = new HashMap<String, Object>();
//        zzmm.put("党员","3414411550429563295");
//        zzmm.put("团员","-8041846168027575351");
//        zzmm.put("群众","-7253559885445905812");
        zzmm.put("党员", "党员");
        zzmm.put("团员", "团员");
        zzmm.put("群众", "群众");
        values.set("政治面貌", zzmm.get(values.getStr("政治面貌")));

        val hjxz = new HashMap<String, Object>();
        hjxz.put("城镇", "-8687024474381005094");
        hjxz.put("农村", "9204046285589922756");
//        hjxz.put("城镇","城镇");
//        hjxz.put("农村","农村");
        values.set("户口性质", hjxz.get(values.getStr("户籍性质")));

        val sf = new HashMap<String, Object>();
//        sf.put("是","-8328782199741136113");
//        sf.put("否","-5963733294712140926");
        sf.put("是", "-5749489616366121330");
        sf.put("否", "-3308115516138632770");
        values.set("是否已婚", sf.get(values.getStr("是否已婚")));
        values.set("是否本地首次缴纳公积金", sf.get(values.getStr("是否本地首次缴纳公积金")));
        values.set("是否本地首次缴纳社保", sf.get(values.getStr("是否本地首次缴纳社保")));
        values.set("是否有病史", sf.get(values.getStr("是否有病史")));
        values.set("是否农历生日", sf.get(values.getStr("是否农历生日")));
        values.set("是否已育", sf.get(values.getStr("是否已育")));

//        values.set("是否存在禁止协议",sf.get(values.getStr("是否存在禁止协议")));
//        values.set("是否曾在复星体系企业工作过",sf.get(values.getStr("是否曾在复星体系企业工作过")));
//        values.set("有无亲属关系",sf.get(values.getStr("有无亲属在复星工作过")));

//        if(values.get("曾在旭川前")!=null&&!"".equals(values.get("曾在旭川前")))
//            values.set("曾在旭川前",values.get("曾在旭川前").toString()+" 00:00");
//        if(values.get("曾在旭川后")!=null&&!"".equals(values.get("曾在旭川后")))
//            values.set("曾在旭川后",values.get("曾在旭川后").toString()+" 00:00");
        JSONArray newSub = new JSONArray();
        val bz = new HashMap<String, Object>();
//        bz.put("肄业","4604531250818403116");
        bz.put("毕业", "1546432875366860265");
        bz.put("在读", "4166947708190475648");

        val xl = new HashMap<String, Object>();
        xl.put("小学", "-3225293628883384937");
        xl.put("中学", "-7447215571168245062");
        xl.put("高中", "-5053815745291728749");
        xl.put("中专", "8807918664862221893");
        xl.put("大专", "1213620974843742112");
        xl.put("大学", "236898857115272301");
        xl.put("研究生硕士", "7700258409404256079");
        xl.put("研究生博士", "3502067555955854080");
//        xl.put("研究生博士后","7163458930986930172");


//        val wlqd = new HashMap<String,Object>();
//        wlqd.put("chinahr.com","3757695312546910015");
//        wlqd.put("51job.com","-3421526936923388039");
//        wlqd.put("zhaopin.com","-5271050438574256562");
//        wlqd.put("jobcn.com","4095057720747575017");
//        wlqd.put("其他","-5137160727942182247");
//        values.set("网络渠道",wlqd.get(values.get("网络渠道")));
//
//        val pmmtqd = new HashMap<String,Object>();
//        pmmtqd.put("其他","-5433497165591786575");
//        pmmtqd.put("《人才信息报》","-9112027675755917417");
//        pmmtqd.put("《人才周刊》","6206147737706578660");
//        values.set("平面媒体渠道",pmmtqd.get(values.get("平面媒体渠道")));
//
//        val zphqd = new HashMap<String,Object>();
//        zphqd.put("企业专场招聘会","7720638710840903747");
//        zphqd.put("社会招聘会","-4046909937190352788");
//        values.set("招聘会渠道",zphqd.get(values.get("招聘会渠道")));
//
//        val js = new HashMap<String,Object>();
//        js.put("不能接受","7462947320068129761");
//        js.put("接受","-6917491448170656797");
//        values.set("接受不接受",js.get(values.get("接受不接受")));
//
//        val zwcd = new HashMap<String,Object>();
//        zwcd.put("一般","4615124093547005448");
//        zwcd.put("精通","-8845903271906750550");
//        zwcd.put("熟练","-1912311485184457456");
//        zwcd.put("不会","3378620968342209790");
//        values.set("掌握程度",zwcd.get(values.get("掌握程度")));

        /*String qudao = values.getStr("获取招聘信息渠道").replace("[","").replace("]","");
        String[] arr = qudao.split(",");
        for(int i=0;i<arr.length;i++){
            values.set(arr[i].replace("\"",""),1);
        }*/

        /*String jsj = values.getStr("计算机水平").replace("[","").replace("]","");
        String[] arrjsj = jsj.split(",");
        for(int i=0;i<arrjsj.length;i++){
            values.set(arrjsj[i].replace("\"",""),1);
        }*/

        /*String yy = values.getStr("语言种类").replace("[","").replace("]","");
        String[] arryy = yy.split(",");
        for(int i=0;i<arryy.length;i++){
            values.set(arryy[i].replace("\"",""),1);
        }*/
//
//        values.set("有无慢性病", zv.get(values.getStr("有无慢性病")));
//        values.set("有无重大特殊病", zv.get(values.getStr("有无重大特殊病")));
//        values.set("有无犯罪记录", zv.get(values.getStr("有无犯罪记录")));


        for (Object o : new JSONArray(values.getStr("sub"))) {
            JSONObject oneSub = new JSONObject(o);
            for (String s : oneSub.keySet()) {
                if (s.contains("备注")) {
                    oneSub.set(s, bz.get(oneSub.getStr(s)));
                }
                if (s.contains("学历")) {
                    oneSub.set(s, xl.get(oneSub.getStr(s)));
                }

            }
            newSub.add(oneSub);
        }

        //自动带入合同信息并自动带初始值
//        JSONObject contract = new JSONObject();
//        contract.set("合同开始时间", LocalDate.now().toString());
//        contract.set("转正时间",LocalDate.now().plusMonths(6).toString());
//        contract.set("合同结束时间",LocalDate.now().plusYears(3).toString());
//        contract.set("合同期限","3年");
//        contract.set("合同状态","履行中");
//        newSub.add(contract);

        values.set("sub", newSub);
        log.info("本次提交的员工登记表单数据为: 【 {} 】", values);
        String result = Cap.initiationProcessChart(values.getStr("templetcode"), values.toString(), Cap.token());

        if (result.startsWith("{")) {
            log.info("登记失败,返回信息为: 【 {} 】", result);
            return new JSONObject("{\"message\": \"员工信息登记失败\"}");
        } else {
            //将每个上传的文件进行修改
            service.updateField(result, filesList);

            log.info("登记成功,员工信息登记协同主表ID为: 【 {} 】", result);
            return new JSONObject("{\"message\": \"员工信息登记成功\"}");
        }
    }

    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
