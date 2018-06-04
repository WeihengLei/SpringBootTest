/**
 * Copyright (c) 2017, HSBC. All rights reserved.
 */


package test;

import com.example.test.Application;
import com.example.test.dao.RoleRepository;
import com.example.test.dao.UserRepository;
import com.example.test.dao.UserRoleRepository;
import com.example.test.entity.Role;
import com.example.test.entity.User;
import com.example.test.entity.UserRole;
import com.example.test.model.TestResponse;
import com.example.test.service.TestService;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

		String uuid = UUID.randomUUID().toString();

		User admin = new User();
		User jack = new User();

		admin.setUsername("admin_" + uuid);
		admin.setPassword("admin");

		jack.setUsername("jack_" + uuid);
		jack.setPassword("123456");

		userDao.save(admin);
		userDao.save(jack);

		Role adminRole = new Role();
		Role userRole = new Role();

		adminRole.setRole("ROLE_ADMIN");
		userRole.setRole("ROLE_USER");

		roleDao.save(adminRole);
		roleDao.save(userRole);

		UserRole userRoleAdminRecord1 = new UserRole();
		userRoleAdminRecord1.setUserId(admin.getId());
		userRoleAdminRecord1.setRoleId(adminRole.getId());
		userRoleDao.save(userRoleAdminRecord1);

		UserRole userRoleAdminRecord2 = new UserRole();
		userRoleAdminRecord2.setUserId(admin.getId());
		userRoleAdminRecord2.setRoleId(userRole.getId());
		userRoleDao.save(userRoleAdminRecord2);

		UserRole userRoleJackRecord = new UserRole();
		userRoleJackRecord.setUserId(jack.getId());
		userRoleJackRecord.setRoleId(userRole.getId());
		userRoleDao.save(userRoleJackRecord);


	}

}
