package com.axiong8080.pdksport.controller;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.axiong8080.pdksport.vo.RefushStepVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: hahaController
 * @Description:
 * @Author lianyixiong
 * @Date 2021/9/14
 * @Version 1.0
 */
@Api(tags = "wx_xcx_user_info服务")
@RestController
@RequestMapping("/wxsports")
public class WXSportController {

    private static final Logger logger = LoggerFactory.getLogger(WXSportController.class);

    @ApiOperation(value = "刷步调用--2022/12/28废弃")
    @PostMapping("/refushStepOver")
    public String refushStepOver(@RequestBody RefushStepVO vo) {
        String url = "https://4og.cn/xm/app/ajax.php?c=add&_t=" + Math.random();
        Map<String, Object> param = new HashMap<>();
        param.put("Content-Type", "application/x-www-form-urlencoded");
        param.put("Cookie", "PHPSESSID=f4hnmm7mn4dpa19gvfkotpnk3b");
        param.put("usr", vo.getUsr());
        param.put("psw", vo.getPsw());
        param.put("bs", vo.getBs());
        String result = cn.hutool.http.HttpUtil.post(url, param);
        logger.info(UnicodeUtil.toString(result));
        return result;
    }


    @ApiOperation(value = "刷步调用")
    @RequestMapping(value = "/refushStep", method = {RequestMethod.GET, RequestMethod.POST})
    public String refushStep(@RequestBody(required = false) RefushStepVO vo,
                             @RequestParam(required = false) String usr,
                             @RequestParam(required = false) String psw,
                             @RequestParam(required = false) String bs) throws IOException {
        Map<String,Object> respon = new HashMap<>();
        if (StrUtil.isEmpty(usr) && StrUtil.isEmpty(vo.getUsr())){
            respon.put("result",412);
            respon.put("message","账号不为空");
            return JSONUtil.toJsonStr(JSONUtil.parseObj(respon));
        }

        if (StrUtil.isEmpty(psw) && StrUtil.isEmpty(vo.getPsw())){
            respon.put("result",413);
            respon.put("message","密码不为空");
            return JSONUtil.toJsonStr(JSONUtil.parseObj(respon));
        }

        if (StrUtil.isEmpty(bs) && StrUtil.isEmpty(vo.getBs())){
            respon.put("result",414);
            respon.put("message","目标不为空");
            return JSONUtil.toJsonStr(JSONUtil.parseObj(respon));
        }
        if (vo == null){
            vo = new RefushStepVO();
        }
        if (StrUtil.isNotEmpty(usr)){
            vo.setUsr(usr);
        }
        if (StrUtil.isNotEmpty(psw)){
            vo.setPsw(psw);
        }
        if (StrUtil.isNotEmpty(bs)){
            vo.setBs(bs);
        }
        String result = getString2(vo);
        logger.info(UnicodeUtil.toString(result));
        respon.put("result",JSONUtil.parseObj(result).getInt("code"));
        respon.put("message",JSONUtil.parseObj(result).getStr("msg"));
        return JSONUtil.toJsonStr(JSONUtil.parseObj(respon));
    }
    private String getString2(RefushStepVO vo) throws IOException {
        String url = "https://apis.jxcxin.cn/api/mi";
        Map<String,Object> req = new HashMap<>();
        req.put("user",vo.getUsr());
        req.put("password",vo.getPsw());
        req.put("step",vo.getBs());
        String uri = HttpUtil.urlWithForm(url,req, Charset.defaultCharset(),false);
        String result = OkHttpClientPost(uri);
        return result;
    }


    @Deprecated
    private String getString(RefushStepVO vo) throws IOException {
        String url;
        String email_url = "https://api.kit9.cn/api/xiaomi_sports/api_email_fixed.php";
        String mobile_url = "https://api.kit9.cn/api/xiaomi_sports/api.php";
        Map<String, Object> param = new HashMap<>();
        if (vo.getUsr().contains("@")){
            //email格式
            param.put("email", vo.getUsr());
            param.put("password", vo.getPsw());
            param.put("step", vo.getBs());
            url = email_url;
        }else{
            //手机号格式
            param.put("mobile", vo.getUsr());
            param.put("password", vo.getPsw());
            param.put("step", vo.getBs());
            url = mobile_url;
        }
        String uri = HttpUtil.urlWithForm(url,param, Charset.defaultCharset(),false);
        String result = OkHttpClientPost(uri);
        return result;
    }

    private String OkHttpClientPost(String url) throws IOException {
        System.out.println(url);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
