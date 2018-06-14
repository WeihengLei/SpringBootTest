
package test;

import static org.junit.Assert.assertThat;

import java.util.Locale;

import com.example.test.Application;
import com.example.test.service.MessageResourceService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;


/**
 * 
 *
 * @author A-team WeiHeng
 * @date 2017-09-29
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
//@WebAppConfiguration
public class MessageResourceTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageResourceService messageResourceService;
	
	//service test
	@Test
	public void reload()  {
		messageResourceService.reload();
		logger.info("success");
	}
	
	@Test
	public void getTextByCodeNLocale()  {
		/**
		 * collection：messageSesource
		 * name:demo
		 *         1、       2、
		 * value:  test      测试
		 * locale: en_US     zh_CN
		 * 
		 */
		String value = messageResourceService.getTextByCodeNLocale("demo", Locale.ENGLISH.US);
        assertThat(value, Matchers.equalTo("test"));  

		logger.info("success--value:"+value);
	}

	

}
