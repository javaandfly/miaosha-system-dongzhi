package com.dong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient{
    /** 发送邮件的核心组件 Spring容器管理 */
    @Autowired
    private JavaMailSender mailSender;

    /** 发件人:properties文件中已经配置，可直接使用 */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件的方法，外部调用
     *
     * @param to 发送目的地
     * @param subject 发送主题
     * @param context 发送内容
     */
    public void sendMail(String to, String subject, String context) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // 支持html文本
            helper.setText(context, true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
