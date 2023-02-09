package cn.luischen.aspect;


import cn.luischen.model.Mail;
import cn.luischen.utils.MailUtil;
import org.springframework.stereotype.Component;

import static cn.luischen.constant.WebConst.SYS_MAIL;

/**
 * 请求的日志处理
 * Created by winterchen on 2018/4/28.
 */
@Component
public class StartComponent {

    public void ipMail(){
        String subject = "“我的博客”提醒";
        String content = "我的博客开机提醒"+"("+subject+")";
        Mail mail = new Mail(null, SYS_MAIL, subject, content);
        MailUtil.sendMail(mail);
    }

}
