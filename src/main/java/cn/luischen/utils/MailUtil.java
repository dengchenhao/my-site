package cn.luischen.utils;


import cn.luischen.model.Mail;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import static cn.luischen.constant.WebConst.SYS_MAIL;

@Slf4j
public class MailUtil {
    private static final String pwd = "fgdfgdfgth";

    public static String userName = "ee";

    public static String projectName="“我的博客”";


    /**
     * 发送邮件
     *
     */
    public static void sendMail(Mail mail) {
        mail.setFrom(userName);
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "smtp");
        //服务器
        prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        //端口
        prop.setProperty("mail.smtp.port", "465");
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //企业邮箱必须要SSL
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //获取Session对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication pa = new PasswordAuthentication(mail.getFrom(), pwd);
                return pa;
            }
        });
        //设置session的调试模式，发布时取消
        session.setDebug(true);
        try {

            MimeMessage mimeMessage = createMimeMessage(session, mail.getFrom(), mail.getTo(), mail);
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            //
            //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
            //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
            //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            //           (1) 邮箱没有开启 SMTP 服务;
            //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
            //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
            //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            //
            //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
            transport.connect(mail.getFrom(), pwd);

            // 6.发送
            transport.send(mimeMessage, mimeMessage.getAllRecipients());

            // 7. 关闭连接
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMailAccount, String receiveMailAccount, Mail mail) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMailAccount, mail.getFrom(), "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, mail.getTo(), "UTF-8")); // 邮件的接收人
//        message.setRecipient(Message.RecipientType.CC, new InternetAddress(receiveMailAccount, mail.getTo(), "UTF-8")); // 邮件的抄送人
//        message.setRecipient(Message.RecipientType.BCC, new InternetAddress(userName,userName, "UTF-8")); // 邮件的密送人

        // 4. Subject: 邮件主题
        message.setSubject(mail.getSubject(), "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）;text换行需要<br>
        message.setContent(mail.getContent(), "text/html;charset=UTF-8");
//        message.setText(mail.getContent(), "UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    private static String getProjectInfo() {
        String ip = NetUtil.getIp();
        return ip;
    }

    public static void main(String args[]) {
        startMailNotice();
        System.out.println();
    }


    // 邮件提醒开机
    public static void startMailNotice() {
        String projectInfo = getProjectInfo();
        String subject = projectName+"提醒";
        subject = subject+"("+projectInfo+")";
        String content = projectName+"开机提醒"+"("+projectInfo+")"+"\n 访问地址已经变成["+NetUtil.getLocalIPv6Address()+"]:8089/admin";
        log.info(content);
        Mail mail = new Mail(null, SYS_MAIL, subject, content);
        MailUtil.sendMail(mail);
    }

    // 邮件提醒停机
    public static void stopMailNotice() {
        String projectInfo = getProjectInfo();
        String subject = projectName+"提醒";
        subject = subject+"("+projectInfo+")";
        String content = projectName+"停机提醒"+"("+projectInfo+")";
        Mail mail = new Mail(null, SYS_MAIL, subject, content);
        MailUtil.sendMail(mail);
    }

}
