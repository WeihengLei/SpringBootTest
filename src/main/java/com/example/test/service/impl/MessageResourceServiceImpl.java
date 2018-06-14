/*
 * Copyright (c) 2017, HSBC. All rights reserved.
 */

package com.example.test.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.test.dao.MessageResourceRepository;
import com.example.test.entity.MessageResource;
import com.example.test.service.MessageResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 
 *
 * @author A-team WeiHeng
 * @date 2017-09-28
 */
@Service("messageResourceService")
public class MessageResourceServiceImpl extends AbstractMessageSource implements MessageResourceService, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();

	private Locale defaultLocale = Locale.ENGLISH;

	@Autowired
	private MessageResourceRepository messageResourceRepository;

	@SuppressWarnings("unused")
	private ResourceLoader resourceLoader;

	public void afterPropertiesSet() throws Exception {
		this.reload();
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = (resourceLoader != null ? resourceLoader : new DefaultResourceLoader());
	}

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		String text = getText(code, locale);
		MessageFormat messageFormat = createMessageFormat(text, locale);
		return messageFormat;
	}

	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		String text = getText(code, locale);
		return text;
	}

	public void setDefaultLocale(Locale locale) {
		this.defaultLocale = locale;
	}

	public void reload() {
		this.properties.clear();
		this.properties.putAll(loadTexts());
	}

	public void printAll() {
		this.logger.info("---------- Database Message Resource Service1 [size: " + this.properties.size() + "]----------");
		System.out.println("---------- Database Message Resource Service [size: " + this.properties.size() + "]----------");
		Iterator<String> names = this.properties.keySet().iterator();
		while (names.hasNext()) {
			String name = names.next();
			Map<String, String> messageWithLocale = this.properties.get(name);

			this.logger.info("# " + name + " #");

			for (Map.Entry<String, String> entry : messageWithLocale.entrySet()) {
				String locale = entry.getKey();
				String value = entry.getValue();
				this.logger.info(locale + ": " + value);
			}
		}
		System.out.println("---------- Database Message Resource Service2 [size: " + this.properties.size() + "]----------");

		this.logger.info("---------- Database Message Resource Service [size: " + this.properties.size() + "]----------");
	}

	@Transactional(readOnly = true)
	 List<MessageResource> findAllMessageResource() {
		return this.messageResourceRepository.findAll();
	}

	private Map<String, Map<String, String>> loadTexts() {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		List<MessageResource> messageResources = this.messageResourceRepository.findAll();
		System.out.println("---------- Database Message Resource [messageResources: " + messageResources+"]----------");
		for (MessageResource messageResource : messageResources) {
			String name = messageResource.getName();
			String value = messageResource.getValue();
			String locale = messageResource.getLocale();

			Map<String, String> messageWithLocale = map.get(name);
			if (messageWithLocale == null)
				messageWithLocale = new HashMap<String, String>();
			messageWithLocale.put(locale, value);
			map.put(name, messageWithLocale);
		}
		System.out.println("---------- Database Message Resource [map: " + map+"]----------");

		return map;
	}

	private String getText(String code, Locale locale) {
		Map<String, String> localized = this.properties.get(code);
		String textForCurrentLanguage = null;
		if (localized != null) {
			//textForCurrentLanguage = localized.get(this.defaultLocale.getLanguage() + "_" + this.defaultLocale.getCountry());
			if (textForCurrentLanguage == null)
				textForCurrentLanguage = localized.get(locale.getLanguage() + "_" + locale.getCountry());
			if (textForCurrentLanguage == null)
				textForCurrentLanguage = localized.get(locale.getLanguage());
			if (textForCurrentLanguage == null)
				textForCurrentLanguage = localized.get(this.defaultLocale.getLanguage() + "_" + this.defaultLocale.getCountry());
			if (textForCurrentLanguage == null)
				textForCurrentLanguage = localized.get(this.defaultLocale.getLanguage());
		}
		return textForCurrentLanguage != null ? textForCurrentLanguage : code;
	}

	public String getTextByCodeNLocale(String code, Locale locale) {
		Map<String, String> localized = this.properties.get(code);
		String textForCurrentLanguage = null;
		if (localized != null) {
			if (textForCurrentLanguage == null)
				textForCurrentLanguage = localized.get(locale.getLanguage() + "_" + locale.getCountry());
			if (textForCurrentLanguage == null)
				textForCurrentLanguage = localized.get(locale.getLanguage());
		}
		
		return textForCurrentLanguage != null ? textForCurrentLanguage : code;
	}
	
}
