package com.example.test.util;


import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Map;

public class SendMailUtil {

    @Autowired
    private JavaMailSender mailSender;
    //发送邮件的模板引擎
    @Autowired
    private FreeMarkerConfigurer configurer;

    /*public void test() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("1121008660@qq.com");
        helper.setTo("1121008660@qq.com");

        helper.setSubject("主题：祝福");

        Map<String, Object> model = new HashedMap();
        model.put("username1", "weiheng1");
        model.put("username2", "weiheng2");
        model.put("username3", "weiheng3");

        try {
            Template template = configurer.getConfiguration().getTemplate("message.ftl");
            try {
                String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

                helper.setText(text, true);
                mailSender.send(mimeMessage);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}