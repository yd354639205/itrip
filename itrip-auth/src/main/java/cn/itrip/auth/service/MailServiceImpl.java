package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.RedisAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    @Resource
    private RedisAPI redisAPI;

    //发件人地址
    public static String senderAddress = "m17680444945@163.com";
    //发件人账户名
    public static String senderAccount = "17680444945";
    //发件人账户密码
    public static String senderPassword = "yd200002";

    public void sendAccountActivateEmail(String code, String activationCode) {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.163.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        try {
            //设置调试信息在控制台打印出来
            session.setDebug(true);
            //3、创建邮件的实例对象
            Message msg = getMimeMessage(session,code,activationCode);
            //4、根据session对象获取邮件传输对象Transport
            Transport transport = session.getTransport();
            //设置发件人的账户名和密码
            transport.connect(senderAccount, senderPassword);
            //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(msg,msg.getAllRecipients());

            //如果只想发送给指定的人，可以如下写法
            //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});

            //5、关闭邮件连接
            transport.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     */
    public MimeMessage getMimeMessage(Session session,String code, String activationCode) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        msg.setSubject("这是一封爱旅行激活账号的邮件");
        msg.setSentDate(new Date());            //setFrom 表示用哪个邮箱发送邮件
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * * RecipientType.TO||BCC||CC
         * *     TO表示主要接收人
         * *     BCC表示秘密抄送人
         * *     CC表示抄送人
         * * InternetAddress  接收者的邮箱地址
         * */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(code));
            /*message.setContent("<a target='_BLANK' href='"+GenerateLinkUtils.generateActivateLink(user)+"'>
            "+user.getUsername()+"先生/女士您好，请点击此链接激活账号"+GenerateLinkUtils.generateActivateLink(user)
            			+"</a>","text/html;charset=utf-8");*/
        msg.setContent("激活码:"+activationCode, "text/html;charset=utf-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        //结束
        return msg;
    }

}
