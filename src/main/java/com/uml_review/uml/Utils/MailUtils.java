package com.uml_review.uml.Utils;

import com.sun.mail.util.MailSSLSocketFactory;
import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.logging.Logger;

@Component
public class MailUtils   {
        @Autowired
        private JavaMailSender javaMailSender;
        /**
         * 配置文件中我的qq邮箱
         */
        @Value("${spring.mail.from}")
        private String from;

        /**
         * 发送HTML邮件
         * @param to 收件者
         * @param subject 邮件主题
         * @param content 文本内容
         */
        public void sendHtmlMail(String to,String subject,String content) {


            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = null;

            try {
                helper = new MimeMessageHelper(message, true);
                helper.setFrom(from);
                helper.setSubject(subject);
                helper.setTo(to);
                helper.setText(content, true);
                helper.setCc(from);
                //抄送给自己 放屏蔽
//                message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse("alohaw@163.com"));
                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }


        }

}
