/**
 * Copyright (c) 2017, HSBC. All rights reserved.
 */


package test;

import com.example.test.Application;
import com.example.test.dao.RoleRepository;
import com.example.test.dao.UserRepository;
import com.example.test.dao.UserRoleRepository;
import com.example.test.entity.security.Role;
import com.example.test.entity.security.User;
import com.example.test.entity.security.UserRole;
import com.example.test.model.TestResponse;
import com.example.test.service.TestService;
import com.sun.javafx.tools.ant.DeployFXTask;
import org.apache.commons.collections.map.HashedMap;
import org.hamcrest.Matchers;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import static org.junit.Assert.assertThat;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UnitTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	MockMvc mvc;
	private RequestBuilder request;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	private TestService testService;

	@Autowired
	private StringEncryptor stringEncryptor;

	@Autowired
	private UserRoleRepository userRoleDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private RoleRepository roleDao;


	@Autowired
	private JavaMailSender mailSender;
	//发送邮件的模板引擎
	@Autowired
	private FreeMarkerConfigurer configurer;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Ignore
	public void getTestMessageByService() throws Exception {

		System.out.println("----1"+stringEncryptor.encrypt("123456"));
		System.out.println("----2"+stringEncryptor.encrypt("qweasd"));

		TestResponse response=testService.testCall("wei");
		Assert.assertNotNull(response);
		assertThat(response.getMessage(), Matchers.containsString("success"));
		assertThat(response.getRespCode(),Matchers.containsString("200"));

	}

	@Test
	//@Ignore
	public void test() throws Exception {



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

	}

}
