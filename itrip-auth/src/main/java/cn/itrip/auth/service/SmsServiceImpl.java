package cn.itrip.auth.service;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SmsServiceImpl implements SmsService {

    public void send(String to, String templateId, String[] datas)throws Exception {
        HashMap<String, Object> result = null;
        //初始化SDK
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        //*初始化服务器地址和端口
        sdk.init("app.cloopen.com", "8883");
        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        sdk.setAccount("8aaf070867e885ce01682115ba3500e9", "83117248a26f4f8bae1d102683790f9d");
        //*初始化应用ID
        sdk.setAppId("8aaf070867e885ce01682115ba5600ea");
        //*调用发送模板短信的接口发送短信                                                                  *
        //*参数顺序说明：                                                                                  *
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        //*第三个参数是要替换的内容数组。
        result = sdk.sendTemplateSMS(to,templateId ,datas);
        if("000000".equals(result.get("statusCode"))){
            System.out.println("验证码发送成功");
        }else{
            //异常返回输出错误码和错误信息
            throw new Exception(result.get("statusCode").toString()+":"+result.get("statusMsg").toString());
        }
    }
}
